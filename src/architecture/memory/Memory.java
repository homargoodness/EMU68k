package architecture.memory;

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

	//private final int SIZE = 0xFFFFFF;

	public Memory() {
		memory = new ArrayList<MemoryLocation>();
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
	}
	
	public void reset() {
		int size = memory.size();
		for (int i = 0; i < size; i++) {
			write(memory.get(i).address, (byte)0);
		}
		memory.clear();
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
