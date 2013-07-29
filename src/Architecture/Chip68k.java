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
	
	private List<Register> dataReg;
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
		propChange.fireIndexedPropertyChange("memory", address, null, data);
	}
	
	public int readMemory(int address) {
		return memory.read(address);
	}
	
	
	/** PC ******************************************************/
	public void setPC(int address) {
		pc.write(address);
		propChange.firePropertyChange("pc", null, getPC());
	}
	
	public int getPC() {
		return pc.read();
	}

	
	/** Data ****************************************************/
	
	public void setDataRegister(int reg, byte data) {
		int contents = dataReg.get(reg).read() & 0xFFFFFF00;
		contents = contents + data;
		dataReg.get(reg).write(contents);
	}

	
	public void setDataRegister(int reg, short data) {
		int contents = dataReg.get(reg).read() & 0xFFFF0000;
		contents = contents + data;
		dataReg.get(reg).write(contents);
	}

	
	public void setDataRegister(int reg, int data) {
		dataReg.get(reg).write(data);
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
		contents = contents + data;
		addressReg.get(reg).write(contents);
		
	}

	
	public void setAddressRegister(int reg, int data) {
		addressReg.get(reg).write(data);
		
	}

	
	public short getAddressRegisterWord(int register) {
		int contents = addressReg.get(register).read();
		return (short) (contents & 0xFFFF);
	}

	
	public int getAddressRegisterLongWord(int register) {
		return addressReg.get(register).read();
	}

	
	
	

	
	

}
