package Architecture.Registers;

import Architecture.Memory.StorageException;

/**
 * Simulates an address register of arbitrary width
 * 
 * TODO address regsiter different from data regsiter as address reg contents
 * has to be > byte
 * 
 * @author Omar Manir
 */
public class AddressRegister implements Register {

	private int contents;
	private final int SIZE = 32; //TODO
	
	public AddressRegister() {
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
