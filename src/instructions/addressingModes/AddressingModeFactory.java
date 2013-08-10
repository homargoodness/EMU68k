package instructions.addressingModes;

import instructions.IllegalInstructionException;

public class AddressingModeFactory {
	
	protected final static int DATA_REG_DIRECT = 0;
	protected final static int ADDRESS_REG_DIRECT = 1;
	protected final static int ADDRESS_REG_INDIRECT = 2;
	protected final static int ADDRESS_REG_INDIRECT_W_POSTINC = 3;
	protected final static int ADDRESS_REG_INDIRECT_W_PREDEC = 4;
	protected final static int ADDRESS_REG_INDIRECT_W_DISP = 5;
	protected final static int ADDRESS_REG_INDIRECT_W_INDEX_8BIT_DISP = 6;
	protected final int ADDRESS_REG_INDIRECT_W_INDEX_BASE_DISP = 6;
	protected final int MEMORY_INDIRECT_POSTINDEXED = 6;
	protected final int MEMORY_INDIRECT_PREINDEXED = 6;
	
	protected final int PC_INDIRECT_W_DISP_MODE_FIELD = 7;
	protected final int PC_INDIRECT_W_DISP_REG_FIELD = 2;
	
	protected final int PC_INDIRECT_W_INDEX_MODE_FIELD = 7;
	protected final int PC_INDIR_W_INDEX_8BIT_DISP_REG_FIELD = 3;
	protected final int PC_INDIR_W_INDEX_BASE_DISP_REG_FIED = 3;
	
	protected final int PC_MEMORY_INDIRECT_MODE_FIELD = 7;
	protected final int PC_MEM_INDIRECT_POSTINDEXED_REG_FIELD = 3;
	protected final int PC_MEM_INDIRECT_PREINDEXED_REG_FIELD = 3;
	
	protected final int ASBSOLUTE_MODE_FIELD = 7;
	protected final int ABSOLUTE__SHORT_REG_FIELD = 0;
	protected final int ABSOLUTE_LONG_REG_FIELD = 0;
	
	protected final static int IMMEDIATE_MODE_FIELD = 7;
	protected final static int IMMEDIATE_REG_FIELD = 4;
	
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
