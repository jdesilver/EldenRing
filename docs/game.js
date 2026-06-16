/*
 * Browser port of the Elden Ring text RPG.
 *
 * The game logic below is a faithful, line-by-line translation of the Java sources
 * (Main, Combat, Player, Weapon, Boss, Console). The only structural change is that
 * every place the Java code blocked on Scanner input becomes an `await` on a Promise
 * that resolves when the player submits a line in the browser. Keeping the structure
 * identical preserves the exact prompts, narration and combat math - including the
 * intentional gameplay quirks documented in the original:
 *   - Site-of-Grace weapon upgrades check runes but never spend them.
 *   - Each Level-Up adds Vigor*30 / Mind*30 to current HP/FP, so they inflate per visit.
 *   - The Level-Up "undo" rebinds the working stat array (an aliasing quirk that makes
 *     the 99-cap check and the end-of-level HP/FP bump read the player's live array).
 *
 * Plain script (no ES modules) so the page also runs when opened directly from disk.
 */
(function (global) {
  "use strict";

  const D = global.Data;
  const { STATS, STRENGTH_INDEX, ARCANE_INDEX, VIGOR_INDEX, MIND_INDEX, DIRECTIONS, Armory, Bestiary, Story } = D;

  const trunc = Math.trunc;
  function randInt(n) { return Math.floor(Math.random() * n); }
  function statsToString(arr) { return "[" + arr.join(", ") + "]"; }

  // ===== Console (Console.java) =====
  // All I/O funnels through a swappable `io` backend (DOM in the browser, a script in tests).

  const Console = {
    INVALID: -2147483648, // Integer.MIN_VALUE; no menu maps to this value
    io: null,

    async speak(text) {
      this.io.println(text);
      this.io.setMode("continue");
      await this.io.readLine();
      this.clear();
    },
    async narrate(beats) {
      for (const beat of beats) {
        await this.speak(beat);
      }
    },
    clear() { this.io.clear(); },
    print(text) { this.io.print(text); },
    println(text) { this.io.println(text); },

    async readInt() {
      this.io.setMode("input");
      const line = String(await this.io.readLine()).trim();
      if (/^[+-]?\d+$/.test(line)) {
        const n = Number(line);
        if (n >= -2147483648 && n <= 2147483647) return n; // mirrors Integer.parseInt range
      }
      return this.INVALID;
    },
    async readLine() {
      this.io.setMode("input");
      return await this.io.readLine();
    },
  };

  // ===== Weapon (Weapon.java) =====

  const WEAPON_TIERS = [
    { damageBonus: 25, scaleFactor: 1.25, nextUpgradeCost: 35, suffix: " +1" },
    { damageBonus: 50, scaleFactor: 1.5, nextUpgradeCost: 85, suffix: " +2" },
    { damageBonus: 100, scaleFactor: 1.75, nextUpgradeCost: 185, suffix: " +3" },
    { damageBonus: 200, scaleFactor: 2.0, nextUpgradeCost: 0, suffix: " +4" },
  ];

  class Weapon {
    constructor(spec) {
      this.name = spec.name;
      this.light = spec.light;
      this.heavy = spec.heavy;
      this.special = spec.special;
      this.scaling = spec.scaling.slice(); // copy so upgrades never mutate shared data
      this.price = spec.price;
      this.damage = spec.damage;
      this.time = spec.time;
      this.level = 0;
      this.upgradePrice = 10;
    }

    // Damage against the player: base damage plus stat scaling, scaled by 20.
    getNewDamage(player) {
      const stats = player.getFightStats();
      let sum = 0.0;
      for (let i = 0; i < this.scaling.length; i++) {
        sum += this.scaling[i] * stats[i];
      }
      return trunc(sum * 20 + this.damage);
    }

    upgrade() {
      if (this.level >= WEAPON_TIERS.length) {
        Console.println("Maximum Level already achieved.\n");
        return;
      }
      const tier = WEAPON_TIERS[this.level];
      this.damage += tier.damageBonus;
      for (let i = 0; i < this.scaling.length; i++) {
        this.scaling[i] *= tier.scaleFactor;
      }
      this.name += tier.suffix;
      this.upgradePrice = tier.nextUpgradeCost;
      this.level++;
    }
  }

  function fist() {
    return new Weapon({ name: "Fist", light: "Punch", heavy: "Slam", special: "Martial Arts", scaling: [0, 0, 0, 0, 0], price: 0, damage: 50, time: 1 });
  }
  function weaponsFrom(specs) { return specs.map((s) => new Weapon(s)); }

  // ===== Player (Player.java) =====

  class Player {
    constructor(name) {
      this.name = name;
      this.hand = fist();
      this.hp = 300;
      this.fp = 200;
      this.stamina = 0;
      this.stats = new Array(STATS.length).fill(0);
      this.runes = 15;
      this.healingTotal = 2;
    }

    async attack(boss, type) {
      const base = this.hand.getNewDamage(this);
      switch (type) {
        case 1:
          Console.clear();
          await Console.speak("You use " + this.hand.light + "!");
          await Console.speak("You hit for " + boss.loseHp(base) + " hp!");
          return this.hand.time - trunc(this.stamina / 10);
        case 2:
          Console.clear();
          await Console.speak("You use " + this.hand.heavy + "!");
          await Console.speak("You hit for " + boss.loseHp(base * 2) + " hp!");
          return this.hand.time * 2 - trunc(this.stamina / 10);
        case 3:
          Console.clear();
          await Console.speak("You use " + this.hand.special + "!");
          await Console.speak("You hit for " + boss.loseHp(base * 2) + " hp!");
          return this.hand.time - trunc(this.stamina / 10);
      }
      return -1;
    }

    async dodge(menuChoice) {
      if (!(menuChoice >= 1 && menuChoice <= DIRECTIONS.length)) {
        return null;
      }
      const direction = menuChoice - 1; // 0-based direction index
      Console.clear();
      await Console.speak("Dodged " + DIRECTIONS[direction] + "!");
      return direction;
    }

    heal(healHp, cap) {
      if (healHp) {
        this.hp = Math.min(this.hp + 50, cap);
      } else {
        this.fp = Math.min(this.fp + 50, cap);
      }
      return 2;
    }

    getFightStats() {
      return this.stats.slice(STRENGTH_INDEX, ARCANE_INDEX + 1);
    }
  }

  // ===== Boss (Boss.java) =====

  class Boss {
    constructor(spec) {
      this.originalHp = spec.hp;
      this.hp = spec.hp;
      this.runes = spec.runes;
      this.winLine = spec.winLine;
      this.phaseLine = spec.phaseLine;
      this.deathLine = spec.deathLine;
      this.phase1Combos = spec.phase1;
      this.phase2Combos = spec.phase2;
      this.phase = 1;
    }

    setPhase(p) { this.phase = p; }

    loseHp(amount) {
      this.hp -= amount;
      return amount;
    }

    nextCombo() {
      let combos = this.phase === 1 ? this.phase1Combos : this.phase2Combos;
      if (!combos || combos.length === 0) {
        combos = this.phase1Combos; // defensive: never draw from an empty list
      }
      return combos[randInt(combos.length)];
    }

    async checkPhase() {
      if (this.hp <= trunc(this.originalHp / 2) && this.phase === 1) {
        await Console.speak(this.phaseLine);
        return true;
      }
      return false;
    }
  }

  function makeBoss(spec) { return new Boss(spec); }

  // ===== Combat (Combat.java) =====

  const DODGE_COST = 2;
  const LOSS = 0;
  const CONTINUE = 1;
  const WIN = 2;

  class Combat {
    constructor(player, boss) {
      this.player = player;
      this.boss = boss;
      this.timeTaken = 0;
    }

    async start() {
      const player = this.player;
      const boss = this.boss;
      const topHp = player.hp;
      const topFp = player.fp;

      while (player.hp > 0 && boss.hp > 0) {
        const outcome = await this.bossTurn(topHp, topFp);
        if (outcome === LOSS) return false;
        if (outcome === WIN) return true;
        // CONTINUE: take another turn
      }
      await Console.speak("You died");
      return false;
    }

    async bossTurn(topHp, topFp) {
      const player = this.player;
      const boss = this.boss;
      let phaseChange = false;

      while (player.hp > 0 && boss.hp > 0) {
        const combo = boss.nextCombo();
        for (const attack of combo) {
          // Charge-up: the player acts while the attack winds up.
          let timeLeft = attack.chargeUp;
          while (timeLeft > 0) {
            Console.println(attack.line);
            const dodge = await this.playerAction(topHp, topFp);

            if (await boss.checkPhase()) {
              boss.setPhase(2);
              phaseChange = true;
              break;
            }
            if (boss.hp <= 0) {
              await Console.speak(boss.deathLine);
              await Console.speak("Foe Slain");
              return WIN;
            }
            if (dodge !== null) {
              if (timeLeft <= DODGE_COST && attack.dodge.includes(dodge)) {
                await Console.speak("Successfully dodged attack!");
                timeLeft = 0;
                break;
              }
              timeLeft -= DODGE_COST;
            }

            timeLeft -= this.timeTaken;
            this.timeTaken = 0;

            if (timeLeft <= 0) {
              await Console.speak("You were hit!");
              player.hp -= attack.damage;
              if (player.hp <= 0) {
                await Console.speak(boss.winLine);
                await Console.speak("You died");
                return LOSS;
              }
            }
          }

          // Cool-down: the boss is vulnerable and the player can act freely.
          timeLeft = attack.coolDown;
          while (timeLeft > 0) {
            await this.playerAction(topHp, topFp);
            if (await boss.checkPhase()) {
              boss.setPhase(2);
              phaseChange = true;
              break;
            }
            timeLeft -= this.timeTaken;
            this.timeTaken = 0;
          }

          if (phaseChange) break;
        }
        return CONTINUE;
      }
      return CONTINUE;
    }

    async playerAction(topHp, topFp) {
      const player = this.player;
      const boss = this.boss;

      Console.println("Health: " + player.hp);
      Console.println("Focus: " + player.fp);
      Console.println("");
      Console.println("Boss Health: " + boss.hp);
      Console.println("Total Heals: " + player.healingTotal);
      Console.println("");
      Console.println("Choose an action:\n1) Attack\n2) Dodge\n3) Heal\n4) Wait\n");

      switch (await Console.readInt()) {
        case 1: {
          Console.clear();
          Console.println("Choose an attack:\n1) Light\n2) Heavy\n3) Special\n");
          switch (await Console.readInt()) {
            case 1: this.timeTaken = await player.attack(boss, 1); break;
            case 2: this.timeTaken = await player.attack(boss, 2); break;
            case 3:
              if (player.fp < 50) {
                await Console.speak("Not enough Focus!");
                this.timeTaken = 2;
              } else {
                player.fp -= 50;
                this.timeTaken = await player.attack(boss, 3);
              }
              break;
            default: await Console.speak("Invalid action. Try again.\n");
          }
          break;
        }
        case 2: {
          Console.clear();
          Console.println("Choose a direction:\n1) Forward\n2) Backward\n3) Right\n4) Left\n");
          switch (await Console.readInt()) {
            case 1: return await player.dodge(1);
            case 2: return await player.dodge(2);
            case 3: return await player.dodge(3);
            case 4: return await player.dodge(4);
            default: await Console.speak("Invalid action. Try again.\n");
          }
          break;
        }
        case 3: {
          Console.clear();
          if (player.healingTotal === 0) {
            await Console.speak("Out of heals!");
            this.timeTaken = 2;
          } else {
            Console.println("What are you healing?\n1) Hp\n2) Fp\n");
            switch (await Console.readInt()) {
              case 1:
                player.healingTotal -= 1;
                this.timeTaken = player.heal(true, topHp);
                break;
              case 2:
                player.healingTotal -= 1;
                this.timeTaken = player.heal(false, topFp);
                break;
              default: await Console.speak("Invalid action. Try again.\n");
            }
          }
          break;
        }
        case 4: {
          Console.clear();
          Console.println("How Long?\n");
          const wait = await Console.readInt();
          if (wait < 0) {
            await Console.speak("Invalid action. Try again.\n");
          } else {
            this.timeTaken = wait;
          }
          break;
        }
        default: await Console.speak("Invalid action. Try again.\n");
      }
      return null;
    }
  }

  // ===== Game script (Main.java) =====

  async function confirm() {
    while (true) {
      Console.println("Are you sure? (Y or N)\n");
      const answer = (await Console.readLine()).trim().toUpperCase();
      if (answer === "Y") return true;
      if (answer === "N") return false;
      await Console.speak("Invalid input. Please enter Y or N.\n");
    }
  }

  // Buy a weapon from the wheel (or back out with -1). Buying refunds the current
  // weapon's price and swaps the current weapon into the chosen wheel slot.
  async function buyWeapon(player, wheel) {
    Console.println("Runes: " + player.runes + "\n");
    Console.println("Choose a Weapon: (-1 to back out)\n");
    for (let i = 0; i < wheel.length; i++) {
      Console.println((i + 1) + ". " + wheel[i].name + " - Price: " + wheel[i].price + " Runes");
    }
    Console.println("");

    while (true) {
      let choice = await Console.readInt();
      if (choice === Console.INVALID) {
        await Console.speak("Invalid input. Please enter a valid number.\n");
        continue;
      }
      if (choice === -1) {
        return player.hand;
      }
      choice--;
      if (choice < 0 || choice >= wheel.length) {
        await Console.speak("Invalid choice. Please select a valid weapon.\n");
        continue;
      }
      if (player.runes < wheel[choice].price) {
        Console.println("Not enough runes. Choose a different weapon.\n");
        continue;
      }
      if (!(await confirm())) {
        continue;
      }
      player.runes -= wheel[choice].price;
      player.runes += player.hand.price;
      const chosen = wheel[choice];
      wheel[choice] = player.hand;
      return chosen;
    }
  }

  function printStatRoster() {
    Console.println("The Stats: " + STATS.join(", "));
    Console.println("");
  }

  // Reads how many runes to put into a stat (a non-negative amount that neither pushes
  // the stat past 99 nor exceeds available runes), or -1 to undo.
  async function promptStatPoints(runes, currentStat) {
    while (true) {
      const amount = await Console.readInt();
      if (amount === Console.INVALID) {
        await Console.speak("Invalid input. Please enter a number.");
      } else if (amount + currentStat > 99) {
        Console.println("Cannot go over 99.");
      } else if (amount > runes) {
        Console.println("Not enough runes.");
      } else if (amount < -1) {
        Console.println("Has to be positive or -1 to undo.");
      } else {
        return amount;
      }
    }
  }

  // Spends runes across the eight stats one at a time, with -1 to undo. The local
  // `stats` starts as the player's live array; an undo rebinds it to a saved copy,
  // which is the original aliasing quirk (the 99-cap check and the HP/FP bump below
  // read player.stats, not the local working array).
  async function levelUp(player, stats) {
    const history = [];
    let currentStatIndex = 0;

    while (true) {
      while (currentStatIndex < STATS.length) {
        Console.clear();
        printStatRoster();
        Console.println("Current Stats: " + statsToString(stats));
        Console.println("Runes remaining: " + player.runes);
        Console.println("");
        Console.print("Points into " + STATS[currentStatIndex] + " (or enter -1 to undo): ");

        const runesSpent = await promptStatPoints(player.runes, player.stats[currentStatIndex]);
        if (runesSpent === -1) {
          if (history.length === 0) {
            Console.println("No actions to undo.");
            continue;
          }
          const last = history.pop();
          stats = last.stats;
          player.runes += last.runesSpent;
          Console.println("Undo successful.");
          if (currentStatIndex > 0) {
            currentStatIndex--;
          }
          continue;
        }

        history.push({ stats: stats.slice(), runesSpent });
        stats[currentStatIndex] += runesSpent;
        player.runes -= runesSpent;
        Console.println("Runes remaining: " + player.runes);
        Console.println("");
        currentStatIndex++;
      }

      Console.clear();
      printStatRoster();
      Console.println("Current Stats: " + statsToString(stats));
      Console.println("");
      if (await confirm()) {
        break;
      }
      currentStatIndex = STATS.length - 1; // step back to revisit the last stat
    }

    player.hp += player.stats[VIGOR_INDEX] * 30;
    player.fp += player.stats[MIND_INDEX] * 30;
    return stats;
  }

  // The hub between fights: buy a weapon, level up, upgrade the current weapon, or leave.
  async function siteOfGrace(player, wheel) {
    while (true) {
      Console.clear();
      Console.println("What dost thou wish to do?\n1) Purchase New Weapon\n2) Level Up\n3) Upgrade Weapon\n4) Leave\n");
      switch (await Console.readInt()) {
        case 1: player.hand = await buyWeapon(player, wheel); break;
        case 2: player.stats = await levelUp(player, player.stats); break;
        case 3: {
          Console.print("This will cost you " + player.hand.upgradePrice + " runes. ");
          if (await confirm()) {
            if (player.runes < player.hand.upgradePrice) {
              Console.println("Not enough runes.");
            } else {
              player.hand.upgrade(); // note: faithfully does NOT spend runes
            }
          }
          break;
        }
        case 4:
          Console.clear();
          return;
        default: await Console.speak("Invalid action. Try again.\n");
      }
    }
  }

  // Fights a boss, restoring HP/FP/heals afterward. On defeat the player may rest at a
  // Site of Grace and try again; on victory they collect runes and gain a heal.
  async function fight(player, boss, wheel) {
    const normalHp = player.hp;
    const normalFp = player.fp;
    const heals = player.healingTotal;
    const bossHp = boss.hp;
    const combat = new Combat(player, boss);

    while (true) {
      if (!(await combat.start())) {
        player.hp = normalHp;
        player.fp = normalFp;
        player.healingTotal = heals;
        boss.hp = bossHp;
        boss.setPhase(1);
        if (player.runes > 0) {
          await siteOfGrace(player, wheel);
        }
        continue;
      }
      player.hp = normalHp;
      player.fp = normalFp;
      player.healingTotal = heals;
      player.runes += boss.runes;
      player.healingTotal += 1;
      return;
    }
  }

  // The decision at the Erdtree. Returns true to become Elden Lord (Melina is sacrificed).
  async function chooseEnding() {
    Console.clear();
    while (true) {
      Console.println("Decide.\n1) Let Melina fullfill her mission.\n2) Let chaos take the world.");
      const answer = await Console.readInt();
      if (answer === 1) {
        await Console.narrate(Story.ENDING_LET_MELINA);
        return true;
      } else if (answer === 2) {
        await Console.narrate(Story.ENDING_TAKE_HER_PLACE);
        return false;
      } else {
        Console.clear();
        await Console.narrate(Story.DECISION_RETRY);
      }
    }
  }

  // The full game script (Main.run).
  async function run() {
    Console.clear();
    await Console.narrate(Story.INTRO);

    Console.print("What is thy name? ");
    const player = new Player(await Console.readLine());

    Console.clear();
    await Console.narrate(Story.TUTORIAL);

    let wheel = weaponsFrom(Armory.tier1());
    player.hand = await buyWeapon(player, wheel);
    player.stats = await levelUp(player, player.stats);

    Console.clear();
    await Console.narrate(Story.CREATION_COMPLETE);
    await Console.speak("Your character, " + player.name + ", is ready to begin the journey.");
    await Console.narrate(Story.JOURNEY_TO_MARGIT);

    await fight(player, makeBoss(Bestiary.margit()), wheel);
    await Console.narrate(Story.AFTER_MARGIT);

    wheel = weaponsFrom(Armory.tier2());
    await siteOfGrace(player, wheel);
    await Console.narrate(Story.BEFORE_GODRICK);
    await fight(player, makeBoss(Bestiary.godrick()), wheel);
    await Console.narrate(Story.AFTER_GODRICK);

    wheel = weaponsFrom(Armory.tier3());
    await siteOfGrace(player, wheel);
    await Console.narrate(Story.BEFORE_RENNALA);
    await fight(player, makeBoss(Bestiary.rennala()), wheel);
    await Console.narrate(Story.AFTER_RENNALA);

    await siteOfGrace(player, wheel);
    await Console.narrate(Story.BEFORE_RED_WOLF);
    await fight(player, makeBoss(Bestiary.redWolf()), wheel);
    await Console.narrate(Story.AFTER_RED_WOLF);

    await siteOfGrace(player, wheel);
    await Console.narrate(Story.BEFORE_SERPENT);
    await fight(player, makeBoss(Bestiary.serpent()), wheel);
    await Console.narrate(Story.AFTER_SERPENT);

    wheel = weaponsFrom(Armory.tier4());
    await siteOfGrace(player, wheel);
    await Console.narrate(Story.BEFORE_MOHG);
    await fight(player, makeBoss(Bestiary.mohg()), wheel);
    await Console.narrate(Story.AFTER_MOHG);

    await siteOfGrace(player, wheel);
    await Console.narrate(Story.BEFORE_MORGOTT);
    await fight(player, makeBoss(Bestiary.morgott()), wheel);
    await Console.narrate(Story.AFTER_MORGOTT);

    await siteOfGrace(player, wheel);
    await Console.narrate(Story.BEFORE_FIRE_GIANT);
    await fight(player, makeBoss(Bestiary.fireGiant()), wheel);
    await Console.narrate(Story.AFTER_FIRE_GIANT);

    await siteOfGrace(player, wheel);
    await Console.narrate(Story.ERDTREE_DECISION);
    const eldenLord = await chooseEnding();

    await Console.narrate(Story.BEFORE_BEAST_CLERGYMAN);
    await fight(player, makeBoss(Bestiary.beastClergyman()), wheel);
    await Console.narrate(Story.AFTER_BEAST_CLERGYMAN);

    wheel = weaponsFrom(Armory.tier5());
    await siteOfGrace(player, wheel);
    await Console.narrate(Story.BEFORE_GIDEON);
    await fight(player, makeBoss(Bestiary.gideon()), wheel);
    await Console.narrate(Story.AFTER_GIDEON);

    await siteOfGrace(player, wheel);
    await Console.narrate(Story.BEFORE_GODFREY);
    await fight(player, makeBoss(Bestiary.godfrey()), wheel);
    await Console.narrate(Story.AFTER_GODFREY);

    wheel = weaponsFrom(Armory.tier6());
    await siteOfGrace(player, wheel);
    await Console.narrate(Story.BEFORE_MALENIA);
    await fight(player, makeBoss(Bestiary.malenia()), wheel);
    await Console.narrate(Story.AFTER_MALENIA);

    await siteOfGrace(player, wheel);
    await Console.narrate(Story.BEFORE_RADAGON);
    await fight(player, makeBoss(Bestiary.radagon()), wheel);
    await Console.narrate(Story.AFTER_RADAGON);

    if (eldenLord) {
      await Console.narrate(Story.EPILOGUE_ELDEN_LORD);
      return;
    }
    await Console.narrate(Story.EPILOGUE_CHAOS);
  }

  // ===== DOM front-end =====

  // Builds an I/O backend bound to the page. Tracks the current "screen" of text
  // (cleared by Console.clear, as the original ANSI clear did), resolves input
  // promises when the player submits a line, and renders accessible quick-action
  // buttons inferred from the current screen so the game is fully playable by
  // keyboard, mouse or touch.
  function createDomIo(els) {
    let screen = "";
    let pending = null;
    let mode = "input";

    function render() {
      els.screen.textContent = screen;
      els.screen.scrollTop = els.screen.scrollHeight;
    }

    function clamp(label) {
      label = label.replace(/\.+$/, "").trim();
      return label.length > 42 ? label.slice(0, 41) + "…" : label;
    }

    function quickButtons() {
      const out = [];
      if (mode === "continue") {
        out.push({ label: "Continue ▶", value: "" });
        return out;
      }
      for (const line of screen.split("\n")) {
        const m = line.match(/^\s*(-?\d+)[\).]\s+(.*\S)/);
        if (m && m[1] !== "-1") {
          out.push({ label: m[1] + " · " + clamp(m[2]), value: m[1] });
        }
      }
      if (/\(Y or N\)/.test(screen)) {
        out.push({ label: "Yes", value: "Y" });
        out.push({ label: "No", value: "N" });
      }
      if (/-1 to undo/.test(screen)) {
        out.push({ label: "Undo (-1)", value: "-1" });
      } else if (/-1 to back out/.test(screen)) {
        out.push({ label: "Back (-1)", value: "-1" });
      }
      return out;
    }

    function rebuildControls() {
      els.prompt.textContent = mode === "continue"
        ? "Press Enter (or select Continue) to advance."
        : "Type your choice, then press Enter.";
      els.quick.innerHTML = "";
      for (const b of quickButtons()) {
        const btn = document.createElement("button");
        btn.type = "button";
        btn.className = "quick-btn";
        btn.textContent = b.label;
        btn.addEventListener("click", () => {
          els.cmd.value = "";
          submit(b.value);
        });
        els.quick.appendChild(btn);
      }
      // Keep keyboard focus on the input so typing always works.
      if (document.activeElement !== els.cmd) {
        els.cmd.focus();
      }
    }

    function submit(value) {
      if (pending) {
        const resolve = pending;
        pending = null;
        resolve(value);
      }
    }

    return {
      print(text) { screen += text; render(); },
      println(text) { screen += text + "\n"; render(); },
      clear() { screen = ""; render(); },
      setMode(m) { mode = m; rebuildControls(); },
      readLine() { return new Promise((resolve) => { pending = resolve; }); },
      submit,
    };
  }

  function bootDom() {
    const els = {
      screen: document.getElementById("screen"),
      prompt: document.getElementById("prompt"),
      quick: document.getElementById("quick"),
      cmd: document.getElementById("cmd"),
      form: document.getElementById("input-form"),
      start: document.getElementById("start"),
      beginBtn: document.getElementById("begin"),
      restartBtn: document.getElementById("restart"),
      play: document.getElementById("play"),
    };

    const io = createDomIo(els);
    Console.io = io;

    els.form.addEventListener("submit", (e) => {
      e.preventDefault();
      const value = els.cmd.value;
      els.cmd.value = "";
      io.submit(value);
    });

    async function startGame() {
      els.start.hidden = true;
      els.play.hidden = false;
      els.restartBtn.hidden = false;
      els.cmd.focus();
      try {
        await run();
        io.setMode("continue");
        Console.println("");
        Console.println("— THE END —");
        Console.println("");
        Console.println("Select Restart above to play again.");
      } catch (err) {
        Console.println("");
        Console.println("An unforeseen calamity befalls the Lands Between. The journey ends here.");
        // eslint-disable-next-line no-console
        if (global.console && global.console.error) global.console.error(err);
      }
    }

    els.beginBtn.addEventListener("click", startGame);
    els.restartBtn.addEventListener("click", () => {
      io.clear();
      startGame();
    });
  }

  if (typeof document !== "undefined") {
    if (document.readyState === "loading") {
      document.addEventListener("DOMContentLoaded", bootDom);
    } else {
      bootDom();
    }
  }

  // Expose internals for headless tests (Node).
  global.Game = { Console, Weapon, Player, Boss, Combat, run, fist, weaponsFrom, makeBoss };
})(typeof window !== "undefined" ? window : globalThis);
