/*

Program: exercise6_gameOf21.java          Last Date of this Revision: April 22, 2026

Purpose: Game of 21

Author: Leif Martin, 
School: CHHS
Course: Computer Programming 20-1

*/

package Mastery;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

enum Rank {
	// All possible card ranks
	ACE(11, "Ace"),
	TWO(2, "Two"), 
	THREE(3, "Three"),
	FOUR(4, "Four"), 
	FIVE(5, "Five"),
	SIX(6, "Six"), 
	SEVEN(7, "Seven"),
	EIGHT(8, "Eight"), 
	NINE(9, "Nine"), 
	TEN(10, "Ten"),
	JACK(10, "Jack"),
	QUEEN(10, "Queen"),
	KING(10, "King");
	
	private final int value;
	private final String display;
	
	// Establish cards value and rank
	Rank(int value, String display){
		this.value = value;
		this.display = display;
	}
	
	// methods to get value of card and to display card rank
	public int getValue() { return value; }
	public String getDisplay() { return display; }
}

// Player (keeps track of money)
class TotalMoney {
	private int money;
	public TotalMoney(int startingMoney) { this.money = startingMoney; }
	public int getMoney() { return this.money; }
	public void updateMoney(int amount) { this.money += amount; }
}

class Card {
	private final Rank rank;
	public Card(Rank rank) { this.rank = rank; }
	public int getValue() { return rank.getValue(); }
	public String toString() { return rank.getDisplay(); }
}

class Deck {
	private List<Card> cards = new ArrayList<>(); 
	
	public Deck() {
		for (int i = 0; i < 1; i++) {
			for (Rank r : Rank.values()) {
				cards.add(new Card(r));
			}
		}
	}
	
	public void shuffle() { Collections.shuffle(cards); }
	
	// Draw a new card
	public Card draw() { 
		return cards.remove(0); 
		}
	
//	public int size() { return cards.size(); }
}

// Defining the hand class
class Hand {
	private List<Card> cards = new ArrayList<>();
	
	// Drawing card into hand
	public String drawCard(Card card) {
		cards.add(card);
		return card.toString();
	}
	
	// Get the value of the hand
	public int getTotal() {
		int total = 0;
		int aces = 0;
		for (Card c : cards) {
			total += c.getValue();
			if (c.toString().equals("Ace")) aces++;
		}
		// Accounting for ace
		while (total > 21 && aces > 0) {
			total -= 10;
			aces--;
		}
		return total;
	}
	// Display hand as String
	public String toString() {
//		for(Card c : cards) {
//			 System.out.print("");
//		}
		return cards.toString() + " (" + getTotal() + ")";
	}
	
//	// Reset hand
//	public void clear() { cards.clear(); }
}

public class exercise6_gameOf21 {
	
	public static void main(String[] args) {
	
		GameOf21 game = new GameOf21();
		game.startGame();
	}
}

class GameOf21 {
		
	public static TotalMoney player = new TotalMoney(1000);

	// Initial game method
	public void startGame() {
		Scanner in = new Scanner(System.in);
		
		// Display start screen
		System.out.println("Welcome to the game of 21!");
		System.out.println("==========================");
		
		// Game running loop
		while(true) {
			playRound();
			
			// Play again?
			System.out.print("Play again? (y/n): ");
			String input = in.next();
			if((input).equals("n") || player.getMoney() == 0) break;
		}
		// Quit game
		System.out.println("You finished with " + player.getMoney() + " dollars.");
		System.out.println("Thank you for playing!");
	}
	
	private static void playRound() {
		// Initializing
		Deck deck = new Deck();
		deck.shuffle();
		Hand pHand = new Hand();
		Hand dHand = new Hand();
		int bet;
		Scanner in = new Scanner(System.in);
		
		System.out.println("You have " + player.getMoney() + " dollars.");
		
		// Betting
		while(true) {
			System.out.print("Bet: ");
			bet = in.nextInt();
			if(bet > 0 && bet <= player.getMoney()) break;
			System.out.println("I'm sorry, that was not a valid bet.");
		}
		
		// Dealing initial cards
		dHand.drawCard(deck.draw());
		pHand.drawCard(deck.draw());
		pHand.drawCard(deck.draw());
		
		// Display hands
		System.out.println("Dealer shows: " + dHand.toString());
		System.out.println("Your hand: " + pHand.toString());	
	
		// Player's turn
		while (pHand.getTotal() <= 21) {
			System.out.print("(h)it, (d)ouble, or (s)tand?: ");
			String choice = in.next().toLowerCase();
			if(!choice.equals("h")) {
				if (choice.equals("d") && player.getMoney() > (bet*2)) { // Double
					pHand.drawCard(deck.draw());
					bet *= 2;
				}
				// Exit players action
				break;	
			}
		
			pHand.drawCard(deck.draw());
			System.out.println("Hand: " + pHand.toString());
		}
		
		// Dealer's turn
		while (dHand.getTotal() < 17 && pHand.getTotal() <= 21) {
			dHand.drawCard(deck.draw());
		}
		System.out.println("Dealers final: " + dHand);
		
		// Round over
		checkWinner(pHand, dHand, bet);
	
	}
	
	// Seeing who if someone won
	private static void checkWinner(Hand p, Hand d, int bet) {
		int pT = p.getTotal();
		int dT = d.getTotal();
		
		// Checking the win condition
		if (pT > 21) { // Player busts
			player.updateMoney(-bet);
			System.out.println("Bust! Lost: " + bet);
		}
		else if (dT > 21) { // dealer busts
			player.updateMoney(bet);
			System.out.println("Dealer busts! Gained: " + bet);
		}
		else if (pT == dT) { // Push
			System.out.println("Push (Tie).");
		}
		else if (pT > dT) { // Player wins
			player.updateMoney(bet);
			System.out.println("You Won: " + bet);
		}
		else { // The player lost
			player.updateMoney(-bet);
			System.out.println("Dealer Wins. Lost: " + bet);
		}

	}
}



///////////////////////////////////////////////////////////

/*
 
/// --- console output --- ///

Welcome to the game of 21!
==========================
You have 1000 dollars.
Bet: 500
Dealer shows: [Ten] (10)
Your hand: [Seven, Two] (9)
(h)it, (d)ouble, or (s)tand?: h
Hand: [Seven, Two, Ace] (20)
(h)it, (d)ouble, or (s)tand?: s
Dealers final: [Ten, Six, King] (26)
Dealer busts! Gained: 500
Play again? (y/n): y
You have 1500 dollars.
Bet: 750
Dealer shows: [Five] (5)
Your hand: [Eight, Four] (12)
(h)it, (d)ouble, or (s)tand?: h
Hand: [Eight, Four, Queen] (22)
Dealers final: [Five] (5)
Bust! Lost: 750
Play again? (y/n): n
You finished with 750 dollars.
Thank you for playing!

*/
