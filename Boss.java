import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a generic boss character in a game.
 * Provides attributes for health (HP), a list of attack combos, and dialogue lines for different scenarios.
 * Bosses can randomly choose from a set of combos to use during combat and can change phases.
 */
public class Boss {
    private int hp; // The boss's current health points (HP)
    protected List<Combo> combos1; // Combos available to the boss in phase 1
    protected List<Combo> combos2; // Combos available to the boss in phase 2
    private Random random; // Random number generator for selecting combos
    private String winLine; // Dialogue line when the boss wins
    private String phaseLine; // Dialogue line for a specific phase
    private String deathLine; // Dialogue line when the boss dies
    private int phase; // Indicates the current phase
    private int runes; // Number of runes dropped by the boss
    private int originalHp; // The boss's original health points

    /**
     * Constructs a new Boss object with specified attributes.
     * 
     * @param hp        The initial health points of the boss
     * @param winLine   The dialogue line when the boss wins
     * @param phaseLine The dialogue line for a specific phase
     * @param deathLine The dialogue line when the boss dies
     * @param runes     The number of runes dropped by the boss
     */
    public Boss(int hp, String winLine, String phaseLine, String deathLine, int runes) {
        this.hp = hp;
        this.originalHp = hp;
        this.winLine = winLine;
        this.phaseLine = phaseLine;
        this.deathLine = deathLine;
        this.combos1 = new ArrayList<>();
        this.combos2 = new ArrayList<>();
        this.random = new Random();
        this.runes = runes;
        this.phase = 1;
    }
    
    /**
     * Prints the given text to the console and waits for user input.
     * 
     * @param text The text to print
     */
    public void speak(String text) {
        Scanner input = new Scanner(System.in);
        System.out.println(text);
        input.nextLine();
        clearScreen();
    }

    /**
     * Clears the console screen.
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Gets the boss's current health points (HP).
     * 
     * @return The boss's current HP
     */
    public int getHp() {
        return hp;
    }

    /**
     * Sets the boss's health points (HP).
     * 
     * @param hp The new value for the boss's HP
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Reduces the boss's health points (HP) by the specified amount.
     * 
     * @param num The amount of HP to reduce
     * @return The amount of HP lost
     */
    public int loseHp(int num) {
        this.hp -= num;
        return num;
    }

    /**
     * Gets the dialogue line for when the boss wins.
     * 
     * @return The boss's win dialogue line
     */
    public String getWinLine() {
        return this.winLine;
    }

    /**
     * Gets the dialogue line for a specific phase of the boss.
     * 
     * @return The boss's phase dialogue line
     */
    public String getPhaseLine() {
        return this.phaseLine;
    }

    /**
     * Gets the dialogue line for when the boss dies.
     * 
     * @return The boss's death dialogue line
     */
    public String getDeathLine() {
        return this.deathLine;
    }

    /**
     * Gets the current phase of the boss.
     * 
     * @return The boss's current phase
     */
    public int getPhase() {
        return this.phase;
    }

    /**
     * Sets the current phase of the boss.
     * 
     * @param phase The new phase to set
     */
    public void setPhase(int phase) {
        this.phase = phase;
    }

    /**
     * Gets the number of runes dropped by the boss.
     * 
     * @return The number of runes
     */
    public int getRunes() {
        return this.runes;
    }

    /**
     * Gets the boss's original health points.
     * 
     * @return The boss's original HP
     */
    public int getOriginalHp() {
        return this.originalHp;
    }

    /**
     * Randomly selects one of the boss's available combos to use during its turn (Phase 1).
     * 
     * @return The selected Combo
     */
    public Combo chooseCombo1() {
        int comboIndex = random.nextInt(combos1.size());
        return combos1.get(comboIndex);
    }

    /**
     * Randomly selects one of the boss's available combos to use during its turn (Phase 2).
     * 
     * @return The selected Combo
     */
    public Combo chooseCombo2() {
        int comboIndex = random.nextInt(combos2.size());
        return combos2.get(comboIndex);
    }

    /**
     * Checks if the boss should change phases based on its current HP.
     * 
     * @return True if the boss's HP is below half and it is still in phase 1, otherwise false
     */
    public boolean checkPhase() {
        if (getHp() <= (originalHp / 2) && getPhase() == 1) {
            speak(getPhaseLine());
            return true;
        }
        return false;
    }
}

/**
 * Represents the boss "Margit" with specific attributes.
 * Margit is a subclass of Boss with a predefined HP value and specific attack combos.
 */
class Margit extends Boss {
    
    /**
     * Constructs the boss "Margit" with a predefined HP value and initializes its attack combos.
     */
    public Margit() {
        super(4174,
              "Put these foolish ambitions to rest.", 
              "Well, thou art of passing skill. Warrior blood must truly run in thy veins, Tarnished.",
              "I shall remember thee, Tarnished. Smouldering with thy meagre flame. Cower in Fear. Of the Night. The hands of the Fell Omen shall brook thee no quarter.",
              15);
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Margit for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Margit slowly raises his sword upwards..\n", 1, 0, new int[]{2, 3}, 80));
        combo1.add(new Attack("Margit quickly brings his sword to the side..\n", 1, 1, new int[]{0, 1}, 50));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Margit spins his sword in a wide arc..\n", 1, 0, new int[]{2, 3}, 100));
        combo2.add(new Attack("Margit performs a sweeping low attack..\n", 1, 0, new int[]{0, 1}, 60));
        combo2.add(new Attack("Margit follows up with a spinning kick..\n", 1, 1, new int[]{2, 3}, 90));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Margit stomps the ground causing a shockwave..\n", 2, 0, new int[]{2, 3}, 120));
        combo3.add(new Attack("Margit follows up with a quick overhead slash..\n", 1, 1, new int[]{0, 1}, 90));
        combos1.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Margit raises his sword and then slams it down with great force..\n", 2, 0, new int[]{2, 3}, 140));
        combo4.add(new Attack("Margit then performs a rapid series of slashes..\n", 2, 0, new int[]{0, 1}, 100));
        combo4.add(new Attack("Margit finishes with a ground pound..\n", 1, 0, new int[]{2, 3}, 130));
        combo4.add(new Attack("Margit performs a quick slashing retreat..\n", 1, 1, new int[]{0, 1}, 40));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Margit for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("The Omen lunges towards you..\n", 1, 0, new int[]{0, 1}, 20));
        combo1.add(new Attack("The Omen swings his sword overhead..\n", 2, 1, new int[]{2, 3}, 70));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("The Omen jumps into the air and summons a lightning spear..\n", 2, 0, new int[]{2, 3}, 130));
        combo2.add(new Attack("The Omen hurls the lightning spear downwards..\n", 3, 0, new int[]{0, 1}, 180));
        combo2.add(new Attack("The Omen performs a quick follow-up attack..\n", 2, 1, new int[]{2, 3}, 70));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("The Omen performs a spinning attack with his sword..\n", 1, 0, new int[]{0, 1}, 40));
        combo3.add(new Attack("The Omen follows with a series of rapid thrusts..\n", 2, 1, new int[]{2, 3}, 80));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("The Omen creates a barrier of dark energy around himself..\n", 3, 0, new int[]{2, 3}, 150));
        combo4.add(new Attack("The barrier then explodes outward, causing damage..\n", 4, 0, new int[]{0, 1}, 200));
        combo4.add(new Attack("The Omen finishes with a powerful shockwave..\n", 3, 5, new int[]{2, 3}, 175));
        combos2.add(new Combo(combo4));
    }
}

/**
 * Represents the boss "Godrick" with specific attributes.
 * Godrick is a subclass of Boss with a predefined HP value and specific attack combos.
 */
