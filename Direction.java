/**
 * The four dodge directions. Their order matters: the in-combat dodge menu numbers them 1-4,
 * and boss attack data stores the dodgeable directions by the matching 0-based index.
 */
enum Direction {
    FORWARD("Forward"),
    BACKWARD("Backward"),
    RIGHT("Right"),
    LEFT("Left");

    private final String label;

    Direction(String label) {
        this.label = label;
    }

    String label() {
        return label;
    }

    /** The direction for a 1-based menu choice (1=Forward .. 4=Left), or {@code null} if out of range. */
    static Direction fromMenuChoice(int choice) {
        return (choice >= 1 && choice <= values().length) ? values()[choice - 1] : null;
    }

    /** The direction a boss attack stores by its 0-based index. */
    static Direction fromIndex(int index) {
        return values()[index];
    }
}
