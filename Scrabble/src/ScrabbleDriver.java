import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScrabbleDriver {

	public static void main(String[] args) throws IOException {
		Tile[][] newBoard = new Tile[15][15];
		Board game = new Board(2);
		for (int row = 0; row < 15; row++) {
			for (int col = 0; col < 15; col++) {
				newBoard[row][col] = new GenericTile();
			}
		}
		Letter[] testLetter = { new Letter('a', 1), new Letter('d', 1), new Letter('e', 1), new Letter('t', 1),
				new Letter('f', 1), new Letter('j', 1), new Letter('l', 1) };
		Letter[] testLetter2 = new Letter[7];
		System.out.println();
		System.out.print("print hand: ");
		game.getCurrentPlayer().printHand();
		System.out.println();
		newBoard[1][2].setLetter(new Letter('a', 1));
		newBoard[1][3].setLetter(new Letter('b', 2));
		game.playWord(newBoard, testLetter);
		//game.printBoard();
		System.out.println();
		for (int row = 0; row < 15; row++){
			for (int col = 0; col < 15; col++){
				if (newBoard[row][col].isEmpty())
					System.out.print(".");
				else
					
					System.out.print("t");
			}
			System.out.println();
		}
		System.out.println("player: " + game.getCurrentPlayer());
		System.out.print("print hand: ");
		game.getCurrentPlayer().printHand();
		System.out.println();
		System.out.println("-------");
		System.out.println(game.getTurn());
		System.out.println();
		newBoard[1][4].setLetter(new Letter('a', 4));
		newBoard[1][5].setLetter(new Letter('b', 2));
		System.out.println();
		for (int row = 0; row < 15; row++){
			for (int col = 0; col < 15; col++){
				if (newBoard[row][col].isEmpty())
					System.out.print(".");
				else
					
					System.out.print("t");
			}
			System.out.println();
		}
		game.playWord(newBoard, new Letter[7]);
		game.printBoard();
		System.out.println(game.getTurn());
		System.out.println();
		System.out.println();
		game.printScores();
	}

}
