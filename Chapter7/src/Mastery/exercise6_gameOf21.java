package Mastery;

import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class exercise6_gameOf21 {
	private static int dealerAce = 0;
	private static int playerAce = 0;
	private static int bet;
	private static int money = 1000;
	private static List<String> playerCards = new ArrayList<>();
	private static List<String> dealerCards = new ArrayList<>();
	static private List<String> deck = new ArrayList<>();
	static Scanner in = new Scanner(System.in);

	
	public static void main(String[] args) { // Main Code and Game
		boolean running = true;
		shuffleDeck();
		String choice = null;
		System.out.println("+============================+");
		System.out.println("| Welcome to the game of 21. |");
		System.out.println("+============================+\n");
		while (running && money > 0) {
			System.out.println("You have " + money + " dollars.");
			deal();
			System.out.println("Would you like to play again?.(y,n)");
			choice = in.next();
			if (choice.equals("y")) {
				System.out.println("You have " + money + " dollars.");
				deal();
			}
			else if (money > 0){
				System.out.println("You finished with " + money + " dollars");
			}
		}
		if (money == 0) {
			System.out.println("Thank you for playing!");
		}
	}
	
	public static void shuffleDeck() { // reseting the deck for game play
		deck.clear(); // Reset	
		for(int i = 0; i < 4; i ++) { // Fill the deck
			deck.add("Ace"); // Ace
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
	public static int getValue(String rank) { // Checking the Values of ranks for cards
		int value;
		switch (rank) {
		case "Ace": // Ace
			value = 11;
			break;
		case"Two": // Two
			value = 2;
			break;
		case "Three": // Three
			value = 3;
			break;
		case "Four": // Four
			value = 4;
			break;
		case "Five": // Five
			value = 5;
			break;
		case "Six": // Six
			value = 6;
			break;
		case "Seven": // Seven
			value = 7;
			break;
		case "Eight": // Eight
			value = 8;
			break;
		case "Nine": // Nine
			value = 9;
			break;
		case "Ten": // Ten
			value = 10;
			break;		
		case "Jack": // Jack
			value = 10;
			break;	
		case "Queen": // Queen
			value = 10;
			break;	
		case "King": // King
			value = 10;
			break;	
		default: // Acelow
			value = -10;				
		}
		return value;
	}
	public static void deal() { // Dealing and betting
		boolean running = true;	
		playerCards.clear();
		dealerCards.clear();
		playerAce = 0;
		dealerAce = 0;
		bet = 0;
		System.out.println("How much would you like to bet?: ");
		bet = in.nextInt();
		while (running) {
			if (bet <= money) {
				running = false;
			}
			else {
				System.out.println("That was an invalid bet, please try again.");
				System.out.println("How much would you like to bet?: ");
				bet = in.nextInt();
			}
		}
		if(deck.size() <= 18) { // Checking if they should reshuffle
			shuffleDeck();		
		}
		System.out.println("The dealer has a " + draw("dealer") + ".(" + getValue(dealerCards.get(0)) + ")");
		System.out.println("You draw a " + draw("player") + " and a " + draw("player") + ".(" + getTotalFor("player") + ")" );
		hitOrStand();
		
	}
	public static void hitOrStand() {
		// initialize variables
		boolean running = true;	
		String choice;
		String draw = null;

		while (running && getTotalFor("player") <= 21) { // Hit, stand and double running
			System.out.println("Would you like to (h)it, (s)tand, or (d)ouble?");
			choice = in.next();
			while (!(choice.equals("h") || choice.equals("s") || choice.equals("d"))) { // input loop
				System.out.println("That wasn't an option.(h,s,d)?");
				System.out.println("Would you like to (h)it, (s)tand, or (d)ouble?");		
				choice = in.next();
			}
			checkAce("player");
		
			if (running && getTotalFor("player")  <= 21) {			
				if(choice.equals("h") || choice.equals("d")) { // Hit	or double
					if (choice == "d"){ // double
						bet *= 2;
					}
					draw = draw("player");
					checkAce("player");
					System.out.println("You draw a " + draw + " and now have (" + getTotalFor("player") + ")");	
					if (getTotalFor("player") > 21) {
						money -= bet;
						System.out.println("You have busted, and lost " + bet + " dollars");
						running = false;
					}	
				}
				else if(choice.equals("s")) { // Stand
					System.out.println("You have stood with (" + getTotalFor("player") + ")"); 
					running = false;
				}	
			}
		}
		if (getTotalFor("player") <= 21){
			dealerDraw();
		}
	}	
	public static String draw(String drawFor) {
		String card = null;
		if (drawFor.equals("player")) { // Draw for the Player
			card = deck.get(0);
			playerCards.add(deck.get(0));
			deck.remove(0);	
			if (card.equals("Ace")) {
				playerAce += 1;
			}
			checkAce("player");
		}
		else if(drawFor.equals("dealer")) { // Draw for the dealer
			card = deck.get(0);
			dealerCards.add(deck.get(0));
			deck.remove(0);		
			if (card.equals("Ace")) {
				dealerAce += 1;
			}
			checkAce("dealer");
		}
		return card;
	}
	public static void dealerDraw() {
		String card = draw("dealer");
		boolean running = true;
		checkAce("dealer");
		System.out.println("The dealer draws a " + card + ". their total is now (" + getTotalFor("dealer") + ")");
		while(getTotalFor("dealer") < 21 && running && getTotalFor("dealer") < 17) {
			checkAce("dealer");
			if(getTotalFor("dealer") > 21) {
				running = false;
			}
			else if (getTotalFor("dealer") >= 17) {
			System.out.println("The dealer stood with (" + getTotalFor("dealer") + ").");
			running = false;
			}
			else {
				draw("dealer"); 
				checkAce("dealer");
				System.out.println("The dealer draws a " + card + ". their total is now (" + getTotalFor("dealer") + ")");
			}
		}
		if (getTotalFor("dealer") > 21) { // Dealer busts
			money += bet;
			System.out.println("Dealer busted! You won " + bet + " dollars and now have " + money);		
		}
		else if (getTotalFor("dealer") == getTotalFor("player")) {// push
			bet /= 2;
			money += bet;
			System.out.println("You both have the same totals! You pushed and gained " + bet + " dollars and now have " + money);	
		}
		else if (getTotalFor("dealer") > getTotalFor("player")) { // dealer wins
			money -= bet;
			System.out.println("Dealer Won! You lost " + bet + " dollars and now have " + money);
		}
		else if (getTotalFor("dealer") < getTotalFor("player")) { // player wins
			money += bet;
			System.out.println("Dealer Lost! You won " + bet + " dollars and now have " + money);
		}
	}
	public static int getTotalFor(String totalFor) {
		int total = 0;
		if (totalFor.equals("player")) {
			for (int i = 0; i < playerCards.size(); i++) {
				total += getValue(playerCards.get(i));
			}
		}
		else if (totalFor.equals("dealer")) {
			for (int i = 0; i < dealerCards.size(); i++) {
				total += getValue(dealerCards.get(i));
			}
		}
		return total;
	}
	public static void checkAce(String checkFor) {
		if(checkFor.equals("player")) {
			if (getTotalFor("player") > 21 && playerAce >= 1) { // Had an ace				
					playerAce -= 1;
					playerCards.add("aceLow");				
			}
		}
		else if (checkFor.equals("dealer")){
			if(getTotalFor("dealer") > 21 && dealerAce >= 1) { // Had an ace
				dealerAce -= 1;
				dealerCards.add("aceLow");
			}
		}
	}
}
//// --- console output --- //
//+============================+
//| Welcome to the game of 21. |
//+============================+
//
//You have 1000 dollars.
//How much would you like to bet?: 
//5000
//That was an invalid bet, please try again.
//How much would you like to bet?: 
//500
//The dealer has a Ten.(10)
//You draw a Eight and a Jack.(18)
//Would you like to (h)it, (s)tand, or (d)ouble?
//s
//You have stood with (18)
//The dealer draws a Seven. their total is now (17)
//Dealer Lost! You won 500 dollars and now have 1500
//Would you like to play again?.(y,n)
//n
//Thank you for playing!
//You finished with 1500 dollars
//How much would you like to bet?: 


