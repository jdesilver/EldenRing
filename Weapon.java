/**
 * A weapon the player can wield. Beyond its three named attacks it carries a base damage, a price,
 * an attack time, and five scaling factors (one per fight stat: Strength, Dexterity, Intelligence,
 * Faith, Arcane). Damage, scaling, name and upgrade cost change as the weapon is upgraded.
 */
public class Weapon {
    /** Per-upgrade tuning: damage bonus, scaling multiplier, how much the next upgrade costs, and the name suffix. */
    private record UpgradeTier(int damageBonus, double scaleFactor, int nextUpgradeCost, String suffix) {}

    private static final UpgradeTier[] TIERS = {
        new UpgradeTier(25, 1.25, 35, " +1"),
        new UpgradeTier(50, 1.5, 85, " +2"),
        new UpgradeTier(100, 1.75, 185, " +3"),
        new UpgradeTier(200, 2.0, 0, " +4"),
    };

    private String name;
    private final String light;
    private final String heavy;
    private final String special;
    private final double[] scaling;
    private final int price;
    private int damage;
    private final int time;
    private int level;
    private int upgradePrice;

    /** The starting weapon: bare fists. */
    public Weapon() {
        this("Fist", "Punch", "Slam", "Martial Arts", new double[]{0, 0, 0, 0, 0}, 0, 50, 1);
    }

    public Weapon(String name, String light, String heavy, String special,
                  double[] scaling, int price, int damage, int time) {
        this.name = name;
        this.light = light;
        this.heavy = heavy;
        this.special = special;
        this.scaling = scaling;
        this.price = price;
        this.damage = damage;
        this.time = time;
        this.level = 0;
        this.upgradePrice = 10;
    }

    public String getName()      { return name; }
    public String getLight()     { return light; }
    public String getHeavy()     { return heavy; }
    public String getSpecial()   { return special; }
    public int getPrice()        { return price; }
    public int getTime()         { return time; }
    public int getUpgradePrice() { return upgradePrice; }

    /**
     * Damage against the given player: the weapon's base damage plus its stat scaling.
     * Each scaling factor multiplies the matching fight stat; the total is scaled by 20.
     */
    public int getNewDamage(Player player) {
        int[] stats = player.getFightStats();
        double sum = 0.0;
        for (int i = 0; i < scaling.length; i++) {
            sum += scaling[i] * stats[i];
        }
        return (int) (sum * 20 + damage);
    }

    /** Upgrades the weapon one level (up to +4), raising its damage and scaling and renaming it. */
    public void upgrade() {
        if (level >= TIERS.length) {
            Console.println("Maximum Level already achieved.\n");
            return;
        }
        UpgradeTier tier = TIERS[level];
        damage += tier.damageBonus();
        for (int i = 0; i < scaling.length; i++) {
            scaling[i] *= tier.scaleFactor();
        }
        name += tier.suffix();
        upgradePrice = tier.nextUpgradeCost();
        level++;
    }
}
