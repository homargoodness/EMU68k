package architecture.registers;

/**
 * Class which represents the 16 bit Status Register
 *
 */
public class StatusRegister implements Register {

	int contents; 
	
	/**
	 * Method to write a value to contents
	 * @return the contents of the register
	 */
	public int read() {
		return contents;
	}
	
	/**
	 * Method to write a value to contents
	 * @param data the contents to be written to register
	 */
	public void write(int data) {
		contents = data;
	}

}
