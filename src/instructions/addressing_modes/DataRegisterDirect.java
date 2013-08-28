package instructions.addressing_modes;

import architecture.ProcessorModel;
import instructions.AddressingMode;
import instructions.IllegalInstructionException;
import static static_variables.References.DataSize;

public class DataRegisterDirect implements AddressingMode {

	@Override
	public int use(DataSize size, int reg, ProcessorModel model) throws IllegalInstructionException {
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

	@Override
	public void use(DataSize size, int reg, int value, ProcessorModel model) throws IllegalInstructionException {
		switch (size) {
		case BYTE:
			model.setDataRegisterByte(reg, value);
			break;
		case WORD:
			model.setDataRegisterWord(reg, value);
			break;
		case LONGWORD:
			model.setDataRegisterLongWord(reg, value);
			break;
		default:
			throw new IllegalInstructionException("invalid size for Data Register Direct write mode");
		}

	}


}
