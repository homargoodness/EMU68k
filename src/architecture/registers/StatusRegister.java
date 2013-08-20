package architecture.registers;

/**
 * Class which represents the 16 bit Status Register
 *
 */
public class StatusRegister {

	short contents; 
	
	/**
	 * Method to write a value to contents
	 * @return the contents of the register
	 */
	public short read() {
		return contents;
	}
	
	/**
	 * Method to write a value to contents
	 * @param data the contents to be written to register
	 */
	public void write(short data) {
		contents = data;
	}

}
