import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * Elden Ring - a text RPG. Author: James DeSilver.
 *
 * <p>This class is the game's script: it walks the Tarnished through character creation, the
 * narrative, and each boss in turn, visiting a Site of Grace between fights. The prose lives in
 * {@link Story}, the bosses in {@link Bestiary}, the weapons in {@link Armory}, and combat in
 * {@link Combat}; here we just sequence them.
 */
public class Main {

    public static void main(String[] args) {
        try {
            run();
        } catch (Throwable t) {
            Console.println("");
            Console.println("An unforeseen calamity befalls the Lands Between. The journey ends here.");
        }
    }

    /** The full game script, wrapped by {@link #main} so no unforeseen error can ever crash to a stack trace. */
    private static void run() {
        Console.clear();
        Console.narrate(Story.INTRO);

        Console.print("What is thy name? ");
        Player player = new Player(Console.readLine());

        Console.clear();
        Console.narrate(Story.TUTORIAL);

        // Character creation: pick a starting weapon and spend starting runes.
        List<Weapon> wheel = new ArrayList<>(Armory.tier1());
        player.setHand(buyWeapon(player, wheel));
        player.setStats(levelUp(player, player.getStats()));

        Console.clear();
        Console.narrate(Story.CREATION_COMPLETE);
        Console.speak("Your character, " + player.getName() + ", is ready to begin the journey.");
        Console.narrate(Story.JOURNEY_TO_MARGIT);

        fight(player, Bestiary.margit(), wheel);
        Console.narrate(Story.AFTER_MARGIT);

        wheel = new ArrayList<>(Armory.tier2());
        siteOfGrace(player, wheel);
        Console.narrate(Story.BEFORE_GODRICK);
        fight(player, Bestiary.godrick(), wheel);
        Console.narrate(Story.AFTER_GODRICK);

        wheel = new ArrayList<>(Armory.tier3());
        siteOfGrace(player, wheel);
        Console.narrate(Story.BEFORE_RENNALA);
        fight(player, Bestiary.rennala(), wheel);
        Console.narrate(Story.AFTER_RENNALA);

        siteOfGrace(player, wheel);
        Console.narrate(Story.BEFORE_RED_WOLF);
        fight(player, Bestiary.redWolf(), wheel);
        Console.narrate(Story.AFTER_RED_WOLF);

        siteOfGrace(player, wheel);
        Console.narrate(Story.BEFORE_SERPENT);
        fight(player, Bestiary.serpent(), wheel);
        Console.narrate(Story.AFTER_SERPENT);

        wheel = new ArrayList<>(Armory.tier4());
        siteOfGrace(player, wheel);
        Console.narrate(Story.BEFORE_MOHG);
        fight(player, Bestiary.mohg(), wheel);
        Console.narrate(Story.AFTER_MOHG);

        siteOfGrace(player, wheel);
        Console.narrate(Story.BEFORE_MORGOTT);
        fight(player, Bestiary.morgott(), wheel);
        Console.narrate(Story.AFTER_MORGOTT);

        siteOfGrace(player, wheel);
        Console.narrate(Story.BEFORE_FIRE_GIANT);
        fight(player, Bestiary.fireGiant(), wheel);
        Console.narrate(Story.AFTER_FIRE_GIANT);

        siteOfGrace(player, wheel);
        Console.narrate(Story.ERDTREE_DECISION);
        boolean eldenLord = chooseEnding();

        Console.narrate(Story.BEFORE_BEAST_CLERGYMAN);
        fight(player, Bestiary.beastClergyman(), wheel);
        Console.narrate(Story.AFTER_BEAST_CLERGYMAN);

        wheel = new ArrayList<>(Armory.tier5());
        siteOfGrace(player, wheel);
        Console.narrate(Story.BEFORE_GIDEON);
        fight(player, Bestiary.gideon(), wheel);
        Console.narrate(Story.AFTER_GIDEON);

        siteOfGrace(player, wheel);
        Console.narrate(Story.BEFORE_GODFREY);
        fight(player, Bestiary.godfrey(), wheel);
        Console.narrate(Story.AFTER_GODFREY);

        wheel = new ArrayList<>(Armory.tier6());
        siteOfGrace(player, wheel);
        Console.narrate(Story.BEFORE_MALENIA);
        fight(player, Bestiary.malenia(), wheel);
        Console.narrate(Story.AFTER_MALENIA);

        siteOfGrace(player, wheel);
        Console.narrate(Story.BEFORE_RADAGON);
        fight(player, Bestiary.radagon(), wheel);
        Console.narrate(Story.AFTER_RADAGON);

        if (eldenLord) {
            Console.narrate(Story.EPILOGUE_ELDEN_LORD);
            return;
        }
        Console.narrate(Story.EPILOGUE_CHAOS);
    }

