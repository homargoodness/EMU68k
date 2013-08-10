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
	private DataSize dataSize;

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
		operand2 = getDestinationOperand();
		
		
		if (size == OPMODE_SIZE_BYTE) dataSize = DataSize.BYTE;
		if (size == OPMODE_SIZE_WORD) dataSize = DataSize.WORD;
		if (size == OPMODE_SIZE_LONG_WORD) dataSize = DataSize.LONGWORD;
	}

	private int getSourceOperand() throws IllegalInstructionException {
		// get the source operand
		if (direction == DIRECTION_DN_TO_EA) {
			return getDataRegisterDirect(dataSize, dataReg, model);
		}
		else {
			if (eaMode == DATA_REG_DIRECT) { // data register direct
				return getDataRegisterDirect(dataSize, eaReg, model);
			}
			else if (eaMode == ADDRESS_REG_DIRECT) { // address register direct
				return getAddressRegisterDirect(dataSize, eaReg, model);
			}
			else if (eaMode == ADDRESS_REG_INDIRECT) { // address register indirect
				return getAddressRegisterIndirect(dataSize, eaReg, model);
			}
			else if (eaMode == ADDRESS_REG_INDIRECT_W_POSTINC) { // address register indirect with postincrement
				return getAddressRegisterIndirectWPostInc(dataSize, eaReg, model);
			}
			else if (eaMode == ADDRESS_REG_INDIRECT_W_PREDEC) { // address register indirect with predecrement
				return getAddressRegisterIndirectWPreDec(dataSize, eaReg, model);
			}
			else if (eaMode == ADDRESS_REG_INDIRECT_W_DISP) { // address register indirect with displacement
				return getAddressRegisterIndirectWDisp(dataSize, eaReg, model);
			}
			else if (eaMode == ADDRESS_REG_INDIRECT_W_INDEX_8BIT_DISP) { // 6
				throw new IllegalInstructionException();
			}
			else if (eaMode == IMMEDIATE_MODE_FIELD) { 

				if (eaReg == IMMEDIATE_REG_FIELD) { // immediate addressing
					return immediate(dataSize, eaReg, model);
				}
			}
		}
		throw new IllegalInstructionException("Invalid address mode in ADD instruction");
	}
	
	private int getDestinationOperand() throws IllegalInstructionException {
		if (direction == DIRECTION_EA_TO_DN) {
			operand2 =  getDataRegisterDirect(dataSize, dataReg, model);
			operand2 += operand1;
			setDataRegisterDirect(dataSize, dataReg, operand2, model);
		}
		else {
			if (eaMode == DATA_REG_DIRECT) {
				throw new IllegalInstructionException("Data Register Direct is an invalid destination for ADD instruction");
			}
			else if (eaMode == ADDRESS_REG_DIRECT) { // address register direct
				throw new IllegalInstructionException("Address Register Direct is an nvalid destination for ADD instruction");
			}
			else if (eaMode == ADDRESS_REG_INDIRECT) {
				operand2 = getAddressRegisterIndirect(dataSize, eaReg, model);
				operand2 += operand1;
				setAddressRegisterIndirect(dataSize, eaReg, operand2, model);
			}
		}
		throw new IllegalInstructionException("Invalid address mode in ADD instruction");
	}



}







