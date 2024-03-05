import java.util.*;

/**
* @James DeSilver
* @EldenRing.java
* @v1.00
*/

public class Main {
//Speaking
	public static void speak(String text) {
	    Scanner input = new Scanner(System.in);
	    System.out.println(text);
	    String next = input.nextLine();
	    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}
	
//Inputting a Stat
	public static int getStat(int runes) throws InputMismatchException {
	    Scanner input = new Scanner(System.in);
	    int amt = 0;
	    while (true) {
	        amt = input.nextInt();
	        if (amt > runes) {
	            System.out.println("Not enough runes");
	            continue;
	        }
	        if (amt < 0) {
	            System.out.println("Has to be positive");
	            continue;
	        }
	        else {
	            return amt;
	        }
	    }
	}
	
//Allocating Runes
	public static int[] levelUp(int[] stats) {
        String STAT[] = {"Vigor", "Mind", "Endurance", "Strength", "Dexterity", "Intelligence", "Faith", "Arcane"};
        Scanner input = new Scanner(System.in);
        
        //Display Runes
        System.out.println("Runes: " + Player.runes);
        System.out.println("8 Stats: Vigor, Mind, Endurance, Strength, Dexterity, Intelligence, Faith, and Arcane");

        //Choosing How the User Will Allocate Runes
        int runesSpent = 0;
        for(int i = 0; i<8; i++) {
            while (true) {
                try {
                    runesSpent = 0;
                    System.out.print("Points into " + STAT[i] + ": ");
                    runesSpent = getStat(Player.runes);
                    if (!areYouSure()) {
                        continue;
                    }
                    stats[i] += runesSpent;
                    Player.runes -= runesSpent;
                } catch (InputMismatchException ex) {
                    continue;
                }
            break;
    	    }
        }
        return stats;
	}

//Buying Weapons
public static int[] buyWeapons(Weapon[] weapons) {
        Scanner input = new Scanner(System.in);
        
        //Display Runes
        System.out.println("Runes: " + Player.runes);

        //Choosing How the User Will Allocate Runes
        int runesSpent = 0;
        for(int i = 0; i<8; i++) {
            while (true) {
                try {
                    runesSpent = 0;
                    System.out.print("Points into " + STAT[i] + ": ");
                    runesSpent = getStat(Player.runes);
                    if (!areYouSure()) {
                        continue;
                    }
                    stats[i] += runesSpent;
                    Player.runes -= runesSpent;
                } catch (InputMismatchException ex) {
                    continue;
                }
            break;
    	    }
        }
        return stats;
	}

//Inputting a Choice
    public static boolean choice() throws InputMismatchException {
	    Scanner input = new Scanner(System.in);
	    String answer = "";
	    boolean confirmation = true;
	    boolean iHateLoops = true;
    	while (iHateLoops) {
    	    //Make Sure with the answer
    	    System.out.print("\nAre you sure? (YES or NO) ");
    	    answer = input.nextLine();
    	    if ((answer.toUpperCase().trim()).equals("YES")) {
    	        confirmation = true;
    	        iHateLoops = false;
    	    }
    	    else if ((answer.toUpperCase().trim()).equals("NO")) {
    	        confirmation = false;
    	        iHateLoops = false;
    	    }
    	}
    	System.out.println();
    	return confirmation;
    }

//Confirming Choices
	public static boolean areYouSure() {
	    boolean finalAnswer = true;
    	while (true) {
    	    try {
    	        finalAnswer = choice();
    	    } catch(Exception InputMismatchException) {
    	        continue;
    	    }
    	    break;
    	}
    	return finalAnswer;
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

    //Get Name and Create Player Object
        System.out.println("Varre: Greetings, Tarnished, to the Lands Between.\nWhat shalt I call thy?\n");
        String name = input.nextLine();
        Player tarnished = new Player(name);
        
    //Exposition
        speak("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nAh I see, wait a moment, are you.. (press ENTER to continue)");
        speak("maidenless?");
        speak("No maidens?");
        speak("Well isn't that surprising..");
        speak("Well it's not likely that you strive to be Elden Lord..");
        speak("Oh..");
        speak("That's");
        speak("Quite foolish.");
        speak("A Tarnished cannot be Elden Lord!");
        speak("But I must be quite frank with you..");
        speak("You amuse me..");
        speak("Would you like some weapons and armor for your foolish endeavors?");
        
        //GET FIRST WEAPONS AND ARMOR
        // tarnished.setRightHand(buyWeapons(tarnished.getRightHand()));
        
        speak("Well then..");
        speak("Go on your merry way..");
        speak("..foolish Tarnished.");
        speak("*walking*");
        speak(" *walking*");
        speak("  *walking*");
        speak("*sees a short beam of light coming from the ground*");
        speak("*touches light*");
        speak("LOST GRACE DISCOVERED");
        speak("*rests*");
        speak("Melina: Do not fret, I am Melina..");
        speak("I offer you an accord..");
        speak("I see your passion, your strength, and your vigor..");
        speak("I shall be your maiden..");
        speak("In return you shall become Elden Lord");
        speak("Now.. place your hand in mine, but for a moment, and I shall turn your runes to strength..");
        speak("*holds hand*");
        
        tarnished.setStats(levelUp(tarnished.getStats()));
        
        
        System.out.println(tarnished.toString());
	    
	    speak("Melina: Good then, I shall wish you well..");
	    speak("Now go..");
	    speak("And slay Margit, the Fell Omen.");
	    speak("*walking*");
        speak(" *walking*");
        speak("  *walking*");
        speak("*sees a golden wall*");
        speak("*goes through the wall*");
        
    //MARGIT, THE FELL OMEN FIGHT
        
        
	}
}
