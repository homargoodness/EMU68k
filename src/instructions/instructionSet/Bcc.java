package instructions.instructionSet;

import architecture.ProcessorModel;
import instructions.IllegalInstructionException;
import instructions.Instruction;
import static instructions.StaticReferences.*;

public class Bcc extends Instruction {
	
	int opCode;
	
	public Bcc(int aCode) {
		opCode = aCode;
	}

	@Override
	public void execute(ProcessorModel model) throws IllegalInstructionException {
		int condition = (opCode >>> 8) & FIRST_4_BITS_MASK;
		int disp = opCode & BYTE_MASK;
		
		if (disp == 0) {
			disp = model.readMemoryWord(model.getPC());
		}
		else if (disp == 0xFF) {
			disp = model.readMemoryLongWord(model.getPC());
		}
			
		if (condition == TEST_TRUE) {
			
		}
		else if (condition == TEST_FALSE) {
			
		}
		else if (condition == TEST_HIGHER) {
			
		}
		else if (condition == TEST_LOW_OR_SAME) {
			
		}
		else if (condition == TEST_CARRY_CLEAR) {
			
		}
		else if (condition == TEST_CARRY_SET) {
			
		}
		else if (condition == TEST_NOT_EQUAL) {
			
		}
		else if (condition == TEST_EQUAL) {
			
		}
		else if (condition == TEST_OVERFLOW_CLEAR) {
			
		}
		else if (condition == TEST_OVERFLOW_SET) {
			
		}
		else if (condition == TEST_PLUS) {
			
		}
		else if (condition == TEST_MINUS) {
			
		}
		else if (condition == TEST_GREATER_OR_EQUAL) {
			
		}
		else if (condition == TEST_LESS_THAN) {
			if (((model.getSRNegativeBit() == 1) && (model.getSROverflowBit() == 0)) ||
			((model.getSRNegativeBit() == 0) && (model.getSROverflowBit() == 1))) {
				model.setPC(model.getPC() + disp);
			}
		}
		else if (condition == TEST_GREATER_THAN) {
			
		}
		else {
			
		}

	}

}
