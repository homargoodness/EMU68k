package Architecture;

import java.beans.PropertyChangeListener;


/**
 *
 */
public interface Chip {
	
	public void addListener(PropertyChangeListener listener);
	
	
	/** Memory **/
	public void writeMemory(int address, byte data);
	
	public byte readMemory(int address);
	
	
	/** PC **/
	public void setPC(int address);
	
	public int getPC();
	
	
	/** Data Register **/
	public void setDataRegister(int reg, byte data);
	
	public void setDataRegister(int reg, short data);
	
	public void setDataRegister(int reg, int data);
	
	
	public byte getDataRegisterByte(int register);
	
	public short getDataRegisterWord(int register);
	
	public int getDataRegisterLongWord(int register);
	
	
	/** Address Register **/
	public void setAddressRegister(int reg, short data);
	
	public void setAddressRegister(int reg, int data);
	
	
	public short getAddressRegisterWord(int register);
	
	public int getAddressRegisterLongWord(int register);
	
	
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
