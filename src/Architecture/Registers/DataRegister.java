package Architecture.Registers;

import Architecture.Memory.StorageException;

/**
 * Simulates a data register of arbitrary width
 * 
 * @author Omar Manir
 */
public class DataRegister implements Register {

	private int contents; //TODO int big enough to hold contents and size???
	private final int SIZE = 32; //TODO
	
	public DataRegister() {
		contents = 0;
	}
	
	public void write(int value) throws StorageException {
		if (value >  SIZE) {
			throw new StorageException();
		}
		contents = value; 
	}

	public int read() {
		return contents;
	}

}
