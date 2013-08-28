package instructions.addressingModes;

import instructions.IllegalInstructionException;
import static instructions.StaticReferences.DataSize;
import architecture.ProcessorModel;

public class AddressRegisterDirect extends AddressingMode {

	@Override
	public int use(DataSize size, int reg, ProcessorModel model) throws IllegalInstructionException {
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

	@Override
	public void use(DataSize size, int reg, int value, ProcessorModel model) throws IllegalInstructionException {
		switch (size) {
		case BYTE:
			throw new IllegalInstructionException();
		case WORD:
			model.setAddressRegisterWord(reg, value);
			break;
		case LONGWORD:
			model.setAddressRegisterLongWord(reg, value);
			break;
		default:
			throw new IllegalInstructionException("invalid size for Address Register Direct write mode");
		}

	}

}
