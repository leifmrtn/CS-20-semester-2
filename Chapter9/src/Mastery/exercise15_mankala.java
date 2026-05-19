/*

Program: exercise15_mankala.java          Last Date of this Revision: May 13, 2026

Purpose: Mankala game

Author: Leif Martin, 
School: CHHS
Course: Computer Programming 20-1

*/

package Mastery;

import static Mastery.User.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

// Defines game participants
enum User {
	PLAYER(),
	OPPONENT();
}	

// Handles board tracking, state modifications, and board rendering
class MankalaBoard {
	
	// Track stones inside the six pits for both participants
	private int[] playerSide = new int[6];
	private int[] opponentSide = new int[6];
	
	// Store total accumulated scores inside the large side bins
	private int playerBin;
	private int opponentBin;	
	
	// Helper methods to modify and retrieve player game state variables
	void updatePlayerBin(int amount) { playerBin += amount; }
	int getPlayerBin() { return playerBin; }
	void setPlayerPit(int pit, int amount) {	playerSide[pit] = amount;}
	void updatePlayerPit(int pit, int amount) { playerSide[pit] += amount; }
	int getPlayerPit(int pit) {	return playerSide[pit];	}
	
	// Opponents Pit Difference: Maps indexes to facilitate counter-clockwise looping array offsets
	int OPD(int value) { 
		if(value < 0) {
			value += 10;
			return value;
		}
		else {
			return 5 - value;
		}
		
	}	
	
	// Helper methods to modify and retrieve opponent game state variables via index mapper
	void updateOpponentBin(int amount) { opponentBin += amount; }
	int getOpponentBin() { return opponentBin; } 
	void setOpponentPit(int pit, int amount) { opponentSide[OPD(pit)] = amount; }
	void updateOpponentPit(int pit, int amount) { opponentSide[OPD(pit)] += amount; }
	int getOpponentPit(int pit) { return opponentSide[OPD(pit)]; }
	
	// Constructor initializing score tracking metrics and allocating uniform starting stones
	MankalaBoard(int numStartingStones) {
		playerBin = 0;
		opponentBin = 0;
		
		for(int i = 0; i < 6; i++) {
			playerSide[i] = numStartingStones;
			opponentSide[i] = numStartingStones;
		}
	}
		
	// Renders textual map layout depicting visual indices, bin locations, and pit counts
	void getDisplay() {	
		System.out.println("");
		System.out.println(" 5  4  3  2  1  0 ");
		System.out.println(Arrays.toString(opponentSide));
		System.out.println(opponentBin + "   		 " + playerBin);
		System.out.println(Arrays.toString(playerSide));
		System.out.println(" 0  1  2  3  4  5 ");
	}
}	
	
// Orchestrates primary turn assignment workflows and continuous round management loops
class Game {
	
	MankalaBoard board;
	
	// Outputs welcome message splash elements
	void start() {
		System.out.println("+===============================+");
		System.out.println("|Welcome to the game of Mankala!|");
		System.out.println("+===============================+");
	
	}
	
	// Drives ongoing rounds until game termination triggers are met
	void playRound(User nextTurn) {
		
		Scanner in = new Scanner(System.in); // Add Scanner
		
		int pit = -1;
		User turn = null;
		
		System.out.print("Number of stones in each bin: ");
		int numStones = in.nextInt();
		board = new MankalaBoard(numStones);
		
		// Run match ticks consecutively while valid pits retain stones
		while(checkGameEnd()) {
			turn = nextTurn;

			switch(turn) {
			case PLAYER:
				
				System.out.print("[PLAYER]"); // Title For turn		
				board.getDisplay(); // Print board
				System.out.print("Pit: "); // Initial prompt for input
				pit = in.nextInt(); // Get initial input	
				
				while(pit < 0 || pit > 5 || board.getPlayerPit(pit) == 0) {
					System.out.println("I'm sorry that was not a valid choice.");
					System.out.print("Pit: "); // Prompt for input
					pit = in.nextInt(); // Get input	
				}
			
				break;
				
			case OPPONENT:
				System.out.print("[OPPONENT]"); // Title For turn	
				board.getDisplay();
				Random rand = new Random(); // Import random
				pit = rand.nextInt(6); // Roll for pit
				
				// Re-roll automatically if selection references an empty index location
				while(pit < 0 || pit > 5 || board.getOpponentPit(pit) == 0) {
					pit = rand.nextInt(6); // Roll pit
				}
				
				break;
			}
			
			// Process move rules and evaluate whose turn follows next
			nextTurn = move(turn, pit);
		}
	}
	
