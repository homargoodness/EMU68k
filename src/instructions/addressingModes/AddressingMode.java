package instructions.addressingModes;

import instructions.IllegalInstructionException;
import static instructions.StaticReferences.DataSize;
import architecture.ProcessorModel;

public abstract class AddressingMode {
	
	//public enum DataSize {BYTE, WORD, LONGWORD};
	
	public abstract int use(DataSize size, int reg, ProcessorModel model) throws IllegalInstructionException;
	
	public abstract void use(DataSize size, int reg, int value, ProcessorModel model) throws IllegalInstructionException;
	
	

}
