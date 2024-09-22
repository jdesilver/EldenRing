/**EldenRing.java
 * Author: James DeSilver
 * v.6.5
 */

import java.util.*;

/**
 * Main class for running the game. Handles the player's interaction with the game world,
 * including leveling up, purchasing weapons, and combat mechanics.
 */
public class Main {

    private static final Scanner input = new Scanner(System.in);  // Static input scanner for all methods
    private static Player tarnished;  // The player's character

    /**
     * Prints text and waits for the player to press Enter before clearing the screen.
     * 
     * @param text The text to display to the player.
     */
    public static void speak(String text) {
        System.out.println(text);
        input.nextLine();
        clearScreen();
    }
    
    /**
     * Clears the console screen using ANSI escape codes.
     * Note: Might not work on all terminals.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Prompts the player to allocate runes to a stat. 
     * Validates the input to ensure the player cannot exceed the stat limits or spend more runes than they have.
     * 
     * @param runes The available runes the player can spend.
     * @param stat The current value of the stat.
     * @return The amount of runes the player chooses to allocate.
     */
    public static int getStat(int runes, int stat) {
        int amt = 0;
        while (true) {
            try {
                amt = input.nextInt();
                if (amt + stat > 99) {
                    System.out.println("Cannot go over 99.");
                } else if (amt > runes) {
                    System.out.println("Not enough runes.");
                } else if (amt < -1) {
                    System.out.println("Has to be positive or -1 to undo.");
                } else {
                    input.nextLine();
                    return amt;
                }
            } catch (InputMismatchException e) {
                speak("Invalid input. Please enter a number.");
                input.next(); // Clear invalid input
            }
        }
    }

    /**
     * Handles the player's leveling up process, allowing them to allocate runes into various stats.
     * 
     * @param tarnished The player character who is leveling up.
     * @param stats The array representing the player's current stats.
     * @return The updated stats array after the player has allocated points.
     */
    public static int[] levelUp(Player tarnished, int[] stats) {
        String[] STAT = {"Vigor", "Mind", "Endurance", "Strength", "Dexterity", "Intelligence", "Faith", "Arcane"};
        Stack<UndoState> history = new Stack<>();  // Stack to track undo states
        int runesSpent = 0;
        int currentStatIndex = 0;

        while (true) {
            while (currentStatIndex < 8) {
                try {
                    clearScreen();
                    // Display stats and remaining runes
                    System.out.print("The Stats: ");
                    for (int i = 0; i < STAT.length - 1; i++) {
                        System.out.print(STAT[i] + ", ");
                    }
                    System.out.println(STAT[STAT.length - 1]);
                    System.out.println();
                    System.out.println("Current Stats: " + Arrays.toString(stats));
                    System.out.println("Runes remaining: " + tarnished.getRunes());
                    System.out.println();
                    
                    // Prompt player for stat allocation
                    System.out.print("Points into " + STAT[currentStatIndex] + " (or enter -1 to undo): ");
                    runesSpent = getStat(tarnished.getRunes(), tarnished.getStats(currentStatIndex));
    
                    if (runesSpent == -1) {
                        if (history.isEmpty()) {
                            System.out.println("No actions to undo.");
                            continue;
                        }
                        // Undo last action
                        UndoState lastState = history.pop();
                        stats = lastState.getStats();
                        tarnished.addRunes(lastState.getRunesSpent());
                        System.out.println("Undo successful.");
                        if (currentStatIndex > 0) {
                            currentStatIndex--;
                        }
                        continue;
                    }
        
                    // Save current state to history stack before making changes
                    history.push(new UndoState(Arrays.copyOf(stats, stats.length), runesSpent));
                    stats[currentStatIndex] += runesSpent;  // Apply stat change
                    tarnished.spendRunes(runesSpent);  // Deduct runes spent
                    System.out.println("Runes remaining: " + tarnished.getRunes());
                    System.out.println();
                    currentStatIndex++;  // Move to the next stat
                } catch (InputMismatchException e) {
                    speak("Invalid input. Please enter a number.\n");
                    input.next(); // Clear invalid input
                }
            }

            // Finalize or allow stat changes
            clearScreen();
            System.out.print("The Stats: ");
            for (int i = 0; i < STAT.length - 1; i++) {
                System.out.print(STAT[i] + ", ");
            }
            System.out.println(STAT[STAT.length - 1]);
            System.out.println();
            System.out.println("Current Stats: " + Arrays.toString(stats));
            System.out.println();
            if (!choice()) {
                currentStatIndex = 7; // Allows player to go back and adjust
            } else {
                break;  // Confirm and exit
            }
        }
        
        // Update HP and FP based on Vigor and Mind stats
        tarnished.setHp(tarnished.getHp() + tarnished.getStats(0) * 30);
        tarnished.setFp(tarnished.getFp() + tarnished.getStats(1) * 30);
        return stats;
    }

    /**
     * Represents a snapshot of the player's state for undo functionality.
     */
    public static class UndoState {
        private final int[] stats;  // Snapshot of the player's stats
        private final int runesSpent;  // Runes spent during this state
    
        /**
         * Constructs a new UndoState instance.
         * 
         * @param stats The player's current stats.
         * @param runesSpent The amount of runes spent.
         */
        public UndoState(int[] stats, int runesSpent) {
            this.stats = stats;
            this.runesSpent = runesSpent;
        }
    
        /**
         * Gets the saved stats from this state.
         * 
         * @return The stats array.
         */
        public int[] getStats() {
            return stats;
        }
    
        /**
         * Gets the runes spent during this state.
         * 
         * @return The amount of runes spent.
         */
        public int getRunesSpent() {
            return runesSpent;
        }
    }