	// Handles real-time counter-clockwise distribution of stones across active array indices
	User move(User turn, int startPit) {
		
		int heldStones = 0;
		int currentPit = 0;
		User side = null;
		User nextTurn = null;

		switch(turn) { // Setting up what side and pit to start with
		
		case PLAYER:
			heldStones = board.getPlayerPit(startPit); 
			board.setPlayerPit(startPit, 0); 			
			currentPit = startPit + 1;
			nextTurn = OPPONENT;	
			break;
			
		case OPPONENT:
			heldStones = board.getOpponentPit(startPit);
			board.setOpponentPit(startPit, 0);
			currentPit = startPit + 1;		
			nextTurn = PLAYER;
			break;
		}
		
		side = turn;
		
		/// Moving stones ///
		while(heldStones > 0) {

			/// Switching the sides ///
			if (currentPit == 6) { // Outside of the pits
				if(side == turn) { // Users turn, on Users side or vice versa
				
					switch(turn) {
					case PLAYER: 
						board.updatePlayerBin(1);
						if (heldStones == 1) { return PLAYER; } // Rule: End directly inside your own bin rewards an extra turn
						heldStones -= 1;		
						break;
						
					case OPPONENT: 
						board.updateOpponentBin(1);
						if (heldStones == 1) { return OPPONENT; } // Rule: End directly inside your own bin rewards an extra turn
						heldStones -= 1;
						break;
					}
				}
				
				currentPit = 0;
				side = (side == PLAYER) ? OPPONENT : PLAYER;
				
				// Breaks the current cycle to re-evaluate tracking bounds before dropping subsequent stones
				continue;
			}	

			// Process stone capture rules when the last stone lands in an empty friendly pit index
			if (heldStones == 1 && side == turn) {
				switch(turn) {
				case PLAYER:
					if(board.getPlayerPit(currentPit) == 0 && board.getOpponentPit(currentPit-10) > 0) {  
						
						board.updatePlayerBin(board.getOpponentPit(currentPit-10) + 1);
						board.setOpponentPit(currentPit-10, 0);
						return nextTurn;
					}
					
					break;

				case OPPONENT:
					if(board.getOpponentPit(currentPit) == 0 && board.getPlayerPit(board.OPD(currentPit)) > 0) {  
						
						board.updateOpponentBin(board.getPlayerPit(currentPit) + 1);
						board.setPlayerPit(board.OPD(currentPit), 0);
						return nextTurn;
					}
					
					break;
				}	
			}

			/// Changing the pit ///
			if (side == PLAYER && heldStones > 0) { // On the players side
				board.updatePlayerPit(currentPit, 1);
			}
			
			else { // The opponents side
				board.updateOpponentPit(currentPit, 1);
			}
			
			heldStones -= 1;
			currentPit += 1;
			}
		
		return nextTurn;
	}

	// Evaluates win parameters, sweeps loose board remnants, and updates metrics display boards
	boolean checkGameEnd() {
		
		Scanner in = new Scanner(System.in);
		
		boolean pPitsEmpty = true;
		boolean oPitsEmpty = true;
		
		for(int i = 0; i < 6; i ++) {// Checking if either side has empty pits
			if(board.getPlayerPit(i) != 0) { pPitsEmpty = false; }
			if(board.getOpponentPit(i) != 0) { oPitsEmpty = false; }
		}
		
		if(oPitsEmpty || pPitsEmpty) { // Game ends
			for(int i = 0; i < 6; i ++) {
				board.updatePlayerBin(board.getPlayerPit(i));
				board.updateOpponentBin(board.getOpponentPit(i));
				board.setPlayerPit(i, 0);
				board.setOpponentPit(i, 0);
			}
			
			board.getDisplay(); // Display Board
			
			int pBin = board.getPlayerBin();
			int oBin = board.getOpponentBin();
			
			if(pBin > oBin) { // Player Won
				System.out.println("You Won!");
			}
			else if(pBin < oBin) { // Player Lost
				System.out.println("You Lost!");
			}
			else { // Tie Game
				System.out.println("You Tied");
			}
			System.out.println(pBin + ":" + oBin); // Final Scores
			
			return false;
		}
		
		return true;
	}	
}

