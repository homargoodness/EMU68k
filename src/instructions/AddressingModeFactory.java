package instructions;

import instructions.addressingModes.*;
import static instructions.StaticReferences.*;


/**
 * Class which contains a static method which returns the appropriate class of addressing mode according to the mode passed in.
 */
public class AddressingModeFactory {

	/**
	 * Checks the mode and register values and returns the appropriate class for the addressing mode required.
	 * @param mode the instruction mode provided by the operation code
	 * @param register the register indicated by the operation code
	 * @return the correct addressing mode object
	 * @throws IllegalInstructionException
	 */
	public static AddressingMode getMode(int mode, int register ) throws IllegalInstructionException {
		
		if (mode == DATA_REG_DIRECT ) {
			return new DataRegisterDirect();
		}
		else if (mode == ADDRESS_REG_DIRECT) { // address register direct
			return new AddressRegisterDirect();
		}
		else if (mode == ADDRESS_REG_INDIRECT) { // address register indirect
			return new AddressRegisterIndirect();
		}
		else if (mode == ADDRESS_REG_INDIRECT_W_POSTINC) { // address register indirect with postincrement
			return new AddressRegisterIndirectWPostInc();
		}
		else if (mode == ADDRESS_REG_INDIRECT_W_PREDEC) { // address register indirect with predecrement
			return new AddressRegisterIndirectWPreDec();
		}
		else if (mode == ADDRESS_REG_INDIRECT_W_DISP) { // address register indirect with displacement
			return new AddressRegisterIndirectWDisplacement();
		}
		else if (mode == ADDRESS_REG_INDIRECT_W_INDEX_8BIT_DISP) { // 6
			throw new IllegalInstructionException("Address Register Indirect with Index 8 bit Displacement not yet implemented");
		}
		else if (mode == IMMEDIATE_MODE_FIELD) { 

			if (register == IMMEDIATE_REG_FIELD) { // immediate addressing
				return new Immediate();
			}
		}
		throw new IllegalInstructionException("MOVE source addressing mode not found");
		
	}

}
