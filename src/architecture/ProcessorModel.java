package architecture;

import java.beans.PropertyChangeListener;


/**
 * Interface for the mediator aggregate class which holds references to the registers and
 * memory and contains the logic which controls access to them. This is the class which
 * instructions use to interact with the model
 */
public interface ProcessorModel {
	
	/**
	 * Method to add a listener to this class which gets updated when the model changes
	 * @param listener the listener which is to be notified of change
	 */
	public void addListener(PropertyChangeListener listener);
	
	/**
	 * Method to reset all registers and memory to their initial state
	 */
	public void reset();
	
	/**
	 * Method to write a single byte to memory
	 * @param address the address where the data is to written
	 * @param data the byte of data to be written to memory
	 */
	public void writeMemoryByte(int address, int data);
	
	/**
	 * Method to write a word (2 bytes) to memory. The least significant byte
	 * is placed in the lower numbered memory address
	 * @param address the start address where the data is to be written
	 * @param data the word of data to be written to memory
	 */
	public void writeMemoryWord(int address, int data);
	
	/**
	 * Method to write a long word (4 bytes) to memory. The least significant byte
	 * is placed in the lower numbered memory address
	 * @param address the start address where the data is to be written
	 * @param data the long word of data to be written to memory
	 */
	public void writeMemoryLongWord(int address, int data);
	
	/**
	 * Method to read a byte from memory 
	 * @param address the address where the byte resides
	 * @return the byte of data read from memory
	 */
	public int readMemoryByte(int address);
	
	/**
	 * Method to read a word (2 bytes) from memory starting at a given address. The least significant
	 * byte is located in the numerically lowest address space
	 * @param address the starting address of the word which holds the low order byte
	 * @return the word read from memory
	 */
	public int readMemoryWord(int address);
	
	/**
	 * Method to a long word (4 bytes) from memory starting at a given address. The least
	 * significant byte is located in the starting address
	 * @param address the location of the least significant byte and the start of the long word
	 * @return the long word from memory
	 */
	public int readMemoryLongWord(int address);
	
	/**
	 * Method to set the value of the Program Counter
	 * @param address the value to set the PC to
	 */
	public void setPC(int address);
	
	/**
	 * Method to return the contents of the PC
	 * @return the contents of the PC
	 */
	public int getPC();
	
	/**
	 * Method to write to the low order byte of the specified data register
	 * @param reg the data register to be modified
	 * @param data the byte to be written to the register
	 */
	public void setDataRegisterByte(int reg, int data);
	
	/**
	 * Method to write to the low order word of the specified data register
	 * @param reg data the register to be written to
	 * @param data the word to be written
	 */
	public void setDataRegisterWord(int reg, int data);
	
	/**
	 * Method to write a long word to the specified data register
	 * @param reg the register to write to
	 * @param data the long word to be written
	 */
	public void setDataRegisterLongWord(int reg, int data);
	
	/**
	 * Method to return the low order byte contained in the specified data register
	 * @param reg the data register to read from
	 * @return the low order byte held in the data register
	 */
	public int getDataRegisterByte(int reg);
	
	/**
	 * Method to return the low order word held in the specified data register
	 * @param reg the data register to read from
	 * @return the low order word held in the register
	 */
	public int getDataRegisterWord(int reg);
	
	/**
	 * Method to return the long word held in the specified data register
	 * @param reg the data register to read from
	 * @return the long word held in the register
	 */
	public int getDataRegisterLongWord(int reg);
	
	/**
	 * Method to write to the low order word in the specified address register
	 * @param reg the address register to write to
	 * @param data the word to write to the address register
	 */
	public void setAddressRegisterWord(int reg, int data);
	
	/**
	 * Method to write a long word to a specified address register
	 * @param reg the register to write to
	 * @param data the long word to write to register
	 */
	public void setAddressRegisterLongWord(int reg, int data);
	
	/**
	 * Method to return the low order word from the specified address register
	 * @param reg the register to read from
	 * @return the low order word held in the register
	 */
	public int getAddressRegisterWord(int reg);
	
	/**
	 * Method to return the long word held in the specified address register
	 * @param reg the register to read from
	 * @return the long word held in the register
	 */
	public int getAddressRegisterLongWord(int reg);
	
	/**
	 * Method to return the value of the first bit(Carry bit) held in the register
	 * @return the carry bit flag value
	 */
	public int getSRCarryBit();
	
	/**
	 * Method to return the value of the second bit position (overflow bit) in the 
	 * status register
	 * @return the value of the overflow flag
	 */
	public int getSROverflowBit();
	
	/**
	 * Method to return the value of the third bit position (zero bit) in the 
	 * status register
	 * @return the value of the zero flag
	 */
	public int getSRZeroBit();
	
	/**
	 * Method to return the value of the fourth bit position (negative bit) in the 
	 * status register
	 * @return the value of the negative flag
	 */
	public int getSRNegativeBit();
	
	/**
	 * Method to return the value of the fifth bit position (extend bit) in the 
	 * status register
	 * @return the value of the extend flag
	 */
	public int getSRExtendBit();
	
	/**
	 * Method to set the value of the first bit(Carry bit) in the register
	 * @param the new value of the carry flag
	 */
	public void setSRCarryBit(int bit);
	
	/**
	 * Method to set the value of the second bit position (overflow bit) in the 
	 * status register
	 * @param the new value of the overflow flag
	 */
	public void setSROverflowBit(int bit);
	
	/**
	 * Method to set the value of the third bit position (zero bit) in the 
	 * status register
	 * @param the new value of the zero flag
	 */
	public void setSRZeroBit(int bit);
	
	/**
	 * Method to set the value of the fourth bit position (negative bit) in the 
	 * status register
	 * @param bit the new value for the negative flag
	 */
	public void setSRNegativeBit(int bit);
	
	/**
	 * Method to set the value of the fifth bit position (extend bit) in the 
	 * status register
	 * @param bit the new value of the extend flag
	 */
	public void setSRExtendBit(int bit);

}
