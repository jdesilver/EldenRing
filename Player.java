import java.util.Scanner;

/**
 * Represents the player character in the game.
 * The player has attributes such as health, focus points (FP), stamina, and runes.
 * The player can also wield a weapon and engage in combat with bosses.
 */
public class Player {
    private String name;
    private int hp; // Health Points
    private int fp; // Focus Points
    private int stamina; // Player's stamina for combat actions
    private int[] stats; // Array to store player stats
    private Weapon hand; // Weapon the player is using
    private int runes; // Currency for buying/upgrading weapons or leveling up
    private int healingTotal; // Number of heals player can perform

    /**
     * Creates a new Player with a default weapon, HP, FP, and starting runes.
     * 
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.hand = new Weapon(); // Default weapon
        this.hp = 300;
        this.fp = 200;
        this.stamina = 0;
        this.stats = new int[8]; // Initialize stats array
        this.runes = 15; // Starting runes
        this.healingTotal = 2; // Default healing amount
    }

    /**
     * Handles player's attack on the boss, based on the attack type.
     * 
     * @param boss The boss being attacked.
     * @param player The player performing the attack.
     * @param type The type of attack: 1 (Light), 2 (Heavy), 3 (Special).
     * @return Time cost of the attack action.
     */
    public int attack(Boss boss, Player player, int type) {
        switch (type) {
            case 1:
                clearScreen();
                speak("You use " + player.getHand().getLight() + "!");
                speak("You hit for " + boss.loseHp(player.getHand().getNewDamage(player)) + " hp!");
                return ((player.getHand().getTime()) - (int) player.getStamina() / 10);
            case 2:
                clearScreen();
                speak("You use " + player.getHand().getHeavy() + "!");
                speak("You hit for " + boss.loseHp(player.getHand().getNewDamage(player) * 2) + " hp!");
                return ((player.getHand().getTime() * 2) - (int) player.getStamina() / 10);
            case 3:
                clearScreen();
                speak("You use " + player.getHand().getSpecial() + "!");
                speak("You hit for " + boss.loseHp(player.getHand().getNewDamage(player) * 2) + " hp!");
                return ((player.getHand().getTime()) - (int) player.getStamina() / 10);
        }
        return -1;
    }

    /**
     * Handles player's dodge action based on the chosen direction.
     * 
     * @param direction The direction to dodge: 1 (Forward), 2 (Backward), 3 (Right), 4 (Left).
     * @return The corresponding dodge action.
     */
    public int dodge(int direction) {
        switch (direction) {
            case 1:
                clearScreen();
                speak("Dodged Forward!");
                return 1;
            case 2:
                clearScreen();
                speak("Dodged Backward!");
                return 2;
            case 3:
                clearScreen();
                speak("Dodged Right!");
                return 3;
            case 4:
                clearScreen();
                speak("Dodged Left!");
                return 4;
        }
        return -1;
    }

    /**
     * Heals the player by either increasing HP or FP.
     * 
     * @param player The player character.
     * @param type True to heal HP, False to heal FP.
     * @param top The maximum value that HP or FP can reach.
     * @return Time cost of the healing action.
     */
    public int heal(Player player, boolean type, int top) {
        if (type) {
            player.hp += 50;
            if (player.hp > top) player.hp = top;
            return 2; // Takes 2 seconds to heal
        } else {
            player.fp += 50;
            if (player.fp > top) player.fp = top;
            return 2;
        }
    }

    // Getters and setters for the player's attributes

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getFp() {
        return fp;
    }

    public void setFp(int fp) {
        this.fp = fp;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public Weapon getHand() {
        return hand;
    }

    public void setHand(Weapon hand) {
        this.hand = hand;
    }

    public int getRunes() {
        return runes;
    }

    public void spendRunes(int amount) {
        this.runes -= amount;
    }

    public void addRunes(int amount) {
        this.runes += amount;
    }

    public int[] getStats() {
        return stats;
    }

    /**
     * Returns an array of player's combat stats.
     * 
     * @return An array of relevant stats for combat.
     */
    public int[] getFightStats() {
        int[] fightStats = new int[5];
        System.arraycopy(stats, 3, fightStats, 0, 5); // Extract relevant stats for fighting
        return fightStats;
    }

    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public int getStats(int index) {
        return stats[index];
    }

    public void setStats(int index, int stat) {
        this.stats[index] = stat;
    }

    public int getHealingTotal() {
        return healingTotal;
    }

    public void setHealingTotal(int healingTotal) {
        this.healingTotal = healingTotal;
    }

    public void addHealingTotal() {
        this.healingTotal += 1;
    }

    /**
     * Displays text to the player and waits for input before continuing.
     * 
     * @param text The text to display to the player.
     */
    public void speak(String text) {
        Scanner input = new Scanner(System.in);
        System.out.println(text);
        input.nextLine(); // Wait for player to press Enter
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
