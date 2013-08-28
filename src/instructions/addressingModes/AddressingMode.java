package instructions.addressingModes;

import instructions.IllegalInstructionException;
import static instructions.References.DataSize;
import architecture.ProcessorModel;

public interface AddressingMode {
	
	
	int use(DataSize size, int reg, ProcessorModel model) throws IllegalInstructionException;
	
	void use(DataSize size, int reg, int value, ProcessorModel model) throws IllegalInstructionException;
	
	

}
