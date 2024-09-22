import java.util.Scanner;

/**
 * Manages the combat between the player and the boss.
 */
public class Combat {
    private Player player; // The player participating in combat
    private Boss boss; // The boss the player is fighting
    private int timeTaken; // Time taken for each action

    /**
     * Creates a Combat instance with the given player and boss.
     * 
     * @param player The player participating in the combat.
     * @param boss The boss the player is fighting.
     */
    public Combat(Player player, Boss boss) {
        this.player = player;
        this.boss = boss;
    }

    /**
     * Starts the combat sequence.
     * 
     * @return True if the player wins, false if the player loses.
     */
    public boolean start() {
        int topHp = player.getHp();
        int topFp = player.getFp();
        int topBossHp = boss.getHp();
        
        while (player.getHp() > 0 && boss.getHp() > 0) {
            int outcome = bossTurn(topHp, topFp, topBossHp);
            switch (outcome) {
                case 0:
                    return false; // Player lost
                case 1:
                    break; // Continue combat
                case 2:
                    return true; // Player won
            }
        }

        speak("You died");
        return false; // Player lost
    }

    /**
     * Handles the player's actions during their turn.
     * 
     * @param topHp The player's maximum health points.
     * @param topFp The player's maximum focus points.
     * @return An integer representing the result of the player's action.
     */
    private int playerAction(int topHp, int topFp) {
        Scanner input = new Scanner(System.in);
        System.out.println("Health: " + player.getHp());
        System.out.println("Focus: " + player.getFp());
        System.out.println();
        System.out.println("Boss Health: " + boss.getHp());
        System.out.println("Total Heals: " + player.getHealingTotal());
        System.out.println();
        
        System.out.println("Choose an action:\n1) Attack\n2) Dodge\n3) Heal\n4) Wait\n");
        int action = input.nextInt();
        input.nextLine();
        switch (action) {
            case 1:
                clearScreen();
                System.out.println("Choose an attack:\n1) Light\n2) Heavy\n3) Special\n");
                int nextAction = input.nextInt();
                switch (nextAction) {
                    case 1:
                        setTimeTaken(player.attack(boss, player, 1));
                        break;
                    case 2:
                        setTimeTaken(player.attack(boss, player, 2));
                        break;
                    case 3:
                        player.setFp(player.getFp() - 50);
                        setTimeTaken(player.attack(boss, player, 3));
                        break;
                    default:
                        speak("Invalid action. Try again.\n");
                }
                break;
            case 2:
                clearScreen();
                System.out.println("Choose a direction:\n1) Forward\n2) Backward\n3) Right\n4) Left\n");
                nextAction = input.nextInt();
                switch (nextAction) {
                    case 1:
                        return player.dodge(1);
                    case 2:
                        return player.dodge(2);
                    case 3:
                        return player.dodge(3);
                    case 4:
                        return player.dodge(4);
                    default:
                        speak("Invalid action. Try again.\n");
                }
                break;
            case 3:
                clearScreen();
                if (player.getHealingTotal() == 0) {
                    speak("Out of heals!");
                    setTimeTaken(2);
                    break;
                }
                System.out.println("What are you healing?\n1) Hp\n2) Fp\n");
                nextAction = input.nextInt();
                switch (nextAction) {
                    case 1:
                        player.setHealingTotal(player.getHealingTotal() - 1);
                        setTimeTaken(player.heal(player, true, topHp));
                        break;
                    case 2:
                        player.setHealingTotal(player.getHealingTotal() - 1);
                        setTimeTaken(player.heal(player, false, topFp));
                        break;
                    default:
                        speak("Invalid action. Try again.\n");
                }
                break;
            case 4:
                clearScreen();
                System.out.println("How Long?\n");
                nextAction = input.nextInt();
                setTimeTaken(nextAction);
                break;
            default:
                speak("Invalid action. Try again.\n");
        }
        return 0; // Continue the turn
    }

    /**
     * Manages the boss's turn and attacks.
     * 
     * @param topHp The player's maximum health points.
     * @param topFp The player's maximum focus points.
     * @param topBossHp The boss's maximum health points.
     * @return An integer representing the outcome of the boss's turn.
     */
    private int bossTurn(int topHp, int topFp, int topBossHp) {
        boolean phaseChange = false;
        Combo combo;
        while (player.getHp() > 0 && boss.getHp() > 0) {
            if (boss.getPhase() == 1) combo = boss.chooseCombo1();
            else combo = boss.chooseCombo2();
            
            for (Attack attack : combo.getAttacks()) {
                int totalTime = attack.getChargeUpTime();
                while (totalTime > 0) {
                    System.out.println(attack.getLine());
                    int dir = playerAction(topHp, topFp);
                    
                    if (boss.checkPhase()) {
                        boss.setPhase(2);
                        phaseChange = true;
                        break;
                    }
                    
                    if (boss.getHp() <= 0) {
                        speak(boss.getDeathLine());
                        speak("Foe Slain");
                        return 2; // Player won
                    }
                    
                    if (dir > 0) {
                        if (totalTime <= 2 && (dir - 1 == attack.getDodgeDirections()[0] || dir - 1 == attack.getDodgeDirections()[1])) {
                            speak("Successfully dodged attack!");
                            totalTime = 0;
                            break;
                        }
                        totalTime -= 2;
                    }
                    
                    totalTime -= getTimeTaken();
                    setTimeTaken(0);
                    
                    if (totalTime <= 0) {
                        speak("You were hit!");
                        player.setHp(player.getHp() - attack.getDamage());
                        if (player.getHp() <= 0) {
                            speak(boss.getWinLine());
                            speak("You died");
                            return 0; // Player lost
                        }
                    }
                }
                
                totalTime = attack.getCoolDownTime();
                while (totalTime > 0) {
                    playerAction(topHp, topFp);
                    
                    if (boss.checkPhase()) {
                        boss.setPhase(2);
                        phaseChange = true;
                        break;
                    }
                    
                    totalTime -= getTimeTaken();
                    setTimeTaken(0);
                }
                if (phaseChange) break;
            }
            return 1; // Continue combat
        }
        return 1; // Continue combat
    }

    /**
     * Sets the time taken for the current action.
     * 
     * @param timeTaken The time taken in seconds.
     */
    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    /**
     * Gets the time taken for the current action.
     * 
     * @return The time taken in seconds.
     */
    public int getTimeTaken() {
        return this.timeTaken;
    }

    /**
     * Prints the given text to the console and clears the screen.
     * 
     * @param text The text to display.
     */
    public void speak(String text) {
        Scanner input = new Scanner(System.in);
        System.out.println(text);
        input.nextLine();
        clearScreen();
    }

    /**
     * Clears the console screen.
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
