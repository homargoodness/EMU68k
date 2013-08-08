package instructions.arithmetic;

import architecture.Chip;
import instructions.Instruction;

public class Add extends Instruction {

	private final int DIRECTION_EA_TO_DN = 0;
	private final int DIRECTION_DN_TO_EA = 1;

	private final int OPMODE_SIZE_BYTE = 0;
	private final int OPMODE_SIZE_WORD = 1;
	private final int OPMODE_SIZE_LONG_WORD = 2;

	private int opCode, direction, size, dataReg, eaMode, eaReg, operand1, operand2;

	public Add(int aCode) {
		opCode = aCode;
	}


	@Override
	public void execute(Chip model) {

		direction = (opCode >>> 8) & 0x1;
		size = (opCode >>> 6) & 0x3;
		dataReg = (opCode >>> 9) & 0x7;
		eaMode = (opCode >> 3) & 0x7;
		eaReg = opCode & 0x7;

		operand1 = getOperand1(model);
	}

	private int getOperand1(Chip model) {

		if (direction == DIRECTION_DN_TO_EA) {
			if (opModeSize == OPMODE_SIZE_BYTE) {
				return model.getDataRegisterByte(dataReg);
			}
			else if (opModeSize == OPMODE_SIZE_WORD) {
				return model.getDataRegisterWord(dataReg);
			}
			else{
				return model.getDataRegisterLongWord(dataReg);
			}
		}
		else {
			
			if (sourceMode == DATA_REG_DIRECT) { // data register direct
				if (size == SIZE_BYTE) { //byte
					operand = model.getDataRegisterByte(source);
				}
				else if (size == SIZE_LONG_WORD) { //long word
					operand = model.getDataRegisterLongWord(source);
				}
				else if (size == SIZE_WORD) { // word
					operand = model.getDataRegisterWord(source);
				}
			}
			else if (sourceMode == ADDRESS_REG_DIRECT) { // address register direct
				if (size == SIZE_LONG_WORD) { //long word
					operand = model.getAddressRegisterLongWord(source);
				}
				else if (size == SIZE_WORD) { // word
					operand = model.getAddressRegisterWord(source);
				}
			}
			else if (sourceMode == ADDRESS_REG_INDIRECT) { // address register indirect
				if (size == SIZE_BYTE) { //byte
					operand = model.readMemoryByte(model.getAddressRegisterLongWord(source) & 0xFF);
				}
				else if (size == SIZE_LONG_WORD) { //long word
					operand = model.readMemoryLongWord(model.getAddressRegisterLongWord(source));
				}
				else if (size == SIZE_WORD) { // word
					operand = model.readMemoryWord(model.getAddressRegisterLongWord(source) & 0xFFFF);
				}
			}
			else if (sourceMode == ADDRESS_REG_INDIRECT_W_POSTINC) { // address register indirect with postincrement
				if (size == SIZE_BYTE) { // byte
					int contents = model.getAddressRegisterLongWord(source);
					model.setAddressRegister(source, contents + 1);
					operand = model.readMemoryByte(contents);
				}
				else if (size == SIZE_LONG_WORD) { // long word
					int contents = model.getAddressRegisterLongWord(source);
					model.setAddressRegister(source, contents + 4);
					operand = model.readMemoryLongWord(contents);
				}
				else if (size == SIZE_WORD) { // word
					int contents = model.getAddressRegisterLongWord(source);
					model.setAddressRegister(source, contents + 2);
					operand = model.readMemoryWord(contents);
				}
			}
			else if (sourceMode == ADDRESS_REG_INDIRECT_W_PREDEC) { // address register indirect with predecrement
				if (size == SIZE_BYTE) { // byte
					int contents = model.getAddressRegisterLongWord(source);
					contents -= 1;
					model.setAddressRegister(source, contents);
					operand = model.readMemoryByte(contents);
				}
				else if (size == SIZE_LONG_WORD) { // long word
					int contents = model.getAddressRegisterLongWord(source);
					contents -= 4;
					model.setAddressRegister(source, contents);
					operand = model.readMemoryLongWord(contents);
				}
				else if (size == SIZE_WORD) { // word
					int contents = model.getAddressRegisterLongWord(source);
					contents -= 2;
					model.setAddressRegister(source, contents);
					operand = model.readMemoryWord(contents);
				}
			}
			else if (sourceMode == ADDRESS_REG_INDIRECT_W_DISP) { // address register indirect with displacement
				if (size == SIZE_BYTE) { // byte
					int contents = model.getAddressRegisterLongWord(source);
					int disp = model.readMemoryWord(model.getPC());
					model.setPC(model.getPC() + 2); // update PC
					contents += disp;
					operand = model.readMemoryByte(contents);
				}
				if (size == SIZE_LONG_WORD) { // long word
					int contents = model.getAddressRegisterLongWord(source);
					int disp = model.readMemoryWord(model.getPC());
					model.setPC(model.getPC() + 2); // update PC
					contents += disp;
					operand = model.readMemoryLongWord(contents);
				}
				if (size == SIZE_WORD) { // word
					int contents = model.getAddressRegisterLongWord(source);
					int disp = model.readMemoryWord(model.getPC());
					model.setPC(model.getPC() + 2); // update PC
					contents += disp;
					operand = model.readMemoryWord(contents);
				}
			}
			else if (sourceMode == ADDRESS_REG_INDIRECT_W_INDEX_8BIT_DISP) { // 6
				if (size == SIZE_BYTE) { // byte
					
				}
				if (size == SIZE_LONG_WORD) { // long word
					
				}
				if (size == SIZE_WORD) { // word
		
				}
			}
			else if (sourceMode == IMMEDIATE_MODE_FIELD) { 

				if (source == IMMEDIATE_REG_FIELD) { // immediate addressing

					if (size == SIZE_BYTE) { // byte
						operand = model.readMemoryByte(model.getPC()) & 0xFF;
						model.setPC(model.getPC() + 1); // update PC
					}
					else if (size == SIZE_WORD) { // word
						operand = model.readMemoryWord(model.getPC());
						model.setPC(model.getPC() + 2); // update PC
					}
					else if (size == SIZE_LONG_WORD) { // long word
						operand = model.readMemoryLongWord(model.getPC());
						model.setPC(model.getPC() + 4); // update PC
					}
				}
			}

		}
		return 0;
	}





}


