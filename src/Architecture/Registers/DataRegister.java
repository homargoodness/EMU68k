package Architecture.Registers;

/**
 * Simulates a data register of arbitrary width
 * 
 * @author Omar Manir
 */
public class DataRegister implements Register { // have 3 seperate write methods all with diff signitures (short, int and long)

	private int contents;
	
	public DataRegister() {
		contents = 0;
	}
	
	public void write(int value) {
		contents = value; 
	}
	
	public int read() {
		return contents;
	}



}
