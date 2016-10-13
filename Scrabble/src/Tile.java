
public abstract class Tile {
	
	private Letter placeHold;
	private boolean isEmpty;
	
	public Tile(){
		placeHold = new Letter(' ', 0);
		isEmpty = true;
	}
	
	public boolean isEmpty(){
		if (isEmpty){
			System.out.print(".");
		}else{
			System.out.print("t");
		}
		return isEmpty;
	}
	
	public void setLetter(Letter letter){
		placeHold.setLetter(letter.getLetter(), letter.getPoint());
		if (placeHold.getLetter() == ' '){
			isEmpty = true;
		}else{
			isEmpty = false;
		}
	}
	
	public Letter getLetter(){
		return placeHold;
	}
	
	public int getTileScore(){
		return placeHold.getPoint() * getFactor();
	}
	
	public abstract boolean isWordMult();
	public abstract String getMultiplier();
	public abstract boolean isMult();
	public abstract int getFactor();
	public abstract String toString();
	
}