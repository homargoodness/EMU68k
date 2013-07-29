package Architecture.Memory;

import java.util.ArrayList;
import java.util.List;




/**
 * Emulates the memory system of the MC68000 Each memory location holds 1 byte
 * (8 bits) which has a value between 0 and 255 Address bus is 24 bits wide so
 * locations 000000 to FFFFFF (hex) are addressable Memory is address using
 * hexadecimal
 * 
 * @author Omar Manir
 * 
 * 
 *
 */
public class Memory {
	
	private List<MemoryLocation> memory;

	private final int SIZE = 0xFFFFFF;
	//private String[] mem; 

	public Memory() {
		memory = new ArrayList<MemoryLocation>();
		//mem = new String[SIZE];
	}

	
	/**
	 * TODO synchronize
	 * @param address
	 * @return
	 * @throws MemoryAccessException
	 */
	public byte read(int address) {
		
		for (int i = 0; i < memory.size(); i++) {
			if (memory.get(i).address == address) {
				return memory.get(i).contents;
			}
		}
		
		return (byte)0xFF;
		
		
		
		//if (address > SIZE || address < 0) { // if address is not within addressable space
			//System.out.println(address);
			//throw new MemoryAccessException();
		//}
		//return mem[address];
		
	}
	

	/**
	 * TODO synchronize
	 * @param data
	 * @param address
	 * @throws MemoryAccessException
	 * @throws StorageException
	 */
	public void write(int address, byte data) {
		
		for (int i = 0; i < memory.size(); i++) {
			if (memory.get(i).address == address) {
				
				memory.get(i).contents = data;
				return;
			}
		}
		
		memory.add(new MemoryLocation(address, data));
		
		//if (address >= SIZE || address < 0) { // if address is not within addressable space
			//throw new MemoryAccessException();
		//}
		//if (data > 255 || data < 0) { // if data in location is not between 0 and 255
			///throw new StorageException();
		//}
		//mem[address] = data;
	
	}
	
	private class MemoryLocation {
		private int address;
		private byte contents;
		
		private MemoryLocation(int anAddress, byte data) {
			address = anAddress;
			contents = data;
		}
	}
	
	
	

}
