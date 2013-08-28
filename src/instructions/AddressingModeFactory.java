package instructions;

import instructions.addressingModes.AddressRegisterDirect;
import instructions.addressingModes.AddressRegisterIndirect;
import instructions.addressingModes.AddressRegisterIndirectWDisplacement;
import instructions.addressingModes.AddressRegisterIndirectWPostInc;
import instructions.addressingModes.AddressRegisterIndirectWPreDec;
import instructions.addressingModes.DataRegisterDirect;
import instructions.addressingModes.Immediate;
import static instructions.References.*;

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
			
		}
		else if (mode == IMMEDIATE_MODE_FIELD) { 

			if (register == IMMEDIATE_REG_FIELD) { // immediate addressing
				return new Immediate();
			}
		}
		throw new IllegalInstructionException("MOVE source addressing mode not found");
		
	}

}