    // ----- Site of Grace -----

    /** The hub between fights: buy a weapon, level up, upgrade the current weapon, or leave. */
    private static void siteOfGrace(Player player, List<Weapon> wheel) {
        while (true) {
            Console.clear();
            Console.println("What dost thou wish to do?\n1) Purchase New Weapon\n2) Level Up\n3) Upgrade Weapon\n4) Leave\n");
            switch (Console.readInt()) {
                case 1 -> player.setHand(buyWeapon(player, wheel));
                case 2 -> player.setStats(levelUp(player, player.getStats()));
                case 3 -> {
                    Console.print("This will cost you " + player.getHand().getUpgradePrice() + " runes. ");
                    if (confirm()) {
                        if (player.getRunes() < player.getHand().getUpgradePrice()) {
                            Console.println("Not enough runes.");
                        } else {
                            player.getHand().upgrade();
                        }
                    }
                }
                case 4 -> {
                    Console.clear();
                    return;
                }
                default -> Console.speak("Invalid action. Try again.\n");
            }
        }
    }

    /**
     * Lets the player buy a weapon from the wheel (or back out with -1). Buying refunds the price of
     * the current weapon and leaves it in the wheel's slot, swapping the chosen weapon into hand.
     */
    private static Weapon buyWeapon(Player player, List<Weapon> wheel) {
        Console.println("Runes: " + player.getRunes() + "\n");
        Console.println("Choose a Weapon: (-1 to back out)\n");
        for (int i = 0; i < wheel.size(); i++) {
            Console.println((i + 1) + ". " + wheel.get(i).getName() + " - Price: " + wheel.get(i).getPrice() + " Runes");
        }
        Console.println("");

        while (true) {
            int choice = Console.readInt();
            if (choice == Console.INVALID) {
                Console.speak("Invalid input. Please enter a valid number.\n");
                continue;
            }
            if (choice == -1) {
                return player.getHand();
            }
            choice--;
            if (choice < 0 || choice >= wheel.size()) {
                Console.speak("Invalid choice. Please select a valid weapon.\n");
                continue;
            }
            if (player.getRunes() < wheel.get(choice).getPrice()) {
                Console.println("Not enough runes. Choose a different weapon.\n");
                continue;
            }
            if (!confirm()) {
                continue;
            }
            player.spendRunes(wheel.get(choice).getPrice());
            player.addRunes(player.getHand().getPrice());
            Weapon chosen = wheel.get(choice);
            wheel.set(choice, player.getHand());
            return chosen;
        }
    }

    // ----- Leveling up -----

    /** A snapshot of the stat allocation, used to undo a point spend. */
    private record UndoState(int[] stats, int runesSpent) {}

