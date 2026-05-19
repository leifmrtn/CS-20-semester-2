/*

Program: exercise14_gameOfLife.java          Last Date of this Revision: May 10, 2026

Purpose: Conway's game of life

Author: Leif Martin, 
School: CHHS
Course: Computer Programming 20-1

*/

package Mastery;

import static Mastery.Cell.*;
import java.util.Scanner;
import java.util.Random;

/**
 * Defines the state and visual representation of an individual cell.
 */
enum Cell {
	ALIVE(true, "\u25A0"), // Filled block for living cells
	DEAD(false, "\u25A1"); // Empty block for dead cells

	private final boolean state;
	private final String colour;

	Cell(boolean state, String colour) {
		this.state = state;
		this.colour = colour;
	}

	// Accessor to check if the cell is currently living
	public boolean getState() {
		return state;
	}

	// Returns the character symbol for printing the cell to console
	public String toString() {
		return colour;
	}
}

/**
 * Manages the 2D arena grid where cells live, die, and interact.
 */
class Grid {

	Cell[][] grid; 

	// Updates the cell type at a specific coordinate
	void setCellState(int row, int col, Cell state) {
		grid[row][col] = state;
	}

	// Returns the width/height dimension of the square grid
	int getSize() {
		return grid.length;
	}

	// Direct check to see if a cell at a specific location is alive
	boolean isAlive(int row, int col) {
		return grid[row][col].getState();
	}

	/**
	 * Constructor allocating memory for the grid and populating it with dead cells.
	 */
	Grid(int gridSize) {
		grid = new Cell[gridSize][gridSize];

		for (int row = 0; row < grid.length; row++) {
			for (int col = 0; col < grid.length; col++) {
				grid[row][col] = Cell.DEAD;
			}
		}
	}

	/**
	 * Renders the full grid layout surrounded by vertical board boundaries.
	 */
	void toDisplay() {
		for (Cell[] row : grid) {
			System.out.print("| ");
			for (Cell cell : row) {
				System.out.printf(cell.toString() + " ");
			}
			System.out.println("| "); // Closes the row boundary
		}
		System.out.println(); // Spacing separation between consecutive cycles
	}

	/**
	 * Scans the 8 surrounding cells in a 3x3 window to count living neighbors.
	 * Keeps tracking safe by checking array index boundaries.
	 */
	int checkNeighbours(int row, int col) {
		int liveNeighbours = 0;

		// Offset loops from -1 to 1 look at rows/columns around the target cell
		for (int r = -1; r <= 1; r++) {
			for (int c = -1; c <= 1; c++) {
				if (r == 0 && c == 0) {
					continue; // Skips the center cell itself
				}

				int checkRow = row + r;
				int checkCol = col + c;

				// Boundary check: ensures coordinates fall inside the valid array dimensions
				if (checkRow >= 0 && checkRow < grid.length && 
						checkCol >= 0 && checkCol < grid.length) {

					if (grid[checkRow][checkCol].getState()) {
						liveNeighbours++;
					}
				}
			}
		}
		return liveNeighbours;
	}
}

/**
 * Orchestrates the application settings, user cell initialization, and animation timer loop.
 */
class GameOfLife {

	Scanner in = new Scanner(System.in);
	int cycleNum;
	Grid grid;

	// Prints the active board out to the user console
	void printGrid() {
		grid.toDisplay();
	}

	// Outputs the welcome splash text explaining John Conway's rule sets
	void start() {
		System.out.println("Welcome to the game of life.");
		System.out.println("If a cell has 3 neighbours and was previously not alive, it will come to life.");
		System.out.println("If a cell has 2 or 3 neighbours and was previously alive, remain alive.");
		System.out.println("In any other scenario, the cell will become/remain dead.");
		System.out.println("===========================================================================");
	}

