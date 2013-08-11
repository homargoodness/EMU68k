package instructions.addressingModes;

import instructions.IllegalInstructionException;
import static instructions.StaticReferences.DataSize;
import architecture.Chip;

public abstract class AddressingMode {
	
	//public enum DataSize {BYTE, WORD, LONGWORD};
	
	public abstract int use(DataSize size, int reg, Chip model) throws IllegalInstructionException;
	
	public abstract void use(DataSize size, int reg, int value, Chip model) throws IllegalInstructionException;
	
	

}
