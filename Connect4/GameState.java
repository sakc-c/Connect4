package Connect4;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The GameState class is responsible for saving and loading the current game
 * state, including player details and the board configuration, to and from a
 * file named "GameState.txt".
 */

public class GameState {
	public void saveGameState(Grid grid, Player p1, Player p2) {

		try (FileWriter fw = new FileWriter("GameState.txt"); BufferedWriter bw = new BufferedWriter(fw)) {
			// write names and symbols of both players separated by comma on 2 lines.
			bw.write(p1.getName() + "," + p1.getSymbol() + "\n" + p2.getName() + "," + p2.getSymbol());
			// the string representation of the current state of the grid is saved.
			bw.write(grid.toString());
			System.out.println("Game state saved successfully.");
		} catch (IOException e) {
			// Catch any IOException that occurs during file operations
			System.out.println("Error saving game state: " + e.getMessage());
		}
	}

	/**
	 * Loads the game state from "GameState.txt", reconstructing player details and
	 * the game board.
	 * 
	 * @param grid the game board to be populated
	 * @return an array containing two Player objects (p1 and p2)
	 */
	public Player[] loadGameState(Grid grid) {
		Player p1 = null;
		Player p2 = null;
		try (FileReader fr = new FileReader("GameState.txt"); Scanner scanner = new Scanner(fr)) {
			p1 = loadPlayerDetails(scanner.nextLine(), grid);
			p2 = loadPlayerDetails(scanner.nextLine(), grid);
			scanner.nextLine(); // Skip the line containing column numbers
			grid.loadBoard(scanner); // Load the current state of the game board
			System.out.println("Game loaded successfully.");
		} catch (IOException e) {
			System.out.println("No saved game state found.");
		}
		return new Player[] { p1, p2 }; // Return an array containing both players
	}

	// creates a player object with name and symbol in the provided String
	private Player loadPlayerDetails(String playerLine, Grid board) {
		String[] data = playerLine.split(",");
		return new Player(data[0], data[1].charAt(0), board);
	}
}
