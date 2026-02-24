package SkillBuilders;

import java.text.DecimalFormat;
import java.util.Scanner;

public class metric_conversion {
	static DecimalFormat dcf = new DecimalFormat("0.0");

	public static void inchesToCentimeters(int number) 
	{
		double answer;
		
		answer = number * 2.54;
		
		System.out.println("\n" + number + 
				" inches equals " + answer + 
				" centimeters.");
	}

	public static void feetToCentimeters(int number)
	{
		double answer;

		answer = number * 30.48;
		 
		System.out.println("\n" + number + 
				" feet equals " + answer + 
				" centimeters.");	
	}
	
	public static void yardsToMeters(int number)
	{
		double answer;

		answer = number * 0.91;
		
		System.out.println("\n" + number + 
				" yards equals " + dcf.format(answer) + 
				" meters.");		
	}
	
	public static void milesToKilometers(int number)
	{
		double answer;

		answer = number * 1.61;
		
		System.out.println("\n" + number + 
				" miles equals " + answer + 
				" kilometers.");		
	}
	
	public static void centimetersToInches(int number)
	{
		double answer;

		answer = number * 0.3937;
		
		System.out.println("\n" + number + 
				" centimeters equals " + answer + 
				" inches.");		
	}
	public static void centimetersToFeet(int number)
	{
		double answer;

		answer = number * 0.328;
		
		System.out.println("\n" + number + 
				" centimeters equals " + answer + 
				" feet.");		
	}
	public static void metersToYards(int number)
	{
		double answer;

		answer = number * 1.09;
		
		System.out.println("\n" + number + 
				" meters equals " + answer + 
				" yards.");		
	}
	public static void kilometersToMiles(int number)
	{
		double answer;

		answer = number * 0.62;
		
		System.out.println("\n" + number + 
				" kilometers equals " + answer + 
				" miles.");		
	}
	
	
	public static void main(String[] args)
	{
		int choice;
		int number;
		Scanner input = new Scanner(System.in);
		
		System.out.println("Please enter a number: ");
		number = input.nextInt();
		
		System.out.println("Convert: ");
		System.out.println("1. Inches To Centimeters\t"
						+ "5. Centimeters To Inches");
		System.out.println("2. Feet To Centimeters\t"
					+ "6. Centimeters To Feet");
		System.out.println("3. Yards To Meters\t"
				+ "5. Meters To Yards");
		System.out.println("4. Miles To Kilometers\t"
				+ "8. Kilometer To Miles");
		
		System.out.println("Please Enter a Choice");
		choice = input.nextInt();
		
		switch(choice)
		{
			case 1: inchesToCentimeters(number);break;
			case 2: feetToCentimeters(number);break;
			case 3: yardsToMeters(number);break;
			//case 4: feetToCentimeters(number);
		}

	}
	
	
	
	
}
//public - class only
//void - doesn't return data type'
//camel case -- first letter lowercase other first letters uppercase
// \t is a tab
// decimal format
