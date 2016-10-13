
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Board {

	private String[] dictionary;
	private List<Letter> letterBag;
	private Tile[][] board;
	private Scanner in;
	private int skipCount;
	private int turnCount;
	private Player[] players;
	private final boolean AM_DEBUGGING = true;

	/**
	 * instantiates the players, letter bag, board, turn count, skip count and
	 * dictionary
	 * 
	 * also draws letters for each player
	 * 
	 * @param playerCount
	 * @throws IOException
	 */
	public Board(int playerCount) throws IOException {
		board = new Tile[15][15];
		turnCount = 0;
		skipCount = 0;

		letterBag = new ArrayList<Letter>();
		for (int k = 1; k <= 12; k++) {
			if (k <= 1) {
				letterBag.add(new Letter('k', 5));
				letterBag.add(new Letter('j', 8));
				letterBag.add(new Letter('x', 8));
				letterBag.add(new Letter('q', 10));
				letterBag.add(new Letter('z', 10));
			} // still need to add blank letters later
			if (k <= 2) {
				letterBag.add(new Letter('b', 3));
				letterBag.add(new Letter('c', 3));
				letterBag.add(new Letter('m', 3));
				letterBag.add(new Letter('p', 3));
				letterBag.add(new Letter('f', 4));
				letterBag.add(new Letter('h', 4));
				letterBag.add(new Letter('v', 4));
				letterBag.add(new Letter('w', 4));
				letterBag.add(new Letter('y', 4));
			}
			if (k <= 3) {
				letterBag.add(new Letter('g', 2));
			}
			if (k <= 4) {
				letterBag.add(new Letter('l', 1));
				letterBag.add(new Letter('s', 1));
				letterBag.add(new Letter('u', 1));
				letterBag.add(new Letter('d', 2));
			}
			if (k <= 6) {
				letterBag.add(new Letter('n', 1));
				letterBag.add(new Letter('r', 1));
				letterBag.add(new Letter('t', 1));
			}
			if (k <= 8) {
				letterBag.add(new Letter('o', 1));
			}
			if (k <= 9) {
				letterBag.add(new Letter('a', 1));
				letterBag.add(new Letter('i', 1));
			}
			if (k <= 12) {
				letterBag.add(new Letter('e', 1));
			}
		}

		players = new Player[playerCount];
		for (int i = 0; i < playerCount; i++) {
			players[i] = (new Player("Player" + i));
			giveLetters(i);
		}

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				board[row][col] = new GenericTile();
			}
		}
		try {
			in = new Scanner(new File("dictionary.txt"));
			LineNumberReader lnr = new LineNumberReader(new FileReader(new File("dictionary.txt")));
			lnr.skip(Long.MAX_VALUE);
			int countLines = (lnr.getLineNumber() + 1);
			lnr.close();
			dictionary = new String[countLines];
			int counter = 0;
			do {
				dictionary[counter] = in.nextLine();
				counter++;
			} while (in.hasNextLine());
		} catch (FileNotFoundException e) {
			System.out.println("gdi vinh");
		}
	}

	/**
	 * takes in a 2D array, and adds the score of the unique
	 * 
	 * @param inBoard
	 */
	public void playWord(Tile[][] inBoard, Letter[] handAfterPlay) {

		/*
		 * if (!isValidWord(board, inBoard)){ System.out.println("INVALID WORD"
		 * ); return; }
		 */
		int score = 0;
		int wordMult = 1;
		boolean isWordMult = false;
		for (int row = 0; row < 15; row++) {
			for (int col = 0; col < 15; col++) {
				if (board[row][col].isEmpty() && !inBoard[row][col].isEmpty()) {
					score += inBoard[row][col].getTileScore();
					if (AM_DEBUGGING)
						System.out.println(score);
					if (inBoard[row][col].isWordMult()) {
						isWordMult = true;
						if (inBoard[row][col].getFactor() > wordMult) {
							wordMult = inBoard[row][col].getFactor();
						}
					}
				}
			}
			if (AM_DEBUGGING)
				System.out.println(" ");
		}
		if (isWordMult) {
			score *= wordMult;
		}
		if (AM_DEBUGGING)
			System.out.println("score : " + score);
		getCurrentPlayer().addPoints(score);
		for (int row = 0; row < 15; row++) {
			for (int col = 0; col < 15; col++) {
				board[row][col].setLetter(inBoard[row][col].getLetter());
			}
		}
		getCurrentPlayer().removeLetters(handAfterPlay);
		giveLetters();
		System.out.println(getCurrentPlayer());
		getCurrentPlayer().printHand();
		turnCount++;
		skipCount = 0;
	}

	/**
	 * skips player's turn and increments the skip counter
	 */
	public void skipTurn() {
		turnCount++;
		skipCount++;
	}

	/**
	 * prints out the board
	 */
	public void printBoard() {
		for (Tile[] row : board) {
			for (Tile col : row) {
				System.out.print(col.getLetter());
			}
			System.out.println();
		}
	}

	/**
	 * returns true if game should end
	 * 
	 * @return true if game should end
	 */
	public boolean endGame() {
		if (skipCount >= 6) {
			return true;
		} else if (letterBag.size() == 0) {
			for (Player x : players) {
				if (x.getLetters() == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public void printWord(int index) {
		System.out.println(dictionary[index]);
	}

	/**
	 * returns the current player
	 * 
	 * @return current player
	 */
	public Player getCurrentPlayer() {
		return players[turnCount % players.length];
	}

	public Player getPlayer(int x) {
		return players[x];
	}

	/**
	 * returns the turn count
	 * 
	 * @return turn
	 */
	public int getTurn() {
		return turnCount;
	}

	public void printScores() {
		for (int i = 0; i < players.length; i++) {
			System.out.print("Player " + i + ": ");
			System.out.println(players[i].getPoints());
		}
	}

	/**
	 * returns if the word is valid
	 * 
	 * @param word
	 * @return validity of word
	 */
	private boolean binary(String word, int first, int last) {
		int mid = (first + last) / 2;
		if (last == first) {
			if (dictionary[first].equals(word)) {
				return true;
			} else {
				return false;
			}
		} else {
			if (dictionary[mid].equals(word)) {
				return true;
			} else if (dictionary[mid].compareTo(word) > 0) {
				return binary(word, mid + 1, last);
			} else if (dictionary[mid].compareTo(word) < 0) {
				return binary(word, first, mid - 1);
			} else {
				return false;
			}
		}
	}

	/**
	 * Wrap method for binary search with helper method binary
	 * 
	 * @param word
	 */

	private boolean isValidWord(String word) {
		return binary(word, 0, dictionary.length - 1);
	}

	/**
	 * precondition: newBoard is different than oldBoard
	 * 
	 * @param oldBoard
	 * @param newBoard
	 * @return
	 */
	private boolean isValidWord(Tile[][] oldBoard, Tile[][] newBoard) {
		int first = 10, last = 0, constant = 0;
		if (isHorizontal(oldBoard, newBoard)) {
			for (int row = 0; row < 15; row++) {
				for (int col = 0; col < 15; col++) {
					if (oldBoard[row][col].isEmpty() && !newBoard[row][col].isEmpty()) {
						if (first > col) {
							first = col;
						}
						if (last < col) {
							last = col;
						}
						constant = row;
					}
				}
			}
			ArrayList<Letter> word = new ArrayList<Letter>();
			for (int i = first; i < last; i++) {
				word.add(board[constant][i].getLetter());
			}
			return isValidWord(wordToString(word));
		} else {
			for (int row = 0; row < 15; row++) {
				for (int col = 0; col < 15; col++) {
					if (oldBoard[row][col].isEmpty() && !newBoard[row][col].isEmpty()) {
						if (first > row) {
							first = row;
						}
						if (last < row) {
							last = row;
						}
						constant = col;
					}
				}
			}
			ArrayList<Letter> word = new ArrayList<Letter>();
			for (int i = first; i < last; i++) {
				word.add(board[i][constant].getLetter());
			}
			return isValidWord(wordToString(word));
		}
	}

	/**
	 * counts the score of the word and returns it
	 * 
	 * @param word
	 * @return word score
	 */
	private int countScore(List<Letter> word, List<Tile> tile) {
		boolean wordMult = false;
		int factor = 1;
		int score = 0;
		for (int i = 0; i < word.size() && i < tile.size(); i++) {
			if (tile.get(i).getMultiplier().equals("word")) {
				wordMult = true;
				factor = tile.get(i).getFactor();
			}
			if (tile.get(i).getMultiplier().equals("letter")) {
				score += word.get(i).getPoint() * tile.get(i).getFactor();
			} else {
				score += word.get(i).getPoint();
			}
		}
		if (wordMult) {
			score *= factor;
		}
		return score;
	}

	/**
	 * gives letters to current player using getCurrentPlayer()
	 */
	private void giveLetters() {
		if (letterBag.size() > 0) {
			int tempIndex;
			Random rand = new Random(3);
			while (getCurrentPlayer().getLetters() < 7 && letterBag.size() > 0) {
				tempIndex = (int) (rand.nextInt(letterBag.size()));
				getCurrentPlayer().grabLetter(letterBag.remove(tempIndex));
			}
		}
	}

	/**
	 * gives letters to indexed player
	 * 
	 * @param index
	 */
	private void giveLetters(int index) {
		if (letterBag.size() > 0) {
			int tempIndex;
			Random rand = new Random(3);
			while (players[index].getLetters() < 7 && letterBag.size() > 0) {
				tempIndex = (int) (rand.nextInt(letterBag.size()));
				players[index].grabLetter(letterBag.remove(tempIndex));
			}
		}
	}

	/**
	 * returns a word in a string from a list of letters
	 * 
	 * @param word
	 * @return word
	 */
	private String wordToString(List<Letter> word) {
		String a = "";
		for (Letter x : word) {
			a += x.getLetter();
		}
		return a;
	}

	/**
	 * precondition: the new word & letters placed are either horizontal or
	 * vertical
	 * 
	 * @param oldBoard
	 * @param afterBoard
	 * @return true if new letters are horizontal, false if vertical
	 */
	private boolean isHorizontal(Tile[][] oldBoard, Tile[][] afterBoard) {
		ArrayList<Integer> rowIndexes = new ArrayList<Integer>();
		for (int row = 0; row < 15; row++) {
			for (int col = 0; col < 15; col++) {
				if (oldBoard[row][col].isEmpty() && !afterBoard[row][col].isEmpty()) {
					rowIndexes.add(row);
				}
			}
		}
		if (rowIndexes.get(0) == rowIndexes.get(rowIndexes.size() - 1)) {
			return true;
		}
		return false;
	}

	/**
	 * Precondition: either colFrom and colTo are equal or rowFrom and rowTo are
	 * equal
	 * 
	 * @param rowFrom
	 * @param colFrom
	 * @param rowTo
	 * @param colTo
	 * @return if it's horizontal
	 */
	private boolean isHorizontal(int rowFrom, int colFrom, int rowTo, int colTo) {
		if (colFrom == colTo) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * returns a list of either row tiles or col tiles based on string input.
	 * String should be either "row" or "col", based on which one is CHANGING
	 * constant is the value of the nonchanging row/col
	 * 
	 * @param changingRowOrCol
	 * @param from
	 * @param to
	 * @param constant
	 * @return
	 */
	private List<Tile> tilesFromIndexes(String changingRowOrCol, int from, int to, int constant) {
		List<Tile> tiles = new ArrayList<Tile>();
		try {
			if (changingRowOrCol.toLowerCase().equals("row")) {
				for (int i = from; i < to; i++) {
					tiles.add(board[i][constant]);
				}
			} else if (changingRowOrCol.toLowerCase().equals("col")) {
				for (int i = from; i < to; i++) {
					tiles.add(board[constant][i]);
				}
			}
			return tiles;
		} catch (IndexOutOfBoundsException e) {
			return tiles;
		}
	}
}