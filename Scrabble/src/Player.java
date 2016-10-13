
public class Player {

	private Letter[] letters;
	private int points;
	private String name;

	public Player(String n) {
		letters = new Letter[7];
		name = n;
		points = 0;
	}

	public void grabLetter(Letter a) {
		for (int i = 0; i < 7; i++) {
			if (letters[i] == null) {
				letters[i] = new Letter(a.getLetter(), a.getPoint());
				return;
			}
		}
	}

	public Letter removeLetter(int index) {
		try {
			Letter temp = letters[index];
			letters[index] = null;
			return temp;
		} catch (NullPointerException e) {
			System.out.println("No letter there");
			return null;
		}
	}

	public void removeLetters(Letter[] lettersAfterPlay) {
		for (int i = 0; i < 7; i++) {
			if (lettersAfterPlay[i] == null){
				letters[i] = null;
			}
			else if (!letters[i].equals(lettersAfterPlay[i]))
				letters[i].setLetter(lettersAfterPlay[i].getLetter(), lettersAfterPlay[i].getPoint());
		}
	}

	public int getLetters() {
		int size = 0;
		for (Letter x : letters) {
			if (x != null) {
				size++;
			}
		}
		return size;
	}

	public Letter[] getPlayerHand() {
		return letters;
	}

	public int getPoints() {
		return points;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public void setLetters(Letter[] x) {
		for (int i = 0; i < 7; i++) {
			letters[i] = x[i];
		}
	}

	public void setPoints(int p) {
		points = p;
	}

	public void addPoints(int n) {
		points += n;
	}

	public void printHand() {
		for (Letter x : letters) {
			if (x == null)
				System.out.print("null");
			else{
				System.out.print(x.getLetter());
			}
		}
	}
	
	public String toString(){
		String a = "";
		a += name;
		return a;
	}
}
