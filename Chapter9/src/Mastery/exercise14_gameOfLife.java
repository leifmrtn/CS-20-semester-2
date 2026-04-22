/*

Program: exercise14_gameOfLife.java          Last Date of this Revision: March 18, 2026

Purpose: conway's game of life

Author: Leif Martin, 
School: CHHS
Course: Computer Programming 20-1

*/

package Mastery;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class exercise14_gameOfLife {
	static int gridSize = 10;
	static boolean[][] grid;
	static boolean[][] nextCycleGrid;
	static int cycleNum = 0;
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {
		String choice = "y";
		// Starting
		System.out.println("Welcome to the game of life.");
		System.out.println("If a cell has 3 neighbours and was previously not alive, it will come to life.");
		System.out.println("If a cell has 2 or 3 neighbours and was previously alive, remain alive.");
		System.out.println("In any other scenario, the cell will become/remain dead.");
		System.out.print("\n");
		System.out.println("How large would you like the grid to be? ");
		gridSize = in.nextInt();
		grid = new boolean[gridSize][gridSize];
		
		displayGrid();
		placeCells();
		// Cycle run loop
		while (choice.equals("y")) {
			System.out.print("\n");
			System.out.println("How many cycles would you like to run? ");
			runCycle(in.nextInt());
			
			System.out.println("Would you like to run more cycles? (y,n) ");
			choice = in.next();
		}
		System.out.println("Thank you for playing!");
	}
	public static void displayGrid() {
		int count = 0;		
		System.out.print("\n");
		for(int r = 0; r < gridSize; r ++) { // for num rows
			for(int c = 0; c < gridSize; c ++) { // for num columns
				if (count == gridSize) {//completed a row/col
					count = 0;
					System.out.print("\n");
				}
				if(grid[r][c]) { // Alive
					System.out.print("X ");
				}
				else { // No cell
					System.out.print("O ");
				}
				count ++;
			}
		}
	}
	public static void placeCells() {
		System.out.print("\n");
		int row;
		int col;		
		//choosing how many cells to place and where/how they're placed
		System.out.println("How many cells would you like to place? ");
		int startingCells = in.nextInt();
		System.out.println("Would you like to place the cells (r)andomly, (m)anually? ");
		String placementMethod = in.next();

		if (placementMethod.equals("r")) {
			int count = 0;
			int randRow = 0;
			int randCol = 0;
			Random rand = new Random();
			while(count < startingCells) {
				randRow = rand.nextInt(gridSize);
				randCol = rand.nextInt(gridSize);
				if (grid[randRow][randCol] == false) {
					grid[randRow][randCol] = true;
					count++;
				}
			}
			displayGrid();
		}
		else {
			for(int i = 0; i < startingCells; i++) {
			row = 0;
			col = 0;
			System.out.println("where would you like to place the cell?");
			System.out.print("[Row]: ");
			row = in.nextInt()-1;
			System.out.print("[Column]: ");
			col = in.nextInt()-1;
			grid[row][col] = true;
			displayGrid();
			}
		}
	}
	public static void runCycle(int numCycles) {
		for (int i = 0; i < numCycles; i++) {
			// delays screen so it isnt overwhelming for user
			try {
			Thread.sleep(1500);
			} catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
			}
			nextCycleGrid = new boolean[gridSize][gridSize];
			for (int row = 0; row < gridSize; row ++) { // for each row
				for(int col = 0; col < gridSize; col ++) { // for each column
					int neighbours = countNeighbours(row, col);
					
					if (grid[row][col]) {
						if (neighbours == 2 || neighbours == 3) { // 2 or 3 neighbours
								nextCycleGrid[row][col] = true; // cell lives		
						}
						else {
							nextCycleGrid[row][col] = false;
						}
					}
					else { // dead cell
						if (neighbours == 3) { // comes to life
							nextCycleGrid[row][col] = true;
						}
						else { // stays dead
							nextCycleGrid[row][col] = false;
						}
					}
				}
				//System.out.println("it ran something"); // debug print
			}
			grid = nextCycleGrid;
			cycleNum++;
			displayGrid();
			System.out.println("\nCycle: " + cycleNum);
		}
	}
	public static int countNeighbours(int row, int col) {

		int liveNeighbours = 0;
		
		for (int i = -1; i <= 1; i ++) { // for 3x row
			for(int j = -1; j <= 1; j ++) { // for 3x columns
				
				if (i == 0 && j == 0) continue;
				
				int neighbourRow = row + i;
				int neighbourCol = col + j;	
				
				if(neighbourRow >= 0 && neighbourRow < gridSize && neighbourCol >= 0 && neighbourCol < gridSize) {
					if (grid[neighbourRow][neighbourCol]) {
						liveNeighbours ++;
					}
				}
			}
		}
	return liveNeighbours;
	}
}



/*
/// --- console output --- ///

*/








































/* Screen Dump
 
Paste the output of your code here
 
 */