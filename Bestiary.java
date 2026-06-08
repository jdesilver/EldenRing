import java.util.List;

/**
 * Factory for every boss in the game. Each boss is pure data: HP, rune reward,
 * the three dialogue lines, and the phase-1 / phase-2 attack combos. Defining bosses
 * as data (rather than one subclass apiece) keeps the entire roster in one readable place.
 */
final class Bestiary {
    private Bestiary() {}

    /** An attack telegraph: its line, charge-up/cool-down time, the two directions that dodge it, and its damage. */
    private static Attack atk(String line, int chargeUp, int coolDown, int dir1, int dir2, int damage) {
        return new Attack(line, chargeUp, coolDown, dir1, dir2, damage);
    }

    /** A combo is an ordered sequence of attacks. */
    private static Combo combo(Attack... attacks) {
        return new Combo(List.of(attacks));
    }

    static Boss margit() {
        return new Boss(4174, 15,
            "Put these foolish ambitions to rest.",
            "Well, thou art of passing skill. Warrior blood must truly run in thy veins, Tarnished.",
            "I shall remember thee, Tarnished. Smouldering with thy meagre flame. Cower in Fear. Of the Night. The hands of the Fell Omen shall brook thee no quarter.",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss godrick() {
        return new Boss(3200, 20,
            "Lowly Tarnished... Thou'rt unfit even to graft... Great Godfrey, did'st thou witness?",
            "Ahh, truest of dragons. Lend me thy strength... Nnngh! Forefathers, one and all... Bear witness!",
            "...I am Lord of all that is Golden.... ...And one day, we'll return together... ...To our home, bathed in rays of gold...",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss redWolf() {
        return new Boss(2204, 30,
            "The Red Wolf of Radagon swiftly devours its prey.",
            "The Red Wolf of Radagon's eyes glow with fierce intensity.",
            "The Red Wolf of Radagon lets out a final growl as it falls.",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss rennala() {
        return new Boss(3493, 35,
            "Be not afeard, little culver. Thy fate lieth under my moon.",
            "Ahh, my beloved... Have no fear, I will hold thee. Patience. Ye will be countless born, forever and ever.\n\nRanni: Upon my name as Ranni the Witch. Mother's rich slumber shall not be disturbed by thee. Foul trespasser. Send word far and wide. Of the last Queen of Caria, Rennala of the Full Moon. And the majesty of the night she conjureth.",
            "Oh little Ranni, my dear daughter. Weave thy night into being.",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss serpent() {
        return new Boss(30439, 40,
            "Now, we can devour the gods, together!",
            "Hmm... Very well. You... Join the Serpent King, as family... Together, we will devour the very gods!",
            "No one will hold me captive. A serpent never dies. Ha ha ha...",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss radahn() {
        return new Boss(9572, 45,
            "Radahn stands tall, the sky darkened by his immense presence and the power of his gravity magic.",
            "Radahn readies his colossal weapon, ready to unleash devastating blows upon you.",
            "Radahn staggers, the force of his attacks finally taking its toll.",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss mohg() {
        return new Boss(18389, 50,
            "Miquella is mine and mine alone.",
            "Tres! Duo! Unus! Nihil! Nihil! Nihil!",
            "Ahh, I can see it, clear as day! The coming of our dynasty! Mohgwyn!",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss goldenGodfrey() {
        return new Boss(21903, 55,
            "Golden Godfrey, the regal warrior, stands with an imposing aura.",
            "He prepares to unleash a series of devastating attacks.",
            "Golden Godfrey pauses, his mighty form readying for the next onslaught.",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss morgott() {
        return new Boss(10399, 60,
            "Put these foolish ambitions to rest. May the curse seep to thy very soul. An apt reward for thy brash ambition",
            "Hrghraah! The thrones... stained by my curse... Such shame I cannot bear. Thy part in this shall not be forgiven.",
            "Tarnished, thou'rt but a fool. The Erdtree wards off all who deign approach. We are... we are all forsaken. None may claim the title of Elden Lord. Upon talking to Morgott twice: Thy deeds shall be met with failure, just as I.",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss fireGiant() {
        return new Boss(43263, 65,
            "The Fire Giant looms with fiery fury, his very presence scorching the earth.",
            "He prepares for a series of devastating fiery assaults.",
            "The Fire Giant takes a moment, his next fiery attack is imminent.",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss godskinDuo() {
        return new Boss(8000, 750,
            "The Godskin Duo emerges with synchronized, deadly attacks. Their coordination is unparalleled.",
            "The duo is preparing for a series of rapid and unpredictable strikes.",
            "The Godskin Duo falls together, the duo is done.",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss beastClergyman() {
        return new Boss(10620, 80,
            "Destined Death has taken you, too.",
            "O, Death. Become my blade, once more",
            "Witless Tarnished... Why covet Destined Death? To kill what?",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss gideon() {
        return new Boss(6226, 85,
            "My fellow, you've fought well, until now.",
            "Gideon prepares a potent strike.",
            "I know...in my bones... A Tarnished cannot become a Lord. Not even you. A man cannot kill a god...",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss godfrey() {
        return new Boss(21903, 90,
            "Tarnished Warrior. 'Twas nobly fought. Thy rest is well deserved. A crown is warranted with strength!",
            "That will be all. Thou didst me good service, Serosh. I've given thee courtesy enough. Rrraaargh! Now I fight as Hoarah Loux! Warrior!",
            "Brave Tarnished... Thy strength befits a crown. *laughs*",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss malenia() {
        return new Boss(33251, 95,
            "I am Malenia, Blade of Miquella...",
            "Wait. *The scarlet bloom flowers once more* You will witness true horror. Now, rot!",
            "Your strength, extraordinary... The mark...of a true Lord... O, dear Miquella... O, dearest Miquella, my brother... I'm sorry. I finally met my match...",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

    static Boss radagon() {
        return new Boss(35339, 100,
            "The god of this world has taketh another's life.",
            "Radagon falls to the ground, yet an Elden Beast creeps out from inside of his soul.",
            "GOD SLAIN",
            // phase 1
            List.of(
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
            ),
            // phase 2
            List.of(
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
            )
        );
    }

}
