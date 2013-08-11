package architecture;

import java.beans.PropertyChangeListener;


/**
 *
 */
public interface Chip {
	
	public void addListener(PropertyChangeListener listener);
	
	public void reset();
	
	
	/** Memory **/
	public void writeMemoryByte(int address, int data);
	
	public void writeMemoryWord(int address, int data);
	
	public void writeMemoryLongWord(int address, int data);
	
	public int readMemoryByte(int address);
	
	public int readMemoryWord(int address);
	
	public int readMemoryLongWord(int address);
	
	
	/** PC **/
	public void setPC(int address);
	
	public int getPC();
	
	
	/** Data Register **/
	public void setDataRegisterByte(int reg, int data);
	
	public void setDataRegisterWord(int reg, int data);
	
	public void setDataRegisterLongWord(int reg, int data);
	
	
	public int getDataRegisterByte(int reg);
	
	public int getDataRegisterWord(int reg);
	
	public int getDataRegisterLongWord(int reg);
	
	
	/** Address Register **/
	public void setAddressRegisterWord(int reg, int data);
	
	public void setAddressRegisterLongWord(int reg, int data);
	
	
	public int getAddressRegisterWord(int reg);
	
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
