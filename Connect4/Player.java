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

  public char getSymbol() {
    return symbol;
  }

  public String toString() {
    return name + " (" + symbol + ")";
  }

  public void takeTurn(Scanner key) {
    System.out.printf("Player %s (%c): Please enter column position (1-7):", this.name, this.symbol);
    int columnPos = key.nextInt();
    key.nextLine();

    if (!board.dropDisc(this.symbol,columnPos)) {
      System.out.println("Invalid move, please try again");
      takeTurn(key);
    }
  }
}
