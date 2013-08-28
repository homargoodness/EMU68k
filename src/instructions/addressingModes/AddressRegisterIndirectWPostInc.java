package instructions.addressingModes;

import instructions.IllegalInstructionException;
import static instructions.StaticReferences.DataSize;
import architecture.ProcessorModel;

public class AddressRegisterIndirectWPostInc extends AddressingMode {

	@Override
	public int use(DataSize size, int reg, ProcessorModel model) throws IllegalInstructionException {
		int contents; // holds the contents of the address register referenced
		switch (size) {
		case BYTE:
			contents = model.getAddressRegisterLongWord(reg);
			model.setAddressRegisterLongWord(reg, contents + 1);
			return model.readMemoryByte(contents);
		case WORD:
			contents = model.getAddressRegisterLongWord(reg);
			model.setAddressRegisterLongWord(reg, contents + 2);
			return model.readMemoryWord(contents);
		case LONGWORD:
			contents = model.getAddressRegisterLongWord(reg);
			model.setAddressRegisterLongWord(reg, contents + 4);
			return model.readMemoryLongWord(contents);
		default:
			throw new IllegalInstructionException("invalid size for Address Register Indirect with Post Increment read mode");
		}
	}

	@Override
	public void use(DataSize size, int reg, int value, ProcessorModel model) throws IllegalInstructionException {
		int contents; // holds the contents of the address register referenced
		switch (size) {
		case BYTE:
			contents = model.getAddressRegisterLongWord(reg);
			model.writeMemoryByte(contents, value);
			model.setAddressRegisterLongWord(reg, contents + 1);
			break;
		case WORD:
			contents = model.getAddressRegisterLongWord(reg);
			model.writeMemoryWord(contents, value);
			model.setAddressRegisterLongWord(reg, contents + 2);
			break;
		case LONGWORD:
			contents = model.getAddressRegisterLongWord(reg);
			model.writeMemoryLongWord(contents, value);
			model.setAddressRegisterLongWord(reg, contents + 4);
			break;
		default:
			throw new IllegalInstructionException("invalid size for Address Register Indirect with Post Increment write mode");
		}
		
	}

}
