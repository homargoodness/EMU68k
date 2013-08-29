package modelTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import architecture.memory.Memory68k;
import architecture.memory.MemoryAccessException;

public class MemoryTests {
	
	Memory68k mem;
	
	byte [] nums = {Byte.MIN_VALUE, 0, 23, 
			56, 78, -57, 120, Byte.MAX_VALUE};
	
	int [] locs = {1,5,-8,100,34567,5766879, -9483726, 16777215 };

	@Before
	public void setUp() throws Exception {
		mem = new Memory68k();
	}

	@Test
	public void testContigousSpace() {
		
		for (int i = 0; i < 8; i++) {
			mem.write(i,nums[i]);
			
			assertEquals(nums[i], mem.read(i));
		}
	}
	
	@Test
	public void testRandomAccess() {
		
		for (int i = 0; i < 8; i++) {
			mem.write(locs[i], nums[i]);
			
			assertEquals(nums[i], mem.read(locs[i]));
		}
	}
	
}
