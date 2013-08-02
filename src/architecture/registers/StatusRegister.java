package architecture.registers;

/**
 * 
 * @author Omar Manir
 *
 */
public class StatusRegister {

	short contents; 
	
	public short read() {
		return contents;
	}
	
	public void write(short data) {
		contents = data;
	}

}
