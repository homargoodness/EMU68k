package instructions.addressingModes;

import instructions.IllegalInstructionException;
import architecture.Chip;

public class Immediate extends AddressingMode {

	@Override
	public int use(DataSize size, int reg, Chip model) throws IllegalInstructionException {
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

	@Override
	public void use(DataSize size, int reg, int value, Chip model) throws IllegalInstructionException {
		throw new IllegalInstructionException("Can not write using Immediate Addressing");

	}

}
