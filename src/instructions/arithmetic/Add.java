package instructions.arithmetic;

import architecture.Chip;
import instructions.IllegalInstructionException;
import instructions.Instruction;

public class Add extends Instruction {

	private final int DIRECTION_EA_TO_DN = 0;
	private final int DIRECTION_DN_TO_EA = 1;

	private final int OPMODE_SIZE_BYTE = 0;
	private final int OPMODE_SIZE_WORD = 1;
	private final int OPMODE_SIZE_LONG_WORD = 2;

	private int opCode, direction, size, dataReg, eaMode, eaReg, operand1, operand2;
	private Chip model;

	public Add(int aCode) {
		opCode = aCode;
	}


	@Override
	public void execute(Chip aModel) throws IllegalInstructionException {

		model = aModel;

		direction = (opCode >>> 8) & 0x1;
		size = (opCode >>> 6) & 0x3;
		dataReg = (opCode >>> 9) & 0x7;
		eaMode = (opCode >> 3) & 0x7;
		eaReg = opCode & 0x7;

		operand1 = getSourceOperand();
	}

	private int getSourceOperand() throws IllegalInstructionException {
		
		
		// get the source operand
		if (direction == DIRECTION_DN_TO_EA) {
			return getDataRegisterDirect(dataSize, dataReg, model);
		}
		else {
			
			if (eaMode == ADDRESS_REG_INDIRECT) {
				return 1;
			}
			else throw new IllegalInstructionException();
			/*
			if (size == OPMODE_SIZE_BYTE) {
				operand1 = super.getOperandUsingAddressingMode(SIZE_BYTE, eaMode, eaReg, model);
			}
			else if (size == OPMODE_SIZE_WORD) {
				operand1 = super.getOperandUsingAddressingMode(SIZE_WORD, eaMode, eaReg, model);
			}
			else {
				operand1 = super.getOperandUsingAddressingMode(SIZE_LONG_WORD, eaMode, eaReg, model);
			}
			*/
		}


	}



}







