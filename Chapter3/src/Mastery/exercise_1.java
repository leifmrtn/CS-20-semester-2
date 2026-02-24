package Mastery;

import java.text.DecimalFormat;
import java.util.Scanner;


public class exercise_1 {

	public static void main(String[] args) {
		//Variables
		int time;
		double height = 0;
		 DecimalFormat df = new DecimalFormat("00.0");

		
		//Inputs
		try (Scanner in = new Scanner(System.in)){
			System.out.print("Please enter the time:  ");
			time = in.nextInt();
		}
		
		//solving and outputting
		height = 100-(4.9*(time*time));
		System.out.print("The height of the object is: " + df.format(height));
	}
}

// -- console --
//Please enter the time:  4
//The height of the object is: 21.6
