package architecture.memory;

/**
 * This class provides an interface which should be implemented
 * by any class which represents processor memory
 * @author homargoodness
 *
 */
public interface Memory {
	
	/**
	 * Method to read a byte from a specified address
	 * @param address the address to read from
	 * @return the byte at the memory location
	 */
	byte read(int address);
	
	/**
	 *  Method to write a byte to memory
	 * @param data the byte to be written to memory
	 * @param address the location to write the data
	 */
	void write(int address, byte data);
	
	/**
	 * Method to empty the memory
	 */
	void reset();

}
