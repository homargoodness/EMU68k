package instructions.addressingModes;

import instructions.IllegalInstructionException;
import architecture.Chip;

public class AddressRegisterIndirect extends AddressingMode {

	@Override
	public int use(DataSize size, int reg, Chip model) throws IllegalInstructionException {
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

	@Override
	public void use(DataSize size, int reg, int value, Chip model) throws IllegalInstructionException {
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

}