class Godrick extends Boss {

    /**
     * Constructs the boss "Godrick" with a predefined HP value and initializes its attack combos.
     */
    public Godrick() {
        super(3200,
              "Lowly Tarnished... Thou'rt unfit even to graft… Great Godfrey, did'st thou witness?",
              "Ahh, truest of dragons. Lend me thy strength… Nnngh! Forefathers, one and all… Bear witness!",
              "...I am Lord of all that is Golden.... ...And one day, we'll return together... ...To our home, bathed in rays of gold...", 
              20); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Godrick for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Godrick swings his axe in a wide arc..\n", 3, 0, new int[]{0, 2}, 100));
        combo1.add(new Attack("Godrick charges forward with a brutal overhead strike..\n", 4, 0, new int[]{1, 3}, 140));
        combo1.add(new Attack("Godrick performs a sweeping low attack..\n", 3, 0, new int[]{0, 2}, 120));
        combo1.add(new Attack("Godrick slams his axe downwards..\n", 5, 0, new int[]{1, 3}, 160));
        combo1.add(new Attack("Godrick follows with a spinning attack..\n", 4, 0, new int[]{0, 2}, 140));
        combo1.add(new Attack("Godrick finishes with a ground-shaking stomp..\n", 6, 5, new int[]{1, 3}, 180));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Godrick charges at you with a fierce tackle..\n", 3, 0, new int[]{0, 2}, 80));
        combo2.add(new Attack("Godrick performs a powerful side swipe..\n", 4, 0, new int[]{1, 3}, 100));
        combo2.add(new Attack("Godrick ends with a devastating overhead smash..\n", 6, 5, new int[]{0, 2}, 200));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Godrick unleashes a rapid flurry of axe strikes..\n", 4, 0, new int[]{0, 2}, 100));
        combo3.add(new Attack("Godrick finishes with a powerful diagonal slash..\n", 5, 4, new int[]{1, 3}, 140));
        combos1.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Godrick raises his axe and performs a wide sweep..\n", 3, 0, new int[]{0, 2}, 120));
        combo4.add(new Attack("Godrick follows with a series of rapid axe slashes..\n", 4, 0, new int[]{1, 3}, 140));
        combo4.add(new Attack("Godrick brings his axe down in a vertical strike..\n", 5, 0, new int[]{0, 2}, 160));
        combo4.add(new Attack("Godrick performs a powerful spinning attack..\n", 4, 0, new int[]{1, 3}, 180));
        combo4.add(new Attack("Godrick ends with a fierce shockwave blast..\n", 6, 5, new int[]{0, 2}, 240));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Godrick for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Godrick charges at you with a sudden rush..\n", 3, 0, new int[]{0, 2}, 100));
        combo1.add(new Attack("Godrick follows with a quick overhead slam..\n", 4, 0, new int[]{1, 3}, 140));
        combo1.add(new Attack("Godrick concludes with a powerful ground smash..\n", 5, 4, new int[]{0, 2}, 180));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Godrick swings his axe in a wide arc..\n", 3, 0, new int[]{0, 2}, 140));
        combo2.add(new Attack("Godrick performs a rapid side swipe..\n", 4, 0, new int[]{1, 3}, 160));
        combo2.add(new Attack("Godrick follows with a powerful charge..\n", 5, 0, new int[]{0, 2}, 180));
        combo2.add(new Attack("Godrick ends with a crushing overhead attack..\n", 6, 5, new int[]{1, 3}, 220));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Godrick performs a quick series of axe slashes..\n", 4, 0, new int[]{0, 2}, 110));
        combo3.add(new Attack("Godrick finishes with a powerful downward smash..\n", 5, 4, new int[]{1, 3}, 160));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Godrick raises his axe and delivers a powerful sweep..\n", 3, 0, new int[]{0, 2}, 140));
        combo4.add(new Attack("Godrick follows with a series of rapid strikes..\n", 4, 0, new int[]{1, 3}, 180));
        combo4.add(new Attack("Godrick performs a spinning attack..\n", 5, 0, new int[]{0, 2}, 200));
        combo4.add(new Attack("Godrick concludes with a devastating shockwave..\n", 6, 5, new int[]{1, 3}, 270));
        combos2.add(new Combo(combo4));
    }
}

/**
 * The RedWolf class represents the boss "Red Wolf of Radagon" with specific HP and attack combos.
 * Red Wolf is a subclass of Boss with predefined HP and specific attack patterns.
 */
class RedWolf extends Boss {

    /**
     * Constructs the boss "Red Wolf of Radagon" with a predefined HP value and initializes its attack combos.
     */
    public RedWolf() {
        super(2204, 
              "The Red Wolf of Radagon swiftly devours its prey.", 
              "The Red Wolf of Radagon's eyes glow with fierce intensity.", 
              "The Red Wolf of Radagon lets out a final growl as it falls.", 
              30); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Red Wolf for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("The Red Wolf bares its fangs, lunging at you with incredible speed...\n", 3, 0, new int[]{2, 3}, 180));
        combo1.add(new Attack("The Red Wolf leaps into the air and slashes downwards with its claws...\n", 2, 1, new int[]{0, 1}, 220));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("The Red Wolf conjures a spectral sword and swings it in a wide arc...\n", 3, 0, new int[]{0, 1}, 200));
        combo2.add(new Attack("The Red Wolf follows up with a biting lunge...\n", 2, 0, new int[]{2, 3}, 150));
        combo2.add(new Attack("The Red Wolf ends with a quick retreat, preparing for another assault...\n", 1, 3, new int[]{0, 1}, 120));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("The Red Wolf dashes to the side, then lunges at you with a fierce bite...\n", 4, 0, new int[]{2, 3}, 220));
        combo3.add(new Attack("The Red Wolf conjures a spectral sword, thrusting it forward...\n", 2, 1, new int[]{0, 1}, 180));
        combos1.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("The Red Wolf lets out a howl, summoning a spectral sword...\n", 5, 0, new int[]{2, 3}, 270));
        combo4.add(new Attack("The Red Wolf leaps forward with its claws bared, slashing multiple times...\n", 4, 0, new int[]{0, 1}, 230));
        combo4.add(new Attack("The Red Wolf ends with a powerful magical shockwave...\n", 3, 4, new int[]{2, 3}, 350));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Red Wolf for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("The Red Wolf summons multiple spectral swords, then charges at you...\n", 4, 0, new int[]{0, 1}, 320));
        combo1.add(new Attack("The Red Wolf follows with a swift bite...\n", 3, 2, new int[]{2, 3}, 220));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("The Red Wolf leaps into the air, raining down spectral swords...\n", 5, 0, new int[]{2, 3}, 370));
        combo2.add(new Attack("The Red Wolf dashes forward with a powerful slash...\n", 4, 0, new int[]{0, 1}, 270));
        combo2.add(new Attack("The Red Wolf conjures a magical blast, sending shockwaves across the ground...\n", 3, 4, new int[]{2, 3}, 420));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("The Red Wolf summons a spectral sword, slashing rapidly...\n", 3, 0, new int[]{0, 1}, 220));
        combo3.add(new Attack("The Red Wolf follows with a series of quick bites...\n", 4, 2, new int[]{2, 3}, 270));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("The Red Wolf charges up, unleashing a powerful magical roar...\n", 6, 0, new int[]{2, 3}, 420));
        combo4.add(new Attack("The Red Wolf lunges forward with its claws, then spins around for another attack...\n", 5, 0, new int[]{0, 1}, 320));
        combo4.add(new Attack("The Red Wolf ends with a sweeping spectral sword attack...\n", 4, 3, new int[]{2, 3}, 370));
        combos2.add(new Combo(combo4));
    }
}

