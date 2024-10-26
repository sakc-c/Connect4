package Connect4;

import java.io.File;
import java.util.Scanner;

public class Connect4 {
	public static void main(String[] args) {
		Grid board = new Grid();
		Scanner key = new Scanner(System.in);
		Player p1= null, p2=null;

		// Load game state at startup
		System.out.println("Do you want to load a saved game? (yes/no)");
		String loadGameResponse = key.nextLine();

		if (loadGameResponse.equalsIgnoreCase("yes")) {
			File savedGameFile = new File("GameState.csv");
			if (savedGameFile.exists()) {
				GameState gameState = new GameState();
				Player[] players = gameState.loadGameState(board);
				p1 = players[0];
				p2 = players[1];
			}
			else {
				System.out.println("No saved game state found. Setting up new players.");
				p1 = createPlayer(key, 'X', board);
				p2 = createPlayer(key, 'O', board);
			}
		} else if (loadGameResponse.equalsIgnoreCase("No")) {
			p1 = createPlayer(key, 'X', board);
			p2 = createPlayer(key, 'O', board);
		}

	// Play the game
	playGame(key, board, p1, p2);
	
    }

	private static Player createPlayer(Scanner key, char symbol, Grid board) {
		System.out.printf("Player %c: Please enter your name:\n", symbol);
		String name = key.nextLine();
		return new Player(name, symbol, board);
	}

	private static void playGame(Scanner key, Grid board, Player p1, Player p2) {
		while (true) {
			// Display the board
			System.out.println(board);

			// Player 1's turn
			if (!takeTurn(key, board, p1, p2)) {
				break; // Exit if Player 1 chooses to exit
			}

			// Check win or draw after Player 1's turn
			if (checkGameEnd(board, p1, p2)) {
				break;
			}

			// Player 2's turn
			System.out.println(board); // Display the board before Player 2's turn
			if (!takeTurn(key, board, p2, p1)) {
				break; // Exit if Player 2 chooses to exit
			}

			// Check win or draw after Player 2's turn
			if (checkGameEnd(board, p2, p1)) {
				break;
			}
		}
	}

	private static boolean takeTurn(Scanner key, Grid board, Player currentPlayer, Player opponent) {
		if (!currentPlayer.takeTurn(key)) {
			System.out.println("Exiting the game. Do you want to save your game? (yes/no)");
			if (key.nextLine().equalsIgnoreCase("yes")) {
				GameState.saveGameState(board, currentPlayer, opponent); // Call save method to save the current game
																			// state
			}
			return false; // Indicate exit
		}
		return true; // Indicate continue
	}

	private static boolean checkGameEnd(Grid board, Player currentPlayer, Player opponent) {
		if (board.checkWin(currentPlayer)) {
			System.out.printf("Player %s wins the game\n", currentPlayer);
			System.out.println(board);
			return true; // Game ends
		}
		if (board.isGridFull()) {
			System.out.println("It's a Draw");
			System.out.println(board);
			return true; // Game ends
		}
		return false; // Game continues
	}
}
