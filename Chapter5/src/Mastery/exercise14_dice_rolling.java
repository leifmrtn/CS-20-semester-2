package Mastery;

import java.util.Random;
import java.util.Scanner;

public class exercise14_dice_rolling {

	public static void main(String[] args) {
		//Variables
		Scanner in = new Scanner(System.in);
		int total;
		int rerun;
		boolean running = true;
		int rollnum;	
		int final_total = 0;
		int average = 0;
		int dice1;
		int dice2;
				
		//Running
		while (running) 	{
			//How many rolls?
			System.out.print("How many times would you like to roll the dice?: ");
			rollnum = in.nextInt();
			
			//rolling
			System.out.println("------------------------");
			System.out.println("Dice 1 | Dice 2 | Total");
			System.out.print("-------|--------|-------");

			//rolls
			Random random = new Random();
			for (int i = 0; i < rollnum; i++) {
				
				dice1 = random.nextInt(6)+1;
				dice2 = random.nextInt(6)+1;
				total = (dice1 + dice2);
				System.out.print("\n   " + dice1 + "   |    " + dice2 + "   |   " + total);
				final_total += total;
				average += total;			
			}
			average /= rollnum;
			
			System.out.print("\n\n-----------------------");
			System.out.println("\nThe total For all dice rolls is: " + final_total);
			System.out.println("With an average of: " + average + " for each pair.");
			System.out.println("-----------------------");
			//display the list

			
			//replay?
			System.out.print("\nWould you like to roll again? 1.Yes 2.No: ");
			rerun = in.nextInt();			
		
			if (rerun == 1) { //play again
				//reset variables
				rollnum = 0;
				average = 0;
				
			}
			
			else { //quit
				System.out.print("Thanks for rolling!");
				running = false;
			}
		}
	}
}

// --- Final Console ---
//How many times would you like to roll the dice?: 7
//------------------------
//Dice 1 | Dice 2 | Total
//-------|--------|-------
//   1   |    6   |   7
//   4   |    1   |   5
//   4   |    4   |   8
//   3   |    4   |   7
//   4   |    5   |   9
//   2   |    3   |   5
//   3   |    5   |   8
//
//-----------------------
//The total For all dice rolls is: 49
//With an average of: 7 for each pair.
//-----------------------
//
//Would you like to roll again? 1.Yes 2.No: 2
//Thanks for rolling!