/**
 * The Rennala class represents the boss "Rennala, Queen of the Full Moon" with specific HP and attack combos.
 * Rennala is a subclass of Boss with predefined HP and specific attack patterns.
 */
class Rennala extends Boss {

    /**
     * Constructs the boss "Rennala, Queen of the Full Moon" with a predefined HP value and initializes its attack combos.
     */
    public Rennala() {
        super(3493, 
              "Be not afeard, little culver. Thy fate lieth under my moon.", 
              "Ahh, my beloved... Have no fear, I will hold thee. Patience. Ye will be countless born, forever and ever.\n\nRanni: Upon my name as Ranni the Witch. Mother's rich slumber shall not be disturbed by thee. Foul trespasser. Send word far and wide. Of the last Queen of Caria, Rennala of the Full Moon. And the majesty of the night she conjureth.", 
              "Oh little Ranni, my dear daughter. Weave thy night into being.", 
              35); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Rennala for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Rennala raises her staff, summoning a barrage of magical projectiles...\n", 4, 0, new int[]{0, 1}, 180));
        combo1.add(new Attack("Rennala conjures a protective shield around herself, reflecting damage back...\n", 3, 1, new int[]{2, 3}, 140));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Rennala calls forth a magical storm, with lightning crashing down...\n", 5, 0, new int[]{2, 3}, 220));
        combo2.add(new Attack("Rennala follows with a wave of arcane energy...\n", 4, 0, new int[]{0, 1}, 200));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Rennala hurls a sphere of concentrated magic at you...\n", 3, 0, new int[]{0, 1}, 180));
        combo3.add(new Attack("Rennala summons spectral arms to strike from a distance...\n", 4, 0, new int[]{2, 3}, 160));
        combos1.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Rennala's staff glows as she channels a powerful beam of light...\n", 6, 0, new int[]{0, 1}, 350));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Rennala for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Rennala summons a spectral wolf to attack alongside her...\n", 4, 0, new int[]{0, 1}, 220));
        combo1.add(new Attack("Rennala casts a series of homing magic missiles...\n", 5, 0, new int[]{2, 3}, 240));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Rennala summons a draconic spirit to unleash a fiery breath...\n", 5, 0, new int[]{2, 3}, 270));
        combo2.add(new Attack("Rennala follows up with a blast of arcane energy...\n", 4, 0, new int[]{0, 1}, 220));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Rennala summons a horde of spectral soldiers to attack...\n", 6, 0, new int[]{0, 1}, 320));
        combo3.add(new Attack("Rennala finishes with a burst of magical energy...\n", 5, 0, new int[]{2, 3}, 300));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Rennala channels her full power, unleashing a massive energy explosion...\n", 7, 0, new int[]{0, 1}, 450));
        combo4.add(new Attack("Rennala then calls forth a meteor shower...\n", 6, 0, new int[]{2, 3}, 400));
        combos2.add(new Combo(combo4));
    }
}

/**
 * The Serpent class represents the boss "God-Devouring Serpent" with specific HP and attack combos.
 * Serpent is a subclass of Boss with predefined HP and specific attack patterns.
 */
class Serpent extends Boss {

    /**
     * Constructs the boss "God-Devouring Serpent" with a predefined HP value and initializes its attack combos.
     */
    public Serpent() {
        super(30439, 
              "Now, we can devour the gods, together!", 
              "Hmm... Very well. You... Join the Serpent King, as family... Together, we will devour the very gods!", 
              "No one will hold me captive. A serpent never dies. Ha ha ha...", 
              40); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to the God-Devouring Serpent for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("The Serpent lunges forward, attempting to swallow you whole...\n", 5, 0, new int[]{0, 1}, 450));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("The Serpent snaps its jaws shut with bone-crushing force...\n", 4, 0, new int[]{0, 1}, 400));
        combo2.add(new Attack("The Serpent follows up with a venomous tail swipe...\n", 4, 0, new int[]{2, 3}, 350));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("The Serpent coils around, striking with its massive tail...\n", 4, 0, new int[]{0, 1}, 350));
        combo3.add(new Attack("The Serpent releases a cloud of toxic venom...\n", 6, 0, new int[]{2, 3}, 500));
        combo3.add(new Attack("The Serpent snaps its jaws shut with a furious bite...\n", 5, 0, new int[]{0, 1}, 450));
        combos1.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("The Serpent rears back, spitting out a stream of burning acid...\n", 5, 0, new int[]{0, 1}, 550));
        combo4.add(new Attack("The Serpent follows up with a sweeping tail attack...\n", 5, 0, new int[]{2, 3}, 500));
        combo4.add(new Attack("The Serpent bites with renewed ferocity...\n", 6, 0, new int[]{0, 1}, 550));
        combo4.add(new Attack("The Serpent lashes out with a powerful tail slam...\n", 7, 0, new int[]{2, 3}, 600));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to the God-Devouring Serpent for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("The Serpent roars, summoning a wave of deadly poison...\n", 6, 0, new int[]{0, 1}, 550));
        combo1.add(new Attack("The Serpent follows up with a vicious bite...\n", 5, 0, new int[]{2, 3}, 500));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("The Serpent coils and squeezes, crushing anything in its grasp...\n", 6, 0, new int[]{0, 1}, 600));
        combo2.add(new Attack("The Serpent then releases a venomous blast...\n", 5, 0, new int[]{2, 3}, 550));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("The Serpent sprays a stream of venomous acid...\n", 5, 0, new int[]{2, 3}, 550));
        combo3.add(new Attack("The Serpent strikes with blinding speed...\n", 4, 0, new int[]{0, 1}, 500));
        combo3.add(new Attack("The Serpent follows with a crushing tail slam...\n", 5, 0, new int[]{2, 3}, 550));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("The Serpent unleashes its full fury, biting and thrashing with deadly intent...\n", 8, 0, new int[]{0, 1}, 750));
        combo4.add(new Attack("The Serpent then follows with a venomous cloud...\n", 6, 0, new int[]{2, 3}, 650));
        combo4.add(new Attack("Finally, the Serpent performs a powerful tail swipe...\n", 7, 0, new int[]{0, 1}, 700));
        combos2.add(new Combo(combo4));
    }
}

/**
 * The Radahn class represents the boss "Starscourge Radahn" with specific HP and attack combos.
 * Radahn is a subclass of Boss with predefined HP and specific attack patterns.
 */
class Radahn extends Boss {

