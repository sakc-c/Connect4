package Connect4;

import java.util.Scanner;

public class Grid {
	private Disc[][] board; // 2D array that holds Disc objects

	public Grid() {
		// Initialise the board
		board = new Disc[6][7];
	}

	/**
	 * Attempts to place a disc in the specified column.
	 * 
	 * If the column input is valid, the disc is placed on the board, and the method
	 * returns true. If the input is invalid (not an integer or out of range), it
	 * returns false. This feedback informs the player's takeTurn method whether
	 * their attempt to drop the disc failed, prompting them to try again if
	 * necessary.
	 */
	public boolean dropDisc(char symbol, String columnInput) {

		int column;

		// Parse the column input and handle invalid input (not an integer)
		try {
			column = Integer.parseInt(columnInput);
		} catch (NumberFormatException e) {
			System.out.println("Please enter a valid column number between 1 and 7.");
			return false; // Return false if parsing fails
		}

		// Check if the column entered is within valid range
		if (column < 1 || column > 7) {
			System.out.println("Move not valid, please select a column between 1-7");
			return false;
		}

		// check for the first empty row starting from bottom of the column
		for (int row = board.length - 1; row >= 0; row--) {
			if (board[row][column - 1] == null) {
				Disc disc = new Disc(symbol, row, column - 1);
				board[row][column - 1] = disc; // Place the disc on the board
				System.out.println("Move valid");
				return true;
			}
		}

		// If we reach here, the column is full
		System.out.println("Move not valid, column is already full");
		return false;
	}

	/**
	 * Check for a win condition based on the player's disc symbol. Dummy disc
	 * created as position does not matter, just want to compare the symbol of
	 * players disc with the discs on the board.
	 */

	public boolean checkWin(Player p) {

		Disc dummyDisc = new Disc(p.getSymbol(), -1, -1);

		if (checkRow(dummyDisc) || checkCol(dummyDisc) || checkAscDiag(dummyDisc) || checkDescDiag(dummyDisc)) {
			return true;
		}
		System.out.println("The game is on-going");
		return false;
	}

	/**
	 * Check for a win in rows.
	 */
	public boolean checkRow(Disc d) {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < 4; col++) { // not enough columns left to make a group of 4 after checking till 3
				if (d.equals(board[row][col]) && d.equals(board[row][col + 1]) && d.equals(board[row][col + 2])
						&& d.equals(board[row][col + 3])) {
					return true; // Win detected
				}
			}
		}
		return false; // Win not detected
	}

	/**
	 * Check for a win in columns.
	 */
	public boolean checkCol(Disc d) {
		for (int col = 0; col < 7; col++) {
			for (int row = 0; row < 3; row++) {
				if (d.equals(board[row][col]) && d.equals(board[row + 1][col]) && d.equals(board[row + 2][col])
						&& d.equals(board[row + 3][col])) {
					return true; // Win detected
				}
			}
		}
		return false; // Win not detected
	}

	/**
	 * Check for a winning diagonal (ascending).
	 */
	public boolean checkAscDiag(Disc d) {
		for (int row = 5; row > 2; row--) { // Start from row 5 down to row 3
			for (int col = 0; col < 4; col++) { // Columns 0 to 3
				if (d.equals(board[row][col]) && d.equals(board[row - 1][col + 1]) && d.equals(board[row - 2][col + 2])
						&& d.equals(board[row - 3][col + 3])) {
					return true; // Win detected
				}
			}
		}
		return false; // Win not detected
	}

	public boolean checkDescDiag(Disc d) {
		for (int row = 0; row < 3; row++) { // Start from row 0 to row 2
			for (int col = 0; col < 4; col++) { // Columns 0 to 3
				if (d.equals(board[row][col]) && d.equals(board[row + 1][col + 1]) && d.equals(board[row + 2][col + 2])
						&& d.equals(board[row + 3][col + 3])) {
					return true; // Win detected
				}
			}
		}
		return false; // Win not detected
	}

	public boolean isGridFull() {
		for (int col = 0; col < board[0].length; col++) { // Iterate through the top row
			if (board[0][col] == null) {
				return false;
			}
		}
		return true; // If no empty spaces are found at the top, the grid is full
	}

	/**
	 * When the Grid is printed, convert the grid to a string representation for
	 * display.
	 */
	public String toString() {
		String result = "\n";

		// Add column headers with leading space
		result += "    ";
		for (int colHeader = 0; colHeader < board[0].length; colHeader++) {
			result += String.format("%-4d", colHeader + 1);
		}
		result += "\n"; // Move to the next line after column headers

		// Add rows of the board with row number
		for (int row = 0; row < board.length; row++) {
			result += String.format("%-2d", row + 1);
			for (int col = 0; col < board[row].length; col++) {
				/**
				 * if position not null then place the symbol, if null then place a space
				 * character to avoid the null pointer exception when printing the board
				 */
				String symbol = (board[row][col] != null) ? board[row][col].toString() : " ";
				result += String.format("| %s ", symbol);
			}
			result += "|\n"; // Close the row with a | and move to the next line
		}

		return result;
	}

	/**
	 * Loads the board state from a string representation saved in a text file.
	 * Each character represents a disc's symbol (either 'X' or 'O').
	 * Discs are placed on the board to reconstruct the previous game state, allowing players
	 * to continue from where they left off.
	 */
	public void loadBoard(Scanner scanner) {
		int row = 0;
		while (scanner.hasNextLine() && row < board.length) {
			/**
			 * Splits the line at each '|' character. Uses "\\|" to treat '|' as a literal
			 * instead of a special character.
			 */
			String[] data = scanner.nextLine().split("\\|");

			// Iterate over each cell in the line to place discs
			for (int col = 1; col < data.length - 1; col++) { // Start from index 1 to skip row numbers
				String cell = data[col].trim();
				if (cell.equals("X") || cell.equals("O")) {
					board[row][col - 1] = new Disc(cell.charAt(0), row, col - 1); // Place disc on the board
				}
			}
			row++;
		}
	}
}
