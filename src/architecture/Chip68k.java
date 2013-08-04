package architecture;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import architecture.memory.Memory;
import architecture.memory.MemoryAccessException;
import architecture.memory.StorageException;
import architecture.registers.GeneralRegister32Bit;
import architecture.registers.Register;
import architecture.registers.StatusRegister;


/**
 * Model in MVC architecture
 * Contains classes which have state
 * No business logic
 *
 */
public class Chip68k implements Chip {
	
	private PropertyChangeSupport propChange;
	
	private Register [] dataReg;
	private Register [] addressReg;
	private Register a7s,pc;
	private StatusRegister sr;
	private Memory memory;
	
	public Chip68k() {
		
		propChange = new PropertyChangeSupport(this);
		
		dataReg = new Register [8];
		addressReg = new Register [8];
		for (int i = 0; i < 8; i++) {
			dataReg[i] = new GeneralRegister32Bit();
			addressReg[i] = new GeneralRegister32Bit();
		}
		
		a7s = new GeneralRegister32Bit(); // shadow address register for system
		
		pc = new GeneralRegister32Bit();
		
		sr = new StatusRegister();
		
		memory = new Memory();
	}
	
	public void addListener(PropertyChangeListener listener) {
		propChange.addPropertyChangeListener(listener);
	}
	
	public void reset() {
		for (int i = 0; i < 8; i++) {
			dataReg[i].write(0);
			addressReg[i].write(0);
		}
		pc.write(0);
		sr.write((short)0);
		memory.reset();
		propChange.firePropertyChange("Reset", null, 1);
	}
	
	
	/** Memory *************************************************/
	public void writeMemory(int address, byte data) {
		memory.write(address, data);
		propChange.fireIndexedPropertyChange("Memory", address, null, data);
	}
	
	public void writeMemory(int address, short data) {
		memory.write(address, (byte)(data >>> 8));
		propChange.fireIndexedPropertyChange("Memory", address, null, (byte)(data >>> 8));
		memory.write(address + 1, (byte)(data & 0xFF));
		propChange.fireIndexedPropertyChange("Memory", address + 1, null, (byte)(data & 0xFF));
	}
	
	public void writeMemory(int address, int data) {
		memory.write(address, (byte)(data >>> 24));
		propChange.fireIndexedPropertyChange("Memory", address, null, (byte)(data >>> 24));
		memory.write(address + 1, (byte)((data >>> 16) & 0xFF));
		propChange.fireIndexedPropertyChange("Memory", address + 1, null, (byte)((data >>> 16) & 0xFF));
		memory.write(address + 2, (byte)((data >>> 8) & 0xFF));
		propChange.fireIndexedPropertyChange("Memory", address + 2, null, (byte)((data >>> 8) & 0xFF));
		memory.write(address + 3, (byte)(data & 0xFF));
		propChange.fireIndexedPropertyChange("Memory", address + 3, null, (byte)(data & 0xFF));
	}
	
	public byte readMemory(int address) {
		setPC(address + 1);
		return memory.read(address);
	}
	
	public short readMemoryWord(int address) {
		int contents = memory.read(address) & 0xFF;
		contents = (contents << 8);
		address++;
		contents |= (memory.read(address) & 0xFF);
		address++;
		setPC(address);
		return (short)contents;
	}
	
	public int readMemoryLongWord(int address) {
		int contents = memory.read(address) & 0xFF;
		contents = (contents << 8);
		address++;
		contents |= memory.read(address) & 0xFF;
		contents = (contents << 8);
		address++;
		contents |= memory.read(address) & 0xFF;
		contents = (contents << 8);
		address++;
		contents |= memory.read(address) & 0xFF;
		address++;
		setPC(address);
		return contents;
	}
	
	
	/** PC ******************************************************/
	public void setPC(int address) {
		pc.write(address);
		propChange.firePropertyChange("ProgramCounter", null, getPC());
	}
	
	public int getPC() {
		return pc.read();
	}

	
	/** Data ****************************************************/
	
	public void setDataRegister(int reg, byte data) {
		int contents = dataReg[reg].read() & 0xFFFFFF00;
		contents |= (data & 0xFF);
		dataReg[reg].write(contents);
		propChange.fireIndexedPropertyChange("DataRegister", reg, null, dataReg[reg].read());
	}

	
	public void setDataRegister(int reg, short data) {
		int contents = dataReg[reg].read() & 0xFFFF0000;
		contents |= (data & 0xFFFF);
		dataReg[reg].write(contents);
		propChange.fireIndexedPropertyChange("DataRegister", reg, null, dataReg[reg].read());
	}

	
	public void setDataRegister(int reg, int data) {
		dataReg[reg].write(data);
		propChange.fireIndexedPropertyChange("DataRegister", reg, null, dataReg[reg].read());
	}

	
	public byte getDataRegisterByte(int reg) {
		int contents = dataReg[reg].read();
		return (byte) (contents & 0xFF);
	}

	
	public short getDataRegisterWord(int reg) {
		int contents = dataReg[reg].read();
		return (short) (contents & 0xFFFF);
	}

	
	public int getDataRegisterLongWord(int reg) {
		return dataReg[reg].read();
	}

	
	/** Address ***********************************************/
	
	public void setAddressRegister(int reg, short data) {
		int contents = addressReg[reg].read() & 0xFFFF0000;
		contents |= (data & 0xFFFF);
		addressReg[reg].write(contents);
		propChange.fireIndexedPropertyChange("AddressRegister", reg, null, addressReg[reg].read());
		
	}

	
	public void setAddressRegister(int reg, int data) {
		addressReg[reg].write(data);
		propChange.fireIndexedPropertyChange("AddressRegister", reg, null, addressReg[reg].read());
	}

	
	public short getAddressRegisterWord(int reg) {
		int contents = addressReg[reg].read();
		return (short) (contents & 0xFFFF);
	}

	
	public int getAddressRegisterLongWord(int reg) {
		return addressReg[reg].read();
	}
	
	
	/** SR ****************************************************/

	public int getSRCarryBit() {
		return sr.read() & 0x1; //TODO set to mask
	}

	public int getSROverflowBit() {
		return sr.read() & 0x2;
	}
	
	public int getSRZeroBit() {
		return sr.read() & 0x4;
	}

	public int getSRNegativeBit() {
		return sr.read() & 0x8;
	}

	public int getSRExtendBit() {
		return sr.read() & 0x10;
	}

	
	public void setSRCarryBit(int bit) {
		short contents = sr.read();
		contents &= 0xFE;
		contents |= (bit & 0x1);
		sr.write(contents);
	
		propChange.firePropertyChange("StatusRegister", null, sr.read());
	}

	public void setSROverflowBit(int bit) { // blank the bit then AND
		short contents = sr.read();
		contents &= 0xFD;
		contents |= (bit << 1);
		sr.write(contents);

		propChange.firePropertyChange("StatusRegister", null, sr.read());
		
	}
	
	public void setSRZeroBit(int bit) {
		short contents = sr.read();
		contents &= 0xFB;
		contents |= (bit << 2);
		sr.write(contents);
		
		propChange.firePropertyChange("StatusRegister", null, sr.read());
	}
	
	public void setSRNegativeBit(int bit) {
		short contents = sr.read();
		contents &= 0xF7;
		contents |= (bit << 3);
		sr.write(contents);
	
		propChange.firePropertyChange("StatusRegister", null, sr.read());
	}

	public void setSRExtendBit(int bit) {
		short contents = sr.read();
		contents &= 0xEF;
		contents |= (bit << 4);
		sr.write(contents);
	
		propChange.firePropertyChange("StatusRegister", null, sr.read());
	}

}
