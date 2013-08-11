package instructions;

public class StaticReferences {
	
	protected final static int DATA_REG_DIRECT = 0;
	protected final static int ADDRESS_REG_DIRECT = 1;
	protected final static int ADDRESS_REG_INDIRECT = 2;
	protected final static int ADDRESS_REG_INDIRECT_W_POSTINC = 3;
	protected final static int ADDRESS_REG_INDIRECT_W_PREDEC = 4;
	protected final static int ADDRESS_REG_INDIRECT_W_DISP = 5;
	protected final static int ADDRESS_REG_INDIRECT_W_INDEX_8BIT_DISP = 6;
	protected final static int ADDRESS_REG_INDIRECT_W_INDEX_BASE_DISP = 6;
	protected final static int MEMORY_INDIRECT_POSTINDEXED = 6;
	protected final static int MEMORY_INDIRECT_PREINDEXED = 6;
	
	protected final static int PC_INDIRECT_W_DISP_MODE_FIELD = 7;
	protected final static int PC_INDIRECT_W_DISP_REG_FIELD = 2;
	
	protected final static int PC_INDIRECT_W_INDEX_MODE_FIELD = 7;
	protected final static int PC_INDIR_W_INDEX_8BIT_DISP_REG_FIELD = 3;
	protected final static int PC_INDIR_W_INDEX_BASE_DISP_REG_FIED = 3;
	
	protected final static int PC_MEMORY_INDIRECT_MODE_FIELD = 7;
	protected final static int PC_MEM_INDIRECT_POSTINDEXED_REG_FIELD = 3;
	protected final static int PC_MEM_INDIRECT_PREINDEXED_REG_FIELD = 3;
	
	protected final static int ASBSOLUTE_MODE_FIELD = 7;
	protected final static int ABSOLUTE__SHORT_REG_FIELD = 0;
	protected final static int ABSOLUTE_LONG_REG_FIELD = 0;
	
	protected final static int IMMEDIATE_MODE_FIELD = 7;
	protected final static int IMMEDIATE_REG_FIELD = 4;
	
	public final static int BYTE_MASK = 0xFF;
	public final static int WORD_MASK = 0xFFFF;
	
	public static enum DataSize {BYTE, WORD, LONGWORD};

}