	/**
	 * Populates the board grid map via automated random rolls or direct user coordinates.
	 */
	void selectingLiveCells() {
		Random rand = new Random();

		System.out.println("Would you like to place the cells (m)anually or (r)andomly?");
		String choice = in.next().toLowerCase();
		System.out.print("Number of cells: ");
		int numCells = in.nextInt();
		int row;
		int col;
		
		for (int i = 0; i < numCells; i++) {
			switch (choice) {
				case "r":
					// Randomly selects valid indices across the square map size
					grid.setCellState(rand.nextInt(grid.getSize()), rand.nextInt(grid.getSize()), ALIVE);
					break;
				case "m":
					System.out.print("Row: ");
					row = in.nextInt();
					System.out.print("Column: ");
					col = in.nextInt();
					grid.setCellState(row, col, ALIVE);
					grid.toDisplay(); // Displays progressive updates for manual tracking placements
					break;
				default:
					break;
			}
		}
		grid.toDisplay();
	}

	/**
	 * Configures global session structures and spins generations forward based on cycle limits.
	 */
	void playRound() throws InterruptedException {
		System.out.print("Grid size: ");
		int gridSize = in.nextInt();

		grid = new Grid(gridSize);
		grid.toDisplay(); 
		
		selectingLiveCells();
		
		System.out.print("How many cycles would you like to run? : ");
		int numCycles = in.nextInt();

		// Time progression animation system loop
		for (int i = 0; i < numCycles; i++) {
			System.out.println("Cycle: " + i);
			runCycle();
			Thread.sleep(250); // Pause duration in milliseconds to create movie-like playback effect
		}
	}

	/**
	 * Evaluates cells simultaneously against standard birth/survival rules, 
	 * drawing outcomes onto a secondary reference grid before swapping them.
	 */
	void runCycle() {
		int numN = 0;
		Grid nextGrid = new Grid(grid.getSize()); // Separate grid prevents modifying cells currently being scanned

		for (int row = 0; row < grid.getSize(); row++) {
			for (int col = 0; col < grid.getSize(); col++) {
				numN = grid.checkNeighbours(row, col);
				
				// Standard Rules: Alive if 3 neighbors always, or 2 neighbors if already alive
				if ((numN == 2 && grid.isAlive(row, col)) || numN == 3) {
					nextGrid.setCellState(row, col, Cell.ALIVE);
				} else {
					nextGrid.setCellState(row, col, Cell.DEAD);
				}
			}
		}
		
		grid = nextGrid; // Overwrite current generation state array references with the newly generated map
		grid.toDisplay();
	}
}

/**
 * Contains master environment initialization context loops and exit confirmation tracking.
 */
