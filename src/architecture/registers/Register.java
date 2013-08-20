package architecture.registers;

/**
 * Interface which registers much implement
 */
public interface Register {
	
	/**
	 * Method to write to register
	 * @param value the new value of the register
	 */
	public void write(int value);
	
	/**
	 * Method which returns contents of register
	 * @return the contents
	 */
	public int read();

}
