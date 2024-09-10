/**
 * Represents a weapon that a player can use in the game.
 * The weapon has attributes such as damage, scaling, and various attack types.
 */
public class Weapon {
    private String name;
    private String light; // Light attack name
    private String heavy; // Heavy attack name
    private String special; // Special attack name
    private double[] scaling; // Scaling factors for the weapon
    private int price; // Price of the weapon
    private int damage; // Base damage of the weapon
    private int upgradePrice; // Cost to upgrade the weapon
    private int level; // Current level of the weapon
    private int time; // Time needed for an attack
    private Player player; // Player using the weapon (if applicable)

    /**
     * Creates a default weapon with initial values.
     */
    public Weapon() {
        this("Fist", "Punch", "Slam", "Martial Arts", new double[]{0, 0, 0, 0, 0}, 0, 50, 1);
    }

    /**
     * Creates a weapon with the specified attributes.
     * 
     * @param name The name of the weapon.
     * @param light The name of the light attack.
     * @param heavy The name of the heavy attack.
     * @param special The name of the special attack.
     * @param scaling The scaling factors for the weapon.
     * @param price The price of the weapon.
     * @param damage The base damage of the weapon.
     * @param time The time needed for an attack.
     */
    public Weapon(String name, String light, String heavy, String special, double[] scaling, int price, int damage, int time) {
        this.name = name;
        this.light = light;
        this.heavy = heavy;
        this.special = special;
        this.scaling = scaling;
        this.price = price;
        this.damage = damage;
        this.level = 0;
        this.time = time;
        this.upgradePrice = 10;
    }

    public String getName() {
        return name;
    }

    public String getLight() {
        return light;
    }

    public String getHeavy() {
        return heavy;
    }

    public String getSpecial() {
        return special;
    }

    public double[] getScaling() {
        return scaling;
    }

    public int getPrice() {
        return price;
    }

    public int getDamage() {
        return damage;
    }

    /**
     * Calculates the new damage based on the weapon's scaling and the player's stats.
     * 
     * @param player The player using the weapon.
     * @return The calculated damage after applying scaling factors.
     */
    public int getNewDamage(Player player) {
        int newDamage;
        double[] scaling = this.getScaling();
        int[] stats = player.getFightStats();
        double sum = 0.0;
        
        for (int i = 0; i < scaling.length; i++) {
            sum += scaling[i] * stats[i];
        }
        
        newDamage = (int)(sum * 20 + damage);
        
        return newDamage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public void setUpgradePrice(int upgradePrice) {
        this.upgradePrice = upgradePrice;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTime() {
        return time;
    }

    /**
     * Upgrades the weapon, increasing its damage and scaling factors.
     * The weapon's level and upgrade price are updated accordingly.
     */
    public void upgrade() {
        if (level == 0) {
            damage += 25;
            for (int i = 0; i < 5; i++) {
                scaling[i] *= 1.25;
            }
            level++;
            upgradePrice += 25;
            name = name + " +1";
        } else if (level == 1) {
            damage += 50;
            for (int i = 0; i < 5; i++) {
                scaling[i] *= 1.5;
            }
            level++;
            upgradePrice += 50;
            name = name + " +2";
        } else if (level == 2) {
            damage += 100;
            for (int i = 0; i < 5; i++) {
                scaling[i] *= 1.75;
            }
            level++;
            upgradePrice += 100;
            name = name + " +3";
        } else if (level == 3) {
            damage += 200;
            for (int i = 0; i < 5; i++) {
                scaling[i] *= 2.0;
            }
            level++;
            upgradePrice = 0; // No further upgrades available
            name = name + " +4";
        } else {
            System.out.println("Maximum Level already achieved.\n");
        }
    }
}
