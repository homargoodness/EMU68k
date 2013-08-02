package architecture.registers;

public class GeneralRegister32Bit implements Register {
	
	private int contents;
	
	public GeneralRegister32Bit() {
		contents = 0;
	}
	
	public void write(int value) {
		contents = value; 
	}

	public int read() {
		return contents;
	}

}
