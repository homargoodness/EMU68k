package instructions.addressingModes;

import instructions.AddressingMode;
import instructions.IllegalInstructionException;
import static instructions.References.DataSize;
import architecture.ProcessorModel;

public class AddressRegisterIndirectWPreDec implements AddressingMode {

	@Override
	public int use(DataSize size, int reg, ProcessorModel model) throws IllegalInstructionException {
		int contents; // holds the contents of the address register referenced
		switch (size) {
		case BYTE:
			contents = model.getAddressRegisterLongWord(reg);
			contents -= 1;
			model.setAddressRegisterLongWord(reg, contents);
			return model.readMemoryByte(contents);
		case WORD:
			contents = model.getAddressRegisterLongWord(reg);
			contents -= 2;
			model.setAddressRegisterLongWord(reg, contents);
			return model.readMemoryWord(contents);
		case LONGWORD:
			contents = model.getAddressRegisterLongWord(reg);
			contents -= 4;
			model.setAddressRegisterLongWord(reg, contents);
			return model.readMemoryLongWord(contents);
		default:
			throw new IllegalInstructionException("invalid size for Address Register Indirect with Pre Decrement read mode");
		}
	}

	@Override
	public void use(DataSize size, int reg, int value, ProcessorModel model) throws IllegalInstructionException {
		int contents; // holds the contents of the address register referenced
		switch (size) {
		case BYTE:
			contents = model.getAddressRegisterLongWord(reg);
			contents -= 1;
			model.writeMemoryByte(contents, value);
			model.setAddressRegisterLongWord(reg, contents);
			break;
		case WORD:
			contents = model.getAddressRegisterLongWord(reg);
			contents -= 2;
			model.writeMemoryWord(contents, value);
			model.setAddressRegisterLongWord(reg, contents);
			break;
		case LONGWORD:
			contents = model.getAddressRegisterLongWord(reg);
			contents -= 4;
			model.writeMemoryLongWord(contents, value);
			model.setAddressRegisterLongWord(reg, contents);
			break;
		default:
			throw new IllegalInstructionException("invalid size for Address Register Indirect with Pre Decrement write mode");
		}

	}

}
