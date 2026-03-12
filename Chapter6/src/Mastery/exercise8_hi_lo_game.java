/*

Program: exercise8_hi_lo_game.java          Last Date of this Revision: March 12, 2026

Purpose: Generate a random number from 1 - 20 and then get the user to input a bet and guess for whether the number will be high or low

Author: Leif Martin, 
School: CHHS
Course: Computer Programming 20-1

*/

package Mastery;

import java.util.Random;
import java.util.Scanner;

public class exercise8_hi_lo_game {

	public static void main(String[] args) {		
		//initialize
		int points = 500;
		boolean running = true;
		int run = 1;
		int predict;
		int numHiLo;
		int bet;
		int number;
		
		//starting board
		System.out.println("High Low Game");
		System.out.println("");
		System.out.println("Numbers: 1-10 are low, 11-20 are high.");
		System.out.println("You have " + points + " points.");
		try(Scanner in = new Scanner(System.in)) {
			while (running) {
				
				System.out.print("Enter points to risk: ");
				bet = in.nextInt();
				
				if(bet <= points) {
					System.out.println("Predict (1=high, 0=low) ");
					predict = in.nextInt();
					
					Random random = new Random();
					number = random.nextInt(19)+1;
					if(number >= 11) {//number is high
						numHiLo = 1;
					}
					else {
						numHiLo = 0;
					}
					if(numHiLo == predict) {//guessed right
						points += bet;
						System.out.println("The number was " + number);
						System.out.println("You won " + bet);
						System.out.println("You now have " + points + " points.");
						System.out.println("Would you like to play again? (1=Yes, 0=No)");
						run = in.nextInt(); 
					}
					else {//guessed wrong
						points -= bet;
						if(points == 0) {//lost all points
							System.out.println("The number was " + number);
							System.out.println("You have lost all of your points");
							System.out.println("Thank you for playing!");
							running = false;
						}
						else {//lost points but not all
							System.out.println("The number was " + number);
							System.out.println("You lost " + bet);
							System.out.println("You now have " + points + " points.");		
							System.out.println("Would you like to play again? (1=Yes, 0=No)");
							run = in.nextInt(); 
						}	
					}								
				}
				else {//not enough money for that bet
					System.out.println("I'm sorry but you don't have enough money for that bet.");
					bet = 0;
				}				
				
				if(run == 0) {
					System.out.println("Thanks for playing!");
					System.out.println("You finished with " + points + " points");
					running = false;
				}	
			}

		}
				bet = 0;					
	}
}

/* Screen Dump
High Low Game

Numbers: 1-10 are low, 11-20 are high.
You have 500 points.
Enter points to risk: 250
Predict (1=high, 0=low) 
1
The number was 14
You won 250
You now have 750 points.
Would you like to play again? (1=Yes, 0=No)
1
Enter points to risk: 500
Predict (1=high, 0=low) 
1
The number was 11
You won 500
You now have 1250 points.
Would you like to play again? (1=Yes, 0=No)
0
Thanks for playing!
You finished with 1250 points
*/