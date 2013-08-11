package instructions;

import architecture.Chip;

public abstract class Instruction {
	
	//public enum DataSize {BYTE, WORD, LONGWORD};
	/*
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
	*/
	public abstract void execute(Chip model) throws IllegalInstructionException;
	
	// ADDRESSING MODES //
	/*
	protected int getDataRegisterDirect(DataSize size, int reg, Chip model) throws IllegalInstructionException {
		switch (size) {
			case BYTE:
				return model.getDataRegisterByte(reg);
			case WORD:
				return model.getDataRegisterWord(reg);
			case LONGWORD:
				return model.getDataRegisterLongWord(reg);
			default:
				throw new IllegalInstructionException("invalid size for Data Register Direct read mode");
		}
	}
	
	protected void setDataRegisterDirect(DataSize size, int reg,int value, Chip model) throws IllegalInstructionException {
		switch (size) {
			case BYTE:
				model.setDataRegister(reg, (byte)value);
				break;
			case WORD:
				model.setDataRegister(reg, (short)value);
				break;
			case LONGWORD:
				model.setDataRegister(reg, value);
				break;
			default:
				throw new IllegalInstructionException("invalid size for Data Register Direct write mode");
		}
	}
	
	protected int getAddressRegisterDirect(DataSize size, int reg, Chip model) throws IllegalInstructionException {
		switch (size) {
		case BYTE:
			throw new IllegalInstructionException("invalid size BYTE for Address Register Direct read mode");
		case WORD:
			return model.getAddressRegisterWord(reg);
		case LONGWORD:
			return model.getAddressRegisterLongWord(reg);
		default:
			throw new IllegalInstructionException("invalid size for Address Register Direct read mode");
		}
	}
	
	protected void setAddressRegisterDirect(DataSize size, int reg, int value, Chip model) throws IllegalInstructionException {
		switch (size) {
		case BYTE:
			throw new IllegalInstructionException();
		case WORD:
			model.setAddressRegister(reg, (short)value);
			break;
		case LONGWORD:
			model.setAddressRegister(reg, value);
			break;
		default:
			throw new IllegalInstructionException("invalid size for Address Register Direct write mode");
		}
	}
	
	protected int getAddressRegisterIndirect(DataSize size, int reg, Chip model) throws IllegalInstructionException {
		switch (size) {
		case BYTE:
			return model.readMemoryByte(model.getAddressRegisterLongWord(reg) & 0xFF);
		case WORD:
			return model.readMemoryLongWord(model.getAddressRegisterLongWord(reg));
		case LONGWORD:
			return model.readMemoryWord(model.getAddressRegisterLongWord(reg) & 0xFFFF);
		default:
			throw new IllegalInstructionException("invalid size for Address Register Indirect read mode");
		}
	}
	
	protected void setAddressRegisterIndirect(DataSize size, int reg, int value, Chip model) throws IllegalInstructionException {
		int contents; // holds the contents of the address register referenced
		switch (size) {
		case BYTE:
			contents = model.getAddressRegisterLongWord(reg);
			model.writeMemory(contents, (byte)value);
			break;
		case WORD:
			contents = model.getAddressRegisterLongWord(reg);
			model.writeMemory(contents, (short)value);
			break;
		case LONGWORD:
			contents = model.getAddressRegisterLongWord(reg);
			model.writeMemory(contents, value);
			break;
		default:
			throw new IllegalInstructionException("invalid size for Address Register Indirect write mode");
		}
	}
	
	protected int getAddressRegisterIndirectWPostInc(DataSize size, int reg, Chip model) throws IllegalInstructionException {
		int contents; // holds the contents of the address register referenced
		switch (size) {
		case BYTE:
			contents = model.getAddressRegisterLongWord(reg);
			model.setAddressRegister(reg, contents + 1);
			return model.readMemoryByte(contents);
		case WORD:
			contents = model.getAddressRegisterLongWord(reg);
			model.setAddressRegister(reg, contents + 2);
			return model.readMemoryWord(contents);
		case LONGWORD:
			contents = model.getAddressRegisterLongWord(reg);
			model.setAddressRegister(reg, contents + 4);
			return model.readMemoryLongWord(contents);
		default:
			throw new IllegalInstructionException("invalid size for Address Register Indirect with Post Increment read mode");
		}
	}
	
	protected void setAddressRegisterIndirectWPostInc(DataSize size, int reg, int value, Chip model) throws IllegalInstructionException {
		int contents; // holds the contents of the address register referenced
		switch (size) {
		case BYTE:
			contents = model.getAddressRegisterLongWord(reg);
			model.writeMemory(contents, (byte) value);
			model.setAddressRegister(reg, contents + 1);
			break;
		case WORD:
			contents = model.getAddressRegisterLongWord(reg);
			model.writeMemory(contents, (short) value);
			model.setAddressRegister(reg, contents + 2);
			break;
		case LONGWORD:
			contents = model.getAddressRegisterLongWord(reg);
			model.writeMemory(contents, value);
			model.setAddressRegister(reg, contents + 4);
			break;
		default:
			throw new IllegalInstructionException("invalid size for Address Register Indirect with Post Increment write mode");
		}
	}
	
	protected int getAddressRegisterIndirectWPreDec(DataSize size, int reg, Chip model) throws IllegalInstructionException {
		int contents; // holds the contents of the address register referenced
		switch (size) {
		case BYTE:
			contents = model.getAddressRegisterLongWord(reg);
			contents -= 1;
			model.setAddressRegister(reg, contents);
			return model.readMemoryByte(contents);
		case WORD:
			contents = model.getAddressRegisterLongWord(reg);
			contents -= 2;
			model.setAddressRegister(reg, contents);
			return model.readMemoryWord(contents);
		case LONGWORD:
			contents = model.getAddressRegisterLongWord(reg);
			contents -= 4;
			model.setAddressRegister(reg, contents);
			return model.readMemoryLongWord(contents);
		default:
			throw new IllegalInstructionException("invalid size for Address Register Indirect with Pre Decrement read mode");
		}
	}
	
	protected void setAddressRegisterIndirectWPreDec(DataSize size, int reg, int value, Chip model) throws IllegalInstructionException {
		int contents; // holds the contents of the address register referenced
		switch (size) {
		case BYTE:
			contents = model.getAddressRegisterLongWord(reg);
			contents -= 1;
			model.writeMemory(contents, (byte) value);
			model.setAddressRegister(reg, contents);
			break;
		case WORD:
			contents = model.getAddressRegisterLongWord(reg);
			contents -= 2;
			model.writeMemory(contents, (short) value);
			model.setAddressRegister(reg, contents);
			break;
		case LONGWORD:
			contents = model.getAddressRegisterLongWord(reg);
			contents -= 4;
			model.writeMemory(contents, value);
			model.setAddressRegister(reg, contents);
			break;
		default:
			throw new IllegalInstructionException("invalid size for Address Register Indirect with Pre Decrement write mode");
		}
	}
	
	protected int getAddressRegisterIndirectWDisp(DataSize size, int reg, Chip model) throws IllegalInstructionException {
		int contents; // holds the contents of the address register referenced
		int disp;
		switch (size) {
		case BYTE:
			contents = model.getAddressRegisterLongWord(reg);
			disp = model.readMemoryWord(model.getPC());
			model.setPC(model.getPC() + 2); // update PC????????????????
			contents += disp;
			return model.readMemoryByte(contents);
		case WORD:
			contents = model.getAddressRegisterLongWord(reg);
			disp = model.readMemoryWord(model.getPC());
			model.setPC(model.getPC() + 2); // update PC?????????????????
			contents += disp;
			return model.readMemoryWord(contents);
		case LONGWORD:
			contents = model.getAddressRegisterLongWord(reg);
			disp = model.readMemoryWord(model.getPC());
			model.setPC(model.getPC() + 2); // update PC???????????????????????
			contents += disp;
			return model.readMemoryLongWord(contents);
		default:
			throw new IllegalInstructionException("invalid size for Address Register Indirect with Displacement read mode");
		}
	}
	
	protected void setAddressRegisterIndirectWDisp(DataSize size, int reg, int value, Chip model) throws IllegalInstructionException {
		int contents; // holds the contents of the address register referenced
		int disp;
		switch (size) {
		case BYTE:
			contents = model.getAddressRegisterLongWord(reg);
			disp = model.readMemoryWord(model.getPC());
			model.setPC(model.getPC() + 2); // update PC??????????????
			contents += disp;
			model.writeMemory(contents, (byte)value);
			break;
		case WORD:
			contents = model.getAddressRegisterLongWord(reg);
			disp = model.readMemoryWord(model.getPC());
			model.setPC(model.getPC() + 2); // update PC??????????????????
			contents += disp;
			model.writeMemory(contents, (short)value);
			break;
		case LONGWORD:
			contents = model.getAddressRegisterLongWord(reg);
			disp = model.readMemoryWord(model.getPC());
			model.setPC(model.getPC() + 2); // update PC???????????????????
			contents += disp;
			model.writeMemory(contents, value);
			break;
		default:
			throw new IllegalInstructionException("invalid size for Address Register Indirect with Displacement write mode");
		}
	}
	
	protected int immediate(DataSize size, int reg, Chip model) throws IllegalInstructionException {
		int address;
		switch (size) {
		case BYTE:
			address = model.getPC();
			model.setPC(model.getPC() + 1); // update PC
			return model.readMemoryByte(address) & 0xFF;
		case WORD:
			address = model.getPC();
			model.setPC(model.getPC() + 2); // update PC
			return model.readMemoryWord(address);
		case LONGWORD:
			address = model.getPC();
			model.setPC(model.getPC() + 4); // update PC
			return model.readMemoryLongWord(address);
		default:
			throw new IllegalInstructionException("invalid size for Immediate Addressing mode");
		}
	}
	*/
	
	
	
