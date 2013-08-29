package modelTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import architecture.ProcessorModel;
import architecture.Chip68k;

public class ChipTests {
	
	ProcessorModel model;

	@Before
	public void setUp() throws Exception {
		model = new Chip68k();
	}

	@Test
	public void writing_byte_to_empty_data_register_returns_correct_value() {
		int value = 0xC9;
		model.setDataRegisterByte(0, value);
		assertEquals(value, model.getDataRegisterByte(0));
		assertEquals(value, model.getDataRegisterWord(0));
		assertEquals(value, model.getDataRegisterLongWord(0));
	}
	
	@Test
	public void writing_word_to_empty_data_register_returns_correct_value() {
		int value = 0xFEB1;
		model.setDataRegisterWord(0, value);
		assertEquals(value & 0xFF, model.getDataRegisterByte(0));
		assertEquals(value, model.getDataRegisterWord(0));
		assertEquals(value, model.getDataRegisterLongWord(0));
	}
		
	@Test
	public void writing_long_word_to_empty_data_register_returns_correct_value() {
		int value = 0xEF56F4A8;
		model.setDataRegisterLongWord(0, value);
		assertEquals(value & 0xFF, model.getDataRegisterByte(0));
		assertEquals(value & 0xFFFF, model.getDataRegisterWord(0));
		assertEquals(value, model.getDataRegisterLongWord(0));
	}

	@Test
	public void writing_to_non_empty_data_register_byte_writes_value_only_to_lower_byte() {
		int contents = 0x8D45B459;
		int value = 5;
		
		model.setDataRegisterLongWord(0, contents);
		
		model.setDataRegisterByte(0, value);
		
		contents = (contents & 0xFFFFFF00) | value;
		
		assertEquals(contents & 0xFF, model.getDataRegisterByte(0));
		assertEquals(contents & 0xFFFF, model.getDataRegisterWord(0));
		assertEquals(contents, model.getDataRegisterLongWord(0));
	}
	
	@Test
	public void writing_to_non_empty_data_register_word_writes_value_only_to_lower_byte() {
		int contents = 0x8D45B459;
		int value = 0xA5F7;
		
		model.setDataRegisterLongWord(0, contents);
		
		model.setDataRegisterWord(0, value);
		
		contents = (contents & 0xFFFF0000) | value;
		
		assertEquals(contents & 0xFF, model.getDataRegisterByte(0));
		assertEquals(contents & 0xFFFF, model.getDataRegisterWord(0));
		assertEquals(contents, model.getDataRegisterLongWord(0));
	}
	
	@Test
	public void writing_to_non_empty_data_register_long_word_writes_value_only_to_lower_byte() {
		int contents = 0x8D45B459;
		int value = 0xFE567892;
		
		model.setDataRegisterLongWord(0, contents);
		
		model.setDataRegisterLongWord(0, value);
		
		assertEquals(value & 0xFF, model.getDataRegisterByte(0));
		assertEquals(value & 0xFFFF, model.getDataRegisterWord(0));
		assertEquals(value, model.getDataRegisterLongWord(0));
	}
	
	@Test
	public void using_byte_operation_with_operand_larger_than_byte_returns_correct_result() {
		int value = 0xFE567892;
		
		model.setDataRegisterByte(0, value);
		
		assertEquals(value & 0xFF, model.getDataRegisterLongWord(0));
	}
	
	@Test
	public void using_word_operation_with_operand_larger_than_word_returns_correct_result() {
		int value = 0xFE567892;
		
		model.setDataRegisterWord(0, value);
		
		assertEquals(value & 0xFFFF, model.getDataRegisterLongWord(0));
	}
	
	@Test
	public void writing_and_reading_byte_to_memory_returns_correct_results() {
		int value = 0x1F;
		
		model.writeMemoryByte(10, value);
		
		assertEquals(value, model.readMemoryByte(10));
	}
	
	@Test
	public void writing_and_reading_word_to_memory_returns_correct_results() {
		int value = 0x1FED;
		
		model.writeMemoryWord(10, value);
		
		assertEquals(value, model.readMemoryWord(10));
	}
	
	@Test
	public void writing_and_reading_long_word_to_memory_returns_correct_results() {
		int value = 0x1FEDFFEE;
		
		model.writeMemoryLongWord(10, value);
		
		assertEquals(value, model.readMemoryLongWord(10));
	}
	
	@Test
	public void writing_word_to_memory_writes_each_byte_to_correct_location() {
		int value = 0x1234;
		model.writeMemoryWord(10, value);
		
		assertEquals(value >>> 8, model.readMemoryByte(10));
		assertEquals(value & 0xFF, model.readMemoryByte(11));
	}
	
	@Test
	public void writing_long_word_to_memory_writes_each_byte_to_correct_location() {
		int value = 0x1F2F3E4C;
		model.writeMemoryLongWord(10, value);
		
		assertEquals(value >>> 24, model.readMemoryByte(10));
		assertEquals((value >>> 16) & 0xFF, model.readMemoryByte(11));
		assertEquals((value >>> 8) & 0xFF, model.readMemoryByte(12));
		assertEquals(value & 0xFF, model.readMemoryByte(13));
	}
	
	@Test
	public void setting_carry_bit_returns_correct_value() {
		model.setSRCarryBit(1);
		assertEquals(1, model.getSRCarryBit());
		
		model.setSRCarryBit(0);
		assertEquals(0, model.getSRCarryBit());
	}
	
	@Test
	public void setting_overflow_bit_returns_correct_value() {
		model.setSROverflowBit(1);
		assertEquals(1, model.getSROverflowBit());
		
		model.setSROverflowBit(0);
		assertEquals(0, model.getSROverflowBit());
	}
	
	@Test
	public void setting_zero_bit_returns_correct_value() {
		model.setSRZeroBit(1);
		assertEquals(1, model.getSRZeroBit());
		
		model.setSRZeroBit(0);
		assertEquals(0, model.getSRZeroBit());
	}
	
	@Test
	public void setting_negative_bit_returns_correct_value() {
		model.setSRNegativeBit(1);
		assertEquals(1, model.getSRNegativeBit());
		
		model.setSRNegativeBit(0);
		assertEquals(0, model.getSRNegativeBit());
	}
	
	@Test
	public void setting_extend_bit_returns_correct_value() {
		model.setSRExtendBit(1);
		assertEquals(1, model.getSRExtendBit());
		
		model.setSRExtendBit(0);
		assertEquals(0, model.getSRExtendBit());
	}
}
