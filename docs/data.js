/*
 * Game data for the browser port of Elden Ring (text RPG).
 *
 * This is a verbatim translation of the Java data classes Story.java, Armory.java,
 * Bestiary.java, Stat.java and Direction.java. The prose and the combat numbers are
 * copied exactly so the web version plays identically to the console original.
 *
 * Everything is hung off the global `Data` object (no modules, so the page also works
 * when opened directly from disk).
 */
(function (global) {
  "use strict";

  // ----- Stats & directions (Stat.java / Direction.java) -----

  // The eight character stats, in display/storage order. The final five (Strength..Arcane)
  // are the "fight stats" that drive weapon damage scaling.
  const STATS = [
    "Vigor", "Mind", "Endurance",
    "Strength", "Dexterity", "Intelligence", "Faith", "Arcane",
  ];
  const STRENGTH_INDEX = 3; // first fight stat
  const ARCANE_INDEX = 7;   // last fight stat
  const VIGOR_INDEX = 0;
  const MIND_INDEX = 1;

  // The four dodge directions; the in-combat menu numbers them 1-4 and boss attack
  // data stores dodgeable directions by the matching 0-based index.
  const DIRECTIONS = ["Forward", "Backward", "Right", "Left"];

  // ----- Weapons (Armory.java) -----
  // Each weapon: {name, light, heavy, special, scaling:[str,dex,int,fai,arc], price, damage, time}

  function w(name, light, heavy, special, scaling, price, damage, time) {
    return { name, light, heavy, special, scaling, price, damage, time };
  }

  const Armory = {
    tier1() {
      return [
        w("Greatsword, scales with Strength primarily and Dexterity secondarily", "Swing", "Slice", "Lion's Claw", [2, 0.5, 0, 0, 0], 0, 400, 4),
        w("Urumi, scales with Dexterity primarily and Strength secondarily", "Whip", "Trip", "Hack'n'Slash", [0.5, 2, 0, 0, 0], 0, 150, 1),
        w("Glintstone Staff, scales with Intelligence", "Glintstone Pebble", "Comet", "Comet Azur", [0, 0, 2, 0, 0], 0, 100, 2),
        w("Winged Scythe, scales with Intelligence and Faith primarily and Strength secondarily", "Scythe", "Sweep", "Death Scythe", [0, 0, 1, 1, 1], 0, 300, 5),
        w("Rivers of Blood, scales with Arcane primarily and Dexterity secondarily", "Bloodletting", "Stab", "Unsheath", [0, 0.5, 0, 0, 1], 0, 200, 2),
      ];
    },
    tier2() {
      return [
        w("Great Club, scales with Strength primarily and Dexterity secondarily", "Smash", "Crush", "Earthquake", [3.0, 0.5, 0.0, 0.0, 0.0], 20, 500, 5),
        w("Reduvia, scales with Dexterity primarily and Arcane secondarily", "Stab", "Flay", "Blood Surge", [0.5, 3.0, 0.0, 0.0, 0.5], 20, 250, 2),
        w("Azur's Glintstone Staff, scales with Intelligence primarily", "Magic Missile", "Arcane Burst", "Meteor Shower", [0.0, 0.0, 3.0, 0.0, 0.0], 20, 300, 3),
        w("Godslayer Sword, scales with Faith primarily and Dexterity secondarily", "Cleave", "Searing Strike", "Divine Retribution", [0.5, 0.0, 0.0, 3.0, 0.0], 20, 400, 4),
        w("Death's Poker, scales with Arcane primarily and Dexterity secondarily", "Pierce", "Spectral Thrust", "Soul Rend", [0.0, 1.0, 0.0, 0.0, 3.0], 20, 350, 3),
      ];
    },
    tier3() {
      return [
        w("Colossal Greatsword, scales with Strength primarily and a bit of Dexterity", "Heavy Swing", "Ground Slam", "Titan's Wrath", [3.5, 0.7, 0.0, 0.0, 0.0], 700, 600, 6),
        w("Silence, scales with Dexterity primarily and Arcane secondarily", "Quick Slash", "Veil Slice", "Silent Execution", [0.7, 3.5, 0.0, 0.0, 0.7], 500, 300, 3),
        w("Moonlight Greatsword, scales with Intelligence primarily", "Lunar Strike", "Starfall", "Cosmic Burst", [0.0, 0.0, 4.0, 0.0, 0.0], 700, 400, 4),
        w("Blasphemous Blade, scales with Faith primarily and Dexterity secondarily", "Scorch", "Blaze Swipe", "Hellfire", [0.6, 0.3, 0.0, 4.0, 0.0], 650, 450, 5),
        w("Mimic Tear's Blade, scales with Arcane primarily and Dexterity secondarily", "Copycat Strike", "Shadow Edge", "Mirrored Death", [0.0, 1.2, 0.0, 0.0, 4.0], 550, 400, 4),
      ];
    },
    tier4() {
      return [
        w("Grafted Blade Greatsword, scales with Strength primarily and a bit of Dexterity", "Overhead Smash", "Sweep", "Mighty Slam", [3.8, 0.6, 0.0, 0.0, 0.0], 700, 600, 6),
        w("Eclipse Shotel, scales with Dexterity primarily and a bit of Strength", "Slice", "Sun's Flare", "Eclipse Cut", [0.8, 3.0, 0.0, 0.0, 0.0], 550, 300, 3),
        w("Sword of Night and Flame, scales with Intelligence primarily and Faith secondarily", "Night Slash", "Flame Sweep", "Starfire", [0.5, 0.2, 3.0, 3.0, 0.0], 750, 450, 4),
        w("Godslayer Greatsword, scales with Faith primarily and a bit of Dexterity", "Sacred Swing", "Holy Cleave", "God's Judgement", [0.6, 0.3, 0.0, 3.5, 0.0], 650, 400, 5),
        w("Night's Sacred Blade, scales with Arcane primarily and Dexterity secondarily", "Dark Slash", "Shadow Stab", "Moonlit Veil", [0.0, 1.2, 0.0, 0.0, 4.0], 600, 350, 4),
      ];
    },
    tier5() {
      return [
        w("Great Club, scales with Strength primarily and a bit of Dexterity", "Smash", "Crush", "Earthquake", [4.0, 0.8, 0.0, 0.0, 0.0], 800, 700, 6),
        w("Bloodhound's Fang, scales with Dexterity primarily and a bit of Arcane", "Slash", "Bloodletting", "Fang Strike", [0.8, 3.0, 0.0, 0.0, 1.0], 700, 350, 4),
        w("Moonveil, scales with Intelligence primarily and Dexterity secondarily", "Lunar Slash", "Starfall", "Moonburst", [0.5, 0.6, 4.0, 0.0, 0.0], 800, 500, 4),
        w("Eclipse Shotel, scales with Dexterity primarily and a bit of Strength", "Cut", "Sun Ray", "Eclipse Strike", [0.9, 3.5, 0.0, 0.0, 0.0], 650, 350, 3),
        w("Dark Moon Greatsword, scales with Intelligence primarily and a bit of Faith", "Lunar Slash", "Cosmic Ray", "Dark Moon Beam", [0.0, 0.0, 5.0, 1.0, 0.0], 850, 600, 5),
      ];
    },
    tier6() {
      return [
        w("Grafted Blade Greatsword, scales with Strength primarily and some Dexterity", "Overhead Smash", "Heavy Cleave", "Titan's Wrath", [5.0, 1.0, 0.0, 0.0, 0.0], 1000, 800, 8),
        w("Reduvia, scales with Dexterity primarily and some Arcane", "Stab", "Flay", "Blood Surge", [0.8, 3.2, 0.0, 0.0, 1.5], 900, 500, 5),
        w("Carian Regal Scepter, scales with Intelligence primarily and some Faith", "Mystic Bolt", "Arcane Wave", "Regal Barrage", [0.3, 0.2, 4.8, 1.2, 0.0], 950, 650, 5),
        w("Godslayer's Greatsword, scales with Faith primarily and some Dexterity", "Holy Cleave", "Divine Strike", "God's Wrath", [0.6, 1.0, 0.0, 4.2, 0.0], 850, 700, 5),
        w("Black Knife, scales with Arcane primarily and some Dexterity", "Shadow Stab", "Silent Cut", "Blackened Blade", [0.0, 1.7, 0.0, 0.0, 4.0], 800, 400, 4),
      ];
    },
  };

  // ----- Bosses (Bestiary.java) -----
  // atk(line, chargeUp, coolDown, dir1, dir2, damage); combo(...attacks)

  function atk(line, chargeUp, coolDown, dir1, dir2, damage) {
    return { line, chargeUp, coolDown, dodge: [dir1, dir2], damage };
  }
  function combo(...attacks) {
    return attacks;
  }
  function boss(hp, runes, winLine, phaseLine, deathLine, phase1, phase2) {
    return { hp, runes, winLine, phaseLine, deathLine, phase1, phase2 };
  }

  const Bestiary = {
    margit() {
      return boss(4174, 15,
        "Put these foolish ambitions to rest.",
        "Well, thou art of passing skill. Warrior blood must truly run in thy veins, Tarnished.",
        "I shall remember thee, Tarnished. Smouldering with thy meagre flame. Cower in Fear. Of the Night. The hands of the Fell Omen shall brook thee no quarter.",
        [
          combo(
            atk("Margit slowly raises his sword upwards..\n", 1, 0, 2, 3, 80),
            atk("Margit quickly brings his sword to the side..\n", 1, 1, 0, 1, 50)
          ),
          combo(
            atk("Margit spins his sword in a wide arc..\n", 1, 0, 2, 3, 100),
            atk("Margit performs a sweeping low attack..\n", 1, 0, 0, 1, 60),
            atk("Margit follows up with a spinning kick..\n", 1, 1, 2, 3, 90)
          ),
          combo(
            atk("Margit stomps the ground causing a shockwave..\n", 2, 0, 2, 3, 120),
            atk("Margit follows up with a quick overhead slash..\n", 1, 1, 0, 1, 90)
          ),
          combo(
            atk("Margit raises his sword and then slams it down with great force..\n", 2, 0, 2, 3, 140),
            atk("Margit then performs a rapid series of slashes..\n", 2, 0, 0, 1, 100),
            atk("Margit finishes with a ground pound..\n", 1, 0, 2, 3, 130),
            atk("Margit performs a quick slashing retreat..\n", 1, 1, 0, 1, 40)
          )
        ],
        [
          combo(
            atk("The Omen lunges towards you..\n", 1, 0, 0, 1, 20),
            atk("The Omen swings his sword overhead..\n", 2, 1, 2, 3, 70)
          ),
          combo(
            atk("The Omen jumps into the air and summons a lightning spear..\n", 2, 0, 2, 3, 130),
            atk("The Omen hurls the lightning spear downwards..\n", 3, 0, 0, 1, 180),
            atk("The Omen performs a quick follow-up attack..\n", 2, 1, 2, 3, 70)
          ),
          combo(
            atk("The Omen performs a spinning attack with his sword..\n", 1, 0, 0, 1, 40),
            atk("The Omen follows with a series of rapid thrusts..\n", 2, 1, 2, 3, 80)
          ),
          combo(
            atk("The Omen creates a barrier of dark energy around himself..\n", 3, 0, 2, 3, 150),
            atk("The barrier then explodes outward, causing damage..\n", 4, 0, 0, 1, 200),
            atk("The Omen finishes with a powerful shockwave..\n", 3, 5, 2, 3, 175)
          )
        ]
      );
    },

    godrick() {
      return boss(3200, 20,
        "Lowly Tarnished... Thou'rt unfit even to graft... Great Godfrey, did'st thou witness?",
        "Ahh, truest of dragons. Lend me thy strength... Nnngh! Forefathers, one and all... Bear witness!",
        "...I am Lord of all that is Golden.... ...And one day, we'll return together... ...To our home, bathed in rays of gold...",
        [
          combo(
            atk("Godrick swings his axe in a wide arc..\n", 3, 0, 0, 2, 100),
            atk("Godrick charges forward with a brutal overhead strike..\n", 4, 0, 1, 3, 140),
            atk("Godrick performs a sweeping low attack..\n", 3, 0, 0, 2, 120),
            atk("Godrick slams his axe downwards..\n", 5, 0, 1, 3, 160),
            atk("Godrick follows with a spinning attack..\n", 4, 0, 0, 2, 140),
            atk("Godrick finishes with a ground-shaking stomp..\n", 6, 5, 1, 3, 180)
          ),
          combo(
            atk("Godrick charges at you with a fierce tackle..\n", 3, 0, 0, 2, 80),
            atk("Godrick performs a powerful side swipe..\n", 4, 0, 1, 3, 100),
            atk("Godrick ends with a devastating overhead smash..\n", 6, 5, 0, 2, 200)
          ),
          combo(
            atk("Godrick unleashes a rapid flurry of axe strikes..\n", 4, 0, 0, 2, 100),
            atk("Godrick finishes with a powerful diagonal slash..\n", 5, 4, 1, 3, 140)
          ),
          combo(
            atk("Godrick raises his axe and performs a wide sweep..\n", 3, 0, 0, 2, 120),
            atk("Godrick follows with a series of rapid axe slashes..\n", 4, 0, 1, 3, 140),
            atk("Godrick brings his axe down in a vertical strike..\n", 5, 0, 0, 2, 160),
            atk("Godrick performs a powerful spinning attack..\n", 4, 0, 1, 3, 180),
            atk("Godrick ends with a fierce shockwave blast..\n", 6, 5, 0, 2, 240)
          )
        ],
        [
          combo(
            atk("Godrick charges at you with a sudden rush..\n", 3, 0, 0, 2, 100),
            atk("Godrick follows with a quick overhead slam..\n", 4, 0, 1, 3, 140),
            atk("Godrick concludes with a powerful ground smash..\n", 5, 4, 0, 2, 180)
          ),
          combo(
            atk("Godrick swings his axe in a wide arc..\n", 3, 0, 0, 2, 140),
            atk("Godrick performs a rapid side swipe..\n", 4, 0, 1, 3, 160),
            atk("Godrick follows with a powerful charge..\n", 5, 0, 0, 2, 180),
            atk("Godrick ends with a crushing overhead attack..\n", 6, 5, 1, 3, 220)
          ),
          combo(
            atk("Godrick performs a quick series of axe slashes..\n", 4, 0, 0, 2, 110),
            atk("Godrick finishes with a powerful downward smash..\n", 5, 4, 1, 3, 160)
          ),
          combo(
            atk("Godrick raises his axe and delivers a powerful sweep..\n", 3, 0, 0, 2, 140),
            atk("Godrick follows with a series of rapid strikes..\n", 4, 0, 1, 3, 180),
            atk("Godrick performs a spinning attack..\n", 5, 0, 0, 2, 200),
            atk("Godrick concludes with a devastating shockwave..\n", 6, 5, 1, 3, 270)
          )
        ]
      );
    },

    redWolf() {
      return boss(2204, 30,
        "The Red Wolf of Radagon swiftly devours its prey.",
        "The Red Wolf of Radagon's eyes glow with fierce intensity.",
        "The Red Wolf of Radagon lets out a final growl as it falls.",
        [
          combo(
            atk("The Red Wolf bares its fangs, lunging at you with incredible speed...\n", 3, 0, 2, 3, 180),
            atk("The Red Wolf leaps into the air and slashes downwards with its claws...\n", 2, 1, 0, 1, 220)
          ),
          combo(
            atk("The Red Wolf conjures a spectral sword and swings it in a wide arc...\n", 3, 0, 0, 1, 200),
            atk("The Red Wolf follows up with a biting lunge...\n", 2, 0, 2, 3, 150),
            atk("The Red Wolf ends with a quick retreat, preparing for another assault...\n", 1, 3, 0, 1, 120)
          ),
          combo(
            atk("The Red Wolf dashes to the side, then lunges at you with a fierce bite...\n", 4, 0, 2, 3, 220),
            atk("The Red Wolf conjures a spectral sword, thrusting it forward...\n", 2, 1, 0, 1, 180)
          ),
          combo(
            atk("The Red Wolf lets out a howl, summoning a spectral sword...\n", 5, 0, 2, 3, 270),
            atk("The Red Wolf leaps forward with its claws bared, slashing multiple times...\n", 4, 0, 0, 1, 230),
            atk("The Red Wolf ends with a powerful magical shockwave...\n", 3, 4, 2, 3, 350)
          )
        ],
        [
          combo(
            atk("The Red Wolf summons multiple spectral swords, then charges at you...\n", 4, 0, 0, 1, 320),
            atk("The Red Wolf follows with a swift bite...\n", 3, 2, 2, 3, 220)
          ),
          combo(
            atk("The Red Wolf leaps into the air, raining down spectral swords...\n", 5, 0, 2, 3, 370),
            atk("The Red Wolf dashes forward with a powerful slash...\n", 4, 0, 0, 1, 270),
            atk("The Red Wolf conjures a magical blast, sending shockwaves across the ground...\n", 3, 4, 2, 3, 420)
          ),
          combo(
            atk("The Red Wolf summons a spectral sword, slashing rapidly...\n", 3, 0, 0, 1, 220),
            atk("The Red Wolf follows with a series of quick bites...\n", 4, 2, 2, 3, 270)
          ),
          combo(
            atk("The Red Wolf charges up, unleashing a powerful magical roar...\n", 6, 0, 2, 3, 420),
            atk("The Red Wolf lunges forward with its claws, then spins around for another attack...\n", 5, 0, 0, 1, 320),
            atk("The Red Wolf ends with a sweeping spectral sword attack...\n", 4, 3, 2, 3, 370)
          )
        ]
      );
    },

    rennala() {
      return boss(3493, 35,
        "Be not afeard, little culver. Thy fate lieth under my moon.",
        "Ahh, my beloved... Have no fear, I will hold thee. Patience. Ye will be countless born, forever and ever.\n\nRanni: Upon my name as Ranni the Witch. Mother's rich slumber shall not be disturbed by thee. Foul trespasser. Send word far and wide. Of the last Queen of Caria, Rennala of the Full Moon. And the majesty of the night she conjureth.",
        "Oh little Ranni, my dear daughter. Weave thy night into being.",
        [
          combo(
            atk("Rennala raises her staff, summoning a barrage of magical projectiles...\n", 4, 0, 0, 1, 180),
            atk("Rennala conjures a protective shield around herself, reflecting damage back...\n", 3, 1, 2, 3, 140)
          ),
          combo(
            atk("Rennala calls forth a magical storm, with lightning crashing down...\n", 5, 0, 2, 3, 220),
            atk("Rennala follows with a wave of arcane energy...\n", 4, 0, 0, 1, 200)
          ),
          combo(
            atk("Rennala hurls a sphere of concentrated magic at you...\n", 3, 0, 0, 1, 180),
            atk("Rennala summons spectral arms to strike from a distance...\n", 4, 0, 2, 3, 160)
          ),
          combo(
            atk("Rennala's staff glows as she channels a powerful beam of light...\n", 6, 0, 0, 1, 350)
          )
        ],
        [
          combo(
            atk("Rennala summons a spectral wolf to attack alongside her...\n", 4, 0, 0, 1, 220),
            atk("Rennala casts a series of homing magic missiles...\n", 5, 0, 2, 3, 240)
          ),
          combo(
            atk("Rennala summons a draconic spirit to unleash a fiery breath...\n", 5, 0, 2, 3, 270),
            atk("Rennala follows up with a blast of arcane energy...\n", 4, 0, 0, 1, 220)
          ),
          combo(
            atk("Rennala summons a horde of spectral soldiers to attack...\n", 6, 0, 0, 1, 320),
            atk("Rennala finishes with a burst of magical energy...\n", 5, 0, 2, 3, 300)
          ),
          combo(
            atk("Rennala channels her full power, unleashing a massive energy explosion...\n", 7, 0, 0, 1, 450),
            atk("Rennala then calls forth a meteor shower...\n", 6, 0, 2, 3, 400)
          )
        ]
      );
    },

    serpent() {
      return boss(30439, 40,
        "Now, we can devour the gods, together!",
        "Hmm... Very well. You... Join the Serpent King, as family... Together, we will devour the very gods!",
        "No one will hold me captive. A serpent never dies. Ha ha ha...",
        [
          combo(
            atk("The Serpent lunges forward, attempting to swallow you whole...\n", 5, 0, 0, 1, 450)
          ),
          combo(
            atk("The Serpent snaps its jaws shut with bone-crushing force...\n", 4, 0, 0, 1, 400),
            atk("The Serpent follows up with a venomous tail swipe...\n", 4, 0, 2, 3, 350)
          ),
          combo(
            atk("The Serpent coils around, striking with its massive tail...\n", 4, 0, 0, 1, 350),
            atk("The Serpent releases a cloud of toxic venom...\n", 6, 0, 2, 3, 500),
            atk("The Serpent snaps its jaws shut with a furious bite...\n", 5, 0, 0, 1, 450)
          ),
          combo(
            atk("The Serpent rears back, spitting out a stream of burning acid...\n", 5, 0, 0, 1, 550),
            atk("The Serpent follows up with a sweeping tail attack...\n", 5, 0, 2, 3, 500),
            atk("The Serpent bites with renewed ferocity...\n", 6, 0, 0, 1, 550),
            atk("The Serpent lashes out with a powerful tail slam...\n", 7, 0, 2, 3, 600)
          )
        ],
        [
          combo(
            atk("The Serpent roars, summoning a wave of deadly poison...\n", 6, 0, 0, 1, 550),
            atk("The Serpent follows up with a vicious bite...\n", 5, 0, 2, 3, 500)
          ),
          combo(
            atk("The Serpent coils and squeezes, crushing anything in its grasp...\n", 6, 0, 0, 1, 600),
            atk("The Serpent then releases a venomous blast...\n", 5, 0, 2, 3, 550)
          ),
          combo(
            atk("The Serpent sprays a stream of venomous acid...\n", 5, 0, 2, 3, 550),
            atk("The Serpent strikes with blinding speed...\n", 4, 0, 0, 1, 500),
            atk("The Serpent follows with a crushing tail slam...\n", 5, 0, 2, 3, 550)
          ),
          combo(
            atk("The Serpent unleashes its full fury, biting and thrashing with deadly intent...\n", 8, 0, 0, 1, 750),
            atk("The Serpent then follows with a venomous cloud...\n", 6, 0, 2, 3, 650),
            atk("Finally, the Serpent performs a powerful tail swipe...\n", 7, 0, 0, 1, 700)
          )
        ]
      );
    },

    radahn() {
      return boss(9572, 45,
        "Radahn stands tall, the sky darkened by his immense presence and the power of his gravity magic.",
        "Radahn readies his colossal weapon, ready to unleash devastating blows upon you.",
        "Radahn staggers, the force of his attacks finally taking its toll.",
        [
          combo(
            atk("Radahn swings his colossal weapon in a sweeping arc...\n", 6, 0, 0, 1, 500)
          ),
          combo(
            atk("Radahn charges forward with a powerful thrust...\n", 5, 0, 0, 1, 450),
            atk("He then follows up with a crushing overhead slam...\n", 6, 0, 2, 3, 500)
          ),
          combo(
            atk("Radahn unleashes a series of rapid swings...\n", 4, 0, 0, 1, 350),
            atk("He charges up for a devastating gravity smash...\n", 7, 0, 2, 3, 650),
            atk("Radahn finishes with a powerful overhead swing...\n", 6, 0, 0, 1, 500)
          ),
          combo(
            atk("Radahn begins with a gravity-infused swing...\n", 6, 0, 0, 1, 550),
            atk("He then stomps the ground, creating a shockwave...\n", 5, 0, 2, 3, 500),
            atk("Radahn follows with a series of sweeping slashes...\n", 6, 0, 0, 1, 550),
            atk("Finally, he performs a massive gravity slam...\n", 7, 0, 2, 3, 650)
          )
        ],
        [
          combo(
            atk("Radahn unleashes a gravity-enhanced charge...\n", 7, 0, 0, 1, 600),
            atk("He then follows with a series of powerful swings...\n", 6, 0, 2, 3, 550)
          ),
          combo(
            atk("Radahn performs a devastating ground smash...\n", 7, 0, 0, 1, 650),
            atk("He then performs a high-speed vertical swing...\n", 6, 0, 2, 3, 600)
          ),
          combo(
            atk("Radahn performs a series of rapid swings...\n", 5, 0, 2, 3, 550),
            atk("He then charges up and slams the ground...\n", 7, 0, 0, 1, 700),
            atk("Radahn finishes with a massive overhead slam...\n", 8, 0, 2, 3, 750)
          ),
          combo(
            atk("Radahn unleashes his full gravity powers, causing massive upheaval...\n", 9, 0, 0, 1, 750),
            atk("He follows with a powerful series of slashes...\n", 7, 0, 2, 3, 650),
            atk("Radahn then stomps the ground, sending shockwaves...\n", 6, 0, 0, 1, 600),
            atk("Finally, he performs a devastating gravity slam...\n", 8, 0, 2, 3, 700)
          )
        ]
      );
    },

    mohg() {
      return boss(18389, 50,
        "Miquella is mine and mine alone.",
        "Tres! Duo! Unus! Nihil! Nihil! Nihil!",
        "Ahh, I can see it, clear as day! The coming of our dynasty! Mohgwyn!",
        [
          combo(
            atk("Mohg slashes with his blood-infused weapon...\n", 4, 0, 0, 1, 400)
          ),
          combo(
            atk("Mohg casts a wave of blood magic...\n", 5, 0, 0, 1, 450),
            atk("He follows with a blood-imbued thrust...\n", 6, 0, 2, 3, 500)
          ),
          combo(
            atk("Mohg unleashes a rapid series of slashes...\n", 4, 0, 0, 1, 350),
            atk("He follows with a powerful blood explosion...\n", 7, 0, 2, 3, 550),
            atk("Mohg finishes with a sweeping blood attack...\n", 5, 0, 0, 1, 450)
          ),
          combo(
            atk("Mohg summons a torrent of blood...\n", 6, 0, 0, 1, 500),
            atk("He follows with a quick succession of slashes...\n", 5, 0, 2, 3, 450),
            atk("Mohg then performs a high-speed blood thrust...\n", 6, 0, 0, 1, 500),
            atk("Finally, he unleashes a massive blood explosion...\n", 8, 0, 2, 3, 600)
          )
        ],
        [
          combo(
            atk("Mohg performs a powerful blood surge...\n", 7, 0, 0, 1, 550),
            atk("He follows with a series of intense blood slashes...\n", 6, 0, 2, 3, 500)
          ),
          combo(
            atk("Mohg casts a blood storm...\n", 8, 0, 0, 1, 600),
            atk("He then charges with a blood-infused lunge...\n", 6, 0, 2, 3, 550)
          ),
          combo(
            atk("Mohg initiates a series of rapid blood strikes...\n", 5, 0, 0, 1, 500),
            atk("He follows with a blood vortex...\n", 7, 0, 2, 3, 600),
            atk("Mohg finishes with a powerful blood eruption...\n", 8, 0, 0, 1, 650)
          ),
          combo(
            atk("Mohg performs a devastating blood surge...\n", 8, 0, 0, 1, 600),
            atk("He follows with an aggressive series of slashes...\n", 7, 0, 2, 3, 550),
            atk("Mohg then unleashes a massive blood explosion...\n", 9, 0, 0, 1, 700),
            atk("Finally, he performs a high-speed blood thrust...\n", 6, 0, 2, 3, 550)
          )
        ]
      );
    },

    goldenGodfrey() {
      return boss(21903, 55,
        "Golden Godfrey, the regal warrior, stands with an imposing aura.",
        "He prepares to unleash a series of devastating attacks.",
        "Golden Godfrey pauses, his mighty form readying for the next onslaught.",
        [
          combo(
            atk("Golden Godfrey swings his colossal axe...\n", 6, 0, 0, 1, 450)
          ),
          combo(
            atk("Godfrey performs a powerful overhead swing...\n", 7, 0, 0, 1, 500),
            atk("He follows with a quick horizontal swipe...\n", 5, 0, 2, 3, 450)
          ),
          combo(
            atk("Golden Godfrey charges his axe...\n", 8, 0, 0, 1, 550),
            atk("He unleashes a series of mighty swings...\n", 6, 0, 2, 3, 500),
            atk("Godfrey finishes with a devastating slam...\n", 7, 0, 0, 1, 550)
          ),
          combo(
            atk("Golden Godfrey starts with a massive ground slam...\n", 9, 0, 0, 1, 600),
            atk("He follows with a rapid sequence of strikes...\n", 7, 0, 2, 3, 550),
            atk("Godfrey ends with a powerful thrust...\n", 8, 0, 0, 1, 600)
          )
        ],
        [
          combo(
            atk("Golden Godfrey performs a fearsome axe slam...\n", 8, 0, 0, 1, 550),
            atk("He then executes a wide swing...\n", 6, 0, 2, 3, 500)
          ),
          combo(
            atk("Godfrey initiates a powerful spin attack...\n", 9, 0, 0, 1, 600),
            atk("He follows with a ground-shaking slam...\n", 8, 0, 2, 3, 550)
          ),
          combo(
            atk("Golden Godfrey starts with a mighty overhead strike...\n", 7, 0, 0, 1, 500),
            atk("He then performs a rapid series of axe swings...\n", 8, 0, 2, 3, 600),
            atk("Godfrey ends with a powerful axe slam...\n", 9, 0, 0, 1, 650)
          ),
          combo(
            atk("Golden Godfrey begins with a devastating multi-hit combo...\n", 10, 0, 0, 1, 650),
            atk("He then performs a massive ground smash...\n", 9, 0, 2, 3, 600),
            atk("Godfrey follows with a powerful spinning attack...\n", 8, 0, 0, 1, 550),
            atk("Finally, he ends with a colossal finishing strike...\n", 10, 0, 2, 3, 700)
          )
        ]
      );
    },

    morgott() {
      return boss(10399, 60,
        "Put these foolish ambitions to rest. May the curse seep to thy very soul. An apt reward for thy brash ambition",
        "Hrghraah! The thrones... stained by my curse... Such shame I cannot bear. Thy part in this shall not be forgiven.",
        "Tarnished, thou'rt but a fool. The Erdtree wards off all who deign approach. We are... we are all forsaken. None may claim the title of Elden Lord. Upon talking to Morgott twice: Thy deeds shall be met with failure, just as I.",
        [
          combo(
            atk("Morgott swings his cursed blade...\n", 5, 0, 0, 1, 400)
          ),
          combo(
            atk("Morgott begins with a dark magical blast...\n", 6, 0, 0, 1, 450),
            atk("He follows with a quick slash...\n", 4, 0, 2, 3, 400)
          ),
          combo(
            atk("Morgott summons dark energy...\n", 7, 0, 0, 1, 500),
            atk("He unleashes a flurry of cursed strikes...\n", 6, 0, 2, 3, 450),
            atk("Morgott concludes with a powerful ground slam...\n", 8, 0, 0, 1, 550)
          ),
          combo(
            atk("Morgott starts with a sweeping dark arc...\n", 7, 0, 0, 1, 500),
            atk("He follows with a rapid multi-hit strike...\n", 6, 0, 2, 3, 450),
            atk("Morgott finishes with a devastating dark explosion...\n", 8, 0, 0, 1, 600)
          )
        ],
        [
          combo(
            atk("Morgott begins with a powerful dark magic surge...\n", 8, 0, 0, 1, 550),
            atk("He follows with a cursed blade swipe...\n", 6, 0, 2, 3, 500)
          ),
          combo(
            atk("Morgott unleashes a rapid sequence of dark slashes...\n", 9, 0, 0, 1, 600),
            atk("He then performs a high-damage magical burst...\n", 8, 0, 2, 3, 550)
          ),
          combo(
            atk("Morgott starts with a menacing dark vortex...\n", 7, 0, 0, 1, 500),
            atk("He follows with a series of high-speed slashes...\n", 8, 0, 2, 3, 550),
            atk("Morgott finishes with a massive cursed impact...\n", 9, 0, 0, 1, 600)
          ),
          combo(
            atk("Morgott begins with a dark energy eruption...\n", 10, 0, 0, 1, 650),
            atk("He then executes a multi-hit cursed assault...\n", 9, 0, 2, 3, 600),
            atk("Morgott concludes with a powerful magic burst...\n", 10, 0, 0, 1, 700)
          )
        ]
      );
    },

    fireGiant() {
      return boss(43263, 65,
        "The Fire Giant looms with fiery fury, his very presence scorching the earth.",
        "He prepares for a series of devastating fiery assaults.",
        "The Fire Giant takes a moment, his next fiery attack is imminent.",
        [
          combo(
            atk("Fire Giant swings his massive club...\n", 8, 0, 0, 1, 600),
            atk("He follows with a burst of fire...\n", 9, 0, 2, 3, 650)
          ),
          combo(
            atk("Fire Giant stomps the ground...\n", 7, 0, 0, 1, 550),
            atk("He unleashes a series of fiery eruptions...\n", 8, 0, 2, 3, 600),
            atk("The Fire Giant finishes with a massive fireball...\n", 10, 0, 0, 1, 700)
          ),
          combo(
            atk("Fire Giant roars, summoning flames...\n", 8, 0, 0, 1, 550),
            atk("He swings his club in a fiery arc...\n", 9, 0, 2, 3, 600),
            atk("The Fire Giant concludes with a powerful ground slam...\n", 10, 0, 0, 1, 700)
          ),
          combo(
            atk("Fire Giant starts with a fiery shockwave...\n", 8, 0, 0, 1, 600),
            atk("He follows with a series of intense fire eruptions...\n", 9, 0, 2, 3, 650),
            atk("The Fire Giant ends with a massive fiery explosion...\n", 12, 0, 0, 1, 750)
          )
        ],
        [
          combo(
            atk("Fire Giant begins with a ground-shaking stomp...\n", 10, 0, 0, 1, 700),
            atk("He follows with a fiery ground eruption...\n", 11, 0, 2, 3, 750)
          ),
          combo(
            atk("Fire Giant roars, causing flames to spew from the ground...\n", 11, 0, 0, 1, 750),
            atk("He then performs a series of powerful fiery swings...\n", 12, 0, 2, 3, 800)
          ),
          combo(
            atk("Fire Giant starts with a massive fiery shockwave...\n", 12, 0, 0, 1, 750),
            atk("He continues with a rapid barrage of fireballs...\n", 11, 0, 2, 3, 700),
            atk("The Fire Giant finishes with a devastating fiery eruption...\n", 13, 0, 0, 1, 800)
          ),
          combo(
            atk("Fire Giant begins with a massive fire vortex...\n", 14, 0, 0, 1, 800),
            atk("He follows with a series of intense fireball eruptions...\n", 12, 0, 2, 3, 750),
            atk("Fire Giant concludes with a colossal fire explosion...\n", 15, 0, 0, 1, 850)
          )
        ]
      );
    },

    godskinDuo() {
      return boss(8000, 750,
        "The Godskin Duo emerges with synchronized, deadly attacks. Their coordination is unparalleled.",
        "The duo is preparing for a series of rapid and unpredictable strikes.",
        "The Godskin Duo falls together, the duo is done.",
        [
          combo(
            atk("Godskin Duo swings their weapons simultaneously...\n", 8, 0, 0, 1, 600),
            atk("One of the duo follows with a quick thrust...\n", 9, 0, 2, 3, 650)
          ),
          combo(
            atk("The duo performs a synchronized spin attack...\n", 10, 0, 0, 1, 700),
            atk("They quickly follow with a pair of slashes...\n", 9, 0, 2, 3, 650),
            atk("One member finishes with a rapid thrust...\n", 11, 0, 0, 1, 750)
          ),
          combo(
            atk("Godskin Duo starts with a quick dash...\n", 8, 0, 0, 1, 600),
            atk("They follow with a series of rapid slashes...\n", 10, 0, 2, 3, 650),
            atk("One member concludes with a powerful finishing blow...\n", 12, 0, 0, 1, 750)
          ),
          combo(
            atk("The duo performs a coordinated charge...\n", 9, 0, 0, 1, 650),
            atk("They follow with a series of alternating attacks...\n", 10, 0, 2, 3, 700),
            atk("One of the duo concludes with a devastating slam...\n", 12, 0, 0, 1, 800)
          )
        ],
        [
          combo(
            atk("The duo begins with a high-speed dash...\n", 9, 0, 0, 1, 650),
            atk("They follow with a series of quick strikes...\n", 10, 0, 2, 3, 700)
          ),
          combo(
            atk("Godskin Duo starts with a powerful charge...\n", 10, 0, 0, 1, 700),
            atk("They perform a rapid series of coordinated slashes...\n", 11, 0, 2, 3, 750)
          ),
          combo(
            atk("The duo performs a synchronized whirlwind attack...\n", 11, 0, 0, 1, 750),
            atk("They follow with a series of powerful swings...\n", 12, 0, 2, 3, 800),
            atk("One member concludes with a devastating finishing move...\n", 13, 0, 0, 1, 850)
          ),
          combo(
            atk("Godskin Duo begins with a high-speed onslaught...\n", 12, 0, 0, 1, 750),
            atk("They follow with a series of rapid strikes...\n", 13, 0, 2, 3, 800),
            atk("The duo concludes with a massive, synchronized attack...\n", 14, 0, 0, 1, 900)
          )
        ]
      );
    },

    beastClergyman() {
      return boss(10620, 80,
        "Destined Death has taken you, too.",
        "O, Death. Become my blade, once more",
        "Witless Tarnished... Why covet Destined Death? To kill what?",
        [
          combo(
            atk("Beast Clergyman swings his staff with a powerful overhead strike...\n", 8, 0, 0, 1, 500),
            atk("He follows up with a quick horizontal slash...\n", 7, 0, 2, 3, 450)
          ),
          combo(
            atk("The cleric begins with a sweeping staff attack...\n", 9, 0, 0, 1, 550),
            atk("He follows with a rapid series of strikes...\n", 8, 0, 2, 3, 500),
            atk("Concluding with a powerful charge...\n", 10, 0, 0, 1, 600)
          ),
          combo(
            atk("Beast Clergyman starts with a charging thrust...\n", 10, 0, 0, 1, 600),
            atk("He quickly follows with a series of quick slashes...\n", 9, 0, 2, 3, 550),
            atk("He concludes with a sweeping staff spin...\n", 12, 0, 0, 1, 700)
          ),
          combo(
            atk("The cleric starts with a rapid multi-strike attack...\n", 10, 0, 0, 1, 700),
            atk("He follows with a powerful slam...\n", 11, 0, 2, 3, 750),
            atk("Finally, a devastating magical burst...\n", 12, 0, 0, 1, 800)
          )
        ],
        [
          combo(
            atk("Malekith begins with a rapid series of shadow slashes...\n", 12, 0, 0, 1, 700),
            atk("He follows with a powerful dark magic attack...\n", 13, 0, 2, 3, 750),
            atk("Concluding with a massive energy wave...\n", 14, 0, 0, 1, 800)
          ),
          combo(
            atk("Malekith starts with a high-speed dash attack...\n", 11, 0, 0, 1, 650),
            atk("He follows with a series of rapid dark strikes...\n", 12, 0, 2, 3, 700),
            atk("Ending with a powerful shadow explosion...\n", 13, 0, 0, 1, 750)
          ),
          combo(
            atk("Malekith begins with a devastating ground slam...\n", 14, 0, 0, 1, 800),
            atk("He then follows with a dark magic barrage...\n", 15, 0, 2, 3, 850),
            atk("Finally, a massive shadow vortex...\n", 16, 0, 0, 1, 900)
          ),
          combo(
            atk("Malekith starts with a rapid multi-strike shadow attack...\n", 14, 0, 0, 1, 800),
            atk("He follows with a powerful energy blast...\n", 15, 0, 2, 3, 850),
            atk("Ending with a devastating dark explosion...\n", 16, 0, 0, 1, 900)
          )
        ]
      );
    },

    gideon() {
      return boss(6226, 85,
        "My fellow, you've fought well, until now.",
        "Gideon prepares a potent strike.",
        "I know...in my bones... A Tarnished cannot become a Lord. Not even you. A man cannot kill a god...",
        [
          combo(
            atk("Gideon begins with a quick thrust...\n", 6, 0, 0, 1, 400),
            atk("He follows with a swift side swing...\n", 7, 0, 2, 3, 350)
          ),
          combo(
            atk("Gideon starts with a powerful slam...\n", 8, 0, 0, 1, 500),
            atk("He continues with a spinning attack...\n", 9, 0, 2, 3, 550),
            atk("And finishes with a strong overhead smash...\n", 10, 0, 0, 1, 600)
          ),
          combo(
            atk("Gideon starts with a rapid flurry of strikes...\n", 7, 0, 0, 1, 400),
            atk("He follows with a piercing lunge...\n", 8, 0, 2, 3, 450),
            atk("Concludes with a sweeping attack...\n", 9, 0, 0, 1, 500)
          ),
          combo(
            atk("Gideon opens with a fast magical blast...\n", 8, 0, 0, 1, 500),
            atk("He transitions into a quick series of melee strikes...\n", 7, 0, 2, 3, 450),
            atk("Finishes with a powerful magic-infused swing...\n", 9, 0, 0, 1, 550)
          )
        ],
        [
          combo(
            atk("Gideon starts with a rapid magical burst...\n", 10, 0, 0, 1, 500),
            atk("Follows up with a powerful melee slam...\n", 11, 0, 2, 3, 600)
          ),
          combo(
            atk("Gideon begins with a high-speed dash attack...\n", 11, 0, 0, 1, 600),
            atk("He quickly follows with a series of magical slashes...\n", 12, 0, 2, 3, 650),
            atk("Ends with a powerful energy wave...\n", 13, 0, 0, 1, 700)
          ),
          combo(
            atk("Gideon initiates with a magical orb launch...\n", 12, 0, 0, 1, 700),
            atk("He follows with a series of powerful staff strikes...\n", 13, 0, 2, 3, 750),
            atk("Concludes with a devastating area-of-effect magic burst...\n", 14, 0, 0, 1, 800)
          ),
          combo(
            atk("Gideon starts with a rapid magical explosion...\n", 13, 0, 0, 1, 650),
            atk("He continues with a series of quick melee attacks...\n", 14, 0, 2, 3, 700),
            atk("Finalizes with an immense energy surge...\n", 15, 0, 0, 1, 750)
          )
        ]
      );
    },

    godfrey() {
      return boss(21903, 90,
        "Tarnished Warrior. 'Twas nobly fought. Thy rest is well deserved. A crown is warranted with strength!",
        "That will be all. Thou didst me good service, Serosh. I've given thee courtesy enough. Rrraaargh! Now I fight as Hoarah Loux! Warrior!",
        "Brave Tarnished... Thy strength befits a crown. *laughs*",
        [
          combo(
            atk("Godfrey starts with a mighty overhead smash...\n", 8, 0, 0, 1, 500),
            atk("He follows with a sweeping horizontal slash...\n", 9, 0, 2, 3, 550)
          ),
          combo(
            atk("Godfrey begins with a powerful ground pound...\n", 10, 0, 0, 1, 600),
            atk("He continues with a quick series of jabs...\n", 8, 0, 2, 3, 500),
            atk("And finishes with a crushing blow...\n", 11, 0, 0, 1, 700)
          ),
          combo(
            atk("Godfrey opens with a rapid succession of heavy strikes...\n", 9, 0, 0, 1, 500),
            atk("He transitions into a powerful stomp...\n", 10, 0, 2, 3, 550),
            atk("Ends with a devastating energy surge...\n", 11, 0, 0, 1, 650)
          ),
          combo(
            atk("Godfrey starts with a fast spinning attack...\n", 8, 0, 0, 1, 400),
            atk("He follows with a fierce thrust...\n", 9, 0, 2, 3, 450),
            atk("Finishes with a powerful slam...\n", 10, 0, 0, 1, 500)
          )
        ],
        [
          combo(
            atk("Hoarah Loux starts with a ground-shaking roar...\n", 12, 0, 0, 1, 700)
          ),
          combo(
            atk("Hoarah Loux performs a quick forward charge...\n", 13, 0, 0, 1, 750),
            atk("He follows with a high-speed swipe...\n", 14, 0, 2, 3, 800)
          ),
          combo(
            atk("Hoarah Loux roars and creates a shockwave...\n", 14, 0, 0, 1, 800),
            atk("He follows with a series of high-speed claw slashes...\n", 15, 0, 2, 3, 850),
            atk("Ends with a devastating charge...\n", 16, 0, 0, 1, 900)
          ),
          combo(
            atk("Hoarah Loux starts with a massive explosion of energy...\n", 15, 0, 0, 1, 800),
            atk("He continues with a series of powerful attacks...\n", 16, 0, 2, 3, 850),
            atk("Concludes with a colossal final slam...\n", 17, 0, 0, 1, 900)
          ),
          combo(
            atk("Hoarah Loux starts with a massive energy explosion...\n", 15, 0, 0, 1, 800),
            atk("He continues with a spinning tail swipe...\n", 16, 0, 2, 3, 850),
            atk("Follows with a powerful stomp...\n", 17, 0, 0, 1, 900),
            atk("Concludes with a high-speed charge...\n", 18, 0, 2, 3, 950)
          )
        ]
      );
    },

    malenia() {
      return boss(33251, 95,
        "I am Malenia, Blade of Miquella...",
        "Wait. *The scarlet bloom flowers once more* You will witness true horror. Now, rot!",
        "Your strength, extraordinary... The mark...of a true Lord... O, dear Miquella... O, dearest Miquella, my brother... I'm sorry. I finally met my match...",
        [
          combo(
            atk("Malenia executes a quick slash...\n", 30, 0, 0, 1, 800)
          ),
          combo(
            atk("Malenia performs a rapid series of slashes...\n", 32, 0, 0, 1, 850),
            atk("Followed by a powerful downward strike...\n", 34, 0, 2, 3, 900)
          ),
          combo(
            atk("Malenia starts with a series of swift slashes...\n", 35, 0, 0, 1, 850),
            atk("Unleashes a spinning attack...\n", 37, 0, 2, 3, 900),
            atk("Ends with a powerful thrust...\n", 39, 0, 0, 1, 950)
          ),
          combo(
            atk("Malenia begins with a rapid series of slashes...\n", 40, 0, 0, 1, 900),
            atk("Follows with a spinning whirlwind attack...\n", 42, 0, 2, 3, 950),
            atk("Unleashes a series of powerful thrusts...\n", 44, 0, 0, 1, 1000),
            atk("Concludes with a devastating finishing move...\n", 46, 0, 2, 3, 1050)
          )
        ],
        [
          combo(
            atk("Malenia releases a swift scarlet rot strike...\n", 28, 0, 0, 1, 800)
          ),
          combo(
            atk("Malenia performs a series of quick, rot-infused cuts...\n", 30, 0, 0, 1, 850),
            atk("Followed by a sharp, rot-drenched stab...\n", 32, 0, 2, 3, 900)
          ),
          combo(
            atk("Malenia begins with a wide, rot-charged slash...\n", 33, 0, 0, 1, 850),
            atk("Unleashes a rapid series of slashes...\n", 35, 0, 2, 3, 900),
            atk("Ends with a powerful rot burst...\n", 37, 0, 0, 1, 950)
          ),
          combo(
            atk("Malenia starts with a large rot-infused explosion...\n", 36, 0, 0, 1, 850),
            atk("Followed by a rapid sequence of slashes...\n", 38, 0, 2, 3, 900),
            atk("Then a powerful, sweeping rot attack...\n", 40, 0, 0, 1, 950),
            atk("Concludes with a massive rot detonation...\n", 42, 0, 2, 3, 1000)
          )
        ]
      );
    },

    radagon() {
      return boss(35339, 100,
        "The god of this world has taketh another's life.",
        "Radagon falls to the ground, yet an Elden Beast creeps out from inside of his soul.",
        "GOD SLAIN",
        [
          combo(
            atk("Radagon swings his hammer with a mighty overhead blow...\n", 8, 0, 0, 1, 500)
          ),
          combo(
            atk("Radagon charges up and slams his hammer into the ground...\n", 10, 0, 0, 1, 600),
            atk("He follows with a quick side swipe...\n", 9, 0, 2, 3, 550)
          ),
          combo(
            atk("Radagon begins with a powerful holy shockwave...\n", 12, 0, 0, 1, 650),
            atk("He then performs a series of rapid hammer slams...\n", 11, 0, 2, 3, 700),
            atk("Ends with a devastating energy blast...\n", 13, 0, 0, 1, 750)
          ),
          combo(
            atk("Radagon starts with a massive ground-shaking slam...\n", 14, 0, 0, 1, 750),
            atk("He continues with a series of powerful vertical strikes...\n", 15, 0, 2, 3, 800),
            atk("Follows with a holy explosion...\n", 16, 0, 0, 1, 850),
            atk("Concludes with a sweeping shockwave...\n", 17, 0, 2, 3, 900)
          )
        ],
        [
          combo(
            atk("Elden Beast releases a quick burst of cosmic energy...\n", 15, 0, 0, 1, 500)
          ),
          combo(
            atk("Elden Beast fires a series of energy orbs...\n", 18, 0, 0, 1, 600),
            atk("Follows with a sweeping cosmic beam...\n", 19, 0, 2, 3, 650)
          ),
          combo(
            atk("Elden Beast starts with a celestial shockwave...\n", 20, 0, 0, 1, 700),
            atk("Unleashes a rapid volley of star shards...\n", 21, 0, 2, 3, 750),
            atk("Ends with a powerful cosmic explosion...\n", 22, 0, 0, 1, 800)
          ),
          combo(
            atk("Elden Beast begins with an enormous cosmic quake...\n", 25, 0, 0, 1, 750),
            atk("Followed by a series of intense energy blasts...\n", 26, 0, 2, 3, 800),
            atk("Then a rapid discharge of celestial shards...\n", 27, 0, 0, 1, 850),
            atk("Concludes with a massive cosmic upheaval...\n", 28, 0, 2, 3, 900)
          ),
          combo(
            atk("Elden Beast releases a quick burst of star energy...\n", 18, 0, 0, 1, 550)
          ),
          combo(
            atk("Elden Beast fires a rapid series of energy pulses...\n", 20, 0, 0, 1, 600),
            atk("Followed by a celestial wave...\n", 21, 0, 2, 3, 650)
          ),
          combo(
            atk("Elden Beast begins with a celestial eruption...\n", 23, 0, 0, 1, 700),
            atk("Unleashes a rapid barrage of cosmic rays...\n", 24, 0, 2, 3, 750),
            atk("Ends with a massive stellar explosion...\n", 25, 0, 0, 1, 800)
          ),
          combo(
            atk("Elden Beast starts with an immense cosmic upheaval...\n", 30, 0, 0, 1, 750),
            atk("Continues with a rapid succession of energy pulses...\n", 31, 0, 2, 3, 800),
            atk("Unleashes a storm of star shards...\n", 32, 0, 0, 1, 850),
            atk("Concludes with a final, cataclysmic cosmic burst...\n", 33, 0, 2, 3, 900)
          )
        ]
      );
    },
  };

  // ----- Narration (Story.java) -----

  const Story = {
    INTRO: [
      "In the beginning, there was only a single Erdtree.",
      "Until one day, an eternal dropped from the Erdtree.",
      "By nature, all Eternals seek to overthrow the Erdtree and destroy all others.",
      "It is said that when a Tarnished ascends to divinity, they shall wreak havoc and chaos upon all.",
      "At long last, you are that Tarnished.",
      "Collect your Rune Fragments, level your strength, and collect the weapons you desire.",
      "You will need all the power you can muster to overthrow the Erdtree.",
      "The lands beyond the Erdtree are fraught with peril, and only those of great fortitude and cunning will prevail.",
    ],

    TUTORIAL: [
      "Tutorial",
      "1. Combos",
      "   - Combos are sequences of attacks executed by bosses.",
      "   - Each combo has a specific order and timing.",
      "   - Pay attention to the attack patterns and prepare to dodge or counter.",
      "2. Timing",
      "   - Boss attacks are divided into phases: charge-up, attack, and cooldown.",
      "   - During the charge-up phase, you have time to react and prepare.",
      "   - React quickly during the attack phase to avoid damage.",
      "   - After the attack, the boss will enter a cooldown phase where they are vulnerable.",
      "3. Types of Attacks",
      "   - Light Attack: Fast but less powerful. Useful for quick hits.",
      "   - Heavy Attack: Slower but more powerful. Can break through defenses.",
      "   - Special Attack: Fast and more powerful. Costs Focus.",
      "   - Heal: You can heal HP or FP during combat .",
      "   - Dodge: Use the dodge feature to evade attacks. You can dodge in two specific directions.",
      "   - Inputs: If you ever input anything that isn't an option, you will be sent back to the original decison.",
      "4. Scaling",
      "   - Your attacks and damage scale with your stats.",
      "   - The more you level up and allocate runes to your stats, the stronger your attacks become.",
      "   - Bosses also have their damage scaling based on their level and phase.",
    ],

    CREATION_COMPLETE: ["You have completed the character creation process."],

    JOURNEY_TO_MARGIT: [
      "Good luck, Tarnished.",
      "You begin your journey in a world where the shadows of past glories linger, haunted by forgotten heroes and lost legends.",
      "As you traverse the crumbling ruins and treacherous landscapes, you will encounter myriad foes and allies, each with their own motives and secrets.",
      "Some will seek to aid you in your quest, offering wisdom and guidance, while others will test your resolve, eager to see you falter.",
      "Through trials and tribulations, your strength will grow, and your skills will be honed in the crucible of battle.",
      "One such trial awaits you at the bridge that leads to the castle's threshold - a test of your mettle against a formidable adversary.",
      "He is known as Margit, the Fell Omen, a guardian of the path to greater power and the threshold to the true depths of your destiny.",
      "Margit wields arcane sorcery and devastating melee strikes, a formidable challenge for any who dare to seek the Erdtree's throne.",
      "Prepare yourself for the fight ahead, for Margit will not yield easily. His presence signifies the first true test of your ascension.",
      "Embrace the power within you, sharpen your weapons, and steady your heart. The battle to come will define your path forward.",
      "With each clash of steel and surge of magic, you will edge closer to the Erdtree's glory - or be cast into darkness.",
      "The heavy mist swirling around the bridge thickens as you approach the castle's looming gates.",
      "The sky above darkens, casting an ominous shadow over the landscape. With each step, the distant thunder rumbles, echoing your racing heartbeat.",
      "At the end of the bridge stands Margit, the Fell Omen, a figure of imposing stature and formidable presence.",
      "His eyes, glowing with arcane fury, lock onto you as you draw closer. The air crackles with latent magic, and his cloak flutters like a storm in anticipation.",
      "Margit's voice, like gravel grinding on stone, pierces the silence. 'Foul Tarnished, in search of the Elden Ring. Emboldened by the flame of ambition. Someone must extinguish thy flame. Let it be Margit the Fell'",
      "He raises his staff high, and the ground trembles as dark energy begins to coalesce around him.",
      "His weapons - enchanted with eldritch power - shine menacingly under the stormy sky.",
      "The bridge beneath you groans, ready to bear witness to the clash that will determine your fate.",
      "With a final, defiant glance, Margit prepares for the battle that will test every ounce of your strength and skill.",
      "It's time. Face your fears, summon your resolve, and step into the crucible of combat. The fate of your journey - and perhaps the very world - rests on this moment.",
    ],

    AFTER_MARGIT: [
      "The clash with Margit has left the bridge in ruins, and the air is thick with the remnants of magic and the echoes of battle.",
      "As the dust settles and the mist begins to clear, you stand victorious but weary. Your body aches from the exertion, and your heart still races from the fight.",
      "From the shadows of the crumbling castle, a figure emerges - a woman of ethereal grace and quiet strength. She moves with an air of calm assurance, her presence a stark contrast to the chaos that just transpired.",
      "Well fought, Tarnished, she says softly, her voice carrying a soothing melody amidst the remnants of the storm. I am Melina, and I have come to aid you on your journey.",
      "She approaches you with a warm, reassuring smile. The path ahead is fraught with even greater dangers and challenges. You have proven your worth, but the road to the Erdtree will test you further.",
      "Melina extends her hand, and as she does, a soft, golden light begins to emanate from the ground nearby.",
      "Come, she beckons, there is a Site of Grace where you may find respite and guidance. It will restore your strength and allow you to prepare for the trials yet to come.",
      "You follow Melina to the Site of Grace, a serene haven amid the desolation. The site is marked by a gentle, radiant light that seems to soothe the very essence of your being.",
      "As you approach, the light envelops you, and you feel a profound sense of peace and renewal. Your wounds heal, your spirit is lifted, and your resolve is strengthened.",
      "Rest here, Melina advises. The grace of this place will provide you with clarity and insight. Use it to reflect on your journey and to prepare for the path that lies ahead.",
      "With the Site of Grace to guide you, you take a moment to catch your breath, knowing that the true depth of your destiny awaits beyond the horizon.",
      "Melina's presence remains a comforting assurance as you settle into the tranquil light, ready to face the challenges that will come with renewed vigor and determination.",
      "When you are ready, Melina says softly, I will be here to guide you. The road is long, but you are not alone.",
      "With that, she steps back into the shadows, leaving you to contemplate your next move as the light of the Site of Grace casts a warm glow around you.",
      "The journey continues, and with each step, the path to the Erdtree becomes clearer, illuminated by the strength and wisdom you have gained.",
    ],

    BEFORE_GODRICK: [
      "As you recover from the fierce battle with Margit, the Grafted Castle comes into view. Its towering spires and dark, looming presence reflect the harshness of its inhabitants. The very air around it seems thick with the weight of countless battles fought and lost.",
      "Melina walks beside you, her gaze steady as she surveys the castle's twisted silhouette. 'This place is steeped in sorrow and despair,' she says. 'Godrick the Grafted, once a noble warrior, has become a grotesque parody of his former self. His power is both fearsome and corrupting.'",
      "You navigate the castle's foreboding corridors, the silence broken only by the distant clamor of rusted armor and the soft echoes of unseen creatures. The walls, once grand, are now lined with grim trophies and macabre relics, hinting at the horrors within.",
      "Entering the throne room, you are confronted by Godrick, his massive, grafted body a monstrous amalgamation of metal and flesh. His eyes, burning with malevolent fury, fixate on you as he roars in defiance.",
      "Mighty Dragon, thou'rt a trueborn heir. Lend me thy strength, o kindred. Deliver me unto greater heights. ...Well. A lowly Tarnished, playing as a lord. I command thee, kneel! I am the lord of all that is golden!",
      "The battle with Godrick is a harrowing ordeal. Each of his blows is delivered with an overwhelming force, the weight of his weapon sending tremors through the castle's very foundation. His strength and resilience are matched only by his cunning and brutality.",
    ],

    AFTER_GODRICK: [
      "Melina's tactical advice proves invaluable as you struggle against Godrick's onslaught. Her insights help you to dodge and counter his devastating attacks, and through sheer determination and skill, you manage to overcome the Grafted King.",
      "The throne room falls silent, the echoes of your battle lingering in the air. As Godrick's massive form crumbles, Melina approaches with a look of relief. 'We have triumphed over a significant foe,' she says. 'But our journey is far from over. The path ahead leads us to the shattered remnants of the Academy of Raya Lucaria.'",
    ],

    BEFORE_RENNALA: [
      "Your journey leads you to the Academy of Raya Lucaria, a place of ancient magic and long-forgotten knowledge. The academy's once-majestic halls now lie in ruin, a haunting testament to its past grandeur.",
      "Melina guides you through the desolate grounds, her presence a beacon of calm in the midst of the academy's eerie silence. 'Rennala, Queen of the Full Moon, resides within these ruins,' she says. 'Her mastery of sorcery is formidable, and her wrath is feared by all who enter her domain.'",
      "As you navigate through the crumbling corridors and shattered classrooms, the very air seems to hum with the remnants of powerful spells. The walls are adorned with arcane symbols and magical artifacts, their glow casting flickering shadows on the debris-strewn floor.",
      "Hush, little culver. I'll soon birth thee anew, a sweeting fresh and pure...",
      "Entering the grand chamber of the academy, Rennala appears, her form wreathed in a luminous aura. The Queen of the Full Moon, her eyes glowing with arcane power, exudes an air of regal authority and danger.",
      "The battle with Rennala is a mesmerizing spectacle of sorcery and arcane might. Her attacks are both beautiful and deadly, weaving intricate patterns of magic that challenge your every move. The room becomes a chaotic dance of light and dark as she unleashes her full power.",
    ],

    AFTER_RENNALA: [
      "With Melina's strategic guidance and your own resilience, you navigate the storm of spells and find the moments to strike. Rennala's defeat brings a profound silence, her ethereal form dissipating into the ether. The academy, though still in ruins, feels a little lighter for your victory.",
      "Melina approaches, her gaze filled with a mix of satisfaction and concern. 'Rennala's fall is a crucial step in our journey,' she says. 'Yet the road ahead will only grow more treacherous. Prepare yourself for the trials that lie beyond.'",
    ],

    BEFORE_RED_WOLF: [
      "The path now takes you to the treacherous realm of the Red Wolf of Radagon. The landscape is a bleak and desolate wasteland, scorched by fiery eruptions and littered with the remnants of ancient battles.",
      "Melina remains a steady guide as you traverse the harsh terrain. 'The Red Wolf of Radagon is a swift and relentless adversary,' she warns. 'His attacks are quick and deadly, and his mastery of combat will test your agility and reflexes.'",
      "As you reach the arena, the ground quakes with the Red Wolf's ferocious roars. The beast, its fur bristling with dark energy, emerges from the shadows, its movements a blur of speed and ferocity.",
      "The battle with the Red Wolf is a relentless test of agility and strategy. His attacks are fast and unpredictable, forcing you to constantly move and adapt. Melina's tactical advice helps you anticipate his movements and find the right moments to strike.",
    ],

    AFTER_RED_WOLF: [
      "After an intense and exhausting fight, you manage to defeat the Red Wolf, his form collapsing into a pool of dark energy. The battlefield falls silent, the oppressive atmosphere lifting slightly as you catch your breath.",
      "Melina approaches, her expression a mixture of relief and encouragement. 'You've faced another formidable challenge and emerged victorious,' she says. 'But the journey is far from over. The next trial will be even more daunting - the domain of Rykard, the God-Devouring Serpent.'",
    ],

    BEFORE_SERPENT: [
      "Your journey now leads you into the heart of darkness, the domain of Rykard, the God-Devouring Serpent. The landscape is a grotesque vision of twisted flesh and dark, pulsating energy, creating a nightmarish environment.",
      "Melina's guidance is a beacon of hope as you navigate through the nightmarish surroundings. 'Rykard's domain is a place of immense power and corruption,' she warns. 'His form is both terrifying and blasphemous. Prepare yourself for a battle of epic proportions.'",
      "As you enter the cavernous lair, Rykard's colossal serpentine form emerges from the shadows, his presence radiating a dark and oppressive energy. The ground trembles beneath his massive body as he prepares to confront you.",
      "The battle with Rykard is a grueling test of endurance and strategy. His immense size and dark powers create a chaotic and challenging fight. Melina's guidance helps you navigate through the tumultuous battle, allowing you to find the moments to strike effectively.",
    ],

    AFTER_SERPENT: [
      "After a fierce and prolonged struggle, you finally defeat Rykard, his form collapsing into a seething mass of dark energy. The lair falls silent, the oppressive atmosphere lifting slightly as you catch your breath.",
      "Melina approaches, her expression a mix of grim satisfaction and concern. 'Rykard's defeat is a significant victory,' she says. 'But the final challenges lie ahead. Stay strong and focused for what is to come.'",
    ],

    BEFORE_MOHG: [
      "The path now leads you to the subterranean lair of Mohg, Lord of Blood. The air is thick with the scent of blood and corruption, and the walls are lined with dark, pulsating veins. The environment is both grotesque and foreboding.",
      "Melina's presence is a reassuring guide as you navigate through the labyrinthine tunnels. 'Mohg's blood magic is a powerful and dangerous force,' she warns. 'This battle will test every ounce of your strength and resolve.'",
      "As you reach the heart of Mohg's lair, the Lord of Blood emerges from the shadows, his form cloaked in a dark aura of blood magic. His presence is both terrifying and commanding, a testament to his mastery of his dark powers.",
      "Dearest Miquella. You must abide alone a while. Welcome, honored guest. To the birthplace of our dynasty!",
    ],

    AFTER_MOHG: [
      "The battle with Mohg is a grueling and intense fight. His blood magic and relentless attacks push you to your limits, requiring you to use every ounce of your strength and skill to survive. Melina's guidance helps you navigate through the chaos and find the openings to strike.",
      "After a taxing and prolonged battle, you manage to defeat Mohg, his form dissolving into a cloud of dark, crimson mist. The lair falls silent, the oppressive atmosphere lifting slightly as you catch your breath.",
      "Melina approaches, her expression one of quiet resolve. 'Mohg's defeat is a testament to your strength and determination,' she says. 'But the final challenge lies ahead - the domain of Morgott, the Omen King.'",
    ],

    BEFORE_MORGOTT: [
      "With the Fire Giant defeated, you now stand on the precipice of a new challenge. The path ahead leads to Morgott, the Omen King. This battle will push you to the edge.",
      "The landscape transforms as you approach Morgott's domain. The air is thick with dark energy, and the atmosphere feels heavy with the weight of impending conflict.",
      "Melina stands by your side, her presence a steadying force. 'Morgott is a formidable foe,' she says. 'His power and dark magic will test everything you have learned.'",
      "As you enter the arena, Morgott emerges from the shadows, his form imposing and malevolent. His dark energy crackles with every movement, signaling the fierce battle to come.",
      "Graceless Tarnished. What is thy business with these thrones? Ahh... Godrick the Golden. The twin prodigies, Miquella and Malenia. General Radahn. Praetor Rykard. Lunar Princess Ranni. Wilful traitors, all. Thy kind are all of a piece. Pillagers. Emboldened by the flame of ambition. Have it writ upon thy meagre grave: Felled by King Morgott! Last of all kings.",
    ],

    AFTER_MORGOTT: [
      "The clash with Morgott is fierce and relentless. His attacks are swift and devastating, requiring you to use every ounce of skill and strategy to survive.",
      "Melina's guidance proves invaluable as you navigate through Morgott's dark magic and brutal strikes. Each moment of the battle is a test of your endurance and resolve.",
      "After a grueling fight, Morgott falls, his dark form dissipating into the shadows. The arena falls silent, and a sense of grim satisfaction settles over you.",
      "Melina approaches, her expression a mix of relief and concern. 'Morgott's defeat is significant,' she says. 'But the path ahead is even more daunting. The Fire Giant awaits us.'",
    ],

    BEFORE_FIRE_GIANT: [
      "The path now leads to the fiery domain of the Fire Giant. The landscape is a nightmarish vision of molten rock and burning ash. The heat is almost unbearable.",
      "Melina's presence remains a beacon of hope as you traverse the treacherous terrain. 'The Fire Giant is a colossal foe,' she warns. 'This battle will push you to your limits.'",
      "As you approach the arena, the ground shakes violently. The Fire Giant emerges from the flames, his immense form radiating intense heat and power.",
      "The battle with the Fire Giant is a test of endurance and strategy. His attacks are powerful, and the landscape itself seems to fight against you. Melina's guidance helps you find the right moments to strike and dodge his blows.",
    ],

    AFTER_FIRE_GIANT: [
      "The battle is a relentless struggle, with the Fire Giant's attacks shaking the very ground beneath you. Each moment is a fight for survival.",
      "Molten rock and fiery eruptions add to the chaos of the battle. Every strike and dodge requires careful timing and strategy. Melina's presence is a source of strength and support.",
      "After an exhausting fight, the Fire Giant falls, his massive form collapsing into a smoldering heap. The heat begins to dissipate as you catch your breath.",
      "Melina approaches, her expression a mix of relief and concern. 'The Fire Giant's defeat is a monumental achievement,' she says. 'But our journey is nearing its end. The final challenge awaits us at the base of the Erdtree.'",
    ],

    ERDTREE_DECISION: [
      "With the Fire Giant defeated, you and Melina stand before the colossal Erdtree. Its branches reach high into the sky, a symbol of both life and power.",
      "Melina looks at you, her eyes filled with a mixture of determination and sadness. 'We have come so far,' she says, her voice trembling slightly. 'To burn down the Erdtree, a great sacrifice must be made.'",
      "The weight of her words is almost unbearable. The thought of losing Melina, who has become so integral to your journey, is heart-wrenching.",
      "Her presence has been a constant source of strength and guidance. Her selflessness and unwavering resolve are both inspiring and heartbreaking.",
      "As you prepare for the final confrontation, Melina's demeanor is calm but tinged with sorrow. 'This is the path we must take,' she says. 'But know that your strength and resolve have been the true driving force behind our journey.'",
      "The moment of decision arrives. Melina stands before the Erdtree, ready to make the ultimate sacrifice. The air is thick with tension and emotion.",
      "Melina's eyes meet yours, filled with a mixture of determination and acceptance. 'If you choose to take my place,' she says softly, 'know that it will change everything.'",
      "The choice is heart-wrenching. Letting Melina sacrifice herself is painful, but it is the only way to achieve your goal. Alternatively, taking her place means facing the consequences of becoming a Lord of Chaos.",
      "Melina's resolve is unwavering. 'If you choose to let me proceed,' she says, 'I will accept my fate with honor. Your journey will continue, and the world will be forever changed.'",
      "You are left with a choice that will define the future. The decision to let Melina sacrifice herself or to take her place will shape the outcome of your journey and the fate of the world.",
    ],

    ENDING_LET_MELINA: [
      "If you chose to let Melina sacrifice herself: Melina's resolve is unwavering as she steps forward, ready to make the ultimate sacrifice. 'Thank you for standing by me until the end,' she says, her voice trembling with emotion.",
      "As Melina initiates the sacrifice, the Erdtree is engulfed in a blinding inferno. Her form merges with the flames, and the world changes as the balance of power shifts.",
      "The forces of chaos are unleashed, and you remain, bearing witness to the new reality that emerges from the ashes. Though Melina is gone, her sacrifice has paved the way for a new era.",
      "The journey has reached its end. The world has been irrevocably changed, and you must now navigate this new reality shaped by your choices and the sacrifices made.",
      "The path forward is uncertain, but your role - whether as a Lord of Chaos or as a witness to Melina's sacrifice - will define the future of this world.",
    ],

    ENDING_TAKE_HER_PLACE: [
      "As you step forward, Melina's expression changes to one of profound relief and sadness. 'You have chosen a path of great consequence,' she says, her voice filled with emotion.",
      "Melina steps back, allowing you to prepare for the ultimate sacrifice. A surge of power and chaos envelops you as the Erdtree is consumed by raw, untamed energy.",
      "The world shifts and changes, the balance of power is altered. You find yourself transformed into a Lord of Chaos, living but forever changed. The world around you is a new, chaotic reality.",
      "The sacrifice was immense, and the consequences are profound. The forces of chaos are unleashed, and you must navigate this new reality with the weight of your choice bearing heavily on you.",
    ],

    DECISION_RETRY: ["There is no other way.. you must decide"],

    BEFORE_BEAST_CLERGYMAN: [
      "With the Erdtree's destruction, the path leads you to a new and foreboding challenge. The Beast Clergyman awaits. His power is vast and his purpose shrouded in mystery.",
      "The landscape transforms once again. Dark clouds loom overhead, and a sense of impending doom fills the air. The Beast Clergyman's domain is a place of shadows and ancient power.",
      "Without Melina by your side, the weight of your journey feels even heavier. The trials you have faced have been immense, and the challenges ahead are formidable.",
      "As you approach the arena, he emerges from the shadows. His form is both majestic and terrifying, a blend of beastly and divine. His presence is both a challenge and a test of your resolve.",
      "Thou, who approacheth Destined Death. I will not have it stolen from me again.",
      "The battle with the Clergyman is fierce and unrelenting. His attacks are swift and devastating, each move a blend of arcane power and physical might.",
      "The fight is grueling, requiring every ounce of your strength and strategy. The Clergyman's form shifts and changes, making each phase of the battle unique and challenging.",
    ],

    AFTER_BEAST_CLERGYMAN: [
      "As the battle progresses, Maliketh falls. His immense power is subdued, but the victory comes at a cost. The weight of your journey presses heavily upon you as you prepare for the next challenge.",
    ],

    BEFORE_GIDEON: [
      "With Maliketh defeated, you now face Sir Gideon Ofnir, the All-Knowing. His knowledge of the world's secrets is vast, and his power is formidable.",
      "The arena for this battle is a grand, ancient hall, filled with relics and symbols of bygone eras. The air is thick with knowledge and power.",
      "The absence of Melina is a poignant reminder of the sacrifices made and the trials yet to come. Sir Gideon's challenge will test not only your strength but also your resolve and understanding of the world.",
      "As Sir Gideon appears, his gaze is piercing and filled with ancient wisdom. He stands as a guardian of the knowledge that will determine the future of the world.",
      "Ahh, I knew you'd come. To stand before the Elden Ring. To become Elden Lord. What a sad state of affairs. I commend your spirit, but alas, none shall take the throne. Queen Marika has high hopes for us. That we continue to struggle. Unto eternity.",
      "The battle with Sir Gideon is a test of both intellect and combat prowess. His attacks are strategic, and his knowledge of the world's secrets makes him a formidable adversary.",
    ],

    AFTER_GIDEON: [
      "The fight is intense, requiring you to anticipate his moves and counter his strategies. Sir Gideon's power is vast, and each moment of the battle is a test of your skills and understanding.",
      "After a prolonged and challenging fight, Sir Gideon falls. His form dissipates, leaving behind the remnants of his vast knowledge and power.",
    ],

    BEFORE_GODFREY: [
      "With Sir Gideon defeated, you now face Godfrey, the First Elden Lord. This battle is a culmination of your journey's trials.",
      "The arena is a grand, ancient battlefield, echoing with the memories of past glories and epic conflicts. The atmosphere is charged with the power of the ancient Elden Lords.",
      "Without Melina's guidance, the weight of this challenge feels even more immense. Godfrey's legacy and power are legendary, and this battle will be a true test of your worth.",
      "As Godfrey appears, his presence is awe-inspiring and commanding. His form radiates power, and his strength as a warrior is unparalleled. Prepare for the ultimate test of your abilities.",
      "It's been a long while, Morgott. Long and hard didst thou fight. Tarnished Warrior. Spurned by the grace of gold. Be assured, the Elden Ring resteth close at hand. Alas, I am returned. To be granted audience once more. Upon my name as Godfrey, The first Elden Lord!",
      "The battle with Godfrey is a test of raw strength and combat skill. His attacks are powerful and relentless, requiring you to use every ounce of your strength and strategy.",
      "The fight is fierce and unyielding, with Godfrey's prowess as a warrior pushing you to your limits. Each strike and maneuver must be executed with precision and determination.",
    ],

    AFTER_GODFREY: [
      "After a grueling and intense battle, Godfrey falls, his form dissipating into the annals of history. The victory is hard-won, and the path ahead remains uncertain.",
    ],

    BEFORE_MALENIA: [
      "With Godfrey defeated, the final challenge awaits. Malenia, Blade of Miquella, and Malenia, Goddess of Rot, are the ultimate trials of your journey.",
      "The arena is a nightmarish vision of decay and rot. The air is thick with the stench of corruption and the remnants of Malenia's power.",
      "The absence of Melina is keenly felt. Malenia's challenge will test every aspect of your strength and resolve, and the stakes are higher than ever.",
      "As Malenia emerges, her form is both beautiful and terrifying, a manifestation of rot and divine power. Prepare for the final confrontation that will shape the world's fate.",
      "I dreamt for so long. My flesh was dull gold...and my blood, rotted. Corpse after corpse, left in my wake... As I awaited... his return. ... Heed my words. I am Malenia. Blade of Miquella. And I have never known defeat.",
      "The battle with Malenia is a test of endurance and skill. Her attacks are swift and devastating, and her power as the Goddess of Rot is formidable.",
    ],

    AFTER_MALENIA: [
      "The fight is intense, with Malenia's corruption spreading and affecting the battlefield. Each moment requires careful strategy and precise execution to overcome her power.",
      "After a harrowing and challenging fight, Malenia falls, her form dissolving into the rotting landscape. The victory is bittersweet, and the path to the final confrontation remains clear.",
    ],

    BEFORE_RADAGON: [
      "With Malenia defeated, you now face the ultimate challenge: Radagon of the Golden Order. This final battle will determine the fate of the world.",
      "The arena is a cosmic expanse, filled with the remnants of divine power and celestial energy. The air is charged with the force of the Elden Beast's power.",
      "The absence of Melina is a stark reminder of the sacrifices made and the choices that have led to this moment. The final confrontation will test everything you have fought for.",
      "As Radagon emerges, his form are majestic and terrifying, a culmination of divine and cosmic power. Prepare for the ultimate battle that will decide the world's fate.",
      "The final battle is a monumental clash of divine and cosmic forces. Radagon's power and the Elden Beast's energy create a formidable challenge.",
    ],

    AFTER_RADAGON: [
      "The fight is epic and relentless, with every attack and maneuver requiring the utmost precision and strategy. The fate of the world rests on this battle.",
      "After a titanic struggle, Radagon and the Elden Beast fall. The cosmic energy dissipates, and the world begins to settle into a new reality. The journey's end is both victorious and tragic.",
    ],

    EPILOGUE_ELDEN_LORD: [
      "If Melina was sacrificed: You stand victorious but alone. The title of Elden Lord is yours, but the absence of Melina casts a shadow over your triumph. The world is now yours to shape, but the cost of victory is profound.",
      "The paths are now set, and the world's future is shaped by the choices made. Whether as Elden Lord or Lord of Chaos, the journey's end is a testament to the trials and sacrifices that have defined your path.",
    ],

    EPILOGUE_CHAOS: [
      "The world is consumed by death and fire, and Melina remains, transformed by the chaos. She approaches you, her form a blend of sorrow and rage.",
      "In this world of devastation, Melina's eyes burn with a promise of vengeance. 'You have brought ruin to all,' she says, her voice filled with anguish. 'The one who walks alongside flame, Shall one day meet the road of Destined Death. Good-bye.'",
    ],
  };

  global.Data = {
    STATS, STRENGTH_INDEX, ARCANE_INDEX, VIGOR_INDEX, MIND_INDEX,
    DIRECTIONS, Armory, Bestiary, Story,
  };
})(typeof window !== "undefined" ? window : globalThis);
