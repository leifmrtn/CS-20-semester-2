package Mastery;

import java.util.Scanner;

public class exercise10_volume {

	public static void main(String[] args) {
		//variables
		int choice;
		int length;
		int width;
		int height;
		int radius;
		double volume;
		
		//deciding whether it is a sphere or a rectangular prism
		try (Scanner in = new Scanner(System.in))	{
			System.out.print("Would you like to know the area of a 1: Rectangular prism, or 2: sphere?");
			choice = in.nextInt();
			
			//rectangular prism
			if (choice == 1) {
				System.out.print("What is the length of the rectangular prism in cm?: ");
				length = in.nextInt();
				System.out.print("What is the width of the rectangular prism in cm?: ");
				width = in.nextInt();
				System.out.print("What is the height of the rectangular prism in cm?: ");
				height = in.nextInt();
				
				volume = length*width*height;
				
				System.out.print("The volume of the rectangular prism is " + volume + "cm^3");
			}
			//sphere
			else if (choice == 2) {
				System.out.print("What is the radius of the sphere in cm?: ");
				radius = in.nextInt();
				
				//solve
				volume = ((4/3)*3.14*radius*radius);
				System.out.print("The volume of your sphere is " + volume + " cm^3");
			}
			//invalid
			else {
				System.out.print("Sorry that was not a valid answer.");
			}
		}

	}

}


// --- console ---
//Would you like to know the area of a 1: Rectangular prism, or 2: sphere?1
//What is the length of the rectangular prism in cm?: 7
//What is the width of the rectangular prism in cm?: 3
//What is the height of the rectangular prism in cm?: 12
//The volume of the rectangular prism is 252.0cm^3