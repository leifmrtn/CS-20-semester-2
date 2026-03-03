package Mastery;

import java.util.Random;
import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class exercise6_gameOf21 {
	private static int dealerTotal;	
	private int dealerAce = 0;
	private static int playerTotal;
	private int playerAce = 0;
	private static int money = 1000;
	private int bet;
	static private List<String> deck = new ArrayList<>();
	static Scanner in = new Scanner(System.in);

	
	public static void main(String[] args) { // Main Code and Game
		shuffleDeck();
		System.out.println("+============================+");
		System.out.println("| Welcome to the game of 21. |");
		System.out.println("+============================+\n");
		System.out.println("You currently have " + money + " dollars.");
		deal();
	}
	
	public static void shuffleDeck() { // reseting the deck for gameplay
		deck.clear(); // Reset	
		for(int i = 0; i < 4; i ++) { // Fill the deck
			deck.add("ace"); // Ace
			deck.add("Two");	deck.add("Three");
			deck.add("Four");	deck.add("Five");
			deck.add("Six");	deck.add("Seven");
			deck.add("Eight");	deck.add("Nine");
			deck.add("Ten"); // Ten
			deck.add("Jack"); // Jack
			deck.add("Queen"); // Queen
			deck.add("King"); // King
		}	
		Collections.shuffle(deck);	// Shuffle the deck
	}
	public static int getValue(String rank) {
		int value;
		if(rank == "Ace") { // Ace
			value = 11;
		}
		else if(rank == "Two"){ // Two
			value = 2;
		}
		else if(rank == "Three"){ // Three
			value = 3;
		}
		else if(rank == "Four"){ // Four
			value = 4;
		}
		else if(rank == "Five"){ // Five
			value = 5;
		}
		else if(rank == "Six"){ // Six
			value = 6;
		}
		else if(rank == "Seven"){ // Seven
			value = 7;
		}
		else if(rank == "Eight"){ // Eight
			value = 8;
		}
		else if(rank == "nine"){ // Nine
			value = 9;
		}
		else if (rank == "Ten") { // Ten
			value = 10;
		}
		else if (rank == "Jack") { // Jack
			value = 10;
		}
		else if (rank == "Queen") { // Queen
			value = 10;
		}
		else if (rank == "King") { // King
			value = 10;
		}
		return value;
	}
	public static void deal() { // Dealing
		check(3);	
		// Reset variables
		playerTotal = 0;
		dealerTotal = 0;
		String pD1 = deck.get(0);
		deck.remove(0);
		String pD2 = deck.get(0);
		deck.remove(0);
		String dD1 = deck.get(0);
		deck.remove(0);
		String dD2 = deck.get(0);
		deck.remove(0);
		
		System.out.println("The dealer has a " + dD1 + ", and hole card.(" + getValue(dD1) + ")");
		System.out.println("You have a " + pD1 + " and " + pD2 + ".(" + (getValue(pD1) + getValue(pD2)) + ")" );
		System.out.println("Would you like to (h)it or (s)tand");
		hitOrStand(in.next());
		
		// System.out.print(deck.size()); // Debug Print
			
	}
	public static boolean check(int bet) {
		boolean valid = false;
		if(deck.size() <= 18) { // Checking if they should reshuffle
			shuffleDeck();		
		}
		if((bet -= money) >= 0) { // enough money for that bet
			valid = true;
		}
		return valid;
		
	}
	public static void winOrLoss() {
	//	if(player > 21) 
			
	}
	public static void hitOrStand(String choice) {
		if(choice == "h") { // Hit		
		}
		else if(choice == "s") { // Stand
		}	
		else {
			System.out.println("error in line 132");
		}
	}
}
//requirements
//play the game until the player says to stop
//announce the winner
//allow the play to stay or take a third card
//deal a card from 1-13 1 is an ace 
//jack, queen, king are all 10
//ace can be 1 or 11
//going over 21 is a loss
