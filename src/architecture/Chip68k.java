package architecture;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import architecture.memory.Memory;
import architecture.registers.GeneralRegister;
import architecture.registers.Register;
import architecture.registers.StatusRegister;


/**
 * Model in MVC architecture. Holds the 16 general purpose registers, PC, SR and memory.
 * It manages reading and writing to the model depending on the size of the operation.
 * 
 * When any aspect of the model is changed registered listeners are notified.
 *
 */
public class Chip68k implements Chip {
	
	private PropertyChangeSupport propChange;
	
	private Register [] dataReg; // array of data registers
	private Register [] addressReg; // array of address registers
	private Register a7s; // shadow address register for system
	private Register pc; // program counter
	private StatusRegister sr; // status register
	private Memory memory; // memory model
	
	/**
	 * Constructor
	 */
	public Chip68k() {
		propChange = new PropertyChangeSupport(this);
		
		dataReg = new Register [8];
		addressReg = new Register [8];
		for (int i = 0; i < 8; i++) { // instantiate registers
			dataReg[i] = new GeneralRegister();
			addressReg[i] = new GeneralRegister();
		}
		
		a7s = new GeneralRegister();
		pc = new GeneralRegister();
		sr = new StatusRegister();
		memory = new Memory();
	}
	
	/**
	 * Method which allows a Property Change Listener to register itself as a listener to this class.
	 * @param listener the change listener to add to this class
	 * 
	 */
	@Override
	public void addListener(PropertyChangeListener listener) {
		propChange.addPropertyChangeListener(listener);
	}
	
	/**
	 * Method to reset all elements of this model and notify listeners of reset.
	 */
	@Override
	public void reset() {
		for (int i = 0; i < 8; i++) { // clear registers
			dataReg[i].write(0);
			addressReg[i].write(0);
		}
		pc.write(0);
		sr.write((short)0);
		memory.reset(); // method to reset memory
		propChange.firePropertyChange("Reset", null, 1); // notify listeners of deletion
	}
	
	/**
	 * Method to write a byte of data to a specified address in memory
	 * @param address the address in memory to be written to
	 * @param data the data to be written
	 */
	@Override
	public void writeMemoryByte(int address, int data) {
		memory.write(address, (byte)data);
		propChange.fireIndexedPropertyChange("Memory", address, null, (data & 0xFF)); // notify listeners of memory write
	}
	
	/**
	 * Method to write a word of data starting at the address specified in big endian notation.
	 * All registered listeners are also notified of the memory write.
	 * @param address address the address in memory to be written to
	 * @param data data the data to be written
	 */
	@Override
	public void writeMemoryWord(int address, int data) {
		memory.write(address, (byte)(data >>> 8)); // write most significant byte
		propChange.fireIndexedPropertyChange("Memory", address, null, (byte)(data >>> 8)); // notify listeners
		
		memory.write(address + 1, (byte)(data & 0xFF)); // write least significant byte to the next location
		propChange.fireIndexedPropertyChange("Memory", address + 1, null, (byte)(data & 0xFF)); // notify listeners
	}
	
	/**
	 * Method to write a long word of data starting at the address specified in big endian notation.
	 * All registered listeners are also notified of the memory write.
	 * @param address address the address in memory to be written to
	 * @param data data the data to be written
	 */
	@Override
	public void writeMemoryLongWord(int address, int data) {
		memory.write(address, (byte)(data >>> 24)); // write most significant byte to memory
		propChange.fireIndexedPropertyChange("Memory", address, null, (byte)(data >>> 24)); // notify listeners
		
		memory.write(address + 1, (byte)((data >>> 16) & 0xFF)); // write next most significant byte to memory
		propChange.fireIndexedPropertyChange("Memory", address + 1, null, (byte)((data >>> 16) & 0xFF)); // notify listeners
		
		memory.write(address + 2, (byte)((data >>> 8) & 0xFF)); // write next most significant byte to memory
		propChange.fireIndexedPropertyChange("Memory", address + 2, null, (byte)((data >>> 8) & 0xFF)); // notify listeners
		
		memory.write(address + 3, (byte)(data & 0xFF)); // write least significant byte to memory
		propChange.fireIndexedPropertyChange("Memory", address + 3, null, (byte)(data & 0xFF)); // notify listeners
	}
	
	/**
	 * Method to read a byte of data from memory at the specified address.
	 * @param address the address to be read from
	 */
	@Override
	public int readMemoryByte(int address) {
		return (memory.read(address) & 0xFF); // return contents
	}
	
