/* Differential check: verify the web port's data (docs/data.js) matches the Java
   source exactly. Parses Bestiary.java / Armory.java / Story.java and compares every
   boss stat, attack, weapon, and narration line against the JS data. Run this after
   changing gameplay in either version to keep the two in sync.

   Run: node verify-port.js */
"use strict";
const fs = require("fs");
const path = require("path");
const vm = require("vm");

// Load data.js to get the JS-side Data object.
vm.runInThisContext(fs.readFileSync(path.join(__dirname, "docs", "data.js"), "utf8"), { filename: "data.js" });
const Data = globalThis.Data;

const read = (f) => fs.readFileSync(path.join(__dirname, f), "utf8");
let problems = 0;
const fail = (msg) => { problems++; console.error("MISMATCH:", msg); };

// Parse a Java string literal beginning at the next '"' at or after `from`.
// Returns { value, end } where end is the index just past the closing quote.
function parseString(text, from) {
  let i = text.indexOf('"', from);
  if (i < 0) return null;
  i++;
  let out = "";
  while (i < text.length) {
    const c = text[i];
    if (c === "\\") {
      const n = text[i + 1];
      out += n === "n" ? "\n" : n === "t" ? "\t" : n === "r" ? "\r" : n;
      i += 2;
    } else if (c === '"') {
      return { value: out, end: i + 1 };
    } else {
      out += c;
      i++;
    }
  }
  return null;
}

function nextStrings(text, from, count) {
  const vals = [];
  let idx = from;
  for (let k = 0; k < count; k++) {
    const r = parseString(text, idx);
    if (!r) return null;
    vals.push(r.value);
    idx = r.end;
  }
  return { values: vals, end: idx };
}

// ---- Bestiary ----
(function checkBestiary() {
  const java = read("Bestiary.java");
  const names = Object.keys(Data.Bestiary);
  // Find each "static Boss <name>()" block in source order.
  const methodRe = /static\s+Boss\s+(\w+)\s*\(\s*\)\s*\{/g;
  const starts = [];
  let m;
  while ((m = methodRe.exec(java))) starts.push({ name: m[1], idx: m.index });
  for (let s = 0; s < starts.length; s++) {
    const name = starts[s].name;
    const body = java.slice(starts[s].idx, s + 1 < starts.length ? starts[s + 1].idx : java.length);

    if (!Data.Bestiary[name]) { fail(`Bestiary: JS missing boss ${name}`); continue; }
    const js = Data.Bestiary[name]();

    // hp, runes
    const hr = body.match(/new\s+Boss\(\s*(\d+)\s*,\s*(\d+)\s*,/);
    if (!hr) { fail(`Bestiary ${name}: cannot parse hp/runes`); continue; }
    const jhp = +hr[1], jrunes = +hr[2];
    if (jhp !== js.hp) fail(`${name}.hp: java ${jhp} vs js ${js.hp}`);
    if (jrunes !== js.runes) fail(`${name}.runes: java ${jrunes} vs js ${js.runes}`);

    // 3 dialogue strings after hp,runes
    const after = body.indexOf(hr[0]) + hr[0].length;
    const three = nextStrings(body, after, 3);
    if (!three) { fail(`${name}: cannot parse dialogue`); continue; }
    const [winLine, phaseLine, deathLine] = three.values;
    if (winLine !== js.winLine) fail(`${name}.winLine differs`);
    if (phaseLine !== js.phaseLine) fail(`${name}.phaseLine differs`);
    if (deathLine !== js.deathLine) fail(`${name}.deathLine differs`);

    // all atk(...) tuples in order
    const atkRe = /atk\(\s*"((?:[^"\\]|\\.)*)"\s*,\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*\)/g;
    const jatks = [];
    let a;
    while ((a = atkRe.exec(body))) {
      jatks.push([+a[2], +a[3], +a[4], +a[5], +a[6]]); // chargeUp, coolDown, d1, d2, dmg (ignore line text for tuple compare)
    }
    const jsAtks = [];
    for (const combo of js.phase1) for (const at of combo) jsAtks.push([at.chargeUp, at.coolDown, at.dodge[0], at.dodge[1], at.damage]);
    for (const combo of js.phase2) for (const at of combo) jsAtks.push([at.chargeUp, at.coolDown, at.dodge[0], at.dodge[1], at.damage]);
    if (jatks.length !== jsAtks.length) {
      fail(`${name}: attack count java ${jatks.length} vs js ${jsAtks.length}`);
    } else {
      for (let i = 0; i < jatks.length; i++) {
        if (JSON.stringify(jatks[i]) !== JSON.stringify(jsAtks[i])) {
          fail(`${name} attack #${i}: java ${JSON.stringify(jatks[i])} vs js ${JSON.stringify(jsAtks[i])}`);
        }
      }
    }
    // also compare attack line texts in order
    const atkLineRe = /atk\(\s*"((?:[^"\\]|\\.)*)"/g;
    const jLines = [];
    while ((a = atkLineRe.exec(body))) jLines.push(a[1].replace(/\\n/g, "\n").replace(/\\t/g, "\t").replace(/\\"/g, '"').replace(/\\\\/g, "\\"));
    const jsLines = [];
    for (const combo of js.phase1) for (const at of combo) jsLines.push(at.line);
    for (const combo of js.phase2) for (const at of combo) jsLines.push(at.line);
    for (let i = 0; i < Math.min(jLines.length, jsLines.length); i++) {
      if (jLines[i] !== jsLines[i]) fail(`${name} attack-line #${i} text differs:\n  java=${JSON.stringify(jLines[i])}\n  js  =${JSON.stringify(jsLines[i])}`);
    }
  }
  console.log(`Bestiary: checked ${starts.length} bosses`);
})();

