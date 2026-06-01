import java.util.EnumSet;
import java.util.Set;

/**
 * A single telegraphed boss attack.
 *
 * @param line       the description shown while the attack charges
 * @param chargeUp   time the attack takes to land (the window the player acts within)
 * @param coolDown   time the boss is vulnerable afterwards
 * @param dodgeable  the two directions that successfully dodge the attack
 * @param damage     damage dealt if it connects
 */
record Attack(String line, int chargeUp, int coolDown, Set<Direction> dodgeable, int damage) {

    /** Convenience constructor matching the source data: dodge directions given as two indices. */
    Attack(String line, int chargeUp, int coolDown, int dir1, int dir2, int damage) {
        this(line, chargeUp, coolDown,
             EnumSet.of(Direction.fromIndex(dir1), Direction.fromIndex(dir2)), damage);
    }
}