    /**
     * Constructs the boss "Starscourge Radahn" with a predefined HP value and initializes its attack combos.
     */
    public Radahn() {
        super(9572, 
              "Radahn stands tall, the sky darkened by his immense presence and the power of his gravity magic.", 
              "Radahn readies his colossal weapon, ready to unleash devastating blows upon you.", 
              "Radahn staggers, the force of his attacks finally taking its toll.", 
              45); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Starscourge Radahn for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Radahn swings his colossal weapon in a sweeping arc...\n", 6, 0, new int[]{0, 1}, 500));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Radahn charges forward with a powerful thrust...\n", 5, 0, new int[]{0, 1}, 450));
        combo2.add(new Attack("He then follows up with a crushing overhead slam...\n", 6, 0, new int[]{2, 3}, 500));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Radahn unleashes a series of rapid swings...\n", 4, 0, new int[]{0, 1}, 350));
        combo3.add(new Attack("He charges up for a devastating gravity smash...\n", 7, 0, new int[]{2, 3}, 650));
        combo3.add(new Attack("Radahn finishes with a powerful overhead swing...\n", 6, 0, new int[]{0, 1}, 500));
        combos1.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Radahn begins with a gravity-infused swing...\n", 6, 0, new int[]{0, 1}, 550));
        combo4.add(new Attack("He then stomps the ground, creating a shockwave...\n", 5, 0, new int[]{2, 3}, 500));
        combo4.add(new Attack("Radahn follows with a series of sweeping slashes...\n", 6, 0, new int[]{0, 1}, 550));
        combo4.add(new Attack("Finally, he performs a massive gravity slam...\n", 7, 0, new int[]{2, 3}, 650));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Starscourge Radahn for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Radahn unleashes a gravity-enhanced charge...\n", 7, 0, new int[]{0, 1}, 600));
        combo1.add(new Attack("He then follows with a series of powerful swings...\n", 6, 0, new int[]{2, 3}, 550));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Radahn performs a devastating ground smash...\n", 7, 0, new int[]{0, 1}, 650));
        combo2.add(new Attack("He then performs a high-speed vertical swing...\n", 6, 0, new int[]{2, 3}, 600));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Radahn performs a series of rapid swings...\n", 5, 0, new int[]{2, 3}, 550));
        combo3.add(new Attack("He then charges up and slams the ground...\n", 7, 0, new int[]{0, 1}, 700));
        combo3.add(new Attack("Radahn finishes with a massive overhead slam...\n", 8, 0, new int[]{2, 3}, 750));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Radahn unleashes his full gravity powers, causing massive upheaval...\n", 9, 0, new int[]{0, 1}, 750));
        combo4.add(new Attack("He follows with a powerful series of slashes...\n", 7, 0, new int[]{2, 3}, 650));
        combo4.add(new Attack("Radahn then stomps the ground, sending shockwaves...\n", 6, 0, new int[]{0, 1}, 600));
        combo4.add(new Attack("Finally, he performs a devastating gravity slam...\n", 8, 0, new int[]{2, 3}, 700));
        combos2.add(new Combo(combo4));
    }
}

/**
 * The Mohg class represents the boss "Mohg, Lord of Blood" with specific HP and attack combos.
 * Mohg is a subclass of Boss with predefined HP and specific attack patterns.
 */
class Mohg extends Boss {

    /**
     * Constructs the boss "Mohg, Lord of Blood" with a predefined HP value and initializes its attack combos.
     */
    public Mohg() {
        super(18389, 
              "Miquella is mine and mine alone.", 
              "Trēs! Duo! Ūnus! Nihil! Nihil! Nihil!", 
              "Ahh, I can see it, clear as day! The coming of our dynasty! Mohgwyn!", 
              50); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Mohg for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Mohg slashes with his blood-infused weapon...\n", 4, 0, new int[]{0, 1}, 400));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Mohg casts a wave of blood magic...\n", 5, 0, new int[]{0, 1}, 450));
        combo2.add(new Attack("He follows with a blood-imbued thrust...\n", 6, 0, new int[]{2, 3}, 500));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Mohg unleashes a rapid series of slashes...\n", 4, 0, new int[]{0, 1}, 350));
        combo3.add(new Attack("He follows with a powerful blood explosion...\n", 7, 0, new int[]{2, 3}, 550));
        combo3.add(new Attack("Mohg finishes with a sweeping blood attack...\n", 5, 0, new int[]{0, 1}, 450));
        combos1.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Mohg summons a torrent of blood...\n", 6, 0, new int[]{0, 1}, 500));
        combo4.add(new Attack("He follows with a quick succession of slashes...\n", 5, 0, new int[]{2, 3}, 450));
        combo4.add(new Attack("Mohg then performs a high-speed blood thrust...\n", 6, 0, new int[]{0, 1}, 500));
        combo4.add(new Attack("Finally, he unleashes a massive blood explosion...\n", 8, 0, new int[]{2, 3}, 600));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Mohg for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Mohg performs a powerful blood surge...\n", 7, 0, new int[]{0, 1}, 550));
        combo1.add(new Attack("He follows with a series of intense blood slashes...\n", 6, 0, new int[]{2, 3}, 500));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Mohg casts a blood storm...\n", 8, 0, new int[]{0, 1}, 600));
        combo2.add(new Attack("He then charges with a blood-infused lunge...\n", 6, 0, new int[]{2, 3}, 550));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Mohg initiates a series of rapid blood strikes...\n", 5, 0, new int[]{0, 1}, 500));
        combo3.add(new Attack("He follows with a blood vortex...\n", 7, 0, new int[]{2, 3}, 600));
        combo3.add(new Attack("Mohg finishes with a powerful blood eruption...\n", 8, 0, new int[]{0, 1}, 650));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Mohg performs a devastating blood surge...\n", 8, 0, new int[]{0, 1}, 600));
        combo4.add(new Attack("He follows with an aggressive series of slashes...\n", 7, 0, new int[]{2, 3}, 550));
        combo4.add(new Attack("Mohg then unleashes a massive blood explosion...\n", 9, 0, new int[]{0, 1}, 700));
        combo4.add(new Attack("Finally, he performs a high-speed blood thrust...\n", 6, 0, new int[]{2, 3}, 550));
        combos2.add(new Combo(combo4));
    }
}

/**
 * The GoldenGodfrey class represents the boss "Golden Godfrey" with specific HP and attack combos.
 * Golden Godfrey is a subclass of Boss with predefined HP and specific attack patterns.
 */
class GoldenGodfrey extends Boss {

    /**
     * Constructs the boss "Golden Godfrey" with a predefined HP value and initializes its attack combos.
     */
    public GoldenGodfrey() {
        super(21903, 
              "Golden Godfrey, the regal warrior, stands with an imposing aura.", 
              "He prepares to unleash a series of devastating attacks.", 
              "Golden Godfrey pauses, his mighty form readying for the next onslaught.", 
              55); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Golden Godfrey for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Golden Godfrey swings his colossal axe...\n", 6, 0, new int[]{0, 1}, 450));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Godfrey performs a powerful overhead swing...\n", 7, 0, new int[]{0, 1}, 500));
        combo2.add(new Attack("He follows with a quick horizontal swipe...\n", 5, 0, new int[]{2, 3}, 450));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Golden Godfrey charges his axe...\n", 8, 0, new int[]{0, 1}, 550));
        combo3.add(new Attack("He unleashes a series of mighty swings...\n", 6, 0, new int[]{2, 3}, 500));
        combo3.add(new Attack("Godfrey finishes with a devastating slam...\n", 7, 0, new int[]{0, 1}, 550));
        combos1.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Golden Godfrey starts with a massive ground slam...\n", 9, 0, new int[]{0, 1}, 600));
        combo4.add(new Attack("He follows with a rapid sequence of strikes...\n", 7, 0, new int[]{2, 3}, 550));
        combo4.add(new Attack("Godfrey ends with a powerful thrust...\n", 8, 0, new int[]{0, 1}, 600));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Golden Godfrey for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Golden Godfrey performs a fearsome axe slam...\n", 8, 0, new int[]{0, 1}, 550));
        combo1.add(new Attack("He then executes a wide swing...\n", 6, 0, new int[]{2, 3}, 500));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Godfrey initiates a powerful spin attack...\n", 9, 0, new int[]{0, 1}, 600));
        combo2.add(new Attack("He follows with a ground-shaking slam...\n", 8, 0, new int[]{2, 3}, 550));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Golden Godfrey starts with a mighty overhead strike...\n", 7, 0, new int[]{0, 1}, 500));
        combo3.add(new Attack("He then performs a rapid series of axe swings...\n", 8, 0, new int[]{2, 3}, 600));
        combo3.add(new Attack("Godfrey ends with a powerful axe slam...\n", 9, 0, new int[]{0, 1}, 650));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Golden Godfrey begins with a devastating multi-hit combo...\n", 10, 0, new int[]{0, 1}, 650));
        combo4.add(new Attack("He then performs a massive ground smash...\n", 9, 0, new int[]{2, 3}, 600));
        combo4.add(new Attack("Godfrey follows with a powerful spinning attack...\n", 8, 0, new int[]{0, 1}, 550));
        combo4.add(new Attack("Finally, he ends with a colossal finishing strike...\n", 10, 0, new int[]{2, 3}, 700));
        combos2.add(new Combo(combo4));
    }
}

