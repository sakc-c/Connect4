package Connect4;

import java.util.Scanner;

public class Player {
	private String name;
	private char symbol;
	private Grid board;

	public Player(String name, char symbol, Grid grid) {
		this.name = name;
		this.symbol = symbol;
		this.board = grid;
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

	/**
	 * Prompt player to enter a column number or exit the game. If the player
	 * chooses to exit, they are asked if they want to save the game. The current
	 * game state is saved if they choose to do so. If an invalid column is entered,
	 * prompt again. The game continues if a valid column is entered.
	 *
	 * @param key Scanner for user input
	 * @param opponent player
	 * @return true if the game should continue, false if the game ends
	 */
	public boolean takeTurn(Scanner key, Player opponent) {
		System.out.printf("Player %s (%c): Please enter column position (1-7) OR E to exit:", this.name, this.symbol);
		String columnPos = key.nextLine();

		if (Character.toLowerCase(columnPos.charAt(0)) == 'e') {
			System.out.println("Exiting the game. Do you want to save your game? (Y/N)");
			if (Character.toUpperCase(key.nextLine().charAt(0)) == 'Y') {
				GameState g = new GameState();

				// Save the game state, passing the current player as the first argument (p1)
				// Ensures that when the game is resumed, it will be the current player's turn.

				g.saveGameState(board, this, opponent);
			} else {
				// any other input except y will exit the game without saving
				System.out.println("Thanks for playing, Game Ended");
				return false;
			}
			return false; // to exit the game
		}

		if (!board.dropDisc(this.symbol, columnPos)) {
			return takeTurn(key, opponent);
		}

		return true; // the game should continue
	}
}
