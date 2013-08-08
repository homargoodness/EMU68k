package instructions;

import architecture.Chip;

public abstract class Instruction {
	
	
	protected final int DATA_REG_DIRECT = 0;
	protected final int ADDRESS_REG_DIRECT = 1;
	protected final int ADDRESS_REG_INDIRECT = 2;
	protected final int ADDRESS_REG_INDIRECT_W_POSTINC = 3;
	protected final int ADDRESS_REG_INDIRECT_W_PREDEC = 4;
	protected final int ADDRESS_REG_INDIRECT_W_DISP = 5;
	protected final int ADDRESS_REG_INDIRECT_W_INDEX_8BIT_DISP = 6;
	protected final int ADDRESS_REG_INDIRECT_W_INDEX_BASE_DISP = 6;
	protected final int MEMORY_INDIRECT_POSTINDEXED = 6;
	protected final int MEMORY_INDIRECT_PREINDEXED = 6;
	
	protected final int PC_INDIRECT_W_DISP_MODE_FIELD = 7;
	protected final int PC_INDIRECT_W_DISP_REG_FIELD = 2;
	
	protected final int PC_INDIRECT_W_INDEX_MODE_FIELD = 7;
	protected final int PC_INDIR_W_INDEX_8BIT_DISP_REG_FIELD = 3;
	protected final int PC_INDIR_W_INDEX_BASE_DISP_REG_FIED = 3;
	
	protected final int PC_MEMORY_INDIRECT_MODE_FIELD = 7;
	protected final int PC_MEM_INDIRECT_POSTINDEXED_REG_FIELD = 3;
	protected final int PC_MEM_INDIRECT_PREINDEXED_REG_FIELD = 3;
	
	protected final int ASBSOLUTE_MODE_FIELD = 7;
	protected final int ABSOLUTE__SHORT_REG_FIELD = 0;
	protected final int ABSOLUTE_LONG_REG_FIELD = 0;
	
	protected final int IMMEDIATE_MODE_FIELD = 7;
	protected final int IMMEDIATE_REG_FIELD = 4;
	
	
	
	public abstract void execute(Chip model);
	
	protected void getOperandUsingAddressingMode(int size, int sourceMode, int source) {
		int operand;
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

}