/**
 * The Morgott class represents the boss "Morgott" with specific HP and attack combos.
 * Morgott is a subclass of Boss with predefined HP and specific attack patterns.
 */
class Morgott extends Boss {

    /**
     * Constructs the boss "Morgott" with a predefined HP value and initializes its attack combos.
     */
    public Morgott() {
        super(10399, 
              "Put these foolish ambitions to rest. May the curse seep to thy very soul. An apt reward for thy brash ambition", 
              "Hrghraah! The thrones... stained by my curse... Such shame I cannot bear. Thy part in this shall not be forgiven.", 
              "Tarnished, thou'rt but a fool. The Erdtree wards off all who deign approach. We are... we are all forsaken. None may claim the title of Elden Lord. Upon talking to Morgott twice: Thy deeds shall be met with failure, just as I.", 
              60); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Morgott for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Morgott swings his cursed blade...\n", 5, 0, new int[]{0, 1}, 400));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Morgott begins with a dark magical blast...\n", 6, 0, new int[]{0, 1}, 450));
        combo2.add(new Attack("He follows with a quick slash...\n", 4, 0, new int[]{2, 3}, 400));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Morgott summons dark energy...\n", 7, 0, new int[]{0, 1}, 500));
        combo3.add(new Attack("He unleashes a flurry of cursed strikes...\n", 6, 0, new int[]{2, 3}, 450));
        combo3.add(new Attack("Morgott concludes with a powerful ground slam...\n", 8, 0, new int[]{0, 1}, 550));
        combos1.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Morgott starts with a sweeping dark arc...\n", 7, 0, new int[]{0, 1}, 500));
        combo4.add(new Attack("He follows with a rapid multi-hit strike...\n", 6, 0, new int[]{2, 3}, 450));
        combo4.add(new Attack("Morgott finishes with a devastating dark explosion...\n", 8, 0, new int[]{0, 1}, 600));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Morgott for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Morgott begins with a powerful dark magic surge...\n", 8, 0, new int[]{0, 1}, 550));
        combo1.add(new Attack("He follows with a cursed blade swipe...\n", 6, 0, new int[]{2, 3}, 500));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Morgott unleashes a rapid sequence of dark slashes...\n", 9, 0, new int[]{0, 1}, 600));
        combo2.add(new Attack("He then performs a high-damage magical burst...\n", 8, 0, new int[]{2, 3}, 550));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Morgott starts with a menacing dark vortex...\n", 7, 0, new int[]{0, 1}, 500));
        combo3.add(new Attack("He follows with a series of high-speed slashes...\n", 8, 0, new int[]{2, 3}, 550));
        combo3.add(new Attack("Morgott finishes with a massive cursed impact...\n", 9, 0, new int[]{0, 1}, 600));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Morgott begins with a dark energy eruption...\n", 10, 0, new int[]{0, 1}, 650));
        combo4.add(new Attack("He then executes a multi-hit cursed assault...\n", 9, 0, new int[]{2, 3}, 600));
        combo4.add(new Attack("Morgott concludes with a powerful magic burst...\n", 10, 0, new int[]{0, 1}, 700));
        combos2.add(new Combo(combo4));
    }
}

/**
 * The FireGiant class represents the boss "Fire Giant" with specific HP and attack combos.
 * Fire Giant is a subclass of Boss with predefined HP and specific attack patterns.
 */
class FireGiant extends Boss {

    /**
     * Constructs the boss "Fire Giant" with a predefined HP value and initializes its attack combos.
     */
    public FireGiant() {
        super(43263, 
              "The Fire Giant looms with fiery fury, his very presence scorching the earth.", 
              "He prepares for a series of devastating fiery assaults.", 
              "The Fire Giant takes a moment, his next fiery attack is imminent.", 
              65); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Fire Giant for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Fire Giant swings his massive club...\n", 8, 0, new int[]{0, 1}, 600));
        combo1.add(new Attack("He follows with a burst of fire...\n", 9, 0, new int[]{2, 3}, 650));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Fire Giant stomps the ground...\n", 7, 0, new int[]{0, 1}, 550));
        combo2.add(new Attack("He unleashes a series of fiery eruptions...\n", 8, 0, new int[]{2, 3}, 600));
        combo2.add(new Attack("The Fire Giant finishes with a massive fireball...\n", 10, 0, new int[]{0, 1}, 700));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Fire Giant roars, summoning flames...\n", 8, 0, new int[]{0, 1}, 550));
        combo3.add(new Attack("He swings his club in a fiery arc...\n", 9, 0, new int[]{2, 3}, 600));
        combo3.add(new Attack("The Fire Giant concludes with a powerful ground slam...\n", 10, 0, new int[]{0, 1}, 700));
        combos1.add(new Combo(combo3));
        
        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Fire Giant starts with a fiery shockwave...\n", 8, 0, new int[]{0, 1}, 600));
        combo4.add(new Attack("He follows with a series of intense fire eruptions...\n", 9, 0, new int[]{2, 3}, 650));
        combo4.add(new Attack("The Fire Giant ends with a massive fiery explosion...\n", 12, 0, new int[]{0, 1}, 750));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Fire Giant for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Fire Giant begins with a ground-shaking stomp...\n", 10, 0, new int[]{0, 1}, 700));
        combo1.add(new Attack("He follows with a fiery ground eruption...\n", 11, 0, new int[]{2, 3}, 750));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Fire Giant roars, causing flames to spew from the ground...\n", 11, 0, new int[]{0, 1}, 750));
        combo2.add(new Attack("He then performs a series of powerful fiery swings...\n", 12, 0, new int[]{2, 3}, 800));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Fire Giant starts with a massive fiery shockwave...\n", 12, 0, new int[]{0, 1}, 750));
        combo3.add(new Attack("He continues with a rapid barrage of fireballs...\n", 11, 0, new int[]{2, 3}, 700));
        combo3.add(new Attack("The Fire Giant finishes with a devastating fiery eruption...\n", 13, 0, new int[]{0, 1}, 800));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Fire Giant begins with a massive fire vortex...\n", 14, 0, new int[]{0, 1}, 800));
        combo4.add(new Attack("He follows with a series of intense fireball eruptions...\n", 12, 0, new int[]{2, 3}, 750));
        combo4.add(new Attack("Fire Giant concludes with a colossal fire explosion...\n", 15, 0, new int[]{0, 1}, 850));
        combos2.add(new Combo(combo4));
    }
}

/**
 * The GodskinDuo class represents the boss "Godskin Duo" with specific HP and attack combos.
 * Godskin Duo is a subclass of Boss with predefined HP and specific attack patterns.
 */
class GodskinDuo extends Boss {

