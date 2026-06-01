import java.util.List;
import java.util.Scanner;

/**
 * All console input and output for the game flows through here, backed by a single
 * {@link Scanner} over {@code System.in}. The original game created a fresh Scanner in several
 * classes; sharing one avoids the input-buffer races that causes and keeps every prompt,
 * pause and screen-clear consistent.
 */
final class Console {
    private static final Scanner IN = new Scanner(System.in);

    private Console() {}

    /** Prints a line, waits for the player to press Enter, then clears the screen. */
    static void speak(String text) {
        System.out.println(text);
        IN.nextLine();
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

    /** Reads the next whitespace-delimited integer token (leaving the rest of the line unread). */
    static int nextInt()      { return IN.nextInt(); }
    /** Reads the remainder of the current line. */
    static String nextLine()  { return IN.nextLine(); }
    /** Reads and discards the next whitespace-delimited token. */
    static String nextToken() { return IN.next(); }
}
