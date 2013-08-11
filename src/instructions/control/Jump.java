package instructions.control;

import architecture.Chip;
import instructions.AddressingModeFactory;
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
		int mode = ((opCode >>> 3) & 0x7);
		int reg = (opCode & 0x7);
		
		if (mode == DATA_REG_DIRECT) {
			throw new IllegalInstructionException("Data Register Direct not allowed as addressing mode for JMP instruction");
		}
		else if (mode == ADDRESS_REG_DIRECT) {
			throw new IllegalInstructionException("Address Register Direct not allowed as addressing mode for JMP instruction");
		}
		else if (mode == ADDRESS_REG_INDIRECT_W_POSTINC) {
			throw new IllegalInstructionException("Address Register Indirect with Post Increment not allowed as addressing mode for JMP instruction");
		}
		else if (mode == ADDRESS_REG_INDIRECT_W_PREDEC) {
			throw new IllegalInstructionException("Address Register Indirect with Pre-Decrement not allowed as addressing mode for JMP instruction");
		}
		else if (mode == IMMEDIATE_MODE_FIELD) {
			if (reg == IMMEDIATE_REG_FIELD) {
				throw new IllegalInstructionException("Immediate Address not allowed as addressing mode for JMP instruction");
			}
		}
		else {
			AddressingMode addressMode = AddressingModeFactory.getMode(mode, reg);
			int operand = addressMode.use(DataSize.LONGWORD, reg, model);
			model.setPC(operand);
		}
	}
}