	//protected int getOperandUsingAddressingMode(int size, int sourceMode, int source, Chip model) {
		
		/*
		if (sourceMode == DATA_REG_DIRECT) { // data register direct
			if (size == SIZE_BYTE) { //byte
				return model.getDataRegisterByte(source);
			}
			else if (size == SIZE_LONG_WORD) { //long word
				return model.getDataRegisterLongWord(source);
			}
			else if (size == SIZE_WORD) { // word
				return model.getDataRegisterWord(source);
			}
		}
		else if (sourceMode == ADDRESS_REG_DIRECT) { // address register direct
			if (size == SIZE_LONG_WORD) { //long word
				return model.getAddressRegisterLongWord(source);
			}
			else if (size == SIZE_WORD) { // word
				return model.getAddressRegisterWord(source);
			}
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT) { // address register indirect
			if (size == SIZE_BYTE) { //byte
				return model.readMemoryByte(model.getAddressRegisterLongWord(source) & 0xFF);
			}
			else if (size == SIZE_LONG_WORD) { //long word
				return model.readMemoryLongWord(model.getAddressRegisterLongWord(source));
			}
			else if (size == SIZE_WORD) { // word
				return model.readMemoryWord(model.getAddressRegisterLongWord(source) & 0xFFFF);
			}
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT_W_POSTINC) { // address register indirect with postincrement
			if (size == SIZE_BYTE) { // byte
				int contents = model.getAddressRegisterLongWord(source);
				model.setAddressRegister(source, contents + 1);
				return model.readMemoryByte(contents);
			}
			else if (size == SIZE_LONG_WORD) { // long word
				int contents = model.getAddressRegisterLongWord(source);
				model.setAddressRegister(source, contents + 4);
				return model.readMemoryLongWord(contents);
			}
			else if (size == SIZE_WORD) { // word
				int contents = model.getAddressRegisterLongWord(source);
				model.setAddressRegister(source, contents + 2);
				return model.readMemoryWord(contents);
			}
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT_W_PREDEC) { // address register indirect with predecrement
			if (size == SIZE_BYTE) { // byte
				int contents = model.getAddressRegisterLongWord(source);
				contents -= 1;
				model.setAddressRegister(source, contents);
				return model.readMemoryByte(contents);
			}
			else if (size == SIZE_LONG_WORD) { // long word
				int contents = model.getAddressRegisterLongWord(source);
				contents -= 4;
				model.setAddressRegister(source, contents);
				return model.readMemoryLongWord(contents);
			}
			else if (size == SIZE_WORD) { // word
				int contents = model.getAddressRegisterLongWord(source);
				contents -= 2;
				model.setAddressRegister(source, contents);
				return model.readMemoryWord(contents);
			}
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT_W_DISP) { // address register indirect with displacement
			if (size == SIZE_BYTE) { // byte
				int contents = model.getAddressRegisterLongWord(source);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				return model.readMemoryByte(contents);
			}
			if (size == SIZE_LONG_WORD) { // long word
				int contents = model.getAddressRegisterLongWord(source);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				return model.readMemoryLongWord(contents);
			}
			if (size == SIZE_WORD) { // word
				int contents = model.getAddressRegisterLongWord(source);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				return model.readMemoryWord(contents);
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
					int address = model.getPC();
					model.setPC(model.getPC() + 1); // update PC
					return model.readMemoryByte(model.getPC()) & 0xFF;
				}
				else if (size == SIZE_WORD) { // word
					int address = model.getPC();
					model.setPC(model.getPC() + 2); // update PC
					return model.readMemoryWord(model.getPC());
				}
				else if (size == SIZE_LONG_WORD) { // long word
					int address = model.getPC();
					model.setPC(model.getPC() + 4); // update PC
					return model.readMemoryLongWord(model.getPC());
				}
			}
		}
		return 0; // TODO instruction not yet supported exception
		*/
		//return 0;
		
