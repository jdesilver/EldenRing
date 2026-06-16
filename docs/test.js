/*
 * Headless tests for the browser port. Run with:  node web/test.js
 *
 * 1. Fuzz: drive the whole game with random input lines across several seeds and
 *    assert it never throws (mirrors the Java fuzzing done on the console version).
 * 2. Win path: drive a real fight to victory through the public Combat class and
 *    assert the WIN result, exercising attack/dodge/phase-change/kill code.
 */
"use strict";
const fs = require("fs");
const path = require("path");
const vm = require("vm");

// Load data.js then game.js into this global context (both attach to globalThis).
for (const f of ["data.js", "game.js"]) {
  const code = fs.readFileSync(path.join(__dirname, f), "utf8");
  vm.runInThisContext(code, { filename: f });
}
const Game = globalThis.Game;

// Seeded PRNG so runs are reproducible; patch Math.random for boss combo picks.
function mulberry32(seed) {
  return function () {
    seed |= 0; seed = (seed + 0x6d2b79f5) | 0;
    let t = Math.imul(seed ^ (seed >>> 15), 1 | seed);
    t = (t + Math.imul(t ^ (t >>> 7), 61 | t)) ^ t;
    return ((t ^ (t >>> 14)) >>> 0) / 4294967296;
  };
}

const STOP = Symbol("stop");

function fuzzIo(rng, maxReads) {
  // Bias toward valid-ish tokens but include garbage, exactly like a fuzzer.
  const tokens = ["", "1", "2", "3", "4", "-1", "0", "5", "10", "50", "Y", "N", "y", "n", "abc", "  3 ", "99", "2147483648", "-2147483649", "999999"];
  let reads = 0;
  return {
    print() {}, println() {}, clear() {}, setMode() {},
    readLine() {
      if (++reads > maxReads) throw STOP;
      return Promise.resolve(tokens[Math.floor(rng() * tokens.length)]);
    },
  };
}

async function fuzzOnce(seed, maxReads) {
  const rng = mulberry32(seed);
  const realRandom = Math.random;
  Math.random = rng;
  Game.Console.io = fuzzIo(rng, maxReads);
  try {
    await Game.run();
    return "completed";
  } catch (e) {
    if (e === STOP) return "stopped";
    throw e; // a real porting bug
  } finally {
    Math.random = realRandom;
  }
}

// A scripted backend that answers based on what the current screen says.
function scriptIo(decide) {
  let screen = "";
  return {
    print(t) { screen += t; },
    println(t) { screen += t + "\n"; },
    clear() { screen = ""; },
    setMode() {},
    readLine() { return Promise.resolve(String(decide(screen))); },
  };
}

async function fightOnce(seed) {
  // Build a real boss and an unkillable, low-damage player, then grind the fight.
  const rng = mulberry32(seed);
  const realRandom = Math.random;
  Math.random = rng;
  try {
    const boss = Game.makeBoss(globalThis.Data.Bestiary.margit());
    const player = new Game.Player("Tester");
    player.hp = 1000000;           // survive any hit so we can grind it down
    player.fp = 1000000;
    player.healingTotal = 99;
    // Bare fists (50 damage, no scaling) so we chip the boss down in small steps,
    // crossing the half-HP phase gate and killing it in phase 2.
    player.hand = Game.fist();

    Game.Console.io = scriptIo((screen) => {
      if (/Choose an action/.test(screen)) return "1"; // Attack
      if (/Choose an attack/.test(screen)) return "1"; // Light
      return "";                                       // advance every "press Enter"
    });

    const won = await new Game.Combat(player, boss).start();
    return { won, hp: boss.hp, phase: boss.phase };
  } finally {
    Math.random = realRandom;
  }
}

async function winPath() {
  // The fight is winnable, but landing the kill during a boss cool-down window is
  // faithfully scored as a death (the original engine only checks for victory during
  // charge-up). So we assert that victory is reachable across seeds and verify the
  // win state, rather than assuming every seed wins.
  let wins = 0;
  for (let seed = 1; seed <= 40; seed++) {
    const r = await fightOnce(seed);
    if (r.won) {
      wins++;
      if (r.hp > 0) throw new Error("winPath: won but boss still has HP " + r.hp);
      if (r.phase !== 2) throw new Error("winPath: won but not in phase 2 (got " + r.phase + ")");
    }
  }
  if (wins === 0) throw new Error("winPath: no victory across 40 seeds");
  return `victory reachable (${wins}/40 seeds won; the rest hit the cool-down kill quirk)`;
}

(async () => {
  let failures = 0;

  for (const seed of [1, 7, 42, 1234, 99999, 2026]) {
    try {
      const result = await fuzzOnce(seed, 6000);
      console.log(`fuzz seed ${seed}: ${result} with no crash`);
    } catch (e) {
      failures++;
      console.error(`fuzz seed ${seed}: CRASHED ->`, e && e.stack ? e.stack : e);
    }
  }

  try {
    console.log("win path:", await winPath());
  } catch (e) {
    failures++;
    console.error("win path FAILED ->", e && e.stack ? e.stack : e);
  }

  if (failures) {
    console.error(`\n${failures} test(s) failed.`);
    process.exit(1);
  }
  console.log("\nAll tests passed.");
})();
