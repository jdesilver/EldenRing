import java.util.List;

/**
 * Represents a sequence of attacks performed by a boss.
 */
public class Combo {
    private List<Attack> attacks; // List of attacks in the combo

    /**
     * Creates a Combo instance with the given list of attacks.
     * 
     * @param attacks The list of attacks in this combo.
     */
    public Combo(List<Attack> attacks) {
        this.attacks = attacks;
    }

    /**
     * Gets the list of attacks in this combo.
     * 
     * @return A list of attacks.
     */
    public List<Attack> getAttacks() {
        return attacks;
    }
}
