package architecture;

import java.beans.PropertyChangeListener;


/**
 *
 */
public interface Chip {
	
	public void addListener(PropertyChangeListener listener);
	
	public void reset();
	
	
	/** Memory **/
	public void writeMemory(int address, byte data);
	
	public void writeMemory(int address, short data);
	
	public void writeMemory(int address, int data);
	
	public byte readMemoryByte(int address);
	
	public short readMemoryWord(int address);
	
	public int readMemoryLongWord(int address);
	
	
	/** PC **/
	public void setPC(int address);
	
	public int getPC();
	
	
	/** Data Register **/
	public void setDataRegister(int reg, byte data);
	
	public void setDataRegister(int reg, short data);
	
	public void setDataRegister(int reg, int data);
	
	
	public byte getDataRegisterByte(int reg);
	
	public short getDataRegisterWord(int reg);
	
	public int getDataRegisterLongWord(int reg);
	
	
	/** Address Register **/
	public void setAddressRegister(int reg, short data);
	
	public void setAddressRegister(int reg, int data);
	
	
	public short getAddressRegisterWord(int reg);
	
	public int getAddressRegisterLongWord(int reg);
	
	
	/** SR **/
	public int getSRCarryBit();
	
	public int getSROverflowBit();
	
	public int getSRZeroBit();
	
	public int getSRNegativeBit();
	
	public int getSRExtendBit();
	
	
	public void setSRCarryBit(int bit);
	
	public void setSROverflowBit(int bit);
	
	public void setSRZeroBit(int bit);
	
	public void setSRNegativeBit(int bit);
	
	public void setSRExtendBit(int bit);
	
	

}
