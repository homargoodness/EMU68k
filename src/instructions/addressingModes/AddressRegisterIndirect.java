package instructions.addressingModes;

import instructions.AddressingMode;
import instructions.IllegalInstructionException;
import static instructions.References.DataSize;
import architecture.ProcessorModel;

public class AddressRegisterIndirect implements AddressingMode {

	@Override
	public int use(DataSize size, int reg, ProcessorModel model) throws IllegalInstructionException {
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
	public void use(DataSize size, int reg, int value, ProcessorModel model) throws IllegalInstructionException {
		int contents; // holds the contents of the address register referenced
		switch (size) {
		case BYTE:
			contents = model.getAddressRegisterLongWord(reg);
			model.writeMemoryByte(contents, value);
			break;
		case WORD:
			contents = model.getAddressRegisterLongWord(reg);
			model.writeMemoryWord(contents, value);
			break;
		case LONGWORD:
			contents = model.getAddressRegisterLongWord(reg);
			model.writeMemoryLongWord(contents, value);
			break;
		default:
			throw new IllegalInstructionException("invalid size for Address Register Indirect write mode");
		
		}
	}

}
