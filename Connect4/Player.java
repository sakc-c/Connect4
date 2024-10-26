package Connect4;

import java.util.Scanner;

public class Player {
  public String name;
  private char symbol;
  private Grid board;

  public Player(String n, char s, Grid g) {
    name = n;
    symbol = s;
    board = g;
  }

  public char getSymbol() {
    return symbol;
  }

  public String toString() {
    return name + " (" + symbol + ")";
  }

  public boolean takeTurn(Scanner key) {
    System.out.printf("Player %s (%c): Please enter column position (1-7) OR E to exit:", this.name, this.symbol);
    String columnPos = key.nextLine();

    if (columnPos.equals("E")) {
    	return false; //indicate the game should exit
    }
    if (!board.dropDisc(this.symbol,columnPos)) {
      return takeTurn(key);
    }
    return true; // the game should continue
  }
}
