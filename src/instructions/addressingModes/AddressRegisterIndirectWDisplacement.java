package instructions.addressingModes;

import instructions.IllegalInstructionException;
import static instructions.StaticReferences.DataSize;
import architecture.Chip;

public class AddressRegisterIndirectWDisplacement extends AddressingMode {

	@Override
	public int use(DataSize size, int reg, Chip model) throws IllegalInstructionException {
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

	@Override
	public void use(DataSize size, int reg, int value, Chip model) throws IllegalInstructionException {
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

}