    /**
     * Constructs the boss "Godskin Duo" with a predefined HP value and initializes its attack combos.
     */
    public GodskinDuo() {
        super(8000, 
              "The Godskin Duo emerges with synchronized, deadly attacks. Their coordination is unparalleled.", 
              "The duo is preparing for a series of rapid and unpredictable strikes.", 
              "The Godskin Duo falls together, the duo is done.", 
              750); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Godskin Duo for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Godskin Duo swings their weapons simultaneously...\n", 8, 0, new int[]{0, 1}, 600));
        combo1.add(new Attack("One of the duo follows with a quick thrust...\n", 9, 0, new int[]{2, 3}, 650));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("The duo performs a synchronized spin attack...\n", 10, 0, new int[]{0, 1}, 700));
        combo2.add(new Attack("They quickly follow with a pair of slashes...\n", 9, 0, new int[]{2, 3}, 650));
        combo2.add(new Attack("One member finishes with a rapid thrust...\n", 11, 0, new int[]{0, 1}, 750));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Godskin Duo starts with a quick dash...\n", 8, 0, new int[]{0, 1}, 600));
        combo3.add(new Attack("They follow with a series of rapid slashes...\n", 10, 0, new int[]{2, 3}, 650));
        combo3.add(new Attack("One member concludes with a powerful finishing blow...\n", 12, 0, new int[]{0, 1}, 750));
        combos1.add(new Combo(combo3));
        
        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("The duo performs a coordinated charge...\n", 9, 0, new int[]{0, 1}, 650));
        combo4.add(new Attack("They follow with a series of alternating attacks...\n", 10, 0, new int[]{2, 3}, 700));
        combo4.add(new Attack("One of the duo concludes with a devastating slam...\n", 12, 0, new int[]{0, 1}, 800));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Godskin Duo for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("The duo begins with a high-speed dash...\n", 9, 0, new int[]{0, 1}, 650));
        combo1.add(new Attack("They follow with a series of quick strikes...\n", 10, 0, new int[]{2, 3}, 700));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Godskin Duo starts with a powerful charge...\n", 10, 0, new int[]{0, 1}, 700));
        combo2.add(new Attack("They perform a rapid series of coordinated slashes...\n", 11, 0, new int[]{2, 3}, 750));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("The duo performs a synchronized whirlwind attack...\n", 11, 0, new int[]{0, 1}, 750));
        combo3.add(new Attack("They follow with a series of powerful swings...\n", 12, 0, new int[]{2, 3}, 800));
        combo3.add(new Attack("One member concludes with a devastating finishing move...\n", 13, 0, new int[]{0, 1}, 850));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Godskin Duo begins with a high-speed onslaught...\n", 12, 0, new int[]{0, 1}, 750));
        combo4.add(new Attack("They follow with a series of rapid strikes...\n", 13, 0, new int[]{2, 3}, 800));
        combo4.add(new Attack("The duo concludes with a massive, synchronized attack...\n", 14, 0, new int[]{0, 1}, 900));
        combos2.add(new Combo(combo4));
    }
}

/**
 * The BeastClergyman class represents the boss "Beast Clergyman" with specific HP and attack combos.
 * Beast Clergyman transforms into Malekith in Phase 2.
 */
class BeastClergyman extends Boss {

    /**
     * Constructs the boss "Beast Clergyman" with a predefined HP value and initializes its attack combos.
     */
    public BeastClergyman() {
        super(10620, 
              "Destined Death has taken you, too.", 
              "O, Death. Become my blade, once more", 
              "Witless Tarnished... Why covet Destined Death? To kill what?", 
              80); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Beast Clergyman for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Beast Clergyman swings his staff with a powerful overhead strike...\n", 8, 0, new int[]{0, 1}, 500));
        combo1.add(new Attack("He follows up with a quick horizontal slash...\n", 7, 0, new int[]{2, 3}, 450));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("The cleric begins with a sweeping staff attack...\n", 9, 0, new int[]{0, 1}, 550));
        combo2.add(new Attack("He follows with a rapid series of strikes...\n", 8, 0, new int[]{2, 3}, 500));
        combo2.add(new Attack("Concluding with a powerful charge...\n", 10, 0, new int[]{0, 1}, 600));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Beast Clergyman starts with a charging thrust...\n", 10, 0, new int[]{0, 1}, 600));
        combo3.add(new Attack("He quickly follows with a series of quick slashes...\n", 9, 0, new int[]{2, 3}, 550));
        combo3.add(new Attack("He concludes with a sweeping staff spin...\n", 12, 0, new int[]{0, 1}, 700));
        combos1.add(new Combo(combo3));
        
        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("The cleric starts with a rapid multi-strike attack...\n", 10, 0, new int[]{0, 1}, 700));
        combo4.add(new Attack("He follows with a powerful slam...\n", 11, 0, new int[]{2, 3}, 750));
        combo4.add(new Attack("Finally, a devastating magical burst...\n", 12, 0, new int[]{0, 1}, 800));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Beast Clergyman for Phase 2.
     */
    private void setupCombos2() {
        // Malekith Combo 1
        List<Attack> malekithCombo1 = new ArrayList<>();
        malekithCombo1.add(new Attack("Malekith begins with a rapid series of shadow slashes...\n", 12, 0, new int[]{0, 1}, 700));
        malekithCombo1.add(new Attack("He follows with a powerful dark magic attack...\n", 13, 0, new int[]{2, 3}, 750));
        malekithCombo1.add(new Attack("Concluding with a massive energy wave...\n", 14, 0, new int[]{0, 1}, 800));
        combos2.add(new Combo(malekithCombo1));

        // Malekith Combo 2
        List<Attack> malekithCombo2 = new ArrayList<>();
        malekithCombo2.add(new Attack("Malekith starts with a high-speed dash attack...\n", 11, 0, new int[]{0, 1}, 650));
        malekithCombo2.add(new Attack("He follows with a series of rapid dark strikes...\n", 12, 0, new int[]{2, 3}, 700));
        malekithCombo2.add(new Attack("Ending with a powerful shadow explosion...\n", 13, 0, new int[]{0, 1}, 750));
        combos2.add(new Combo(malekithCombo2));

        // Malekith Combo 3
        List<Attack> malekithCombo3 = new ArrayList<>();
        malekithCombo3.add(new Attack("Malekith begins with a devastating ground slam...\n", 14, 0, new int[]{0, 1}, 800));
        malekithCombo3.add(new Attack("He then follows with a dark magic barrage...\n", 15, 0, new int[]{2, 3}, 850));
        malekithCombo3.add(new Attack("Finally, a massive shadow vortex...\n", 16, 0, new int[]{0, 1}, 900));
        combos2.add(new Combo(malekithCombo3));

        // Malekith Combo 4
        List<Attack> malekithCombo4 = new ArrayList<>();
        malekithCombo4.add(new Attack("Malekith starts with a rapid multi-strike shadow attack...\n", 14, 0, new int[]{0, 1}, 800));
        malekithCombo4.add(new Attack("He follows with a powerful energy blast...\n", 15, 0, new int[]{2, 3}, 850));
        malekithCombo4.add(new Attack("Ending with a devastating dark explosion...\n", 16, 0, new int[]{0, 1}, 900));
        combos2.add(new Combo(malekithCombo4));
    }
}

/**
 * The Gideon class represents the boss "Gideon" with specific HP and attack combos.
 * Gideon transforms into a more powerful state in Phase 2.
 */
class Gideon extends Boss {

