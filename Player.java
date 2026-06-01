import java.util.Arrays;

/**
 * The Tarnished: the player's character. Tracks health, focus, the eight stats, the equipped weapon,
 * runes (the currency) and remaining heals, and performs the player's combat actions.
 */
public class Player {
    private String name;
    private int hp;
    private int fp;
    private int stamina;
    private int[] stats;
    private Weapon hand;
    private int runes;
    private int healingTotal;

    public Player(String name) {
        this.name = name;
        this.hand = new Weapon();      // start with bare fists
        this.hp = 300;
        this.fp = 200;
        this.stamina = 0;
        this.stats = new int[Stat.COUNT];
        this.runes = 15;
        this.healingTotal = 2;
    }

    /**
     * Performs an attack of the given type (1 = light, 2 = heavy, 3 = special) against the boss,
     * announcing the strike and the damage dealt.
     *
     * @return the time the action costs (faster the more stamina the player has).
     */
    public int attack(Boss boss, int type) {
        int base = hand.getNewDamage(this);
        switch (type) {
            case 1 -> {
                Console.clear();
                Console.speak("You use " + hand.getLight() + "!");
                Console.speak("You hit for " + boss.loseHp(base) + " hp!");
                return hand.getTime() - stamina / 10;
            }
            case 2 -> {
                Console.clear();
                Console.speak("You use " + hand.getHeavy() + "!");
                Console.speak("You hit for " + boss.loseHp(base * 2) + " hp!");
                return hand.getTime() * 2 - stamina / 10;
            }
            case 3 -> {
                Console.clear();
                Console.speak("You use " + hand.getSpecial() + "!");
                Console.speak("You hit for " + boss.loseHp(base * 2) + " hp!");
                return hand.getTime() - stamina / 10;
            }
        }
        return -1;
    }

    /** Dodges in the given menu direction (1-4), announcing it; returns the direction dodged. */
    public Direction dodge(int menuChoice) {
        Direction direction = Direction.fromMenuChoice(menuChoice);
        if (direction == null) {
            return null;
        }
        Console.clear();
        Console.speak("Dodged " + direction.label() + "!");
        return direction;
    }

    /** Restores 50 HP or FP (capped at {@code cap}); takes 2 time. */
    public int heal(boolean healHp, int cap) {
        if (healHp) {
            hp = Math.min(hp + 50, cap);
        } else {
            fp = Math.min(fp + 50, cap);
        }
        return 2;
    }

    /** The five stats that drive weapon scaling: Strength, Dexterity, Intelligence, Faith, Arcane. */
    public int[] getFightStats() {
        return Arrays.copyOfRange(stats, Stat.STRENGTH.ordinal(), Stat.ARCANE.ordinal() + 1);
    }

    public String getName()                 { return name; }
    public int getHp()                      { return hp; }
    public void setHp(int hp)               { this.hp = hp; }
    public int getFp()                      { return fp; }
    public void setFp(int fp)               { this.fp = fp; }
    public Weapon getHand()                 { return hand; }
    public void setHand(Weapon hand)        { this.hand = hand; }
    public int getRunes()                   { return runes; }
    public void spendRunes(int amount)      { this.runes -= amount; }
    public void addRunes(int amount)        { this.runes += amount; }
    public int[] getStats()                 { return stats; }
    public void setStats(int[] stats)       { this.stats = stats; }
    public int getStats(int index)          { return stats[index]; }
    public int getHealingTotal()            { return healingTotal; }
    public void setHealingTotal(int total)  { this.healingTotal = total; }
    public void addHealingTotal()           { this.healingTotal += 1; }
}
