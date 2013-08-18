package architecture.memory;

import java.util.ArrayList;
import java.util.List;


/**
 * Data structure which emulates the 68k memory space with method to set and retrieve the data
 * at a particular memory location.
 * 
 * Each memory location is represented by the MemoryLocation inner class which holds the address
 * and memory contents
 * 
 * The contents are held in an arraylist. When a new valueis written to memory, the list is checked
 * to see if there is already a value in the memory location, if there is it is overwritten, otherwise
 * a new element is added to the list. When reading from memory, first the list is checked, if the address is 
 * populated, the contents are returned otherwise a default value of 0xFF is returned.
 */
public class Memory {
	
	private List<MemoryLocation> memory; // the list holding the contents of memory

	/**
	 * Constructor
	 */
	public Memory() {
		memory = new ArrayList<MemoryLocation>();
	}

	
	/**
	 * Method to read a byte from a specified memory location
	 * @param address the address to read from
	 * @return the byte at the memory location
	 */
	public byte read(int address) {
		
		for (int i = 0; i < memory.size(); i++) { // iterate through all memory locations held in list
			if (memory.get(i).address == address) { // if the address is found
				return memory.get(i).contents; // return the byte of data held there
			}
		}
		return (byte)0xFF; // otherwise return value 0xFF
	}
	

	/**
	 * Method to write a byte to memory. If the memory location already exists in the list, the value is overwritten 
	 * with the new value, otherise a new element is added to list
	 * @param data the byte to be written to memory
	 * @param address the location to write the data
	 */
	public void write(int address, byte data) {
		
		for (int i = 0; i < memory.size(); i++) { // loop through all memeory locations held in list
			if (memory.get(i).address == address) { // if the address is found
				memory.get(i).contents = data; // overwrite the contents
				return; // end method
			}
		}
		memory.add(new MemoryLocation(address, data)); // if the address was not found return 0xFF
	}
	
	/** 
	 * Method to empty the memory
	 * TODO need the loop???
	 */
	public void reset() {
		int size = memory.size();
		for (int i = 0; i < size; i++) {
			write(memory.get(i).address, (byte)0);
		}
		memory.clear();
	}
	
	
	/**
	 * Inner class which is used to model 1 memory location which has an address
	 * and contents in the form of a byte
	 */
	private class MemoryLocation {
		private int address; // the address of this memory location
		private byte contents; // the data this memory location holds
		
		/**
		 * Constructor
		 * @param anAddress the address of the new memory location
		 * @param data the data to be written to the memory location
		 */
		private MemoryLocation(int anAddress, byte data) {
			address = anAddress;
			contents = data;
		}
	}
	
}
