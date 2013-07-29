package Architecture.Registers;

import Architecture.Memory.StorageException;

/**
 * Simulates a data register of arbitrary width
 * 
 * @author Omar Manir
 */
public class DataRegister implements Register { // have 3 seperate write methods all with diff signitures (short, int and long)

	private int contents;
	private final int SIZE = 32; //TODO
	
	public DataRegister() {
		contents = 0;
	}
	
	public void write(int value) {
		//if (value >  SIZE) {
			//throw new StorageException();
		//}
		contents = value; 
	}
	
	public int read() {
		return contents;
	}



}
