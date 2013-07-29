package Architecture.Registers;

import Architecture.Memory.StorageException;

public interface Register { // TODO change instruction heirarchy so that data reg has different set methods
	
	public void write(int value);
	
	public int read();

}
