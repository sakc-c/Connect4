package Connect4;

import java.util.Scanner;

public class Connect4 {

    public static void main(String[] args) {
        Grid board = new Grid();
        Scanner key = new Scanner(System.in);

        // Ask Player 1 to input and create a player object
        System.out.println("Player 1: Please enter your name and symbol (X or O)");
        String userInput = key.nextLine();
        String[] input = userInput.split(" ");
        String name = input[0];
        char symbol = input[1].charAt(0);
        Player p1 = new Player(name, symbol, board);

        // Assign the leftover symbol to player 2
        char symbol2 = (symbol == 'X') ? 'O' : 'X';

        // Ask Player 2 to input name and create another player object
        System.out.printf("Player 2 (%c): Please enter your name\n", symbol2);
        userInput = key.nextLine();
        input = userInput.split(" ");
        String name2 = input[0];
        Player p2 = new Player(name2, symbol2, board); // create player 2 as per input

        // Play the game
        while (true) {
            // Display the board
            System.out.println(board);

            // Player 1's turn
            p1.takeTurn(key);
            if (board.checkWin(p1)) {
                System.out.printf("Player %s wins the game\n", p1);
                System.out.println(board);
                break; // Exit if Player 1 wins
            }
            if (board.isGridFull()) {
                System.out.println("It's a Draw");
                System.out.println(board);
                break; // Exit if the grid is full
            }

            // Display the board again
            System.out.println(board);

            // Player 2's turn
            p2.takeTurn(key);
            if (board.checkWin(p2)) {
                System.out.printf("Player %s wins the game\n", p2);
                System.out.println(board);
                break; // Exit if Player 2 wins
            }
            if (board.isGridFull()) {
                System.out.println("It's a Draw");
                System.out.println(board);
                break; // Exit if the grid is full
            }
        }

        key.close();
    }
}

