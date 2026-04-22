/*

Program: exercise4_diceRol1Game.java          Last Date of this Revision: April 20, 2026

Purpose: Dice Roll Game - get the user to roll a dice make a bet and then call whether it will be < or > 7, (with 7 being a loss) 

Author: Leif Martin, 
School: CHHS
Course: Computer Programming 20-1

*/

package Mastery;

import java.util.Random;
import java.util.Scanner;

class Die {
	private final Random rand = new Random();
	
	public int roll() {
		return rand.nextInt(6)+1;
	}
}

class Player {
	private int points;
	public Player(int startingPoints) { this.points = startingPoints; }
	public int getPoints() { return points; }
	public void updatePoints(int amount) { this.points += amount; }
	public boolean canAfford(int amount) { return (amount > 0 && amount <= points); }
}

//Running game
public class exercise4_diceRollGame {
	public static void main(String[] args) {
		DiceGame game = new DiceGame(1000);
		game.start();
		System.out.print("Thank you for playing!");
	}
}

class DiceGame {
	// Establishing new game
	private Die d1 = new Die();
	private Die d2 = new Die();
	private Player player;
	private Scanner in = new Scanner(System.in);
	public DiceGame(int startingPoints) { this.player = new Player(startingPoints); }
	
	// Start game
	public void start() {
		System.out.println("Welcome to the dice game!");
		System.out.println("You have " + player.getPoints() + " points.");
		
		// Game loop
		while (player.getPoints() > 0) {
			playRound();
			System.out.print("Play again? (y/n): ");
			if (in.next().toLowerCase().equals("n")) break;
		}	
	}
	
	public void playRound() {
		// Betting
		System.out.print("Risk: ");
		int bet = in.nextInt();
		if (!player.canAfford(bet)) { 
			System.out.println("You do not have enough money for that bet.");
			return;	
		}
		
		// Calling roll
		System.out.print("0 for Low, 1 for High: ");
		int call = in.nextInt();
		
		// Rolling dice
		int roll = d1.roll() + d2.roll();
		System.out.println("Rolled: " + roll);
		
		// Player Won
		if ((roll < 7 && call == 0) || (roll > 7 && call == 1)) {
			player.updatePoints(bet);
			System.out.println("You Win " + bet + " points!");
		}else { // Player Lost
			player.updatePoints(-bet);
			System.out.println("You lose " + bet + " points!");
		}
		
		System.out.println("You have " + player.getPoints() + " points left.");
	}
}
	
/*

/// --- console output --- ///
Welcome to the dice game!
You have 1000 points.
Risk: 1001
You do not have enough money for that bet.
Play again? (y/n): y
Risk: 250
0 for Low, 1 for High: 0
Rolled: 8
You lose 250 points!
You have 750 points left.
Play again? (y/n): y
Risk: 500
0 for Low, 1 for High: 1
Rolled: 5
You lose 500 points!
You have 250 points left.
Play again? (y/n): n
Thank you for playing!

*/


