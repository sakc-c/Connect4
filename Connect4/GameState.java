package Connect4;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GameState {
	public static void saveGameState(Grid grid, Player p1, Player p2) {
		
		try (FileWriter fw = new FileWriter("GameState.csv"); BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write(p1.name + "," + p1.getSymbol() + "\n" + p2.name + "," + p2.getSymbol());
			bw.write(grid.toString());
			System.out.println("Game state saved successfully.");
		} catch (IOException e) {
			System.out.println("Error saving game state: " + e.getMessage());
		}
	}

	public Player[] loadGameState(Grid grid) {
		Player p1=null;
		Player p2=null;
		try (FileReader fr = new FileReader("GameState.csv"); Scanner scanner = new Scanner(fr)) {
			p1 = loadPlayerDetails(scanner.nextLine(), grid);
			p2 = loadPlayerDetails(scanner.nextLine(), grid);
			scanner.nextLine(); // Skip header line
			grid.loadBoard(scanner);
			System.out.println("Game loaded successfully.");
		} catch (FileNotFoundException e) {
			System.out.println("No saved game state found.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Player[]{p1, p2}; // Return an array containing both players
	}

	private Player loadPlayerDetails(String playerLine, Grid board) {
		String[] data = playerLine.split(",");
		return new Player(data[0], data[1].charAt(0), board);
	}
}
