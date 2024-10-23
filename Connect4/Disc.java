package Connect4;

public class Disc {
	char symbol; // 'X' or 'O' for players' discs
	int row;
	int column;

	public Disc(char symbol, int row, int col) {
		this.symbol = symbol;
		this.row = row;
		this.column = col;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!(o instanceof Disc)) {
			return false;
		} else {
			Disc other = (Disc) o;
			if (this.symbol == other.symbol) {
				return true;
			} else {
				return false;
			}
		}
	}

	public String toString() {
		return String.format("%c", symbol);
	}
}