    /**
     * Spends runes across the eight stats, one at a time, with -1 to undo the previous spend.
     * On confirmation, raises max HP/FP from Vigor/Mind and returns the new stat array.
     */
    private static int[] levelUp(Player player, int[] stats) {
        Deque<UndoState> history = new ArrayDeque<>();
        int currentStatIndex = 0;

        while (true) {
            while (currentStatIndex < Stat.COUNT) {
                Console.clear();
                printStatRoster();
                Console.println("Current Stats: " + Arrays.toString(stats));
                Console.println("Runes remaining: " + player.getRunes());
                Console.println("");
                Console.print("Points into " + Stat.values()[currentStatIndex].label() + " (or enter -1 to undo): ");

                int runesSpent = promptStatPoints(player.getRunes(), player.getStats(currentStatIndex));
                if (runesSpent == -1) {
                    if (history.isEmpty()) {
                        Console.println("No actions to undo.");
                        continue;
                    }
                    UndoState last = history.pop();
                    stats = last.stats();
                    player.addRunes(last.runesSpent());
                    Console.println("Undo successful.");
                    if (currentStatIndex > 0) {
                        currentStatIndex--;
                    }
                    continue;
                }

                history.push(new UndoState(Arrays.copyOf(stats, stats.length), runesSpent));
                stats[currentStatIndex] += runesSpent;
                player.spendRunes(runesSpent);
                Console.println("Runes remaining: " + player.getRunes());
                Console.println("");
                currentStatIndex++;
            }

            Console.clear();
            printStatRoster();
            Console.println("Current Stats: " + Arrays.toString(stats));
            Console.println("");
            if (confirm()) {
                break;
            }
            currentStatIndex = Stat.COUNT - 1;  // step back to revisit the last stat
        }

        player.setHp(player.getHp() + player.getStats(Stat.VIGOR.ordinal()) * 30);
        player.setFp(player.getFp() + player.getStats(Stat.MIND.ordinal()) * 30);
        return stats;
    }

    /** Prints the "The Stats: ..." roster line followed by a blank line. */
    private static void printStatRoster() {
        StringBuilder roster = new StringBuilder("The Stats: ");
        Stat[] all = Stat.values();
        for (int i = 0; i < all.length; i++) {
            roster.append(all[i].label());
            if (i < all.length - 1) {
                roster.append(", ");
            }
        }
        Console.println(roster.toString());
        Console.println("");
    }

    /**
     * Reads how many runes to put into a stat, re-prompting until the input is valid: a non-negative
     * amount that neither pushes the stat past 99 nor exceeds available runes, or -1 to undo.
     */
    private static int promptStatPoints(int runes, int currentStat) {
        while (true) {
            int amount = Console.readInt();
            if (amount == Console.INVALID) {
                Console.speak("Invalid input. Please enter a number.");
            } else if ((long) amount + currentStat > 99) {
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

    // ----- Combat orchestration -----

    /**
     * Fights a boss, restoring the player's HP/FP/heals afterward. On defeat the player may rest at a
     * Site of Grace and try again; on victory they collect the boss's runes and gain a heal.
     */
    private static void fight(Player player, Boss boss, List<Weapon> wheel) {
        int normalHp = player.getHp();
        int normalFp = player.getFp();
        int heals = player.getHealingTotal();
        int bossHp = boss.getHp();
        Combat combat = new Combat(player, boss);

        while (true) {
            if (!combat.start()) {
                player.setHp(normalHp);
                player.setFp(normalFp);
                player.setHealingTotal(heals);
                boss.setHp(bossHp);
                boss.setPhase(1);
                if (player.getRunes() > 0) {
                    siteOfGrace(player, wheel);
                }
                continue;
            }
            player.setHp(normalHp);
            player.setFp(normalFp);
            player.setHealingTotal(heals);
            player.addRunes(boss.getRunes());
            player.addHealingTotal();
            return;
        }
    }

    // ----- The ending choice -----

    /** The decision at the Erdtree. Returns true to become Elden Lord (Melina is sacrificed). */
    private static boolean chooseEnding() {
        Console.clear();
        while (true) {
            Console.println("Decide.\n1) Let Melina fullfill her mission.\n2) Let chaos take the world.");
            int answer = Console.readInt();
            if (answer == 1) {
                Console.narrate(Story.ENDING_LET_MELINA);
                return true;
            } else if (answer == 2) {
                Console.narrate(Story.ENDING_TAKE_HER_PLACE);
                return false;
            } else {
                Console.clear();
                Console.narrate(Story.DECISION_RETRY);
            }
        }
    }

    /** Asks a yes/no question, re-prompting until the player answers Y or N. */
    private static boolean confirm() {
        while (true) {
            Console.println("Are you sure? (Y or N)\n");
            String answer = Console.readLine().trim().toUpperCase();
            if (answer.equals("Y")) {
                return true;
            }
            if (answer.equals("N")) {
                return false;
            }
            Console.speak("Invalid input. Please enter Y or N.\n");
        }
    }
}