class exercise14_gameOfLife {
	public static void main(String[] args) throws InterruptedException {
		Scanner in = new Scanner(System.in);

		// Infinite game loop container block
		while (true) {
			GameOfLife game = new GameOfLife();
			game.start();
			game.playRound();
			
			System.out.print("Would you like to play another round? (y/n): ");
			String choice = in.next();
			if (choice.equalsIgnoreCase("n")) {
				break;
			}
		}

		System.out.print("Thank you for playing!");
	}
}
/*
  /// --- console output --- ///
Welcome to the game of life.
If a cell has 3 neighbours and was previously not alive, it will come to life.
If a cell has 2 or 3 neighbours and was previously alive, remain alive.
In any other scenario, the cell will become/remain dead.
===========================================================================
Grid size: 12
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 

Would you like to place the cells (m)anually or (r)andomly?
r
Number of cells: 100
| □ □ ■ □ ■ ■ ■ □ □ □ ■ □ | 
| □ □ ■ ■ ■ □ □ □ ■ ■ □ □ | 
| ■ □ □ □ □ □ □ □ □ □ ■ ■ | 
| ■ □ □ ■ ■ ■ □ □ ■ □ ■ ■ | 
| ■ ■ ■ □ □ ■ □ ■ □ □ ■ □ | 
| ■ □ ■ ■ ■ ■ ■ ■ □ ■ □ ■ | 
| ■ ■ ■ □ ■ ■ □ ■ ■ ■ ■ ■ | 
| ■ □ □ □ □ □ ■ □ □ □ ■ □ | 
| □ □ ■ ■ ■ ■ ■ □ □ ■ □ □ | 
| □ ■ □ ■ □ ■ □ ■ ■ □ □ □ | 
| ■ □ □ ■ □ ■ ■ □ ■ ■ ■ □ | 
| ■ □ □ ■ □ ■ ■ ■ □ □ ■ ■ | 

How many cycles would you like to run? : 25
Cycle: 0
| □ □ ■ □ ■ ■ □ □ □ ■ □ □ | 
| □ ■ ■ □ ■ □ □ □ □ ■ □ ■ | 
| □ ■ ■ □ □ ■ □ □ ■ □ □ ■ | 
| ■ □ ■ ■ ■ ■ ■ □ □ □ □ □ | 
| ■ □ □ □ □ □ □ ■ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ ■ | 
| ■ □ ■ □ □ □ □ □ □ □ □ ■ | 
| ■ □ □ □ □ □ □ □ □ □ □ ■ | 
| □ ■ ■ ■ □ □ □ □ ■ ■ □ □ | 
| □ ■ □ □ □ □ □ □ □ □ ■ □ | 
| ■ ■ □ ■ □ □ □ □ □ □ ■ ■ | 
| □ □ □ □ □ ■ □ ■ ■ □ ■ ■ | 

Cycle: 1
| □ ■ ■ □ ■ ■ □ □ □ □ ■ □ | 
| □ □ □ □ ■ □ □ □ ■ ■ □ □ | 
| ■ □ □ □ □ □ ■ □ □ □ ■ □ | 
| ■ □ ■ ■ ■ ■ ■ ■ □ □ □ □ | 
| □ ■ □ ■ ■ ■ ■ □ □ □ □ □ | 
| □ ■ □ □ □ □ □ □ □ □ □ □ | 
| □ ■ □ □ □ □ □ □ □ □ ■ ■ | 
| ■ □ □ ■ □ □ □ □ □ □ ■ □ | 
| ■ ■ ■ □ □ □ □ □ □ ■ ■ □ | 
| □ □ □ ■ □ □ □ □ □ □ ■ ■ | 
| ■ ■ ■ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ ■ ■ ■ | 

Cycle: 2
| □ □ □ ■ ■ ■ □ □ □ ■ □ □ | 
| □ ■ □ ■ ■ □ □ □ □ ■ ■ □ | 
| □ ■ □ □ □ □ ■ □ ■ ■ □ □ | 
| ■ □ ■ □ □ □ □ ■ □ □ □ □ | 
| ■ ■ □ □ □ □ □ ■ □ □ □ □ | 
| ■ ■ □ □ ■ ■ □ □ □ □ □ □ | 
| ■ ■ ■ □ □ □ □ □ □ □ ■ ■ | 
| ■ □ □ □ □ □ □ □ □ □ □ □ | 
| ■ ■ ■ ■ □ □ □ □ □ ■ □ □ | 
| □ □ □ ■ □ □ □ □ □ ■ ■ ■ | 
| □ ■ ■ □ □ □ □ □ □ ■ □ □ | 
| □ ■ □ □ □ □ □ □ □ □ ■ □ | 

Cycle: 3
| □ □ ■ ■ □ ■ □ □ □ ■ ■ □ | 
| □ □ □ ■ □ □ □ □ □ □ ■ □ | 
| ■ ■ □ ■ □ □ □ ■ ■ ■ ■ □ | 
| ■ □ ■ □ □ □ ■ ■ □ □ □ □ | 
| □ □ ■ □ □ □ ■ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ ■ □ □ □ □ □ □ □ □ □ | 
| □ □ □ ■ □ □ □ □ □ □ ■ □ | 
| ■ ■ ■ ■ □ □ □ □ □ ■ □ □ | 
| ■ □ □ ■ □ □ □ □ ■ ■ □ □ | 
| □ ■ ■ □ □ □ □ □ □ ■ □ ■ | 
| □ ■ ■ □ □ □ □ □ □ □ □ □ | 

Cycle: 4
| □ □ ■ ■ ■ □ □ □ □ ■ ■ □ | 
| □ ■ □ ■ □ □ □ □ □ □ □ ■ | 
| ■ ■ □ ■ □ □ ■ ■ ■ ■ ■ □ | 
| ■ □ ■ ■ □ □ ■ □ □ ■ □ □ | 
| □ ■ □ □ □ □ ■ ■ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ ■ □ □ □ □ □ □ □ □ | 
| ■ ■ □ ■ ■ □ □ □ ■ ■ ■ □ | 
| ■ □ □ ■ □ □ □ □ ■ ■ □ □ | 
| ■ □ □ ■ □ □ □ □ ■ ■ ■ □ | 
| □ ■ ■ □ □ □ □ □ □ □ □ □ | 

Cycle: 5
| □ □ ■ ■ ■ □ □ □ □ □ ■ □ | 
| ■ ■ □ □ □ □ □ ■ □ □ □ ■ | 
| ■ □ □ ■ ■ □ ■ ■ ■ ■ ■ □ | 
| ■ □ □ ■ □ ■ □ □ □ ■ ■ □ | 
| □ ■ ■ □ □ □ ■ ■ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ ■ ■ ■ □ □ □ □ ■ □ □ | 
| ■ ■ □ ■ ■ □ □ □ ■ □ ■ □ | 
| ■ □ □ ■ □ □ □ ■ □ □ □ □ | 
| ■ □ □ ■ □ □ □ □ ■ □ ■ □ | 
| □ ■ ■ □ □ □ □ □ □ ■ □ □ | 

Cycle: 6
| □ ■ ■ ■ □ □ □ □ □ □ □ □ | 
| ■ ■ □ □ □ ■ ■ ■ □ □ □ ■ | 
| ■ □ ■ ■ ■ ■ ■ ■ □ □ □ ■ | 
| ■ □ □ ■ □ ■ □ □ □ □ ■ □ | 
| □ ■ ■ □ □ □ ■ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ ■ □ □ □ □ □ □ □ □ | 
| □ ■ ■ □ ■ □ □ □ □ ■ □ □ | 
| ■ ■ □ □ □ □ □ □ ■ ■ □ □ | 
| ■ □ □ ■ □ □ □ ■ ■ □ □ □ | 
| ■ □ □ ■ □ □ □ □ ■ ■ □ □ | 
| □ ■ ■ □ □ □ □ □ □ ■ □ □ | 

Cycle: 7
| ■ ■ ■ □ □ □ ■ □ □ □ □ □ | 
| ■ □ □ □ □ □ □ ■ □ □ □ □ | 
| ■ □ ■ ■ □ □ □ ■ □ □ ■ ■ | 
| ■ □ □ □ □ □ □ ■ □ □ □ □ | 
| □ ■ ■ □ □ □ □ □ □ □ □ □ | 
| □ □ ■ □ □ □ □ □ □ □ □ □ | 
| □ □ ■ ■ □ □ □ □ □ □ □ □ | 
| ■ ■ ■ ■ □ □ □ □ ■ ■ □ □ | 
| ■ □ □ ■ □ □ □ ■ □ ■ □ □ | 
| ■ □ ■ □ □ □ □ ■ □ □ □ □ | 
| ■ □ □ ■ □ □ □ ■ □ ■ □ □ | 
| □ ■ ■ □ □ □ □ □ ■ ■ □ □ | 

Cycle: 8
| ■ ■ □ □ □ □ □ □ □ □ □ □ | 
| ■ □ □ ■ □ □ ■ ■ □ □ □ □ | 
| ■ □ □ □ □ □ ■ ■ ■ □ □ □ | 
| ■ □ □ ■ □ □ □ □ □ □ □ □ | 
| □ ■ ■ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| ■ □ □ □ ■ □ □ □ ■ ■ □ □ | 
| ■ □ □ ■ □ □ □ ■ □ ■ □ □ | 
| ■ □ ■ ■ □ □ ■ ■ □ □ □ □ | 
| ■ □ □ ■ □ □ □ ■ □ ■ □ □ | 
| □ ■ ■ □ □ □ □ □ ■ ■ □ □ | 

Cycle: 9
| ■ ■ □ □ □ □ □ □ □ □ □ □ | 
| ■ □ □ □ □ □ ■ □ ■ □ □ □ | 
| ■ ■ □ □ □ □ ■ □ ■ □ □ □ | 
| ■ □ ■ □ □ □ □ ■ □ □ □ □ | 
| □ ■ ■ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ ■ ■ □ □ | 
| ■ □ ■ ■ ■ □ ■ ■ □ ■ □ □ | 
| ■ □ ■ ■ ■ □ ■ ■ □ □ □ □ | 
| ■ □ □ ■ □ □ ■ ■ □ ■ □ □ | 
| □ ■ ■ □ □ □ □ □ ■ ■ □ □ | 

Cycle: 10
| ■ ■ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| ■ □ □ □ □ □ ■ □ ■ □ □ □ | 
| ■ □ ■ □ □ □ □ ■ □ □ □ □ | 
| □ ■ ■ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ ■ □ □ □ ■ ■ ■ □ □ | 
| □ □ ■ □ ■ □ ■ □ □ ■ □ □ | 
| ■ □ □ □ □ □ □ □ □ □ □ □ | 
| ■ □ □ □ ■ ■ ■ □ □ ■ □ □ | 
| □ ■ ■ □ □ □ □ ■ ■ ■ □ □ | 

Cycle: 11
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| ■ ■ □ □ □ □ □ □ □ □ □ □ | 
| □ ■ □ □ □ □ □ ■ □ □ □ □ | 
| ■ □ ■ □ □ □ □ ■ □ □ □ □ | 
| □ ■ ■ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ ■ □ □ □ | 
| □ □ □ ■ □ □ □ ■ ■ ■ □ □ | 
| □ □ □ ■ □ □ □ ■ □ ■ □ □ | 
| □ ■ □ ■ ■ □ ■ □ □ □ □ □ | 
| ■ □ □ □ □ ■ ■ ■ □ ■ □ □ | 
| □ ■ □ □ □ ■ ■ ■ ■ ■ □ □ | 

Cycle: 12
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| ■ ■ □ □ □ □ □ □ □ □ □ □ | 
| □ □ ■ □ □ □ □ □ □ □ □ □ | 
| ■ □ ■ □ □ □ □ □ □ □ □ □ | 
| □ ■ ■ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ ■ ■ ■ □ □ | 
| □ □ □ □ □ □ □ ■ □ ■ □ □ | 
| □ □ □ ■ □ □ ■ ■ □ ■ □ □ | 
| □ □ ■ ■ ■ □ □ □ □ □ □ □ | 
| ■ ■ ■ □ □ □ □ □ □ ■ □ □ | 
| □ □ □ □ □ ■ □ □ □ ■ □ □ | 

Cycle: 13
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ ■ □ □ □ □ □ □ □ □ □ □ | 
| ■ □ ■ □ □ □ □ □ □ □ □ □ | 
| □ □ ■ ■ □ □ □ □ □ □ □ □ | 
| □ ■ ■ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ ■ □ □ □ | 
| □ □ □ □ □ □ □ ■ □ ■ □ □ | 
| □ □ □ □ □ □ □ □ □ ■ ■ □ | 
| □ □ ■ ■ ■ □ ■ ■ □ □ □ □ | 
| □ □ □ □ ■ □ □ □ ■ □ □ □ | 
| □ ■ ■ □ ■ □ □ □ □ □ □ □ | 
| □ ■ □ □ □ □ □ □ □ □ □ □ | 

Cycle: 14
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ ■ □ □ □ □ □ □ □ □ □ □ | 
| □ □ ■ ■ □ □ □ □ □ □ □ □ | 
| □ □ □ ■ □ □ □ □ □ □ □ □ | 
| □ ■ ■ ■ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ ■ □ □ □ | 
| □ □ □ □ □ □ □ □ □ ■ ■ □ | 
| □ □ □ ■ □ □ ■ ■ □ ■ ■ □ | 
| □ □ □ ■ ■ ■ □ ■ ■ ■ □ □ | 
| □ ■ □ □ ■ □ □ ■ □ □ □ □ | 
| □ ■ ■ ■ □ □ □ □ □ □ □ □ | 
| □ ■ ■ □ □ □ □ □ □ □ □ □ | 

Cycle: 15
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ ■ □ □ □ □ □ □ □ □ □ | 
| □ □ ■ ■ □ □ □ □ □ □ □ □ | 
| □ ■ □ □ ■ □ □ □ □ □ □ □ | 
| □ □ ■ ■ □ □ □ □ □ □ □ □ | 
| □ □ ■ □ □ □ □ □ □ ■ □ □ | 
| □ □ □ □ □ □ □ ■ □ □ ■ □ | 
| □ □ □ ■ □ ■ ■ ■ □ □ □ □ | 
| □ □ ■ ■ □ ■ □ □ □ ■ ■ □ | 
| □ ■ □ □ □ ■ ■ ■ □ □ □ □ | 
| ■ □ □ ■ □ □ □ □ □ □ □ □ | 
| □ ■ □ ■ □ □ □ □ □ □ □ □ | 

Cycle: 16
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ ■ ■ □ □ □ □ □ □ □ □ | 
| □ ■ ■ ■ □ □ □ □ □ □ □ □ | 
| □ ■ □ □ ■ □ □ □ □ □ □ □ | 
| □ ■ ■ ■ □ □ □ □ □ □ □ □ | 
| □ □ ■ ■ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ ■ ■ □ □ □ | 
| □ □ ■ ■ □ ■ □ ■ ■ ■ ■ □ | 
| □ □ ■ ■ □ □ □ □ ■ □ □ □ | 
| □ ■ □ ■ □ ■ ■ □ □ □ □ □ | 
| ■ ■ □ □ ■ □ ■ □ □ □ □ □ | 
| □ □ ■ □ □ □ □ □ □ □ □ □ | 

Cycle: 17
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ ■ □ ■ □ □ □ □ □ □ □ □ | 
| □ ■ □ □ ■ □ □ □ □ □ □ □ | 
| ■ □ □ □ ■ □ □ □ □ □ □ □ | 
| □ ■ □ □ ■ □ □ □ □ □ □ □ | 
| □ ■ □ ■ □ □ □ □ □ □ □ □ | 
| □ □ □ □ ■ □ ■ ■ □ □ □ □ | 
| □ □ ■ ■ ■ □ ■ □ □ □ □ □ | 
| □ ■ □ □ □ ■ □ □ ■ □ □ □ | 
| ■ ■ □ ■ □ ■ ■ ■ □ □ □ □ | 
| ■ ■ □ ■ ■ □ ■ □ □ □ □ □ | 
| □ ■ □ □ □ □ □ □ □ □ □ □ | 

Cycle: 18
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ ■ □ □ □ □ □ □ □ □ □ | 
| ■ ■ ■ ■ ■ □ □ □ □ □ □ □ | 
| ■ ■ □ ■ ■ ■ □ □ □ □ □ □ | 
| ■ ■ ■ ■ ■ □ □ □ □ □ □ □ | 
| □ □ ■ ■ ■ ■ □ □ □ □ □ □ | 
| □ □ □ □ ■ □ ■ ■ □ □ □ □ | 
| □ □ ■ ■ ■ □ ■ □ □ □ □ □ | 
| ■ ■ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ ■ □ □ □ ■ □ □ □ □ | 
| □ □ □ ■ ■ □ ■ ■ □ □ □ □ | 
| ■ ■ ■ □ □ □ □ □ □ □ □ □ | 

Cycle: 19
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ ■ □ □ □ □ □ □ □ □ □ | 
| ■ □ □ □ □ ■ □ □ □ □ □ □ | 
| □ □ □ □ □ ■ □ □ □ □ □ □ | 
| ■ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ ■ □ □ □ □ □ | 
| □ □ □ □ □ □ ■ ■ □ □ □ □ | 
| □ ■ ■ ■ ■ □ ■ ■ □ □ □ □ | 
| □ ■ □ □ ■ □ □ □ □ □ □ □ | 
| □ □ ■ ■ ■ □ ■ ■ □ □ □ □ | 
| □ ■ □ ■ ■ □ ■ ■ □ □ □ □ | 
| □ ■ ■ ■ □ □ □ □ □ □ □ □ | 

Cycle: 20
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ ■ ■ □ □ □ □ | 
| □ □ ■ ■ □ □ □ □ □ □ □ □ | 
| □ ■ ■ ■ ■ □ ■ ■ □ □ □ □ | 
| □ ■ □ □ □ □ □ □ □ □ □ □ | 
| □ ■ □ □ □ □ ■ ■ □ □ □ □ | 
| □ ■ □ □ □ □ ■ ■ □ □ □ □ | 
| □ ■ □ ■ ■ □ □ □ □ □ □ □ | 

Cycle: 21
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ ■ □ □ ■ ■ □ □ □ □ □ □ | 
| □ ■ □ □ ■ □ □ □ □ □ □ □ | 
| ■ ■ □ ■ □ ■ □ □ □ □ □ □ | 
| ■ ■ ■ □ □ □ ■ ■ □ □ □ □ | 
| ■ ■ □ □ □ ■ ■ ■ □ □ □ □ | 
| □ □ ■ □ □ □ □ □ □ □ □ □ | 

Cycle: 22
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ ■ ■ □ □ □ □ □ □ | 
| □ ■ □ ■ □ □ □ □ □ □ □ □ | 
| □ □ □ ■ ■ ■ ■ □ □ □ □ □ | 
| □ □ □ □ ■ □ □ ■ □ □ □ □ | 
| ■ □ □ □ □ ■ □ ■ □ □ □ □ | 
| □ ■ □ □ □ □ ■ □ □ □ □ □ | 

Cycle: 23
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ ■ □ □ □ □ □ □ □ | 
| □ □ ■ ■ □ □ ■ □ □ □ □ □ | 
| □ □ ■ ■ □ ■ ■ □ □ □ □ □ | 
| □ □ □ ■ □ □ □ ■ □ □ □ □ | 
| □ □ □ □ □ ■ □ ■ □ □ □ □ | 
| □ □ □ □ □ □ ■ □ □ □ □ □ | 

Cycle: 24
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ □ □ □ □ □ □ □ □ □ | 
| □ □ □ ■ □ □ □ □ □ □ □ □ | 
| □ □ ■ □ □ □ ■ □ □ □ □ □ | 
| □ □ □ □ □ ■ ■ ■ □ □ □ □ | 
| □ □ ■ ■ □ ■ □ ■ □ □ □ □ | 
| □ □ □ □ □ □ □ ■ □ □ □ □ | 
| □ □ □ □ □ □ ■ □ □ □ □ □ | 

Would you like to play another round? (y/n): n
Thank you for playing!
 */