// ---- Armory ----
(function checkArmory() {
  const java = read("Armory.java");
  for (let t = 1; t <= 6; t++) {
    const jsTier = Data.Armory["tier" + t]();
    const blockRe = new RegExp("static\\s+List<Weapon>\\s+tier" + t + "\\(\\)\\s*\\{([\\s\\S]*?)\\n\\s*\\}", "m");
    const bm = java.match(blockRe);
    if (!bm) { fail(`Armory: cannot find tier${t}`); continue; }
    const block = bm[1];
    const wRe = /new\s+Weapon\(\s*"((?:[^"\\]|\\.)*)"\s*,\s*"((?:[^"\\]|\\.)*)"\s*,\s*"((?:[^"\\]|\\.)*)"\s*,\s*"((?:[^"\\]|\\.)*)"\s*,\s*new\s+double\[\]\{([^}]*)\}\s*,\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*\)/g;
    const jw = [];
    let w;
    while ((w = wRe.exec(block))) {
      jw.push({
        name: w[1], light: w[2], heavy: w[3], special: w[4],
        scaling: w[5].split(",").map((x) => parseFloat(x.trim())),
        price: +w[6], damage: +w[7], time: +w[8],
      });
    }
    if (jw.length !== jsTier.length) { fail(`Armory tier${t}: count java ${jw.length} vs js ${jsTier.length}`); continue; }
    for (let i = 0; i < jw.length; i++) {
      const A = jw[i], B = jsTier[i];
      for (const k of ["name", "light", "heavy", "special", "price", "damage", "time"]) {
        if (A[k] !== B[k]) fail(`Armory tier${t}[${i}].${k}: java ${JSON.stringify(A[k])} vs js ${JSON.stringify(B[k])}`);
      }
      if (JSON.stringify(A.scaling) !== JSON.stringify(B.scaling)) fail(`Armory tier${t}[${i}].scaling: java ${JSON.stringify(A.scaling)} vs js ${JSON.stringify(B.scaling)}`);
    }
  }
  console.log("Armory: checked 6 tiers");
})();

// ---- Story ----
(function checkStory() {
  const java = read("Story.java");
  const keys = Object.keys(Data.Story);
  let checked = 0;
  for (const key of keys) {
    const declRe = new RegExp("List<String>\\s+" + key + "\\s*=\\s*List\\.of\\(");
    const dm = java.match(declRe);
    if (!dm) { fail(`Story: java missing ${key}`); continue; }
    const start = java.indexOf(dm[0]) + dm[0].length;
    // capture until the matching ");"
    const end = java.indexOf(");", start);
    const segment = java.slice(start, end);
    const strs = [];
    let idx = 0;
    while (true) {
      const r = parseString(segment, idx);
      if (!r) break;
      strs.push(r.value);
      idx = r.end;
    }
    const jsArr = Data.Story[key];
    if (strs.length !== jsArr.length) { fail(`Story ${key}: count java ${strs.length} vs js ${jsArr.length}`); continue; }
    for (let i = 0; i < strs.length; i++) {
      if (strs[i] !== jsArr[i]) fail(`Story ${key}[${i}] differs:\n  java=${JSON.stringify(strs[i])}\n  js  =${JSON.stringify(jsArr[i])}`);
    }
    checked++;
  }
  console.log(`Story: checked ${checked} scenes`);
})();

console.log(problems === 0 ? "\nFIDELITY OK — JS data matches Java exactly." : `\n${problems} mismatch(es) found.`);
process.exit(problems ? 1 : 0);