	//}
	
	/*
	// write the operand to the appropriate destination
			if (destMode == DATA_REG_DIRECT) { //data register
				if (size == SIZE_BYTE ) { // byte
					model.setDataRegister(dest, (byte)operand);
				}
				else if (size == SIZE_LONG_WORD) { // long word
					model.setDataRegister(dest, operand);
				}
				else if (size == SIZE_WORD) { // word
					model.setDataRegister(dest, (short)operand);
				}
			}
			else if (destMode == ADDRESS_REG_DIRECT) {// address register
				if (size == SIZE_LONG_WORD) { // long word
					model.setAddressRegister(dest, operand);
				}
				else if (size == SIZE_WORD) { // word
					model.setAddressRegister(dest, (short) operand);
				}
			}
			else if (destMode == ADDRESS_REG_INDIRECT) { // address register indirect
				if (size == SIZE_BYTE) { // byte
					int contents = model.getAddressRegisterLongWord(dest);
					model.writeMemory(contents, (byte)operand);
				}
				else if (size == SIZE_LONG_WORD) { // long word
					int contents = model.getAddressRegisterLongWord(dest);
					model.writeMemory(contents, operand);
				}
				else if (size == SIZE_WORD) { // word
					int contents = model.getAddressRegisterLongWord(dest);
					model.writeMemory(contents, (short)operand);
				}
			}
			else if (destMode == ADDRESS_REG_INDIRECT_W_POSTINC) { // address register indirect with postincrement
				if (size == SIZE_BYTE) {
					int contents = model.getAddressRegisterLongWord(dest);
					model.writeMemory(contents, (byte) operand);
					model.setAddressRegister(dest, contents + 1);
				}
				else if (size == SIZE_LONG_WORD) {
					int contents = model.getAddressRegisterLongWord(dest);
					model.writeMemory(contents, operand);
					model.setAddressRegister(dest, contents + 4);
				}
				else if (size == SIZE_WORD) {
					int contents = model.getAddressRegisterLongWord(dest);
					model.writeMemory(contents, (short) operand);
					model.setAddressRegister(dest, contents + 2);
				}
			}
			else if (destMode == ADDRESS_REG_INDIRECT_W_PREDEC) { // address register indirect with predecrement
				if (size == SIZE_BYTE) {
					int contents = model.getAddressRegisterLongWord(dest);
					contents -= 1;
					model.writeMemory(contents, (byte) operand);
					model.setAddressRegister(dest, contents);
				}
				else if (size == SIZE_LONG_WORD) {
					int contents = model.getAddressRegisterLongWord(dest);
					contents -= 4;
					model.writeMemory(contents, operand);
					model.setAddressRegister(dest, contents);
				}
				else if (size == SIZE_WORD) {
					int contents = model.getAddressRegisterLongWord(dest);
					contents -= 2;
					model.writeMemory(contents, (short) operand);
					model.setAddressRegister(dest, contents);
				}
			}
			else if (destMode == ADDRESS_REG_INDIRECT_W_DISP) {//TODO test this!!!!!!! disp as source and dest?????????????
				if (size == SIZE_BYTE) { // byte
					int contents = model.getAddressRegisterLongWord(dest);
					int disp = model.readMemoryWord(model.getPC());
					model.setPC(model.getPC() + 2); // update PC
					contents += disp;
					model.writeMemory(contents, (byte)operand);
				}
				if (size == SIZE_LONG_WORD) { // long word
					int contents = model.getAddressRegisterLongWord(dest);
					int disp = model.readMemoryWord(model.getPC());
					model.setPC(model.getPC() + 2); // update PC
					contents += disp;
					model.writeMemory(contents, operand);
				}
				if (size == SIZE_WORD) { // word
					int contents = model.getAddressRegisterLongWord(dest);
					int disp = model.readMemoryWord(model.getPC());
					model.setPC(model.getPC() + 2); // update PC
					contents += disp;
					model.writeMemory(contents, (short)operand);
				}
			}
			else if (destMode == ADDRESS_REG_INDIRECT_W_INDEX_8BIT_DISP) {
				if (size == SIZE_BYTE) { // byte
					
				}
				if (size == SIZE_LONG_WORD) { // long word
					
				}
				if (size == SIZE_WORD) { // word
		
				}
			}
			*/

}
