package instructions.instructionSet;

import architecture.Chip;
import instructions.addressingModes.AddressingModeFactory;
import instructions.IllegalInstructionException;
import instructions.Instruction;
import instructions.addressingModes.AddressingMode;
import static instructions.StaticReferences.*;


/**
 * Class which implements the MC68k JUMP instruction
 */
public class Jump extends Instruction {

	int opCode;
	
	public Jump(int aCode) {
		opCode = aCode;
	}
	
	
	@Override
	public void execute(Chip model) throws IllegalInstructionException {
		int mode = ((opCode >>> 3) & FIRST_3_BITS_MASK);
		int reg = (opCode & FIRST_3_BITS_MASK);
		
		AddressingMode addressMode = AddressingModeFactory.getMode(mode, reg);
		int operand = addressMode.use(DataSize.LONGWORD, reg, model);
		model.setPC(operand);
			
		
	}
}
