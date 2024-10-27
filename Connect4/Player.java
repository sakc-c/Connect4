package Connect4;

import java.util.Scanner;

public class Player {
	private String name;
	private char symbol;
	private Grid board;

	public Player(String n, char s, Grid g) {
		name = n;
		symbol = s;
		board = g;
	}

	// getters for Symbol and Name
	public char getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

	// When player object is printed, display their name and (symbol) as a String
	public String toString() {
		return name + " (" + symbol + ")";
	}

	public boolean takeTurn(Scanner key, Player opponent) {
		// Prompt player to enter a column number or exit the game
		System.out.printf("Player %s (%c): Please enter column position (1-7) OR E to exit:", this.name, this.symbol);
		String columnPos = key.nextLine();

		// Check if player wants to exit the game
		if (columnPos.equals("E")) {
			System.out.println("Exiting the game. Do you want to save your game? (y/n)");
			// Save the game state if player chooses to do so
			if (key.nextLine().charAt(0) == 'y') {
				GameState g = new GameState();

				/**
				 * Save the current game state, passing the current player as the first argument
				 * (p1) This ensures that when the game is resumed, it will be the current
				 * player's turn.
				 */
				g.saveGameState(board, this, opponent);
			}
			else {
				System.out.println("Thanks for playing, Game Ended");
				return false;
			}
			return false; // to exit the game
		}

		// Attempt to drop a disc in the chosen column; if invalid, prompt again
		if (!board.dropDisc(this.symbol, columnPos)) {
			return takeTurn(key, opponent);
		}

		return true; // the game should continue
	}
}
