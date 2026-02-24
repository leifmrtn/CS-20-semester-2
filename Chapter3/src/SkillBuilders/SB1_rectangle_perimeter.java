package SkillBuilders;

import java.util.Scanner;

public class SB1_rectangle_perimeter {

	public static void main(String[] args)
	{
		//establishing variables 
		int length;
		int width;
		int choice;
		int answer;
		
		// getting the input set up
		try (Scanner input = new Scanner(System.in))	{
	
			//inputting length
			System.out.print("What is the length? : ");
			length = input.nextInt();
			
			//inputting width
			System.out.print("What is the Width? : ");
			width = input.nextInt(); 
			
			//declaring area or perimeter
			
			System.out.println("Which calculation do you wish to perform");
			System.out.println("1. Area");
			System.out.println("2. Perimeter");
			choice = input.nextInt();
		}
		//area
		if (choice == 1)	{
			answer = (width*length);
			System.out.print("The total area is " + answer + " units");
		}
		
		//perimeter
		else if (choice == 2)	{
			answer = 2*(width+length);
			System.out.print("The total perimeter is " + answer + " units");
		}
		
		//error
		else {
			System.out.print("Im sorry but that isn't a valid answer. Please start again");
		}

	}

}


//What is the length? : 7
//What is the Width? : 5
//Which calculation do you wish to perform
//1. Area
//2. Perimeter
//2
//The total perimeter is 24 units
