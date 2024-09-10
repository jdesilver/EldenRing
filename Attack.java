/**
 * Represents an attack with attributes such as charge-up time, cooldown time, dodge directions, and damage.
 */
public class Attack {
    private String line; // Text to be displayed during the attack
    private int chargeUpTime; // Time required to charge up the attack
    private int coolDownTime; // Time required for the attack to cool down
    private int[] dodgeDirections; // Possible directions to dodge the attack
    private int damage; // Damage dealt by the attack

    /**
     * Creates an Attack with specified attributes.
     * 
     * @param line The text to display when the attack is used.
     * @param chargeUpTime The time needed to charge up the attack.
     * @param coolDownTime The time needed for the attack to cool down.
     * @param dodgeDirections Array representing the directions in which the attack can be dodged.
     * @param damage The amount of damage the attack deals.
     */
    public Attack(String line, int chargeUpTime, int coolDownTime, int[] dodgeDirections, int damage) {
        this.line = line;
        this.chargeUpTime = chargeUpTime;
        this.coolDownTime = coolDownTime;
        this.dodgeDirections = dodgeDirections;
        this.damage = damage;
    }

    /**
     * Gets the line of text displayed during the attack.
     * 
     * @return The line of text for the attack.
     */
    public String getLine() {
        return this.line;
    }

    /**
     * Gets the charge-up time of the attack.
     * 
     * @return The charge-up time in seconds.
     */
    public int getChargeUpTime() {
        return chargeUpTime;
    }

    /**
     * Gets the cooldown time of the attack.
     * 
     * @return The cooldown time in seconds.
     */
    public int getCoolDownTime() {
        return coolDownTime;
    }

    /**
     * Gets the possible dodge directions for the attack.
     * 
     * @return An array of integers representing the dodge directions.
     */
    public int[] getDodgeDirections() {
        return dodgeDirections;
    }

    /**
     * Gets the damage dealt by the attack.
     * 
     * @return The damage value of the attack.
     */
    public int getDamage() {
        return this.damage;
    }
}