        /**
     * Allows the player to purchase a new weapon. Displays available weapons
     * and validates the player's choice and rune count.
     * 
     * @param tarnished The player character purchasing the weapon.
     * @param weaponWheel An array of available weapons for purchase.
     * @return The new weapon purchased by the player.
     */
    public static Weapon buyWeapon(Player tarnished, Weapon[] weaponWheel) {
        System.out.println("Runes: " + tarnished.getRunes() + "\n");
        System.out.println("Choose a Weapon: (-1 to back out)\n");

        // Display weapon options
        for (int i = 0; i < weaponWheel.length; i++) {
            System.out.println((i + 1) + ". " + weaponWheel[i].getName() + " - Price: " + weaponWheel[i].getPrice() + " Runes");
        }
        System.out.println();

        // Handle player input for weapon selection
        while (true) {
            try {
                int choice = input.nextInt();
                if (choice == -1) return tarnished.getHand();  // Cancel purchase
                choice--;  // Convert to zero-based index
                
                // Validate weapon choice
                if (choice < 0 || choice >= weaponWheel.length) {
                    speak("Invalid choice. Please select a valid weapon.\n");
                    continue;
                }

                // Check if player has enough runes to buy the weapon
                if (tarnished.getRunes() < weaponWheel[choice].getPrice()) {
                    System.out.println("Not enough runes. Choose a different weapon.\n");
                    continue;
                }
                input.nextLine();  // Clear input buffer
                
                // Confirm purchase
                if (!choice()) {
                    continue;
                }

                // Handle rune transfer and weapon swap
                tarnished.spendRunes(weaponWheel[choice].getPrice());
                tarnished.addRunes(tarnished.getHand().getPrice());
                Weapon newWeapon = weaponWheel[choice];
                weaponWheel[choice] = tarnished.getHand();  // Swap weapons in the wheel
                return newWeapon;

            } catch (InputMismatchException ex) {
                speak("Invalid input. Please enter a valid number.\n");
                input.next();  // Clear invalid input
            }
        }
    }

    /**
     * Prompts the player to confirm their choice with a Yes/No question.
     * 
     * @return true if the player confirms (Y), false if the player declines (N).
     */
    public static boolean choice() {
        while (true) {
            System.out.println("Are you sure? (Y or N)\n");
            String answer = input.nextLine().trim().toUpperCase();
            if (answer.equals("Y")) {
                return true;
            } else if (answer.equals("N")) {
                return false;
            } else {
                speak("Invalid input. Please enter Y or N.\n");
            }
        }
    }

    /**
     * Manages the combat sequence between the player and a boss. 
     * Restores the player's original stats if the player is defeated.
     * 
     * @param weaponWheel The array of available weapons.
     * @param player The player character.
     * @param boss The boss the player is fighting.
     */
    public static void fight(Weapon[] weaponWheel, Player player, Boss boss) {
        // Save player's original stats to restore after combat
        int normalHp = player.getHp();
        int normalFp = player.getFp();
        int healingTotal = player.getHealingTotal();
        int bossHp = boss.getHp();

        Combat combat = new Combat(player, boss);  // Initialize combat system

        while (true) {
            if (!combat.start()) {  // If player is defeated
                // Restore original stats
                player.setHp(normalHp);
                player.setFp(normalFp);
                player.setHealingTotal(healingTotal);
                boss.setHp(bossHp);
                boss.setPhase(1);  // Reset boss to phase 1
                
                // Offer player option to visit Site of Grace if they have runes
                if (player.getRunes() > 0) {
                    siteOfGrace(player, weaponWheel);
                }
                continue;
            }

            // If the player wins, restore stats and award runes and healing
            player.setHp(normalHp);
            player.setFp(normalFp);
            player.setHealingTotal(healingTotal);
            player.addRunes(boss.getRunes());
            player.addHealingTotal();
            return;
        }
    }

    /**
     * Handles player interaction at a Site of Grace, allowing the player to purchase weapons,
     * level up, or upgrade their current weapon.
     * 
     * @param player The player character interacting with the Site of Grace.
     * @param weaponWheel The array of available weapons for purchase.
     */
    public static void siteOfGrace(Player player, Weapon[] weaponWheel) {
        while (true) {
            clearScreen();
            // Display options at the Site of Grace
            System.out.println("What dost thou wish to do?\n1) Purchase New Weapon\n2) Level Up\n3) Upgrade Weapon\n4) Leave\n");
            
            // Handle player input
            int answer = input.nextInt();
            switch (answer) {
                case 1:
                    // Player buys a new weapon
                    tarnished.setHand(buyWeapon(tarnished, weaponWheel));
                    break;
                case 2:
                    // Player levels up
                    tarnished.setStats(levelUp(tarnished, tarnished.getStats()));
                    break;
                case 3:
                    // Upgrade weapon if the player has enough runes
                    System.out.print("This will cost you " + tarnished.getHand().getUpgradePrice() + " runes. ");
                    if (!choice()) {
                        break;
                    }
                    if (tarnished.getRunes() < tarnished.getHand().getUpgradePrice()) {
                        System.out.println("Not enough runes.");
                        break;
                    }
                    tarnished.getHand().upgrade();
                    break;
                case 4:
                    // Player leaves the Site of Grace
                    clearScreen();
                    return;
                default:
                    speak("Invalid action. Try again.\n");
            }
        }
    }

