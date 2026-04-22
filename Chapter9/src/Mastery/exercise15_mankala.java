
/*

Program: exercise15_mankala.java          Last Date of this Revision: March 30, 2026

Purpose: Mankala game

Author: Leif Martin, 
School: CHHS
Course: Computer Programming 20-1

*/
package Mastery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class exercise15_mankala {
	static int playerBin = 0;
	static int opponentBin = 0;
	static int[] pits = new int[14];
	static String status = null; // "opponentTurn", "playerTurn", "gameOver"

	
	public static void main(String[] args) {
		status = "startGame";
		boolean running = true;
		Scanner input = new Scanner(System.in);

		System.out.println("Welcome to the game of Mankala!");
		while(running) {
			
			switch (status) {
			case "startGame":
				board();
				System.out.println("Please enter how many stones you would like in each pit:(Base=3)");
				int numStones = input.nextInt();
				reset(numStones);
				status = "playerTurn";
				break;
			case "playerTurn":
				System.out.println("PLAYER TURN");
				board();
				move(playerTurn(input));
				checkGameEnd();
				break;
			case "opponentTurn":
				System.out.println("OPPONENT TURN");
				board();
				move(opponentTurn());
				checkGameEnd();
				break;
			case "gameOver":
				board();
				if (pits[6] > pits[13]) { // Player won
					System.out.println("You have won! [" + pits[6] + "-" + pits[13] + "]");
				}
				else if (pits[6] < pits[13]) { // Opponent won
					System.out.println("You have lost! [" + pits[6] + "-" + pits[13] + "]");
				}
				else { // Tie
					System.out.println("Tie Game!");
				}	
				System.out.println("Would You like to play again? [y, n]");
				String choice = input.nextLine();
				if(choice.equals("y")) {
					reset(0);
					status = "startGame";
				}
				else {
					running = false;
					System.out.println("Thank you for playing!");
				}
				break;
			}		
		}
	
		
		
		input.close();
	}
	public static void reset(int numStones) {
		playerBin = 0;
		opponentBin = 0;
		for(int i = 0; i < 6; i ++) { // Player
			pits[i] = numStones;
			pits[i+7] = numStones;
		}
		pits[6] = opponentBin;
		pits[13] = opponentBin;
	}
	public static void board() {
		System.out.println("  " + getPitsFor("opponent"));
		System.out.println(pits[13] + "	             " + pits[6]);
		System.out.println("  " + getPitsFor("player"));
		System.out.println("   A  B  C  D  E  F");
	}
	public static String getPitsFor(String forWho) {
		int[] printPits = new int [6];
		if (forWho.equals("player")) {
			for(int i = 0; i < 6; i++) {
				printPits[i] = pits[i];
			}
		}
		else if (forWho.equals("opponent")) {
			for(int i = 0; i < 6; i++) {
				printPits[i] = pits[pits.length - i - 2];
			}	
		}
		return Arrays.toString(printPits);
	}
	public static int playerTurn(Scanner in) {
		System.out.println("Which pit would you like to____? [A,B,C,D,E,F]");
		String choice = in.nextLine();
		int chosenPit = -1;
		boolean running = true;

		while(running || pits[chosenPit] == 0) {
			running = false;
			switch (choice.toUpperCase()) {
			case "A":
				chosenPit = 0;
				break;
			case "B":
				chosenPit = 1;
				break;
			case "C":
				chosenPit = 2;
				break;
			case "D":
				chosenPit = 3;
				break;
			case "E":
				chosenPit = 4;
				break;
			case "F":
				chosenPit = 5;
				break;
			default:
				running = true;
			}
			if (running) {
				System.out.println("I'm sorry that wasn't valid. Please pick another option:");
				choice = in.nextLine();
			}
		}
		return chosenPit;
	}
	public static int opponentTurn() {
		Random rand = new Random();
		int chosenPit = rand.nextInt(6)+7;
		while(pits[chosenPit] == 0) {
			chosenPit = rand.nextInt(6)+7;
		}
		System.out.println("					the pit is " + chosenPit + " (for opponent pit " + (chosenPit-6));
		return chosenPit;
	}
	public static void move(int pit) {
		int stones = pits[pit]; // get the draw pit number of stones
		pits[pit] = 0; // Set the draw pit to 0

		switch (status) {
		case "playerTurn":
			for(; stones > 0; stones --) { // loop
				pit ++; // change the pit
				if(pit == 13) { // go to start of board
					pit = 0;
				}
				pits[pit] ++; // adding one stone to pit
				if(stones == 1) { // final stone
					if (pits[pit]-1 == 0 && pit >= 0 && pit <= 5 && pit != 6) { // take from the other bin
							pits[6] += pits[pit];
							pits[6] += pits[6+(6-pit)];
							pits[pit] = 0;
							pits[6+(6-pit)] = 0;
					}
				}
			}
			if(pit != 6) { // play again (final stone in bin)
				status = "opponentTurn";
			}
			break;
		case "opponentTurn":
			for(; stones > 0; stones --) { // loop
				pit ++; // change the pit
				if(pit == 7) {
					pit ++; // skip the player pit
				}
				if(pit == 14) { // go to start of board
					pit = 0;
				}
				pits[pit] ++; // adding one stone to pit
				if(stones == 1) { // final stone
					if (pits[pit]-1 == 0 && pit >= 7 && pit <= 12 && pit != 13) {
							pits[13] += pits[pit];
							pits[13] += pits[6-(pit-6)];
							pits[pit] = 0;
							pits[6-(pit-6)] = 0;
					}
				}
			}
			if(pit != 13) { // play again (final stone in bin)
				status = "playerTurn";
			}
			break;
		}
	}	
	public static void checkGameEnd() {
		boolean playerSideEmpty = true;
		boolean opponentSideEmpty = true;
		for(int i = 0; i < 6; i++) {
			if(pits[i] != 0) {
				playerSideEmpty = false;
			}
			if (pits[i+7] != 0) {
				opponentSideEmpty = false;
			}
		}
		if (opponentSideEmpty || playerSideEmpty) {
			for(int i = 0; i < 6; i ++) {
				playerBin += pits[i];
				opponentBin += pits[i+7];
				pits[i] = 0;
				pits[i+7] = 0;
				status = "gameOver";
			}
		}
	}
}

/*
/// --- console output --- ///

*/


