package Architecture.Registers;

import Architecture.Memory.StorageException;

public interface Register {
	
	public void write(int value) throws StorageException;
	
	public int read();

}
