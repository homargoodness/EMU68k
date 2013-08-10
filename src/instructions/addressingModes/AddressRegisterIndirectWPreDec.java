package instructions.addressingModes;

import instructions.IllegalInstructionException;
import architecture.Chip;

public class AddressRegisterIndirectWPreDec extends AddressingMode {

	@Override
	public int use(DataSize size, int reg, Chip model) throws IllegalInstructionException {
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

	@Override
	public void use(DataSize size, int reg, int value, Chip model) throws IllegalInstructionException {
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

}
