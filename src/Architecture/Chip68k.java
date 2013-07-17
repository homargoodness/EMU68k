package Architecture;

import Architecture.Memory.Memory;
import Architecture.Memory.MemoryAccessException;
import Architecture.Memory.StorageException;
import Architecture.Registers.AddressRegister;
import Architecture.Registers.DataRegister;
import Architecture.Registers.Register;
import Architecture.Registers.StatusRegister;

/**
 * Model in MVC architecture
 * Contains classes which have state
 * No business logic
 *
 */
public class Chip68k implements Chip {
	
	private Register d0,d1,d2,d3,d4,d5,d6,d7; // TODO store as array list?
	private Register a0,a1,a2,a3,a4,a5,a6,a7u,a7s,pc;
	private StatusRegister sr;
	private Memory memory; // TODO DEFAULT CONTENTS SHOULD BE FF
	
	public Chip68k() {
		d0 = new DataRegister();
		d1 = new DataRegister();
		d2 = new DataRegister();
		d3 = new DataRegister();
		d4 = new DataRegister();
		d5 = new DataRegister();
		d6 = new DataRegister();
		d7 = new DataRegister();
		
		a0 = new AddressRegister();
		a1 = new AddressRegister();
		a2 = new AddressRegister();
		a3 = new AddressRegister();
		a4 = new AddressRegister();
		a5 = new AddressRegister();
		a6 = new AddressRegister();
		a7u = new AddressRegister();
		
		a7s = new AddressRegister();
		
		pc = new AddressRegister();
		
		sr = new StatusRegister();
		
		memory = new Memory();
	}
	
	public void writeMemory(int address, String data) {
		memory.writeByte(data, address);
	}
	

}
