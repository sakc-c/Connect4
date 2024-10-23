package Connect4;

public class Grid {
	private Disc[][] board; // 2D array that holds Disc objects

	public Grid() {
		// Initialise the board
		board = new Disc[6][7];
	}

	public boolean dropDisc(char symbol, int column) {

		// ensure a valid column input
		if (column < 1 || column > 7) {
			System.out.println("Move not valid, please select a column between 1-7");
			return false;
		}

		// check for the first empty starting from bottom
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

	public boolean checkWin(Player p) {
		Disc dummyDisc = new Disc(p.getSymbol(), -1, -1); // position does not matter, just want to compare the symbol
															// of
		// players disc

		if (checkRow(dummyDisc) || checkCol(dummyDisc) || checkAscDiag(dummyDisc) || checkDescDiag(dummyDisc)) {
			return true;
		}
		System.out.println("The game is on-going");
		return false;
	}

	public boolean checkRow(Disc d) {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < 4; col++) { // not enough columns left to make a group of 4 after checking till 3
				if (d.equals(board[row][col]) && d.equals(board[row][col + 1]) && d.equals(board[row][col + 2])
						&& d.equals(board[row][col + 3])) {
					return true; // Win detected
				}
			}
		}
		return false; // No win found
	}

	public boolean checkCol(Disc d) {
		for (int col = 0; col < 7; col++) {
			for (int row = 0; row < 3; row++) {
				if (d.equals(board[row][col]) && d.equals(board[row + 1][col]) && d.equals(board[row + 2][col])
						&& d.equals(board[row + 3][col])) {
					return true; // Win detected
				}
			}
		}
		return false; // No win found
	}

	public boolean checkAscDiag(Disc d) {
		for (int row = 5; row > 2; row--) { // Start from row 5 down to row 3
			for (int col = 0; col < 4; col++) { // Columns 0 to 3
				if (d.equals(board[row][col]) && d.equals(board[row - 1][col + 1]) && d.equals(board[row - 2][col + 2])
						&& d.equals(board[row - 3][col + 3])) {
					return true; // Found a winning diagonal
				}
			}
		}
		return false; // No win found
	}

	public boolean checkDescDiag(Disc d) {
		for (int row = 0; row < 3; row++) { // Start from row 0 to row 2
			for (int col = 0; col < 4; col++) { // Columns 0 to 3
				if (d.equals(board[row][col]) && d.equals(board[row + 1][col + 1]) && d.equals(board[row + 2][col + 2])
						&& d.equals(board[row + 3][col + 3])) {
					return true; // Found a winning diagonal
				}
			}
		}
		return false; // No win found
	}

	public boolean isGridFull() {
		for (int col = 0; col < board[0].length; col++) { // Iterate through the top row
			if (board[0][col] == null) {
				return false;
			}
		}
		return true; // If no empty spaces are found at the top, the grid is full
	}

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
				String symbol = (board[row][col] != null) ? board[row][col].toString() : " ";
				result += String.format("| %s ", symbol);
			}
			result += "|\n"; // Close the row with a pipe and move to the next line
		}

		return result;
	}
}
