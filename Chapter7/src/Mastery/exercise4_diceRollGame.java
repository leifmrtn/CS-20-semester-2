package Mastery;

import java.util.Random;
import java.util.Scanner;

public class exercise4_diceRollGame {
private static int points = 1000;

static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		boolean running = true;
		System.out.println("Welcome to the dice rolling game! (1-6 is low, 7 is a loss, 8-12 is high.");
		System.out.println("You have " + points + " points.");
		while (running) {
			riskPoints();
		}
	}
	
	public static void riskPoints() {
		boolean running = true;
		int choice;
		while(running) {
			System.out.println("How many points would you like to risk? (-1 to quit): ");
			choice = in.nextInt();
			if (choice == -1) {
				System.out.println("Thank you for playing!");
				System.out.println("You finished with " + points + " points.");
				running = false;
			}
			else if(choice > -1 || choice > points) {
				running = false;
				callRoll(choice);
			}
			else {
				System.out.println("I'm sorry but that wasn't an acceptable amount. Please try again: ");
				choice = in.nextInt();
			}
		}
	}
	public static void callRoll(int riskedPoints) {
		System.out.println("Make a call (0 for low, 1 for high): ");
		int call = in.nextInt();
		Random rand = new Random();
		int roll = (rand.nextInt(6) + 1) + (rand.nextInt(6)+1);
		
		// Winning or losing 
		if (roll == 7) { // loss
			points -= riskedPoints;
			System.out.println("You rolled: 7");
			System.out.println("You now have " + points + " points.");
		}
		else if (call == 0 && roll < 7) { // Bet low - roll low (win)
			points += riskedPoints;
			System.out.println("You rolled: " + roll);
			System.out.println("You now have " + points + " points.");
		}
		else if (call == 0 && roll > 7) { // bet low - roll high (loss)
			points -= riskedPoints;
			System.out.println("You rolled: " + roll);
			System.out.println("You now have " + points + " points.");
		}
		else if (call == 1 && roll < 7) { // Bet high - roll low (loss)
			points -= riskedPoints;
			System.out.println("You rolled: " + roll);
			System.out.println("You now have " + points + " points.");
		}
		else if (call == 1 && roll > 7) { // Bet high - roll high (win)
			points += riskedPoints;
			System.out.println("You rolled: " + roll);
			System.out.println("You now have " + points + " points.");
		}
		else {
			System.out.println("That wasnt a valid option. Please try again.");
		}
			
			
	}
	public void temp() {
		
	}
}

// --- console output --- //
//Welcome to the dice rolling game! (1-6 is low, 7 is a loss, 8-12 is high.
//You have 1000 points.
//How many points would you like to risk? (-1 to quit): 
//200
//Make a call (0 for low, 1 for high): 
//1
//You rolled: 7
//You now have 800 points.
//How many points would you like to risk? (-1 to quit): 
//400
//Make a call (0 for low, 1 for high): 
//2
//That wasnt a valid option. Please try again.
//How many points would you like to risk? (-1 to quit): 
//200
//Make a call (0 for low, 1 for high): 
//1
//You rolled: 2
//You now have 600 points.
//How many points would you like to risk? (-1 to quit): 
//-1
//Thank you for playing!
//You finished with 600 points.
//How many points would you like to risk? (-1 to quit): 



