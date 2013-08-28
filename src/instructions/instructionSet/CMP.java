package instructions.instructionSet;

import org.ietf.jgss.Oid;

import architecture.ProcessorModel;
import instructions.IllegalInstructionException;
import instructions.Instruction;
import static instructions.References.*;
import instructions.References.DataSize;
import instructions.addressingModes.AddressingMode;
import instructions.addressingModes.AddressingModeFactory;

public class CMP extends Instruction {

	private final int BYTE = 0x0;
	private final int WORD = 0x1;
	private final int LONG_WORD = 0x2;
	
	private int opCode;
	
	public CMP(int aCode) {
		opCode = aCode;
	}
	
	@Override
	public void execute(ProcessorModel model) throws IllegalInstructionException {
		int operand1;
		int operand2;
		
		int dataReg = (opCode >>> 9) & FIRST_3_BITS_MASK;
		int opMode = (opCode >>> 6) & FIRST_3_BITS_MASK;
		int eaMode = (opMode >>> 3) & FIRST_3_BITS_MASK;
		int eaReg = opMode & FIRST_3_BITS_MASK;
		
		DataSize dataSize;
		if (opMode == BYTE) {
			dataSize = DataSize.BYTE;
			operand1 = model.getDataRegisterByte(dataReg);
		}
		else if (opMode == WORD) {
			dataSize = DataSize.WORD;
			operand1 = model.getDataRegisterWord(dataReg);
		}
		else {
			dataSize = DataSize.LONGWORD;
			operand1 = model.getDataRegisterLongWord(dataReg);
		}

		AddressingMode addressMode = AddressingModeFactory.getMode(eaMode, eaReg);
		operand2 = addressMode.use(dataSize, eaReg, model);
		
		long result = operand1 - operand2;
		
		model.setSRNegativeBit(evaluateNFlag(dataSize, result));
		model.setSRZeroBit(evaluateZFlag(dataSize, result));
		model.setSRCarryBit(evaluateCFlag(dataSize, result));
		model.setSROverflowBit(evaluateVFlag(dataSize, result));
	}

}
