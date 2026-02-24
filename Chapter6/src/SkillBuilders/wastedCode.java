package SkillBuilders;

import java.util.Random;
//import java.util.Scanner;

public class wastedCode {
	 //variables 
	
	static int points = 500;

	
	
	

	public static void main(String[] args) {		
		//initialize
		boolean winLoss;
		
		//Starting
		board();
		}

	public static void board() {
		//String format = "| %-48s | %-14s |%"; also can do this
		//-4d means that 4 spaces will be between it and the next sting
		
		//string formats
		String f1 = "| %-25s | %n";
	//	String f2 = "| %-11s | %-11s | %n";
		String t1 = "+===========================+%n";
		String l1 = "+---------------------------+%n";
	//	String l2 = "+-------------+-------------+%n";
		String inline = "|---> ";

		//String clearScreen = "\\033[H\\033[2J";
		
		//board
		
		System.out.printf("%n" + t1);
		System.out.printf(f1, "High Low Game");
		System.out.printf(t1);
		System.out.printf(f1, "Points: " + points);
		System.out.printf(l1);
		System.out.printf(f1, "");
		System.out.printf(f1, "");
		System.out.printf(l1);
		System.out.printf(f1, "");
		System.out.printf(f1, "");//win or loss
		System.out.printf(t1);
		System.out.printf(inline);
	//	System.out.print(clearScreen);
			
		//System.out.printf(f2, "Total Points", "[" + points +"]");		
	}
	

}


//---console---//