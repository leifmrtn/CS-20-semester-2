/*

Program: exercise9_gameOfNim.java          Last Date of this Revision: March 12, 2026

Purpose: User goes against computer, each can draw 1-3 stones and the last one to take a stone loses

Author: Leif Martin, 
School: CHHS
Course: Computer Programming 20-1

*/
package Mastery;

import java.util.Scanner;
import java.util.Random;

public class exercise9_gameOfNim {
	
	
	public static void main(String[] args) {	
		//establish variables
		Random random = new Random(); 
		int stones;
		int draw;
		int compDraw;
		boolean running = true;
				
		//rules and set up
		System.out.println("+========================================================+");
		System.out.println("|               Welcome to the Game Of Nim.              |");
		System.out.println("|You can take 1-3 stones, last one to take a stone loses.|");
		System.out.println("+========================================================+");
		stones = random.nextInt(14)+15;
		
		while(running) {//playing the game
			//drawing
			System.out.print("There are " + stones + " left, How many would you like to take?: ");
			Scanner in = new Scanner(System.in);
			draw = in.nextInt();
			//players turn
			while (validEntry(draw, stones) == false) {//not a valid input
				System.out.println("That move did not work");
				System.out.print("There are " + stones + " left, How many would you like to take?: ");
				draw = in.nextInt();
			}
			if (validEntry(draw, stones)) {//valid
				stones -= draw;
				if (stones == 0) {// player lost
					running = false;
					System.out.println("the games ended");
					System.out.println("The computer won");
				}
			}
			//computers turn
			compDraw = drawStones(stones);
			System.out.println("There are " + stones + " the computer takes " + compDraw + " stones.");
			stones -= compDraw;
			if(stones == 0 && running) {//computer lost
				running = false;
				System.out.println("The game ended");
				System.out.println("You won");
			}
		}     		
	}
	
	public static boolean validEntry(int draw, int remaining) {//checking if user can draw stone
		boolean valid;
		valid = (draw > 0 && draw <= 3 && (remaining - draw) >= 0);
		return valid;	
	}
	
	public static int drawStones(int remaining) {//computer draw stones
		int draw;
		Random random = new Random(); 
		if (remaining > 3) {//regular draw 1-3
			draw = random.nextInt(2)+1;
			return draw;
		}
		else if (remaining == 2 || remaining == 3) {//making sure the computer doesn't lose if it can prevent it and make player lose
			draw = remaining-1;
			return draw;
		}
		else {//robot draws last stone
			draw = 1;
			return draw;
		}	
	}
}


/* Screen Dump
+========================================================+
|               Welcome to the Game Of Nim.              |
|You can take 1-3 stones, last one to take a stone loses.|
+========================================================+
There are 19 left, How many would you like to take?: 3
There are 16 the computer takes 1 stones.
There are 15 left, How many would you like to take?: 3
There are 12 the computer takes 1 stones.
There are 11 left, How many would you like to take?: 4
That move did not work
There are 11 left, How many would you like to take?: 3
There are 8 the computer takes 2 stones.
There are 6 left, How many would you like to take?: 1
There are 5 the computer takes 2 stones.
There are 3 left, How many would you like to take?: 2
There are 1 the computer takes 1 stones.
The game ended
You won
*/
