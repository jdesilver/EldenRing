import java.util.List;
import java.util.Scanner;

/**
 * All console input and output for the game flows through here, backed by a single
 * {@link Scanner} over {@code System.in}. The original game created a fresh Scanner in several
 * classes; sharing one avoids the input-buffer races that causes and keeps every prompt,
 * pause and screen-clear consistent.
 *
 * <p>Every read funnels through {@link #rawLine()}, so input is line-based and can never throw:
 * non-numeric input becomes {@link #INVALID} (callers treat it like any other invalid menu
 * choice), and end-of-input ends the game cleanly instead of throwing a stack trace.
 */
final class Console {
    /** Returned by {@link #readInt()} when the line is not a valid integer. No menu maps to this value. */
    static final int INVALID = Integer.MIN_VALUE;

    private static final Scanner IN = new Scanner(System.in);

    private Console() {}

    /** Prints a line, waits for the player to press Enter, then clears the screen. */
    static void speak(String text) {
        System.out.println(text);
        rawLine();
        clear();
    }

    /** Speaks each beat of a scene in turn, pausing between them. */
    static void narrate(List<String> beats) {
        for (String beat : beats) {
            speak(beat);
        }
    }

    /** Clears the console using ANSI escape codes (as the original did). */
    static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    static void print(String text)   { System.out.print(text); }
    static void println(String text) { System.out.println(text); }

    /**
     * Reads one whole line and parses it as an integer, returning {@link #INVALID} when the line is
     * not a valid {@code int} (letters, blanks, extra tokens, or out of range). Never throws.
     */
    static int readInt() {
        try {
            return Integer.parseInt(rawLine().trim());
        } catch (NumberFormatException e) {
            return INVALID;
        }
    }

    /** Reads one whole line of input. Never throws. */
    static String readLine() {
        return rawLine();
    }

    /**
     * The single input choke point. Returns the next line of input, or - at end of input (Ctrl+Z, or
     * a closed/empty stdin) - prints a farewell and exits cleanly, so that no read can ever throw.
     */
    private static String rawLine() {
        if (!IN.hasNextLine()) {
            System.out.println();
            System.out.println("The flame flickers out. Farewell, Tarnished.");
            System.out.flush();
            System.exit(0);
        }
        return IN.nextLine();
    }
}