	/**
	 * Method to read a word of data starting from memory the specified address.
	 * @param address the start address to be read from
	 */
	@Override
	public int readMemoryWord(int address) {
		int contents = memory.read(address) & 0xFF; // read the most significant byte into first 8 bits
		contents = (contents << 8); // move bits to the first byte position
		address++; // increment address to read from
		contents |= (memory.read(address) & 0xFF); // read 8 bits to the low order byte
		return contents; // return word
	}
	
	/**
	 * Method to read a long word of data starting from memory the specified address.
	 * @param address the start address to be read from
	 */
	@Override
	public int readMemoryLongWord(int address) {
		int contents = memory.read(address) & 0xFF; // read the most significant byte into the lowest byte of integer
		contents = (contents << 8); // move least significant byte to 2nd byte
		address++; // increment address to be read from
		contents |= memory.read(address) & 0xFF; // read next byte from memory into lowest byte on integer
		contents = (contents << 8); // move the 2 bytes contained in integer to make room for next byte
		address++; // increment address to be read from
		contents |= memory.read(address) & 0xFF; // read in next byte to the low byte of integer
		contents = (contents << 8); // move bytes to create room for last byte
		address++; // increment address
		contents |= memory.read(address) & 0xFF; // read last byte
		return contents; // return long word
	}
	
	
	
	/**
	 * Method to set the program counter.
	 * @param address the value to be written to the PC
	 */
	@Override
	public void setPC(int address) {
		pc.write(address); // write data to PC
		propChange.firePropertyChange("ProgramCounter", null, getPC()); // notify listeners
	}
	

	/**
	 * Method to return the contents of the PC.
	 */
	@Override
	public int getPC() {
		return pc.read(); // return contents of PC
	}
	
	
	/**
	 * Method to write a value to the lower byte of a specified data register.
	 * @param reg the register to be written to
	 * @param data the byte of data to be written
	 */
	@Override
	public void setDataRegisterByte(int reg, int data) {
		int contents = dataReg[reg].read() & 0xFFFFFF00; // copy data register to integer with lowest byte set to 0
		contents |= (data & 0xFF); // write byte to lowest order byte in integer
		dataReg[reg].write(contents); // write new vale to register
		propChange.fireIndexedPropertyChange("DataRegister", reg, null, dataReg[reg].read()); // notify listeners
	}

	/**
	 * Method to write a value to the lower word of a specified data register.
	 * @param reg the register to be written to
	 * @param data the word of data to be written
	 */
	@Override
	public void setDataRegisterWord(int reg, int data) {
		int contents = dataReg[reg].read() & 0xFFFF0000; // write data register to integer with low order word set to 0
		contents |= (data & 0xFFFF); //write data to low order word in integer
		dataReg[reg].write(contents); // write new value to register
		propChange.fireIndexedPropertyChange("DataRegister", reg, null, dataReg[reg].read()); // notify listeners
	}

	/**
	 * Method to write a value to a specified data register.
	 * @param reg the register to be written to
	 * @param data the long word of data to be written
	 */
	@Override
	public void setDataRegisterLongWord(int reg, int data) {
		dataReg[reg].write(data); // write value to register
		propChange.fireIndexedPropertyChange("DataRegister", reg, null, dataReg[reg].read()); // notify listeners
	}

	/**
	 * Method to read the lower bye of a specified data register.
	 * @param reg the register to read from
	 */
	@Override
	public int getDataRegisterByte(int reg) {
		int contents = dataReg[reg].read(); // read contents of register
		return (contents & 0xFF); // return lowest byte of register
	}

	/**
	 * Method to read the lower word of a specified data register.
	 * @param reg the register to read from
	 */
	@Override
	public int getDataRegisterWord(int reg) {
		int contents = dataReg[reg].read(); // read contents of register
		return (short) (contents & 0xFFFF); // return low order word of register
	}
	
	/**
	 * Method to read the contents of a specified data register.
	 * @param reg the register to read from
	 */
	@Override
	public int getDataRegisterLongWord(int reg) {
		return dataReg[reg].read(); // return contents of register
	}

	/**
	 * Method to write a value to the lower word of the address register.
	 * @param reg the register to write to
	 * @param data the word of data to be written
	 */
	@Override
	public void setAddressRegisterWord(int reg, int data) {
		int contents = addressReg[reg].read() & 0xFFFF0000; // set lower word to 0
		contents |= (data & 0xFFFF); // write data to lower word
		addressReg[reg].write(contents); // update register contents
		propChange.fireIndexedPropertyChange("AddressRegister", reg, null, addressReg[reg].read()); // notify listeners
		
	}

	/**
	 * Method to write a long word to the specified address register.
	 * @param reg the register to be written to
	 * @param data the long word to be written 
	 */
	@Override
	public void setAddressRegisterLongWord(int reg, int data) {
		addressReg[reg].write(data); // update register with new long word
		propChange.fireIndexedPropertyChange("AddressRegister", reg, null, addressReg[reg].read()); // notify listeners
	}