    /**
     * Constructs the boss "Gideon" with a predefined HP value and initializes its attack combos.
     */
    public Gideon() {
        super(6226, 
              "My fellow, you've fought well, until now.", 
              "Gideon prepares a potent strike.", 
              "I know...in my bones... A Tarnished cannot become a Lord. Not even you. A man cannot kill a god...", 
              85); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Gideon for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Gideon begins with a quick thrust...\n", 6, 0, new int[]{0, 1}, 400));
        combo1.add(new Attack("He follows with a swift side swing...\n", 7, 0, new int[]{2, 3}, 350));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Gideon starts with a powerful slam...\n", 8, 0, new int[]{0, 1}, 500));
        combo2.add(new Attack("He continues with a spinning attack...\n", 9, 0, new int[]{2, 3}, 550));
        combo2.add(new Attack("And finishes with a strong overhead smash...\n", 10, 0, new int[]{0, 1}, 600));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Gideon starts with a rapid flurry of strikes...\n", 7, 0, new int[]{0, 1}, 400));
        combo3.add(new Attack("He follows with a piercing lunge...\n", 8, 0, new int[]{2, 3}, 450));
        combo3.add(new Attack("Concludes with a sweeping attack...\n", 9, 0, new int[]{0, 1}, 500));
        combos1.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Gideon opens with a fast magical blast...\n", 8, 0, new int[]{0, 1}, 500));
        combo4.add(new Attack("He transitions into a quick series of melee strikes...\n", 7, 0, new int[]{2, 3}, 450));
        combo4.add(new Attack("Finishes with a powerful magic-infused swing...\n", 9, 0, new int[]{0, 1}, 550));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Gideon for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Gideon starts with a rapid magical burst...\n", 10, 0, new int[]{0, 1}, 500));
        combo1.add(new Attack("Follows up with a powerful melee slam...\n", 11, 0, new int[]{2, 3}, 600));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Gideon begins with a high-speed dash attack...\n", 11, 0, new int[]{0, 1}, 600));
        combo2.add(new Attack("He quickly follows with a series of magical slashes...\n", 12, 0, new int[]{2, 3}, 650));
        combo2.add(new Attack("Ends with a powerful energy wave...\n", 13, 0, new int[]{0, 1}, 700));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Gideon initiates with a magical orb launch...\n", 12, 0, new int[]{0, 1}, 700));
        combo3.add(new Attack("He follows with a series of powerful staff strikes...\n", 13, 0, new int[]{2, 3}, 750));
        combo3.add(new Attack("Concludes with a devastating area-of-effect magic burst...\n", 14, 0, new int[]{0, 1}, 800));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Gideon starts with a rapid magical explosion...\n", 13, 0, new int[]{0, 1}, 650));
        combo4.add(new Attack("He continues with a series of quick melee attacks...\n", 14, 0, new int[]{2, 3}, 700));
        combo4.add(new Attack("Finalizes with an immense energy surge...\n", 15, 0, new int[]{0, 1}, 750));
        combos2.add(new Combo(combo4));
    }
}

/**
 * The Godfrey class represents the boss "Godfrey" with specific HP and attack combos.
 * Godfrey is a subclass of Boss with predefined HP and specific attack patterns.
 */
class Godfrey extends Boss {

    /**
     * Constructs the boss "Godfrey" with a predefined HP value and initializes its attack combos.
     */
    public Godfrey() {
        super(21903, 
              "Tarnished Warrior. 'Twas nobly fought. Thy rest is well deserved. A crown is warranted with strength!", 
              "That will be all. Thou didst me good service, Serosh. I've given thee courtesy enough. Rrraaargh! Now I fight as Hoarah Loux! Warrior!", 
              "Brave Tarnished... Thy strength befits a crown. *laughs*", 
              90); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Godfrey for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Godfrey starts with a mighty overhead smash...\n", 8, 0, new int[]{0, 1}, 500));
        combo1.add(new Attack("He follows with a sweeping horizontal slash...\n", 9, 0, new int[]{2, 3}, 550));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Godfrey begins with a powerful ground pound...\n", 10, 0, new int[]{0, 1}, 600));
        combo2.add(new Attack("He continues with a quick series of jabs...\n", 8, 0, new int[]{2, 3}, 500));
        combo2.add(new Attack("And finishes with a crushing blow...\n", 11, 0, new int[]{0, 1}, 700));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Godfrey opens with a rapid succession of heavy strikes...\n", 9, 0, new int[]{0, 1}, 500));
        combo3.add(new Attack("He transitions into a powerful stomp...\n", 10, 0, new int[]{2, 3}, 550));
        combo3.add(new Attack("Ends with a devastating energy surge...\n", 11, 0, new int[]{0, 1}, 650));
        combos1.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Godfrey starts with a fast spinning attack...\n", 8, 0, new int[]{0, 1}, 400));
        combo4.add(new Attack("He follows with a fierce thrust...\n", 9, 0, new int[]{2, 3}, 450));
        combo4.add(new Attack("Finishes with a powerful slam...\n", 10, 0, new int[]{0, 1}, 500));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Godfrey for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Hoarah Loux starts with a ground-shaking roar...\n", 12, 0, new int[]{0, 1}, 700));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Hoarah Loux performs a quick forward charge...\n", 13, 0, new int[]{0, 1}, 750));
        combo2.add(new Attack("He follows with a high-speed swipe...\n", 14, 0, new int[]{2, 3}, 800));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Hoarah Loux roars and creates a shockwave...\n", 14, 0, new int[]{0, 1}, 800));
        combo3.add(new Attack("He follows with a series of high-speed claw slashes...\n", 15, 0, new int[]{2, 3}, 850));
        combo3.add(new Attack("Ends with a devastating charge...\n", 16, 0, new int[]{0, 1}, 900));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Hoarah Loux starts with a massive explosion of energy...\n", 15, 0, new int[]{0, 1}, 800));
        combo4.add(new Attack("He continues with a series of powerful attacks...\n", 16, 0, new int[]{2, 3}, 850));
        combo4.add(new Attack("Concludes with a colossal final slam...\n", 17, 0, new int[]{0, 1}, 900));
        combos2.add(new Combo(combo4));

        // Combo 5
        List<Attack> combo5 = new ArrayList<>();
        combo5.add(new Attack("Hoarah Loux starts with a massive energy explosion...\n", 15, 0, new int[]{0, 1}, 800));
        combo5.add(new Attack("He continues with a spinning tail swipe...\n", 16, 0, new int[]{2, 3}, 850));
        combo5.add(new Attack("Follows with a powerful stomp...\n", 17, 0, new int[]{0, 1}, 900));
        combo5.add(new Attack("Concludes with a high-speed charge...\n", 18, 0, new int[]{2, 3}, 950));
        combos2.add(new Combo(combo5));
    }
}

/**
 * The Malenia class represents the boss "Malenia" with a specific HP value and attack combos.
 * Malenia is a subclass of Boss with predefined HP and specific attack patterns.
 */
class Malenia extends Boss {

