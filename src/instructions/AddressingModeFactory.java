package instructions;

import instructions.addressing_modes.AddressRegisterDirect;
import instructions.addressing_modes.AddressRegisterIndirect;
import instructions.addressing_modes.AddressRegisterIndirectWDisplacement;
import instructions.addressing_modes.AddressRegisterIndirectWPostInc;
import instructions.addressing_modes.AddressRegisterIndirectWPreDec;
import instructions.addressing_modes.DataRegisterDirect;
import instructions.addressing_modes.Immediate;
import static static_variables.References.*;

public class AddressingModeFactory {
	
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
			//TODO
		}
		else if (mode == IMMEDIATE_MODE_FIELD) { 

			if (register == IMMEDIATE_REG_FIELD) { // immediate addressing
				return new Immediate();
			}
		}
		throw new IllegalInstructionException("MOVE source addressing mode not found");
		
	}

}
