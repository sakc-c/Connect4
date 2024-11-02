package Connect4;

import java.util.Scanner;

/**
 * Extension: Save and Load Feature.
 * 
 * Players can exit mid-game by entering 'E'. If they choose to save, the
 * current game state—including player names, symbols, and the board String is
 * saved to a text file.
 *
 * Upon restarting, users can load the saved game or start anew. If loading,
 * player objects are instantiated from the saved data, and existing discs are
 * placed on the new board, allowing for continuation.
 */

public class Connect4 {
	public static void main(String[] args) {
		Grid board = new Grid();
		Scanner key = new Scanner(System.in);
		Player p1 = null, p2 = null;

		// Prompts user whether to load a saved game or start a new game.
		System.out.println("Do you want to load a saved game? (Y/N)");
		String ans = key.nextLine();
		char loadGame;

		if (ans.isEmpty()) {
			System.out.println("No input received, starting a new game");
			loadGame = 'N'; // set to N if no input
		} else {
			loadGame = Character.toUpperCase(ans.charAt(0));
		}

		if (loadGame == 'Y') {
			GameState gameState = new GameState();
			Player[] players = gameState.loadGameState(board);

			// If user wants to load a saved game, but loading the game state is
			// unsuccessful (players are null), prompt the user to input names and symbols
			// for a new game.
			if (players[0] == null || players[1] == null) {
				Player[] newPlayers = createPlayer(key, board);
				p1 = newPlayers[0];
				p2 = newPlayers[1];
			} else {
				p1 = players[0];
				p2 = players[1];
			}
		} else { // Start a new game if the input is anything other than 'y'.
			Player[] newPlayers = createPlayer(key, board);
			p1 = newPlayers[0];
			p2 = newPlayers[1];
		}

		// Main game loop continues until the user chooses to exit or the game ends with
		// a win or draw.
		while (true) {
			// Display the current board and allow Player 1 to take their turn.
			// Exit the loop if Player 1 chooses to exit (takeTurn method would return
			// false)
			System.out.println(board);
			if (!p1.takeTurn(key, p2)) {
				break;
			}
			// Check if the current player (p1) has won or if the last turn resulted in a
			// draw.
			if (checkGameEnd(board, p1, p2)) {
				break;
			}

			// The same checks and actions apply to Player 2.
			System.out.println(board);
			if (!p2.takeTurn(key, p1)) {
				break;
			}
			if (checkGameEnd(board, p2, p1)) {
				break;
			}
		}
	}

	/**
	 * Asks the users to input their name & symbol (for player 1) and returns
	 * instantiated Player objects for both users. Validation added - If only name
	 * is provided, prompts the user to provide both name and symbol.
	 */
	private static Player[] createPlayer(Scanner key, Grid board) {

		String userInput = " ";

		while (true) {
			System.out.println("Player 1: Please enter your name and symbol (X or O)");
			userInput = key.nextLine();
			String[] input = userInput.split(" ");

			// Check if input has at least 2 elements
			if (input.length < 2) {
				System.out.println("Error: Please provide both your name and symbol.");
				continue; // Prompt again
			}
			String name = input[0];
			char symbol = Character.toUpperCase(input[1].charAt(0));

			// to ensure symbol is either X or O
			if (symbol != 'X' && symbol != 'O') {
				System.out.println("Error: Please choose a valid symbol.");
				continue;
			}

			Player p1 = new Player(name, symbol, board);

			// Assign the leftover symbol to player 2
			char symbol2 = (symbol == 'X') ? 'O' : 'X';

			// Ask Player 2 to input name and create another player object
			System.out.printf("Player 2 (%c): Please enter your name\n", symbol2);
			userInput = key.nextLine();
			input = userInput.split(" ");
			String name2 = input[0];
			Player p2 = new Player(name2, symbol2, board); // Create player 2 as per input

			return new Player[] { p1, p2 };
		}
	}

	/**
	 * Checks if the current player has won or if the grid is full. Returns true if
	 * the game has ended, otherwise returns false to continue the game.
	 */
	private static boolean checkGameEnd(Grid board, Player currentPlayer, Player opponent) {
		if (board.checkWin(currentPlayer)) {
			System.out.printf("Player %s wins the game\n", currentPlayer);
			System.out.println(board);
			return true; // Winner, exit the game
		}
		if (board.isGridFull()) {
			System.out.println("It's a Draw");
			System.out.println(board);
			return true; // Draw, exit the game
		}
		return false; // Game has not ended, continue the game
	}
}
