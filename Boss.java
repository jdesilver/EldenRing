import java.util.List;
import java.util.Random;

/**
 * A boss encounter. Every boss shares this behaviour and differs only in data — its health, rune
 * reward, dialogue, and the combos it uses in each phase — so {@link Bestiary} builds them all from
 * one class rather than a subclass apiece. A boss enters phase 2 once its health drops to half.
 */
public class Boss {
    private final int originalHp;
    private final int runes;
    private final String winLine;
    private final String phaseLine;
    private final String deathLine;
    private final List<Combo> phase1Combos;
    private final List<Combo> phase2Combos;
    private final Random random = new Random();

    private int hp;
    private int phase = 1;

    public Boss(int hp, int runes, String winLine, String phaseLine, String deathLine,
                List<Combo> phase1Combos, List<Combo> phase2Combos) {
        this.hp = hp;
        this.originalHp = hp;
        this.runes = runes;
        this.winLine = winLine;
        this.phaseLine = phaseLine;
        this.deathLine = deathLine;
        this.phase1Combos = phase1Combos;
        this.phase2Combos = phase2Combos;
    }

    public int getHp()          { return hp; }
    public void setHp(int hp)   { this.hp = hp; }
    public int getRunes()       { return runes; }
    public int getPhase()       { return phase; }
    public void setPhase(int p) { this.phase = p; }
    public String getWinLine()  { return winLine; }
    public String getDeathLine(){ return deathLine; }

    /** Reduces the boss's health by {@code amount} and returns the amount lost. */
    public int loseHp(int amount) {
        hp -= amount;
        return amount;
    }

    /** Picks a random combo from the boss's current phase. */
    public Combo nextCombo() {
        List<Combo> combos = (phase == 1) ? phase1Combos : phase2Combos;
        return combos.get(random.nextInt(combos.size()));
    }

    /**
     * If the boss has dropped to half health while still in phase 1, announces the phase transition
     * and reports it (the caller advances the phase). Returns false otherwise.
     */
    public boolean checkPhase() {
        if (hp <= originalHp / 2 && phase == 1) {
            Console.speak(phaseLine);
            return true;
        }
        return false;
    }
}