	/**
	 * Method to read the lower word of the specified address register.
	 * @param reg the register to be read from
	 */
	@Override
	public int getAddressRegisterWord(int reg) {
		int contents = addressReg[reg].read(); // read contents of register
		return (contents & 0xFFFF); // return lower word of contents
	}

	/**
	 * Method to return the contents of the specified address register
	 * @param the register to be read from
	 */	
	@Override
	public int getAddressRegisterLongWord(int reg) {
		return addressReg[reg].read(); // return contents of register
	}
	
	/**
	 * Method to return least significant (Carry bit) from the status register
	 */
	@Override
	public int getSRCarryBit() {
		return sr.read() & 0x1; //TODO set to mask ** return SR with a mask hiding all bits apart from the first
	}
	
	/**
	 * Method to return the 2nd bit (Overflow bit) from the status register.
	 */
	@Override
	public int getSROverflowBit() {
		return (sr.read() >>> 1) & 0x1; // return SR with a mask hiding all bits apart from the second
	}
	
	/**
	 * Method to return the 3rd bit (Zero bit) from the status register.
	 */
	@Override
	public int getSRZeroBit() {
		return (sr.read() >>> 2) & 0x1; // return SR with all bits masked apart from 3rd bit
	}

	/**
	 * Method to return the 4th bit (Negative bit) from the status register.
	 */
	@Override
	public int getSRNegativeBit() {
		return (sr.read() >>>3) & 0x1; // return SR with all bits masked apart from 4th bit
	}

	/**
	 * Method to return the 5th bit (Extend bit) from the status register.
	 */
	@Override
	public int getSRExtendBit() {
		return (sr.read() >>> 4) & 0x1; // return SR with all bits masked apart from 5th bit
	}

	/**
	 * Method to set the 1st bit (Carry bit) of the status register.
	 * @param bit the value the first bit should be set to
	 */
	@Override
	public void setSRCarryBit(int bit) {
		int contents = sr.read(); // get the current contents of SR
		contents &= 0xFE; // make the C bit using a mask
		contents |= (bit & 0x1); // set the C bit to the correct value
		sr.write(contents & 0xFFFF); // write the contents back into SR
	
		propChange.firePropertyChange("StatusRegister", null, sr.read()); // notify listeners of a change to SR
	}

	/**
	 * Method to set the 2nd bit (Overflow bit) of the status register.
	 * @param bit the value the second bit should be set to
	 */
	@Override
	public void setSROverflowBit(int bit) { // blank the bit then AND
		int contents = sr.read(); // get the current contents of SR
		contents &= 0xFD; // set the V bit to 0 using mask
		contents |= (bit << 1); // set the C bit to the correct value
		sr.write(contents & 0xFFFF); // write the contents back into SR

		propChange.firePropertyChange("StatusRegister", null, sr.read()); // notify listeners of a change to SR
		
	}
	
	/**
	 * Method to set the 3rd bit (Zero bit) of the status register.
	 * @param bit the value the third bit should be set to
	 */
	@Override
	public void setSRZeroBit(int bit) {
		int contents = sr.read(); // get current contents of SR
		contents &= 0xFB; // set the Z bit to 0
		contents |= (bit << 2); // set the Z bit to the correct value
		sr.write(contents & 0xFFFF); // write the new value back into SR
		
		propChange.firePropertyChange("StatusRegister", null, sr.read()); // notify listeners of a change to SR
	}
	
 	/**
	 * Method to set the 4th bit (Negative bit) of the status register.
	 * @param bit the value the fourth bit should be set to
	 */
	@Override
	public void setSRNegativeBit(int bit) {
		int contents = sr.read(); // read the current contents of SR
		contents &= 0xF7; // use mask to set the N bit to 0
		contents |= (bit << 3); // write the new value of the N bit
		sr.write(contents & 0xFFFF); // write the contents back into SR
	
		propChange.firePropertyChange("StatusRegister", null, sr.read()); // notify listeners of change to SR
	}

	/**
	 * Method to set the 5th bit (Extend bit) of the status register.
	 * @param bit the value the fifth bit should be set to
	 */
	@Override
	public void setSRExtendBit(int bit) {
		int contents = sr.read(); // read the current contents of SR
		contents &= 0xEF; // use mask to set the X bit to 0
		contents |= (bit << 4); // write the new value of X bit
		sr.write(contents & 0xFFFF); // write contents back to SR
	
		propChange.firePropertyChange("StatusRegister", null, sr.read()); // notify listeners of change to SR
	}

}
