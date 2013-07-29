package Architecture;

import java.beans.PropertyChangeListener;


/**
 *
 */
public interface Chip {
	
	public void addListener(PropertyChangeListener listener);
	
	public void writeMemory(int address, byte data);
	
	public byte readMemory(int address);
	
	public void setPC(int address);
	
	public int getPC();
	
	
	public void setDataRegister(int reg, byte data);
	
	public void setDataRegister(int reg, short data);
	
	public void setDataRegister(int reg, int data);
	
	public byte getDataRegisterByte(int register);
	
	public short getDataRegisterWord(int register);
	
	public int getDataRegisterLongWord(int register);
	
	
	public void setAddressRegister(int reg, short data);
	
	public void setAddressRegister(int reg, int data);
	
	public short getAddressRegisterWord(int register);
	
	public int getAddressRegisterLongWord(int register);

}
