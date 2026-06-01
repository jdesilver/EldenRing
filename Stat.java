/**
 * The eight character stats, in the order they are displayed and stored in the player's stat array.
 * The final five (Strength through Arcane) are the "fight stats" that drive weapon damage scaling.
 */
enum Stat {
    VIGOR("Vigor"),
    MIND("Mind"),
    ENDURANCE("Endurance"),
    STRENGTH("Strength"),
    DEXTERITY("Dexterity"),
    INTELLIGENCE("Intelligence"),
    FAITH("Faith"),
    ARCANE("Arcane");

    static final int COUNT = values().length;

    private final String label;

    Stat(String label) {
        this.label = label;
    }

    String label() {
        return label;
    }
}
