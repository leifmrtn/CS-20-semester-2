package SkillBuilders;

import java.util.Random;
import java.util.Scanner;

public class random_number 
{

	public static void main(String[] args)
	{

		
		//Establish variables
		int number;
		int range;
		int RG = 5;
		int guess;
		boolean win = false;

		// asking the range
		try (Scanner in = new Scanner(System.in))
		{
			System.out.print("What would you like the range to be?: ");
			range = in.nextInt();
		
		
		//running
		Random random = new Random();
		number = random.nextInt(range);
		while (RG != 0) 
		{
			System.out.print("You have " + RG + " guesses left. what is your next guess?: ");
			guess = in.nextInt();
			if (guess == number)
			{
				win = true;
			}
			else
			{
				RG -=1 ;
			}
			//announce win
			if (win == true)
			{
				System.out.print("You've Won!!, the number was " + number);
				RG = 0;
			}
			//loss
			else if (RG == 0) 
			{
				System.out.print("I'm sorry but you've run out of guesses, please try agian later.");
			}
			
			}
		}
	}
}


// --- console ---
//What would you like the range to be?: 10
//You have 5 guesses left. what is your next guess?: 7
//You have 4 guesses left. what is your next guess?: 2
//You've Won!!, the number was 2

