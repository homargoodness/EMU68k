package modelTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import architecture.Chip;
import architecture.Chip68k;

public class ChipTests {
	
	Chip model;

	@Before
	public void setUp() throws Exception {
		model = new Chip68k();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
