package instructions;

public class StaticReferences {
	
	public final static int DATA_REG_DIRECT = 0;
	public final static int ADDRESS_REG_DIRECT = 1;
	public final static int ADDRESS_REG_INDIRECT = 2;
	public final static int ADDRESS_REG_INDIRECT_W_POSTINC = 3;
	public final static int ADDRESS_REG_INDIRECT_W_PREDEC = 4;
	public final static int ADDRESS_REG_INDIRECT_W_DISP = 5;
	public final static int ADDRESS_REG_INDIRECT_W_INDEX_8BIT_DISP = 6;
	public final static int ADDRESS_REG_INDIRECT_W_INDEX_BASE_DISP = 6;
	public final static int MEMORY_INDIRECT_POSTINDEXED = 6;
	public final static int MEMORY_INDIRECT_PREINDEXED = 6;
	
	public final static int PC_INDIRECT_W_DISP_MODE_FIELD = 7;
	public final static int PC_INDIRECT_W_DISP_REG_FIELD = 2;
	
	public final static int PC_INDIRECT_W_INDEX_MODE_FIELD = 7;
	public final static int PC_INDIR_W_INDEX_8BIT_DISP_REG_FIELD = 3;
	public final static int PC_INDIR_W_INDEX_BASE_DISP_REG_FIED = 3;
	
	public final static int PC_MEMORY_INDIRECT_MODE_FIELD = 7;
	public final static int PC_MEM_INDIRECT_POSTINDEXED_REG_FIELD = 3;
	public final static int PC_MEM_INDIRECT_PREINDEXED_REG_FIELD = 3;
	
	public final static int ASBSOLUTE_MODE_FIELD = 7;
	public final static int ABSOLUTE__SHORT_REG_FIELD = 0;
	public final static int ABSOLUTE_LONG_REG_FIELD = 0;
	
	public final static int IMMEDIATE_MODE_FIELD = 7;
	public final static int IMMEDIATE_REG_FIELD = 4;
	
	public final static int BYTE_MASK = 0xFF;
	public final static int WORD_MASK = 0xFFFF;
	public final static int LONG_WORD_MASK = 0xFFFFFFFF;
	
	public final static int FIRST_BIT_MASK = 0x1;
	public final static int FIRST_2_BITS_MASK = 0x3;
	public final static int FIRST_3_BITS_MASK = 0x7;
	public final static int FIRST_4_BITS_MASK = 0xF;
	
	public final static int TEST_TRUE = 0;
	public final static int TEST_FALSE = 0x1;
	public final static int TEST_HIGHER = 0x2;
	public final static int TEST_LOW_OR_SAME = 0x3;
	public final static int	TEST_CARRY_CLEAR = 0x4;
	public final static int TEST_CARRY_SET = 0x5;
	public final static int TEST_NOT_EQUAL = 0x6;
	public final static int TEST_EQUAL = 0x7;
	public final static int TEST_OVERFLOW_CLEAR = 0x8;
	public final static int TEST_OVERFLOW_SET = 0x9;
	public final static int TEST_PLUS = 0xA;
	public final static int TEST_MINUS = 0xB;
	public final static int TEST_GREATER_OR_EQUAL = 0xC;
	public final static int TEST_LESS_THAN = 0xD;
	public final static int TEST_GREATER_THAN = 0xE;
	public final static int TEST_LESS_OR_EQUAL = 0xF;
	
	public static enum DataSize {BYTE, WORD, LONGWORD};

}
