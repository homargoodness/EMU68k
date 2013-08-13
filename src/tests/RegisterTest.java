package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import architecture.registers.GeneralRegister32Bit;

public class RegisterTest {
	
	int zero = 0;
	int positive = 150;
	int largePositive = 249865732;
	int negative = -20;
	int largeNegative = -38582863;
	int maxValue = Integer.MAX_VALUE;
	int minValue = Integer.MIN_VALUE;
	
	GeneralRegister32Bit reg;

	@Before
	public void setUp() throws Exception {
		 reg = new GeneralRegister32Bit();
	}
	
	@Test
	public void testZero() {
		reg.write(zero);
		assertEquals(zero, reg.read());
	}
	
	@Test
	public void testpositive() {
		reg.write(positive);
		assertEquals(positive, reg.read());
	}
	
	@Test
	public void testLargePositive() {
		reg.write(largePositive);
		assertEquals(largePositive, reg.read());
	}
	
	@Test
	public void testNegative() {
		reg.write(negative);
		assertEquals(negative, reg.read());
	}
	
	@Test
	public void testLargeNegative() {
		reg.write(largeNegative);
		assertEquals(largeNegative, reg.read());
	}
	
	@Test
	public void testMaxValue() {
		reg.write(maxValue);
		assertEquals(maxValue, reg.read());
	}
	
	@Test
	public void testMinValue() {
		reg.write(minValue);
		assertEquals(minValue, reg.read());
	}

}
