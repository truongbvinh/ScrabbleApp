
public class Letter {

	private char letter;
	private int pointVal;
	
	public Letter (char tileLetter, int point){
		letter = tileLetter;
		pointVal = point;
	}
	
	public boolean equals(Letter other){
		if (letter == other.getLetter()){
			return true;
		}
		return false;
	}
	
	public char getLetter(){
		return letter;
	}
	
	public int getPoint(){
		return pointVal;
	}
	
	public void setLetter(char tileLetter, int point){
		letter = tileLetter;
		pointVal = point;
	}
	
	public String toString(){
		String a = "";
		a += letter;
		return a;
	}
}