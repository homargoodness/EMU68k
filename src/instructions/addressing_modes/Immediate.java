package instructions.addressing_modes;

import instructions.AddressingMode;
import instructions.IllegalInstructionException;
import static static_variables.References.DataSize;
import architecture.ProcessorModel;

public class Immediate implements AddressingMode {

	@Override
	public int use(DataSize size, int reg, ProcessorModel model) throws IllegalInstructionException {
		int address;
		switch (size) {
		case BYTE:
			address = model.getPC() + 1;
			model.setPC(model.getPC() + 2); // update PC by 2 despite only reading from first byte to keep PC on a word boundry (even address)
			return model.readMemoryByte(address);
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
	public void use(DataSize size, int reg, int value, ProcessorModel model) throws IllegalInstructionException {
		throw new IllegalInstructionException("Can not write using Immediate Addressing");

	}

}