    public static void main(String[] args) {
        clearScreen();
        speak("In the beginning, there was only a single Erdtree.");
        speak("Until one day, an eternal dropped from the Erdtree.");
        speak("By nature, all Eternals seek to overthrow the Erdtree and destroy all others.");
        speak("It is said that when a Tarnished ascends to divinity, they shall wreak havoc and chaos upon all.");
        speak("At long last, you are that Tarnished.");
        speak("Collect your Rune Fragments, level your strength, and collect the weapons you desire.");
        speak("You will need all the power you can muster to overthrow the Erdtree.");
        speak("The lands beyond the Erdtree are fraught with peril, and only those of great fortitude and cunning will prevail.");

        System.out.print("What is thy name? ");
        String name = input.nextLine();

        tarnished = new Player(name);
        
        clearScreen();
        speak("Tutorial");
        speak("1. Combos");
        speak("   - Combos are sequences of attacks executed by bosses.");
        speak("   - Each combo has a specific order and timing.");
        speak("   - Pay attention to the attack patterns and prepare to dodge or counter.");

        speak("2. Timing");
        speak("   - Boss attacks are divided into phases: charge-up, attack, and cooldown.");
        speak("   - During the charge-up phase, you have time to react and prepare.");
        speak("   - React quickly during the attack phase to avoid damage.");
        speak("   - After the attack, the boss will enter a cooldown phase where they are vulnerable.");

        speak("3. Types of Attacks");
        speak("   - Light Attack: Fast but less powerful. Useful for quick hits.");
        speak("   - Heavy Attack: Slower but more powerful. Can break through defenses.");
        speak("   - Special Attack: Fast and more powerful. Costs Focus.");
        speak("   - Heal: You can heal HP or FP during combat .");
        speak("   - Dodge: Use the dodge feature to evade attacks. You can dodge in two specific directions.");
        speak("   - Inputs: If you ever input anything that isn't an option, you will be sent back to the original decison.");

        speak("4. Scaling");
        speak("   - Your attacks and damage scale with your stats.");
        speak("   - The more you level up and allocate runes to your stats, the stronger your attacks become.");
        speak("   - Bosses also have their damage scaling based on their level and phase.");

        Weapon[] weaponWheel = {
            new Weapon("Greatsword, scales with Strength primarily and Dexterity secondarily", "Swing", "Slice", "Lion's Claw", new double[]{2, 0.5, 0, 0, 0}, 0, 400, 4), 
            new Weapon("Urumi, scales with Dexterity primarily and Strength secondarily", "Whip", "Trip", "Hack'n'Slash", new double[]{0.5, 2, 0, 0, 0}, 0, 150, 1),
            new Weapon("Glintstone Staff, scales with Intelligence", "Glintstone Pebble", "Comet", "Comet Azur", new double[]{0, 0, 2, 0, 0}, 0, 100, 2),
            new Weapon("Winged Scythe, scales with Intelligence and Faith primarily and Strength secondarily", "Scythe", "Sweep", "Death Scythe", new double[]{0, 0, 1, 1, 1}, 0, 300, 5),
            new Weapon("Rivers of Blood, scales with Arcane primarily and Dexterity secondarily", "Bloodletting", "Stab", "Unsheath", new double[]{0, 0.5, 0, 0, 1}, 0, 200, 2)
        };

        
        tarnished.setHand(buyWeapon(tarnished, weaponWheel));

        tarnished.setStats(levelUp(tarnished, tarnished.getStats()));

        clearScreen();
        speak("You have completed the character creation process.");
        speak("Your character, " + tarnished.getName() + ", is ready to begin the journey.");
        speak("Good luck, Tarnished.");
        
        speak("You begin your journey in a world where the shadows of past glories linger, haunted by forgotten heroes and lost legends.");
        speak("As you traverse the crumbling ruins and treacherous landscapes, you will encounter myriad foes and allies, each with their own motives and secrets.");
        speak("Some will seek to aid you in your quest, offering wisdom and guidance, while others will test your resolve, eager to see you falter.");
        speak("Through trials and tribulations, your strength will grow, and your skills will be honed in the crucible of battle.");
        speak("One such trial awaits you at the bridge that leads to the castle's threshold—a test of your mettle against a formidable adversary.");
        speak("He is known as Margit, the Fell Omen, a guardian of the path to greater power and the threshold to the true depths of your destiny.");
        speak("Margit wields arcane sorcery and devastating melee strikes, a formidable challenge for any who dare to seek the Erdtree’s throne.");
        speak("Prepare yourself for the fight ahead, for Margit will not yield easily. His presence signifies the first true test of your ascension.");
        speak("Embrace the power within you, sharpen your weapons, and steady your heart. The battle to come will define your path forward.");
        speak("With each clash of steel and surge of magic, you will edge closer to the Erdtree's glory—or be cast into darkness.");
        
        speak("The heavy mist swirling around the bridge thickens as you approach the castle's looming gates.");
        speak("The sky above darkens, casting an ominous shadow over the landscape. With each step, the distant thunder rumbles, echoing your racing heartbeat.");
        speak("At the end of the bridge stands Margit, the Fell Omen, a figure of imposing stature and formidable presence.");
        speak("His eyes, glowing with arcane fury, lock onto you as you draw closer. The air crackles with latent magic, and his cloak flutters like a storm in anticipation.");
        speak("Margit’s voice, like gravel grinding on stone, pierces the silence. 'Foul Tarnished, in search of the Elden Ring. Emboldened by the flame of ambition. Someone must extinguish thy flame. Let it be Margit the Fell'");
        speak("He raises his staff high, and the ground trembles as dark energy begins to coalesce around him.");
        speak("His weapons—enchanted with eldritch power—shine menacingly under the stormy sky.");
        speak("The bridge beneath you groans, ready to bear witness to the clash that will determine your fate.");
        speak("With a final, defiant glance, Margit prepares for the battle that will test every ounce of your strength and skill.");
        speak("It’s time. Face your fears, summon your resolve, and step into the crucible of combat. The fate of your journey—and perhaps the very world—rests on this moment.");
    
        Boss margit = new Margit();
        fight(weaponWheel, tarnished, margit);
        
        speak("The clash with Margit has left the bridge in ruins, and the air is thick with the remnants of magic and the echoes of battle.");
        speak("As the dust settles and the mist begins to clear, you stand victorious but weary. Your body aches from the exertion, and your heart still races from the fight.");
        speak("From the shadows of the crumbling castle, a figure emerges—a woman of ethereal grace and quiet strength. She moves with an air of calm assurance, her presence a stark contrast to the chaos that just transpired.");
        speak("Well fought, Tarnished, she says softly, her voice carrying a soothing melody amidst the remnants of the storm. I am Melina, and I have come to aid you on your journey.");
        speak("She approaches you with a warm, reassuring smile. The path ahead is fraught with even greater dangers and challenges. You have proven your worth, but the road to the Erdtree will test you further.");
        speak("Melina extends her hand, and as she does, a soft, golden light begins to emanate from the ground nearby.");
        speak("Come, she beckons, there is a Site of Grace where you may find respite and guidance. It will restore your strength and allow you to prepare for the trials yet to come.");
        speak("You follow Melina to the Site of Grace, a serene haven amid the desolation. The site is marked by a gentle, radiant light that seems to soothe the very essence of your being.");
        speak("As you approach, the light envelops you, and you feel a profound sense of peace and renewal. Your wounds heal, your spirit is lifted, and your resolve is strengthened.");
        speak("Rest here, Melina advises. The grace of this place will provide you with clarity and insight. Use it to reflect on your journey and to prepare for the path that lies ahead.");
        speak("With the Site of Grace to guide you, you take a moment to catch your breath, knowing that the true depth of your destiny awaits beyond the horizon.");
        speak("Melina’s presence remains a comforting assurance as you settle into the tranquil light, ready to face the challenges that will come with renewed vigor and determination.");
        speak("When you are ready, Melina says softly, I will be here to guide you. The road is long, but you are not alone.");
        speak("With that, she steps back into the shadows, leaving you to contemplate your next move as the light of the Site of Grace casts a warm glow around you.");
        speak("The journey continues, and with each step, the path to the Erdtree becomes clearer, illuminated by the strength and wisdom you have gained.");
        
        weaponWheel = new Weapon[] {
            new Weapon("Great Club, scales with Strength primarily and Dexterity secondarily", "Smash", "Crush", "Earthquake", new double[]{3.0, 0.5, 0.0, 0.0, 0.0}, 20, 500, 5), 
            new Weapon("Reduvia, scales with Dexterity primarily and Arcane secondarily", "Stab", "Flay", "Blood Surge", new double[]{0.5, 3.0, 0.0, 0.0, 0.5}, 20, 250, 2), 
            new Weapon("Azur's Glintstone Staff, scales with Intelligence primarily", "Magic Missile", "Arcane Burst", "Meteor Shower", new double[]{0.0, 0.0, 3.0, 0.0, 0.0}, 20, 300, 3), 
            new Weapon("Godslayer Sword, scales with Faith primarily and Dexterity secondarily", "Cleave", "Searing Strike", "Divine Retribution", new double[]{0.5, 0.0, 0.0, 3.0, 0.0}, 20, 400, 4), 
            new Weapon("Death's Poker, scales with Arcane primarily and Dexterity secondarily", "Pierce", "Spectral Thrust", "Soul Rend", new double[]{0.0, 1.0, 0.0, 0.0, 3.0}, 20, 350, 3)
        };

        siteOfGrace(tarnished, weaponWheel);
        
        // Preparing for the encounter with Godrick the Grafted
        speak("As you recover from the fierce battle with Margit, the Grafted Castle comes into view. Its towering spires and dark, looming presence reflect the harshness of its inhabitants. The very air around it seems thick with the weight of countless battles fought and lost.");
        speak("Melina walks beside you, her gaze steady as she surveys the castle’s twisted silhouette. 'This place is steeped in sorrow and despair,' she says. 'Godrick the Grafted, once a noble warrior, has become a grotesque parody of his former self. His power is both fearsome and corrupting.'");
        speak("You navigate the castle’s foreboding corridors, the silence broken only by the distant clamor of rusted armor and the soft echoes of unseen creatures. The walls, once grand, are now lined with grim trophies and macabre relics, hinting at the horrors within.");
        speak("Entering the throne room, you are confronted by Godrick, his massive, grafted body a monstrous amalgamation of metal and flesh. His eyes, burning with malevolent fury, fixate on you as he roars in defiance.");
        speak("Mighty Dragon, thou'rt a trueborn heir. Lend me thy strength, o kindred. Deliver me unto greater heights. ...Well. A lowly Tarnished, playing as a lord. I command thee, kneel! I am the lord of all that is golden!");
        speak("The battle with Godrick is a harrowing ordeal. Each of his blows is delivered with an overwhelming force, the weight of his weapon sending tremors through the castle’s very foundation. His strength and resilience are matched only by his cunning and brutality.");
        
        Boss godrick = new Godrick();
        fight(weaponWheel, tarnished, godrick);
        
        speak("Melina’s tactical advice proves invaluable as you struggle against Godrick’s onslaught. Her insights help you to dodge and counter his devastating attacks, and through sheer determination and skill, you manage to overcome the Grafted King.");
        speak("The throne room falls silent, the echoes of your battle lingering in the air. As Godrick’s massive form crumbles, Melina approaches with a look of relief. 'We have triumphed over a significant foe,' she says. 'But our journey is far from over. The path ahead leads us to the shattered remnants of the Academy of Raya Lucaria.'");
        
        weaponWheel = new Weapon[] {
            new Weapon("Colossal Greatsword, scales with Strength primarily and a bit of Dexterity", "Heavy Swing", "Ground Slam", "Titan's Wrath", new double[]{3.5, 0.7, 0.0, 0.0, 0.0}, 700, 600, 6), 
            new Weapon("Silence, scales with Dexterity primarily and Arcane secondarily", "Quick Slash", "Veil Slice", "Silent Execution", new double[]{0.7, 3.5, 0.0, 0.0, 0.7}, 500, 300, 3), 
            new Weapon("Moonlight Greatsword, scales with Intelligence primarily", "Lunar Strike", "Starfall", "Cosmic Burst", new double[]{0.0, 0.0, 4.0, 0.0, 0.0}, 700, 400, 4), 
            new Weapon("Blasphemous Blade, scales with Faith primarily and Dexterity secondarily", "Scorch", "Blaze Swipe", "Hellfire", new double[]{0.6, 0.3, 0.0, 4.0, 0.0}, 650, 450, 5), 
            new Weapon("Mimic Tear's Blade, scales with Arcane primarily and Dexterity secondarily", "Copycat Strike", "Shadow Edge", "Mirrored Death", new double[]{0.0, 1.2, 0.0, 0.0, 4.0}, 550, 400, 4)
        };
        
        siteOfGrace(tarnished, weaponWheel);
        
        // Preparing for the encounter with Rennala, Queen of the Full Moon
        speak("Your journey leads you to the Academy of Raya Lucaria, a place of ancient magic and long-forgotten knowledge. The academy's once-majestic halls now lie in ruin, a haunting testament to its past grandeur.");
        speak("Melina guides you through the desolate grounds, her presence a beacon of calm in the midst of the academy’s eerie silence. 'Rennala, Queen of the Full Moon, resides within these ruins,' she says. 'Her mastery of sorcery is formidable, and her wrath is feared by all who enter her domain.'");
        speak("As you navigate through the crumbling corridors and shattered classrooms, the very air seems to hum with the remnants of powerful spells. The walls are adorned with arcane symbols and magical artifacts, their glow casting flickering shadows on the debris-strewn floor.");
        speak("Hush, little culver. I'll soon birth thee anew, a sweeting fresh and pure…");
        speak("Entering the grand chamber of the academy, Rennala appears, her form wreathed in a luminous aura. The Queen of the Full Moon, her eyes glowing with arcane power, exudes an air of regal authority and danger.");
        speak("The battle with Rennala is a mesmerizing spectacle of sorcery and arcane might. Her attacks are both beautiful and deadly, weaving intricate patterns of magic that challenge your every move. The room becomes a chaotic dance of light and dark as she unleashes her full power.");
        
        Boss rennala = new Rennala();
        fight(weaponWheel, tarnished, rennala);
        
        speak("With Melina’s strategic guidance and your own resilience, you navigate the storm of spells and find the moments to strike. Rennala’s defeat brings a profound silence, her ethereal form dissipating into the ether. The academy, though still in ruins, feels a little lighter for your victory.");
        speak("Melina approaches, her gaze filled with a mix of satisfaction and concern. 'Rennala’s fall is a crucial step in our journey,' she says. 'Yet the road ahead will only grow more treacherous. Prepare yourself for the trials that lie beyond.'");
        
        siteOfGrace(tarnished, weaponWheel);
        
        // Preparing for the encounter with the Red Wolf of Radagon
        speak("The path now takes you to the treacherous realm of the Red Wolf of Radagon. The landscape is a bleak and desolate wasteland, scorched by fiery eruptions and littered with the remnants of ancient battles.");
        speak("Melina remains a steady guide as you traverse the harsh terrain. 'The Red Wolf of Radagon is a swift and relentless adversary,' she warns. 'His attacks are quick and deadly, and his mastery of combat will test your agility and reflexes.'");
        speak("As you reach the arena, the ground quakes with the Red Wolf’s ferocious roars. The beast, its fur bristling with dark energy, emerges from the shadows, its movements a blur of speed and ferocity.");
        speak("The battle with the Red Wolf is a relentless test of agility and strategy. His attacks are fast and unpredictable, forcing you to constantly move and adapt. Melina’s tactical advice helps you anticipate his movements and find the right moments to strike.");
        
        Boss redwolf = new RedWolf();
        fight(weaponWheel, tarnished, redwolf);
        
        speak("After an intense and exhausting fight, you manage to defeat the Red Wolf, his form collapsing into a pool of dark energy. The battlefield falls silent, the oppressive atmosphere lifting slightly as you catch your breath.");
        speak("Melina approaches, her expression a mixture of relief and encouragement. 'You’ve faced another formidable challenge and emerged victorious,' she says. 'But the journey is far from over. The next trial will be even more daunting—the domain of Rykard, the God-Devouring Serpent.'");
        
        siteOfGrace(tarnished, weaponWheel);
        
        // Preparing for the encounter with the God-Devouring Serpent, Rykard, Lord of Blasphemy
        speak("Your journey now leads you into the heart of darkness, the domain of Rykard, the God-Devouring Serpent. The landscape is a grotesque vision of twisted flesh and dark, pulsating energy, creating a nightmarish environment.");
        speak("Melina’s guidance is a beacon of hope as you navigate through the nightmarish surroundings. 'Rykard’s domain is a place of immense power and corruption,' she warns. 'His form is both terrifying and blasphemous. Prepare yourself for a battle of epic proportions.'");
        speak("As you enter the cavernous lair, Rykard’s colossal serpentine form emerges from the shadows, his presence radiating a dark and oppressive energy. The ground trembles beneath his massive body as he prepares to confront you.");
        speak("The battle with Rykard is a grueling test of endurance and strategy. His immense size and dark powers create a chaotic and challenging fight. Melina’s guidance helps you navigate through the tumultuous battle, allowing you to find the moments to strike effectively.");
        
        Boss serpent = new Serpent();
        fight(weaponWheel, tarnished, serpent);
        
        speak("After a fierce and prolonged struggle, you finally defeat Rykard, his form collapsing into a seething mass of dark energy. The lair falls silent, the oppressive atmosphere lifting slightly as you catch your breath.");
        speak("Melina approaches, her expression a mix of grim satisfaction and concern. 'Rykard’s defeat is a significant victory,' she says. 'But the final challenges lie ahead. Stay strong and focused for what is to come.'");
        
        weaponWheel = new Weapon[] {
            new Weapon("Grafted Blade Greatsword, scales with Strength primarily and a bit of Dexterity", "Overhead Smash", "Sweep", "Mighty Slam", new double[]{3.8, 0.6, 0.0, 0.0, 0.0}, 700, 600, 6), 
            new Weapon("Eclipse Shotel, scales with Dexterity primarily and a bit of Strength", "Slice", "Sun's Flare", "Eclipse Cut", new double[]{0.8, 3.0, 0.0, 0.0, 0.0}, 550, 300, 3), 
            new Weapon("Sword of Night and Flame, scales with Intelligence primarily and Faith secondarily", "Night Slash", "Flame Sweep", "Starfire", new double[]{0.5, 0.2, 3.0, 3.0, 0.0}, 750, 450, 4), 
            new Weapon("Godslayer Greatsword, scales with Faith primarily and a bit of Dexterity", "Sacred Swing", "Holy Cleave", "God's Judgement", new double[]{0.6, 0.3, 0.0, 3.5, 0.0}, 650, 400, 5), 
            new Weapon("Night's Sacred Blade, scales with Arcane primarily and Dexterity secondarily", "Dark Slash", "Shadow Stab", "Moonlit Veil", new double[]{0.0, 1.2, 0.0, 0.0, 4.0}, 600, 350, 4)
        };
        
        siteOfGrace(tarnished, weaponWheel);
        
        // Preparing for the encounter with Mohg, Lord of Blood
        speak("The path now leads you to the subterranean lair of Mohg, Lord of Blood. The air is thick with the scent of blood and corruption, and the walls are lined with dark, pulsating veins. The environment is both grotesque and foreboding.");
        speak("Melina’s presence is a reassuring guide as you navigate through the labyrinthine tunnels. 'Mohg’s blood magic is a powerful and dangerous force,' she warns. 'This battle will test every ounce of your strength and resolve.'");
        speak("As you reach the heart of Mohg’s lair, the Lord of Blood emerges from the shadows, his form cloaked in a dark aura of blood magic. His presence is both terrifying and commanding, a testament to his mastery of his dark powers.");
        speak("Dearest Miquella. You must abide alone a while. Welcome, honored guest. To the birthplace of our dynasty!");
        
        Boss mohg = new Mohg();
        fight(weaponWheel, tarnished, mohg);
        
        speak("The battle with Mohg is a grueling and intense fight. His blood magic and relentless attacks push you to your limits, requiring you to use every ounce of your strength and skill to survive. Melina’s guidance helps you navigate through the chaos and find the openings to strike.");
        speak("After a taxing and prolonged battle, you manage to defeat Mohg, his form dissolving into a cloud of dark, crimson mist. The lair falls silent, the oppressive atmosphere lifting slightly as you catch your breath.");
        speak("Melina approaches, her expression one of quiet resolve. 'Mohg’s defeat is a testament to your strength and determination,' she says. 'But the final challenge lies ahead—the domain of Morgott, the Omen King.'");
        
        siteOfGrace(tarnished, weaponWheel);
        
        // Preparing for the encounter with Morgott, the Omen King
        speak("With the Fire Giant defeated, you now stand on the precipice of a new challenge. The path ahead leads to Morgott, the Omen King. This battle will push you to the edge.");
        speak("The landscape transforms as you approach Morgott’s domain. The air is thick with dark energy, and the atmosphere feels heavy with the weight of impending conflict.");
        speak("Melina stands by your side, her presence a steadying force. 'Morgott is a formidable foe,' she says. 'His power and dark magic will test everything you have learned.'");
        speak("As you enter the arena, Morgott emerges from the shadows, his form imposing and malevolent. His dark energy crackles with every movement, signaling the fierce battle to come.");
        speak("Graceless Tarnished. What is thy business with these thrones? Ahh... Godrick the Golden. The twin prodigies, Miquella and Malenia. General Radahn. Praetor Rykard. Lunar Princess Ranni. Wilful traitors, all. Thy kind are all of a piece. Pillagers. Emboldened by the flame of ambition. Have it writ upon thy meagre grave: Felled by King Morgott! Last of all kings.");
        
        Boss morgott = new Morgott();
        fight(weaponWheel, tarnished, morgott);
        
        // Boss fight with Morgott, the Omen King
        speak("The clash with Morgott is fierce and relentless. His attacks are swift and devastating, requiring you to use every ounce of skill and strategy to survive.");
        speak("Melina’s guidance proves invaluable as you navigate through Morgott’s dark magic and brutal strikes. Each moment of the battle is a test of your endurance and resolve.");
        speak("After a grueling fight, Morgott falls, his dark form dissipating into the shadows. The arena falls silent, and a sense of grim satisfaction settles over you.");
        speak("Melina approaches, her expression a mix of relief and concern. 'Morgott’s defeat is significant,' she says. 'But the path ahead is even more daunting. The Fire Giant awaits us.'");
        
        siteOfGrace(tarnished, weaponWheel);
        
        // Preparing for the encounter with the Fire Giant
        speak("The path now leads to the fiery domain of the Fire Giant. The landscape is a nightmarish vision of molten rock and burning ash. The heat is almost unbearable.");
        speak("Melina’s presence remains a beacon of hope as you traverse the treacherous terrain. 'The Fire Giant is a colossal foe,' she warns. 'This battle will push you to your limits.'");
        speak("As you approach the arena, the ground shakes violently. The Fire Giant emerges from the flames, his immense form radiating intense heat and power.");
        speak("The battle with the Fire Giant is a test of endurance and strategy. His attacks are powerful, and the landscape itself seems to fight against you. Melina’s guidance helps you find the right moments to strike and dodge his blows.");
        
        Boss firegiant = new FireGiant();
        fight(weaponWheel, tarnished, firegiant);
        
        // Boss fight with the Fire Giant
        speak("The battle is a relentless struggle, with the Fire Giant’s attacks shaking the very ground beneath you. Each moment is a fight for survival.");
        speak("Molten rock and fiery eruptions add to the chaos of the battle. Every strike and dodge requires careful timing and strategy. Melina’s presence is a source of strength and support.");
        speak("After an exhausting fight, the Fire Giant falls, his massive form collapsing into a smoldering heap. The heat begins to dissipate as you catch your breath.");
        speak("Melina approaches, her expression a mix of relief and concern. 'The Fire Giant’s defeat is a monumental achievement,' she says. 'But our journey is nearing its end. The final challenge awaits us at the base of the Erdtree.'");
        
        siteOfGrace(tarnished, weaponWheel);
        
        // Preparing for the final challenge and the sacrifice of Melina
        speak("With the Fire Giant defeated, you and Melina stand before the colossal Erdtree. Its branches reach high into the sky, a symbol of both life and power.");
        speak("Melina looks at you, her eyes filled with a mixture of determination and sadness. 'We have come so far,' she says, her voice trembling slightly. 'To burn down the Erdtree, a great sacrifice must be made.'");
        speak("The weight of her words is almost unbearable. The thought of losing Melina, who has become so integral to your journey, is heart-wrenching.");
        speak("Her presence has been a constant source of strength and guidance. Her selflessness and unwavering resolve are both inspiring and heartbreaking.");
        speak("As you prepare for the final confrontation, Melina’s demeanor is calm but tinged with sorrow. 'This is the path we must take,' she says. 'But know that your strength and resolve have been the true driving force behind our journey.'");
        
        // The decision: to take Melina's place or let her sacrifice herself
        speak("The moment of decision arrives. Melina stands before the Erdtree, ready to make the ultimate sacrifice. The air is thick with tension and emotion.");
        speak("Melina’s eyes meet yours, filled with a mixture of determination and acceptance. 'If you choose to take my place,' she says softly, 'know that it will change everything.'");
        speak("The choice is heart-wrenching. Letting Melina sacrifice herself is painful, but it is the only way to achieve your goal. Alternatively, taking her place means facing the consequences of becoming a Lord of Chaos.");
        speak("Melina’s resolve is unwavering. 'If you choose to let me proceed,' she says, 'I will accept my fate with honor. Your journey will continue, and the world will be forever changed.'");
        speak("You are left with a choice that will define the future. The decision to let Melina sacrifice herself or to take her place will shape the outcome of your journey and the fate of the world.");
        
        clearScreen();
        boolean eldenLord;
        while (true) {
            System.out.println("Decide.\n1) Let Melina fullfill her mission.\n2) Let chaos take the world.");
            int answer = input.nextInt();
            if (answer == 1) {
                speak("If you chose to let Melina sacrifice herself: Melina’s resolve is unwavering as she steps forward, ready to make the ultimate sacrifice. 'Thank you for standing by me until the end,' she says, her voice trembling with emotion.");
                speak("As Melina initiates the sacrifice, the Erdtree is engulfed in a blinding inferno. Her form merges with the flames, and the world changes as the balance of power shifts.");
                speak("The forces of chaos are unleashed, and you remain, bearing witness to the new reality that emerges from the ashes. Though Melina is gone, her sacrifice has paved the way for a new era.");
                speak("The journey has reached its end. The world has been irrevocably changed, and you must now navigate this new reality shaped by your choices and the sacrifices made.");
                speak("The path forward is uncertain, but your role—whether as a Lord of Chaos or as a witness to Melina’s sacrifice—will define the future of this world.");
                eldenLord = true;
                break;
            } else if (answer == 2) {
                speak("As you step forward, Melina’s expression changes to one of profound relief and sadness. 'You have chosen a path of great consequence,' she says, her voice filled with emotion.");
                speak("Melina steps back, allowing you to prepare for the ultimate sacrifice. A surge of power and chaos envelops you as the Erdtree is consumed by raw, untamed energy.");
                speak("The world shifts and changes, the balance of power is altered. You find yourself transformed into a Lord of Chaos, living but forever changed. The world around you is a new, chaotic reality.");
                speak("The sacrifice was immense, and the consequences are profound. The forces of chaos are unleashed, and you must navigate this new reality with the weight of your choice bearing heavily on you.");
                eldenLord = false;
                break;
            } else {
                clearScreen();
                speak("There is no other way.. you must decide");
            }
        }
        
        // Preparing for the encounter with Beast Clergyman/Maliketh, the Black Blade
        speak("With the Erdtree’s destruction, the path leads you to a new and foreboding challenge. The Beast Clergyman awaits. His power is vast and his purpose shrouded in mystery.");
        speak("The landscape transforms once again. Dark clouds loom overhead, and a sense of impending doom fills the air. The Beast Clergyman’s domain is a place of shadows and ancient power.");
        speak("Without Melina by your side, the weight of your journey feels even heavier. The trials you have faced have been immense, and the challenges ahead are formidable.");
        speak("As you approach the arena, he emerges from the shadows. His form is both majestic and terrifying, a blend of beastly and divine. His presence is both a challenge and a test of your resolve.");
        speak("Thou, who approacheth Destined Death. I will not have it stolen from me again.");
        
        // Boss fight with Beast Clergyman/Maliketh, the Black Blade
        speak("The battle with the Clergyman is fierce and unrelenting. His attacks are swift and devastating, each move a blend of arcane power and physical might.");
        speak("The fight is grueling, requiring every ounce of your strength and strategy. The Clergyman’s form shifts and changes, making each phase of the battle unique and challenging.");
        
        Boss beastClergyman = new BeastClergyman();
        fight(weaponWheel, tarnished, beastClergyman);
        
        speak("As the battle progresses, Maliketh falls. His immense power is subdued, but the victory comes at a cost. The weight of your journey presses heavily upon you as you prepare for the next challenge.");
        
        weaponWheel = new Weapon[] {
            new Weapon("Great Club, scales with Strength primarily and a bit of Dexterity", "Smash", "Crush", "Earthquake", new double[]{4.0, 0.8, 0.0, 0.0, 0.0}, 800, 700, 6), 
            new Weapon("Bloodhound's Fang, scales with Dexterity primarily and a bit of Arcane", "Slash", "Bloodletting", "Fang Strike", new double[]{0.8, 3.0, 0.0, 0.0, 1.0}, 700, 350, 4), 
            new Weapon("Moonveil, scales with Intelligence primarily and Dexterity secondarily", "Lunar Slash", "Starfall", "Moonburst", new double[]{0.5, 0.6, 4.0, 0.0, 0.0}, 800, 500, 4), 
            new Weapon("Eclipse Shotel, scales with Dexterity primarily and a bit of Strength", "Cut", "Sun Ray", "Eclipse Strike", new double[]{0.9, 3.5, 0.0, 0.0, 0.0}, 650, 350, 3), 
            new Weapon("Dark Moon Greatsword, scales with Intelligence primarily and a bit of Faith", "Lunar Slash", "Cosmic Ray", "Dark Moon Beam", new double[]{0.0, 0.0, 5.0, 1.0, 0.0}, 850, 600, 5)
        };

        siteOfGrace(tarnished, weaponWheel);
        
        // Preparing for the encounter with Sir Gideon Ofnir, the All-Knowing
        speak("With Maliketh defeated, you now face Sir Gideon Ofnir, the All-Knowing. His knowledge of the world’s secrets is vast, and his power is formidable.");
        speak("The arena for this battle is a grand, ancient hall, filled with relics and symbols of bygone eras. The air is thick with knowledge and power.");
        speak("The absence of Melina is a poignant reminder of the sacrifices made and the trials yet to come. Sir Gideon’s challenge will test not only your strength but also your resolve and understanding of the world.");
        speak("As Sir Gideon appears, his gaze is piercing and filled with ancient wisdom. He stands as a guardian of the knowledge that will determine the future of the world.");
        speak("Ahh, I knew you'd come. To stand before the Elden Ring. To become Elden Lord. What a sad state of affairs. I commend your spirit, but alas, none shall take the throne. Queen Marika has high hopes for us. That we continue to struggle. Unto eternity.");
        
        // Boss fight with Sir Gideon Ofnir, the All-Knowing
        speak("The battle with Sir Gideon is a test of both intellect and combat prowess. His attacks are strategic, and his knowledge of the world’s secrets makes him a formidable adversary.");
        
        Boss gideon = new Gideon();
        fight(weaponWheel, tarnished, gideon);
        
        speak("The fight is intense, requiring you to anticipate his moves and counter his strategies. Sir Gideon’s power is vast, and each moment of the battle is a test of your skills and understanding.");
        speak("After a prolonged and challenging fight, Sir Gideon falls. His form dissipates, leaving behind the remnants of his vast knowledge and power.");
        
        siteOfGrace(tarnished, weaponWheel);
        
        // Preparing for the encounter with Godfrey, First Elden Lord, Hoarah Loux, Warrior
        speak("With Sir Gideon defeated, you now face Godfrey, the First Elden Lord. This battle is a culmination of your journey’s trials.");
        speak("The arena is a grand, ancient battlefield, echoing with the memories of past glories and epic conflicts. The atmosphere is charged with the power of the ancient Elden Lords.");
        speak("Without Melina’s guidance, the weight of this challenge feels even more immense. Godfrey’s legacy and power are legendary, and this battle will be a true test of your worth.");
        speak("As Godfrey appears, his presence is awe-inspiring and commanding. His form radiates power, and his strength as a warrior is unparalleled. Prepare for the ultimate test of your abilities.");
        speak("It's been a long while, Morgott. Long and hard didst thou fight. Tarnished Warrior. Spurned by the grace of gold. Be assured, the Elden Ring resteth close at hand. Alas, I am returned. To be granted audience once more. Upon my name as Godfrey, The first Elden Lord!");
        
        // Boss fight with Godfrey, First Elden Lord, Hoarah Loux, Warrior
        speak("The battle with Godfrey is a test of raw strength and combat skill. His attacks are powerful and relentless, requiring you to use every ounce of your strength and strategy.");
        speak("The fight is fierce and unyielding, with Godfrey’s prowess as a warrior pushing you to your limits. Each strike and maneuver must be executed with precision and determination.");
        
        Boss godfrey = new Godfrey();
        fight(weaponWheel, tarnished, godfrey);
        
        speak("After a grueling and intense battle, Godfrey falls, his form dissipating into the annals of history. The victory is hard-won, and the path ahead remains uncertain.");
        
        weaponWheel = new Weapon[] {
            new Weapon("Grafted Blade Greatsword, scales with Strength primarily and some Dexterity", "Overhead Smash", "Heavy Cleave", "Titan's Wrath", new double[]{5.0, 1.0, 0.0, 0.0, 0.0}, 1000, 800, 8),
            new Weapon("Reduvia, scales with Dexterity primarily and some Arcane", "Stab", "Flay", "Blood Surge", new double[]{0.8, 3.2, 0.0, 0.0, 1.5}, 900, 500, 5),
            new Weapon("Carian Regal Scepter, scales with Intelligence primarily and some Faith", "Mystic Bolt", "Arcane Wave", "Regal Barrage", new double[]{0.3, 0.2, 4.8, 1.2, 0.0}, 950, 650, 5),
            new Weapon("Godslayer's Greatsword, scales with Faith primarily and some Dexterity", "Holy Cleave", "Divine Strike", "God's Wrath", new double[]{0.6, 1.0, 0.0, 4.2, 0.0}, 850, 700, 5),
            new Weapon("Black Knife, scales with Arcane primarily and some Dexterity", "Shadow Stab", "Silent Cut", "Blackened Blade", new double[]{0.0, 1.7, 0.0, 0.0, 4.0}, 800, 400, 4)
        };
        siteOfGrace(tarnished, weaponWheel);
        
        // Preparing for the encounter with Malenia, Blade of Miquella/Malenia, Goddess of Rot
        speak("With Godfrey defeated, the final challenge awaits. Malenia, Blade of Miquella, and Malenia, Goddess of Rot, are the ultimate trials of your journey.");
        speak("The arena is a nightmarish vision of decay and rot. The air is thick with the stench of corruption and the remnants of Malenia’s power.");
        speak("The absence of Melina is keenly felt. Malenia’s challenge will test every aspect of your strength and resolve, and the stakes are higher than ever.");
        speak("As Malenia emerges, her form is both beautiful and terrifying, a manifestation of rot and divine power. Prepare for the final confrontation that will shape the world’s fate.");
        speak("I dreamt for so long. My flesh was dull gold...and my blood, rotted. Corpse after corpse, left in my wake... As I awaited... his return. ... Heed my words. I am Malenia. Blade of Miquella. And I have never known defeat.");
        
        // Boss fight with Malenia, Blade of Miquella/Malenia, Goddess of Rot
        speak("The battle with Malenia is a test of endurance and skill. Her attacks are swift and devastating, and her power as the Goddess of Rot is formidable.");
        
        Boss malenia = new Malenia();
        fight(weaponWheel, tarnished, malenia);
        
        speak("The fight is intense, with Malenia’s corruption spreading and affecting the battlefield. Each moment requires careful strategy and precise execution to overcome her power.");
        speak("After a harrowing and challenging fight, Malenia falls, her form dissolving into the rotting landscape. The victory is bittersweet, and the path to the final confrontation remains clear.");
        
        siteOfGrace(tarnished, weaponWheel);
        
        // Preparing for the final encounter with Radagon of the Golden Order/Elden Beast
        speak("With Malenia defeated, you now face the ultimate challenge: Radagon of the Golden Order. This final battle will determine the fate of the world.");
        speak("The arena is a cosmic expanse, filled with the remnants of divine power and celestial energy. The air is charged with the force of the Elden Beast’s power.");
        speak("The absence of Melina is a stark reminder of the sacrifices made and the choices that have led to this moment. The final confrontation will test everything you have fought for.");
        speak("As Radagon emerges, his form are majestic and terrifying, a culmination of divine and cosmic power. Prepare for the ultimate battle that will decide the world’s fate.");
        
        // Final boss fight with Radagon of the Golden Order/Elden Beast
        speak("The final battle is a monumental clash of divine and cosmic forces. Radagon’s power and the Elden Beast’s energy create a formidable challenge.");
        
        Boss radagon = new Radagon();
        fight(weaponWheel, tarnished, radagon);
        
        speak("The fight is epic and relentless, with every attack and maneuver requiring the utmost precision and strategy. The fate of the world rests on this battle.");
        speak("After a titanic struggle, Radagon and the Elden Beast fall. The cosmic energy dissipates, and the world begins to settle into a new reality. The journey’s end is both victorious and tragic.");
        
        // Divergence based on the choice
        if (eldenLord) {
            speak("If Melina was sacrificed: You stand victorious but alone. The title of Elden Lord is yours, but the absence of Melina casts a shadow over your triumph. The world is now yours to shape, but the cost of victory is profound.");
            speak("The paths are now set, and the world’s future is shaped by the choices made. Whether as Elden Lord or Lord of Chaos, the journey’s end is a testament to the trials and sacrifices that have defined your path.");
            return;
        }
        speak("The world is consumed by death and fire, and Melina remains, transformed by the chaos. She approaches you, her form a blend of sorrow and rage.");
        speak("In this world of devastation, Melina’s eyes burn with a promise of vengeance. 'You have brought ruin to all,' she says, her voice filled with anguish. 'The one who walks alongside flame, Shall one day meet the road of Destined Death. Good-bye.'");
    }
}
