package Architecture;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

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
	
	private PropertyChangeSupport propChange;
	
	private List<Register> dataReg; //TODO use array instead
	private List<Register> addressReg;
	private Register a7s,pc;
	private StatusRegister sr;
	private Memory memory; // TODO DEFAULT CONTENTS SHOULD BE FF
	
	public Chip68k() {
		
		propChange = new PropertyChangeSupport(this);
		
		dataReg = new ArrayList<Register>();
		for (int i = 0; i < 8; i++) {
			dataReg.add(new DataRegister());
		}
		
		addressReg = new ArrayList<Register>();
		for (int i = 0; i < 8; i++) {
			addressReg.add(new AddressRegister());
		}
		
		
		a7s = new AddressRegister(); // shadow address register for system
		
		pc = new AddressRegister();
		
		sr = new StatusRegister();
		
		memory = new Memory();
	}
	
	public void addListener(PropertyChangeListener listener) {
		propChange.addPropertyChangeListener(listener);
	}
	
	
	/** Memory *************************************************/
	public void writeMemory(int address, byte data) {
		memory.write(address, data);
		propChange.fireIndexedPropertyChange("Memory", address, null, data);
	}
	
	public void writeMemory(int address, short data) {
		
	}
	
	public void writeMemory(int address, int data) {
		
	}
	
	public byte readMemory(int address) {
		pc.write(address + 1);
		return memory.read(address);
	}
	
	public short readMemoryWord(int address) {
		int contents = memory.read(address) & 0xFF;
		contents = (contents << 8);
		address++;
		contents |= memory.read(address) & 0xFF;
		contents = (contents << 8);
		pc.write(address);
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
		int contents = dataReg.get(reg).read() & 0xFFFFFF00;
		contents |= (data & 0xFF);
		dataReg.get(reg).write(contents);
		propChange.fireIndexedPropertyChange("DataRegister", reg, null, dataReg.get(reg).read());
	}

	
	public void setDataRegister(int reg, short data) {
		int contents = dataReg.get(reg).read() & 0xFFFF0000;
		contents |= (data & 0xFFFF);
		dataReg.get(reg).write(contents);
		propChange.fireIndexedPropertyChange("DataRegister", reg, null, dataReg.get(reg).read());
	}

	
	public void setDataRegister(int reg, int data) {
		dataReg.get(reg).write(data);
		propChange.fireIndexedPropertyChange("DataRegister", reg, null, dataReg.get(reg).read());
	}

	
	public byte getDataRegisterByte(int register) {
		int contents = dataReg.get(register).read();
		return (byte) (contents & 0xFF);
	}

	
	public short getDataRegisterWord(int register) {
		int contents = dataReg.get(register).read();
		return (short) (contents & 0xFFFF);
	}

	
	public int getDataRegisterLongWord(int register) {
		return dataReg.get(register).read();
	}

	
	/** Address ***********************************************/
	
	public void setAddressRegister(int reg, short data) {
		int contents = addressReg.get(reg).read() & 0xFFFF0000;
		contents |= (data & 0xFFFF); //TODO need the and here????!?!
		addressReg.get(reg).write(contents);
		propChange.fireIndexedPropertyChange("AddressRegister", reg, null, addressReg.get(reg).read());
		
	}

	
	public void setAddressRegister(int reg, int data) {
		addressReg.get(reg).write(data);
		propChange.firePropertyChange("a" + reg, null, addressReg.get(reg).read());
		propChange.fireIndexedPropertyChange("AddressRegister", reg, null, addressReg.get(reg).read());
	}

	
	public short getAddressRegisterWord(int register) {
		int contents = addressReg.get(register).read();
		return (short) (contents & 0xFFFF);
	}

	
	public int getAddressRegisterLongWord(int register) {
		return addressReg.get(register).read();
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
