package instructions.addressingModes;

import architecture.Chip;
import instructions.IllegalInstructionException;
import static instructions.StaticReferences.DataSize;

public class DataRegisterDirect extends AddressingMode {

	//@Override
	public int use(DataSize size, int reg, Chip model) throws IllegalInstructionException {
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

	//@Override
	public void use(DataSize size, int reg, int value, Chip model) throws IllegalInstructionException {
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


}