// Global programmatic entry point routing primary game setup cycles
public class exercise15_mankala {
	public static void main(String[] args) {
		
		Game game = new Game();
		Scanner in = new Scanner(System.in);
		
		game.start();
		
		// Master initialization container loop for program play sequences
		while(true) {
			game.playRound(PLAYER);
			
			//Play again?
			System.out.print("Play again? (y/n): ");
			String choice = in.next();
			if (choice.equals("n")) { break; }
		}
		
		System.out.print("Thank you for playing!");
	}
}

/*
/// --- console output --- ///
+===============================+
|Welcome to the game of Mankala!|
+===============================+
Number of stones in each bin: 3
[PLAYER]
 5  4  3  2  1  0 
[3, 3, 3, 3, 3, 3]
0   		 0
[3, 3, 3, 3, 3, 3]
 0  1  2  3  4  5 
Pit: 2
[OPPONENT]
 5  4  3  2  1  0 
[3, 3, 3, 3, 3, 3]
0   		 0
[3, 3, 0, 4, 4, 4]
 0  1  2  3  4  5 
[PLAYER]
 5  4  3  2  1  0 
[4, 0, 3, 3, 3, 3]
1   		 0
[4, 3, 0, 4, 4, 4]
 0  1  2  3  4  5 
Pit: 4
[OPPONENT]
 5  4  3  2  1  0 
[4, 0, 3, 3, 4, 4]
1   		 1
[4, 3, 0, 4, 0, 5]
 0  1  2  3  4  5 
[PLAYER]
 5  4  3  2  1  0 
[0, 0, 3, 3, 4, 4]
2   		 1
[5, 4, 1, 4, 0, 5]
 0  1  2  3  4  5 
Pit: 3
[OPPONENT]
 5  4  3  2  1  0 
[0, 0, 3, 3, 4, 5]
2   		 2
[5, 4, 1, 0, 1, 6]
 0  1  2  3  4  5 
[PLAYER]
 5  4  3  2  1  0 
[0, 1, 4, 4, 5, 0]
9   		 2
[0, 4, 1, 0, 1, 6]
 0  1  2  3  4  5 
Pit: 2
[OPPONENT]
 5  4  3  2  1  0 
[0, 1, 4, 0, 5, 0]
9   		 7
[0, 4, 0, 0, 1, 6]
 0  1  2  3  4  5 
[PLAYER]
 5  4  3  2  1  0 
[1, 2, 0, 0, 5, 0]
10   		 7
[1, 4, 0, 0, 1, 6]
 0  1  2  3  4  5 
Pit: 1
[OPPONENT]
 5  4  3  2  1  0 
[1, 2, 0, 0, 5, 0]
10   		 7
[1, 0, 1, 1, 2, 7]
 0  1  2  3  4  5 
[OPPONENT]
 5  4  3  2  1  0 
[0, 2, 0, 0, 5, 0]
11   		 7
[1, 0, 1, 1, 2, 7]
 0  1  2  3  4  5 
[OPPONENT]
 5  4  3  2  1  0 
[1, 3, 1, 1, 0, 0]
12   		 7
[1, 0, 1, 1, 2, 7]
 0  1  2  3  4  5 
[PLAYER]
 5  4  3  2  1  0 
[1, 4, 0, 1, 0, 0]
12   		 7
[1, 0, 1, 1, 2, 7]
 0  1  2  3  4  5 
Pit: 4
[PLAYER]
 5  4  3  2  1  0 
[1, 4, 0, 1, 0, 0]
12   		 8
[1, 0, 1, 1, 0, 8]
 0  1  2  3  4  5 
Pit: 0
[OPPONENT]
 5  4  3  2  1  0 
[1, 0, 0, 1, 0, 0]
12   		 13
[0, 0, 1, 1, 0, 8]
 0  1  2  3  4  5 
[PLAYER]
 5  4  3  2  1  0 
[1, 0, 0, 0, 0, 0]
14   		 13
[0, 0, 0, 1, 0, 8]
 0  1  2  3  4  5 
Pit: 3
[OPPONENT]
 5  4  3  2  1  0 
[1, 0, 0, 0, 0, 0]
14   		 13
[0, 0, 0, 0, 1, 8]
 0  1  2  3  4  5 

 5  4  3  2  1  0 
[0, 0, 0, 0, 0, 0]
15   		 22
[0, 0, 0, 0, 0, 0]
 0  1  2  3  4  5 
You Won!
22:15
Play again? (y/n): y
Number of stones in each bin: 3
[PLAYER]
 5  4  3  2  1  0 
[3, 3, 3, 3, 3, 3]
0   		 0
[3, 3, 3, 3, 3, 3]
 0  1  2  3  4  5 
Pit: 3
[PLAYER]
 5  4  3  2  1  0 
[3, 3, 3, 3, 3, 3]
0   		 1
[3, 3, 3, 0, 4, 4]
 0  1  2  3  4  5 
Pit: 5
[OPPONENT]
 5  4  3  2  1  0 
[3, 3, 3, 4, 4, 4]
0   		 2
[3, 3, 3, 0, 4, 0]
 0  1  2  3  4  5 
[PLAYER]
 5  4  3  2  1  0 
[4, 4, 4, 5, 0, 4]
0   		 2
[3, 3, 3, 0, 4, 0]
 0  1  2  3  4  5 
Pit: 0
[OPPONENT]
 5  4  3  2  1  0 
[4, 4, 4, 0, 0, 4]
0   		 8
[0, 4, 4, 0, 4, 0]
 0  1  2  3  4  5 
[PLAYER]
 5  4  3  2  1  0 
[0, 4, 4, 0, 0, 4]
1   		 8
[1, 5, 5, 0, 4, 0]
 0  1  2  3  4  5 
Pit: 1
[PLAYER]
 5  4  3  2  1  0 
[0, 4, 4, 0, 0, 4]
1   		 9
[1, 0, 6, 1, 5, 1]
 0  1  2  3  4  5 
Pit: 5
[PLAYER]
 5  4  3  2  1  0 
[0, 4, 4, 0, 0, 4]
1   		 10
[1, 0, 6, 1, 5, 0]
 0  1  2  3  4  5 
Pit: 0
[OPPONENT]
 5  4  3  2  1  0 
[0, 0, 4, 0, 0, 4]
1   		 15
[0, 0, 6, 1, 5, 0]
 0  1  2  3  4  5 
[PLAYER]
 5  4  3  2  1  0 
[0, 1, 5, 1, 1, 0]
1   		 15
[0, 0, 6, 1, 5, 0]
 0  1  2  3  4  5 
Pit: 2
[OPPONENT]
 5  4  3  2  1  0 
[0, 1, 5, 1, 2, 1]
1   		 16
[0, 0, 0, 2, 6, 1]
 0  1  2  3  4  5 
[PLAYER]
 5  4  3  2  1  0 
[1, 0, 5, 1, 2, 1]
1   		 16
[0, 0, 0, 2, 6, 1]
 0  1  2  3  4  5 
Pit: 5
[PLAYER]
 5  4  3  2  1  0 
[1, 0, 5, 1, 2, 1]
1   		 17
[0, 0, 0, 2, 6, 0]
 0  1  2  3  4  5 
Pit: 3
[OPPONENT]
 5  4  3  2  1  0 
[1, 0, 5, 1, 2, 0]
1   		 19
[0, 0, 0, 0, 7, 0]
 0  1  2  3  4  5 
[PLAYER]
 5  4  3  2  1  0 
[1, 0, 6, 2, 0, 0]
1   		 19
[0, 0, 0, 0, 7, 0]
 0  1  2  3  4  5 
Pit: 0
I'm sorry that was not a valid choice.
Pit: 4
[OPPONENT]
 5  4  3  2  1  0 
[1, 1, 7, 3, 1, 1]
1   		 20
[0, 0, 0, 0, 0, 1]
 0  1  2  3  4  5 
[PLAYER]
 5  4  3  2  1  0 
[2, 0, 7, 3, 1, 1]
1   		 20
[0, 0, 0, 0, 0, 1]
 0  1  2  3  4  5 
Pit: 5

 5  4  3  2  1  0 
[0, 0, 0, 0, 0, 0]
15   		 21
[0, 0, 0, 0, 0, 0]
 0  1  2  3  4  5 
You Won!
21:15
Play again? (y/n): n
Thank you for playing!
*/
