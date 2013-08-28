package architecture.registers;

/**
 * Class which represents a 32 bit general purpose register
 */
public class GeneralRegister implements Register {
	
	private int contents;
	
	/**
	 * Constructor which initialises the contents to 0
	 */
	public GeneralRegister() {
		contents = 0;
	}
	
	/**
	 * Method which sets the contents
	 * @param value the new contents of register
	 */
	@Override
	public void write(int value) {
		contents = value; 
	}

	/**
	 * Method which returns the contents of the register
	 * @return the contents
	 */
	@Override
	public int read() {
		return contents;
	}

}
