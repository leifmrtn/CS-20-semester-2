package Mastery;

import java.util.Scanner;

public class exercise_2
{

	public static void main(String[] args) 
	{

		//establish Variables
		int diameter;
		double answer;
		
		try (Scanner in = new Scanner(System.in)) 
		{
			System.out.print("Please enter the diameter of the pizza in inches is: ");
			diameter = in.nextInt();								
		}		
		// solving and outputting answer
		answer = 1.75 + (0.5*diameter);
		System.out.print("The cost to make the pizza is: " + answer + "$");
		
	}
}

// --- Console ---
//Please enter the diameter of the pizza in inches is: 10
//The cost to make the pizza is: 6.75$