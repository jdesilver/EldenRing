/**
 * Runs a single boss encounter. Combat is time-based: every boss attack has a charge-up window, and
 * each action the player takes (attack, heal, wait) spends time from that window. Dodging in time and
 * in one of the attack's two dodgeable directions avoids it outright; otherwise, when the window runs
 * out, the attack lands. After an attack the boss is briefly vulnerable during its cool-down.
 */
public class Combat {
    private static final int DODGE_COST = 2;

    // bossTurn outcomes.
    private static final int LOSS = 0;
    private static final int CONTINUE = 1;
    private static final int WIN = 2;

    private final Player player;
    private final Boss boss;
    private int timeTaken;

    public Combat(Player player, Boss boss) {
        this.player = player;
        this.boss = boss;
    }

    /** Plays out the fight. Returns true if the player wins, false if they die. */
    public boolean start() {
        int topHp = player.getHp();
        int topFp = player.getFp();

        while (player.getHp() > 0 && boss.getHp() > 0) {
            int outcome = bossTurn(topHp, topFp);
            if (outcome == LOSS) return false;
            if (outcome == WIN) return true;
            // CONTINUE: take another turn
        }
        Console.speak("You died");
        return false;
    }

    /** Plays one combo from the boss, interleaved with the player's actions. */
    private int bossTurn(int topHp, int topFp) {
        boolean phaseChange = false;
        while (player.getHp() > 0 && boss.getHp() > 0) {
            Combo combo = boss.nextCombo();
            for (Attack attack : combo.attacks()) {
                // Charge-up: the player acts while the attack winds up.
                int timeLeft = attack.chargeUp();
                while (timeLeft > 0) {
                    Console.println(attack.line());
                    Direction dodge = playerAction(topHp, topFp);

                    if (boss.checkPhase()) {
                        boss.setPhase(2);
                        phaseChange = true;
                        break;
                    }
                    if (boss.getHp() <= 0) {
                        Console.speak(boss.getDeathLine());
                        Console.speak("Foe Slain");
                        return WIN;
                    }
                    if (dodge != null) {
                        if (timeLeft <= DODGE_COST && attack.dodgeable().contains(dodge)) {
                            Console.speak("Successfully dodged attack!");
                            timeLeft = 0;
                            break;
                        }
                        timeLeft -= DODGE_COST;
                    }

                    timeLeft -= timeTaken;
                    timeTaken = 0;

                    if (timeLeft <= 0) {
                        Console.speak("You were hit!");
                        player.setHp(player.getHp() - attack.damage());
                        if (player.getHp() <= 0) {
                            Console.speak(boss.getWinLine());
                            Console.speak("You died");
                            return LOSS;
                        }
                    }
                }

                // Cool-down: the boss is vulnerable and the player can act freely.
                timeLeft = attack.coolDown();
                while (timeLeft > 0) {
                    playerAction(topHp, topFp);
                    if (boss.checkPhase()) {
                        boss.setPhase(2);
                        phaseChange = true;
                        break;
                    }
                    timeLeft -= timeTaken;
                    timeTaken = 0;
                }

                if (phaseChange) break;
            }
            return CONTINUE;
        }
        return CONTINUE;
    }

    /**
     * Presents the player's action menu and carries out their choice. Sets {@link #timeTaken} for
     * timed actions (attack, heal, wait); returns the direction dodged, or {@code null} otherwise.
     */
    private Direction playerAction(int topHp, int topFp) {
        Console.println("Health: " + player.getHp());
        Console.println("Focus: " + player.getFp());
        Console.println("");
        Console.println("Boss Health: " + boss.getHp());
        Console.println("Total Heals: " + player.getHealingTotal());
        Console.println("");
        Console.println("Choose an action:\n1) Attack\n2) Dodge\n3) Heal\n4) Wait\n");

        switch (Console.readInt()) {
            case 1 -> {
                Console.clear();
                Console.println("Choose an attack:\n1) Light\n2) Heavy\n3) Special\n");
                switch (Console.readInt()) {
                    case 1 -> timeTaken = player.attack(boss, 1);
                    case 2 -> timeTaken = player.attack(boss, 2);
                    case 3 -> {
                        if (player.getFp() < 50) {
                            Console.speak("Not enough Focus!");
                            timeTaken = 2;
                        } else {
                            player.setFp(player.getFp() - 50);
                            timeTaken = player.attack(boss, 3);
                        }
                    }
                    default -> Console.speak("Invalid action. Try again.\n");
                }
            }
            case 2 -> {
                Console.clear();
                Console.println("Choose a direction:\n1) Forward\n2) Backward\n3) Right\n4) Left\n");
                switch (Console.readInt()) {
                    case 1 -> { return player.dodge(1); }
                    case 2 -> { return player.dodge(2); }
                    case 3 -> { return player.dodge(3); }
                    case 4 -> { return player.dodge(4); }
                    default -> Console.speak("Invalid action. Try again.\n");
                }
            }
            case 3 -> {
                Console.clear();
                if (player.getHealingTotal() == 0) {
                    Console.speak("Out of heals!");
                    timeTaken = 2;
                } else {
                    Console.println("What are you healing?\n1) Hp\n2) Fp\n");
                    switch (Console.readInt()) {
                        case 1 -> {
                            player.setHealingTotal(player.getHealingTotal() - 1);
                            timeTaken = player.heal(true, topHp);
                        }
                        case 2 -> {
                            player.setHealingTotal(player.getHealingTotal() - 1);
                            timeTaken = player.heal(false, topFp);
                        }
                        default -> Console.speak("Invalid action. Try again.\n");
                    }
                }
            }
            case 4 -> {
                Console.clear();
                Console.println("How Long?\n");
                int wait = Console.readInt();
                if (wait < 0) {
                    Console.speak("Invalid action. Try again.\n");
                } else {
                    timeTaken = wait;
                }
            }
            default -> Console.speak("Invalid action. Try again.\n");
        }
        return null;
    }
}
