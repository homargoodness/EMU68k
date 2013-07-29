package Architecture.Registers;

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
	
	public AddressRegister() {
		contents = 0;
	}
	
	public void write(int value) {
		contents = value; 
	}

	public int read() {
		return contents;
	}

}