    /**
     * Constructs the boss "Malenia" with a predefined HP value and initializes its attack combos.
     */
    public Malenia() {
        super(33251, 
              "I am Malenia, Blade of Miquella…",
              "Wait. *The scarlet bloom flowers once more* You will witness true horror. Now, rot!",
              "Your strength, extraordinary... The mark...of a true Lord... O, dear Miquella... O, dearest Miquella, my brother... I'm sorry. I finally met my match...", 
              95); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Malenia for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Malenia executes a quick slash...\n", 30, 0, new int[]{0, 1}, 800));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Malenia performs a rapid series of slashes...\n", 32, 0, new int[]{0, 1}, 850));
        combo2.add(new Attack("Followed by a powerful downward strike...\n", 34, 0, new int[]{2, 3}, 900));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Malenia starts with a series of swift slashes...\n", 35, 0, new int[]{0, 1}, 850));
        combo3.add(new Attack("Unleashes a spinning attack...\n", 37, 0, new int[]{2, 3}, 900));
        combo3.add(new Attack("Ends with a powerful thrust...\n", 39, 0, new int[]{0, 1}, 950));
        combos1.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Malenia begins with a rapid series of slashes...\n", 40, 0, new int[]{0, 1}, 900));
        combo4.add(new Attack("Follows with a spinning whirlwind attack...\n", 42, 0, new int[]{2, 3}, 950));
        combo4.add(new Attack("Unleashes a series of powerful thrusts...\n", 44, 0, new int[]{0, 1}, 1000));
        combo4.add(new Attack("Concludes with a devastating finishing move...\n", 46, 0, new int[]{2, 3}, 1050));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Malenia for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Malenia releases a swift scarlet rot strike...\n", 28, 0, new int[]{0, 1}, 800));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Malenia performs a series of quick, rot-infused cuts...\n", 30, 0, new int[]{0, 1}, 850));
        combo2.add(new Attack("Followed by a sharp, rot-drenched stab...\n", 32, 0, new int[]{2, 3}, 900));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Malenia begins with a wide, rot-charged slash...\n", 33, 0, new int[]{0, 1}, 850));
        combo3.add(new Attack("Unleashes a rapid series of slashes...\n", 35, 0, new int[]{2, 3}, 900));
        combo3.add(new Attack("Ends with a powerful rot burst...\n", 37, 0, new int[]{0, 1}, 950));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Malenia starts with a large rot-infused explosion...\n", 36, 0, new int[]{0, 1}, 850));
        combo4.add(new Attack("Followed by a rapid sequence of slashes...\n", 38, 0, new int[]{2, 3}, 900));
        combo4.add(new Attack("Then a powerful, sweeping rot attack...\n", 40, 0, new int[]{0, 1}, 950));
        combo4.add(new Attack("Concludes with a massive rot detonation...\n", 42, 0, new int[]{2, 3}, 1000));
        combos2.add(new Combo(combo4));
    }
}

/**
 * The Radagon class represents the boss "Radagon" with specific HP and attack combos.
 * Radagon is a subclass of Boss with predefined HP and specific attack patterns involving a hammer and holy damage.
 */
class Radagon extends Boss {

    /**
     * Constructs the boss "Radagon" with a predefined HP value and initializes its attack combos.
     */
    public Radagon() {
        super(35339, 
              "The god of this world has taketh another's life.",
              "Radagon falls to the ground, yet an Elden Beast creeps out from inside of his soul.",
              "GOD SLAIN", 100); // Number of runes awarded on defeat
        setupCombos1();
        setupCombos2();
    }

    /**
     * Initializes the attack combos specific to Radagon for Phase 1.
     */
    private void setupCombos1() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Radagon swings his hammer with a mighty overhead blow...\n", 8, 0, new int[]{0, 1}, 500));
        combos1.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Radagon charges up and slams his hammer into the ground...\n", 10, 0, new int[]{0, 1}, 600));
        combo2.add(new Attack("He follows with a quick side swipe...\n", 9, 0, new int[]{2, 3}, 550));
        combos1.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Radagon begins with a powerful holy shockwave...\n", 12, 0, new int[]{0, 1}, 650));
        combo3.add(new Attack("He then performs a series of rapid hammer slams...\n", 11, 0, new int[]{2, 3}, 700));
        combo3.add(new Attack("Ends with a devastating energy blast...\n", 13, 0, new int[]{0, 1}, 750));
        combos1.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Radagon starts with a massive ground-shaking slam...\n", 14, 0, new int[]{0, 1}, 750));
        combo4.add(new Attack("He continues with a series of powerful vertical strikes...\n", 15, 0, new int[]{2, 3}, 800));
        combo4.add(new Attack("Follows with a holy explosion...\n", 16, 0, new int[]{0, 1}, 850));
        combo4.add(new Attack("Concludes with a sweeping shockwave...\n", 17, 0, new int[]{2, 3}, 900));
        combos1.add(new Combo(combo4));
    }

    /**
     * Initializes the attack combos specific to Elden Beast for Phase 2.
     */
    private void setupCombos2() {
        // Combo 1
        List<Attack> combo1 = new ArrayList<>();
        combo1.add(new Attack("Elden Beast releases a quick burst of cosmic energy...\n", 15, 0, new int[]{0, 1}, 500));
        combos2.add(new Combo(combo1));

        // Combo 2
        List<Attack> combo2 = new ArrayList<>();
        combo2.add(new Attack("Elden Beast fires a series of energy orbs...\n", 18, 0, new int[]{0, 1}, 600));
        combo2.add(new Attack("Follows with a sweeping cosmic beam...\n", 19, 0, new int[]{2, 3}, 650));
        combos2.add(new Combo(combo2));

        // Combo 3
        List<Attack> combo3 = new ArrayList<>();
        combo3.add(new Attack("Elden Beast starts with a celestial shockwave...\n", 20, 0, new int[]{0, 1}, 700));
        combo3.add(new Attack("Unleashes a rapid volley of star shards...\n", 21, 0, new int[]{2, 3}, 750));
        combo3.add(new Attack("Ends with a powerful cosmic explosion...\n", 22, 0, new int[]{0, 1}, 800));
        combos2.add(new Combo(combo3));

        // Combo 4
        List<Attack> combo4 = new ArrayList<>();
        combo4.add(new Attack("Elden Beast begins with an enormous cosmic quake...\n", 25, 0, new int[]{0, 1}, 750));
        combo4.add(new Attack("Followed by a series of intense energy blasts...\n", 26, 0, new int[]{2, 3}, 800));
        combo4.add(new Attack("Then a rapid discharge of celestial shards...\n", 27, 0, new int[]{0, 1}, 850));
        combo4.add(new Attack("Concludes with a massive cosmic upheaval...\n", 28, 0, new int[]{2, 3}, 900));
        combos2.add(new Combo(combo4));

        // Combo 5
        List<Attack> combo5 = new ArrayList<>();
        combo5.add(new Attack("Elden Beast releases a quick burst of star energy...\n", 18, 0, new int[]{0, 1}, 550));
        combos2.add(new Combo(combo5));

        // Combo 6
        List<Attack> combo6 = new ArrayList<>();
        combo6.add(new Attack("Elden Beast fires a rapid series of energy pulses...\n", 20, 0, new int[]{0, 1}, 600));
        combo6.add(new Attack("Followed by a celestial wave...\n", 21, 0, new int[]{2, 3}, 650));
        combos2.add(new Combo(combo6));

        // Combo 7
        List<Attack> combo7 = new ArrayList<>();
        combo7.add(new Attack("Elden Beast begins with a celestial eruption...\n", 23, 0, new int[]{0, 1}, 700));
        combo7.add(new Attack("Unleashes a rapid barrage of cosmic rays...\n", 24, 0, new int[]{2, 3}, 750));
        combo7.add(new Attack("Ends with a massive stellar explosion...\n", 25, 0, new int[]{0, 1}, 800));
        combos2.add(new Combo(combo7));

        // Combo 8
        List<Attack> combo8 = new ArrayList<>();
        combo8.add(new Attack("Elden Beast starts with an immense cosmic upheaval...\n", 30, 0, new int[]{0, 1}, 750));
        combo8.add(new Attack("Continues with a rapid succession of energy pulses...\n", 31, 0, new int[]{2, 3}, 800));
        combo8.add(new Attack("Unleashes a storm of star shards...\n", 32, 0, new int[]{0, 1}, 850));
        combo8.add(new Attack("Concludes with a final, cataclysmic cosmic burst...\n", 33, 0, new int[]{2, 3}, 900));
        combos2.add(new Combo(combo8));
    }
}
