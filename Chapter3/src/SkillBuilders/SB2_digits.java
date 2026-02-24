package SkillBuilders;

import java.util.Scanner;

public class SB2_digits {

	public static void main(String[] args)	{
		
		//establishing variables
		int number;
		int ones;
		int tens;
	
		try (Scanner in = new Scanner(System.in))	{
			System.out.print("Please input a number with 2 digits : ");
			number = in.nextInt();
		}
		

		//find first and the second digit of a two digit number
		ones = (number % 100) / 10;
		tens = number % 10;
		
		//output
		if 	(number < 100) {
			System.out.println("The ones unit is : " + ones);
			System.out.print("The number in the tens position is : " + tens);
			}
		
		//invalid input
		else {
			System.out.print("This doesnt seem to be valid, please try again.");
		}
	}	
}



//Please input a number with 2 digits : 67
//The ones unit is : 6
//The number in the tens position is : 7