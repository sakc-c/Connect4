package Connect4;

/**
 * Represents a disc in the Connect 4 game. Each disc has a symbol, a row, and a
 * column indicating its position on the grid.
 */
public class Disc {
	private char symbol;
	private int row;
	private int column;

	public Disc(char symbol, int row, int col) {
		this.symbol = symbol;
		this.row = row;
		this.column = col;
	}

	// getters for private variables
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	// When printed, the symbol of the disc will be displayed.
	public String toString() {
		return String.format("%c", symbol);
	}

	/**
	 * Compares this Disc object to another object for equality. Two Disc objects
	 * are considered equal if they have the same symbol.
	 */
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Disc)) {
			return false;
		} else {
			Disc other = (Disc) o;
			if (this.symbol == other.symbol) {
				return true; // Return true if symbols of both discs are equal
			} else {
				return false;
			}
		}
	}
}