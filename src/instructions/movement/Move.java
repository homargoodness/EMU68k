package instructions.movement;

import architecture.Chip;
import instructions.Instruction;

/**
 * This class implements the MOVE instruction for the MC68k mircoprocessor.
 *
 */
public class Move extends Instruction {

	private final int SIZE_BYTE = 1;
	private final int SIZE_LONG_WORD = 2;
	private final int SIZE_WORD = 3;
	
	private int opCode; // stores the 16 bit operation code for the instruction

	/**
	 * Constructor
	 * @param aCode the operation code
	 */
	public Move(int aCode) {
		opCode = aCode;
	}

	@Override
	public void execute(Chip model) {

		int size = (opCode >>> 0xC) & 0x3; // calculate the size parameter of the instruction (b, w, or l)
		int destMode = (opCode >>> 0x6) & 0x7; // dictates how to find the destination of the move opoeration 
		int dest = (opCode >>> 0x9) & 0x7; // calculate where to put the value which is being moved
		int sourceMode = (opCode >>> 0x3) & 0x7; // dictates how to find the source of the value being moved
		int source = opCode & 0x7; // calculate where to get the value which is being moved
		int operand = 0; // the value being moved
		
		// get the operand from the appropriate source
		if (sourceMode == DATA_REG_DIRECT) { // data register direct
			if (size == SIZE_BYTE) { //byte
				operand = model.getDataRegisterByte(source);
			}
			else if (size == SIZE_LONG_WORD) { //long word
				operand = model.getDataRegisterLongWord(source);
			}
			else if (size == SIZE_WORD) { // word
				operand = model.getDataRegisterWord(source);
			}
		}
		else if (sourceMode == ADDRESS_REG_DIRECT) { // address register direct
			if (size == SIZE_LONG_WORD) { //long word
				operand = model.getAddressRegisterLongWord(source);
			}
			else if (size == SIZE_WORD) { // word
				operand = model.getAddressRegisterWord(source);
			}
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT) { // address register indirect
			if (size == SIZE_BYTE) { //byte
				operand = model.readMemoryByte(model.getAddressRegisterLongWord(source) & 0xFF);
			}
			else if (size == SIZE_LONG_WORD) { //long word
				operand = model.readMemoryLongWord(model.getAddressRegisterLongWord(source));
			}
			else if (size == SIZE_WORD) { // word
				operand = model.readMemoryWord(model.getAddressRegisterLongWord(source) & 0xFFFF);
			}
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT_W_POSTINC) { // address register indirect with postincrement
			if (size == SIZE_BYTE) { // byte
				int contents = model.getAddressRegisterLongWord(source);
				model.setAddressRegister(source, contents + 1);
				operand = model.readMemoryByte(contents);
			}
			else if (size == SIZE_LONG_WORD) { // long word
				int contents = model.getAddressRegisterLongWord(source);
				model.setAddressRegister(source, contents + 4);
				operand = model.readMemoryLongWord(contents);
			}
			else if (size == SIZE_WORD) { // word
				int contents = model.getAddressRegisterLongWord(source);
				model.setAddressRegister(source, contents + 2);
				operand = model.readMemoryWord(contents);
			}
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT_W_PREDEC) { // address register indirect with predecrement
			if (size == SIZE_BYTE) { // byte
				int contents = model.getAddressRegisterLongWord(source);
				contents -= 1;
				model.setAddressRegister(source, contents);
				operand = model.readMemoryByte(contents);
			}
			else if (size == SIZE_LONG_WORD) { // long word
				int contents = model.getAddressRegisterLongWord(source);
				contents -= 4;
				model.setAddressRegister(source, contents);
				operand = model.readMemoryLongWord(contents);
			}
			else if (size == SIZE_WORD) { // word
				int contents = model.getAddressRegisterLongWord(source);
				contents -= 2;
				model.setAddressRegister(source, contents);
				operand = model.readMemoryWord(contents);
			}
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT_W_DISP) { // address register indirect with displacement
			if (size == SIZE_BYTE) { // byte
				int contents = model.getAddressRegisterLongWord(source);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				operand = model.readMemoryByte(contents);
			}
			if (size == SIZE_LONG_WORD) { // long word
				int contents = model.getAddressRegisterLongWord(source);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				operand = model.readMemoryLongWord(contents);
			}
			if (size == SIZE_WORD) { // word
				int contents = model.getAddressRegisterLongWord(source);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				operand = model.readMemoryWord(contents);
			}
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT_W_INDEX_8BIT_DISP) { // 6
			if (size == SIZE_BYTE) { // byte
				
			}
			if (size == SIZE_LONG_WORD) { // long word
				
			}
			if (size == SIZE_WORD) { // word
	
			}
		}
		else if (sourceMode == IMMEDIATE_MODE_FIELD) { 

			if (source == IMMEDIATE_REG_FIELD) { // immediate addressing

				if (size == SIZE_BYTE) { // byte
					operand = model.readMemoryByte(model.getPC()) & 0xFF;
					model.setPC(model.getPC() + 1); // update PC
				}
				else if (size == SIZE_WORD) { // word
					operand = model.readMemoryWord(model.getPC());
					model.setPC(model.getPC() + 2); // update PC
				}
				else if (size == SIZE_LONG_WORD) { // long word
					operand = model.readMemoryLongWord(model.getPC());
					model.setPC(model.getPC() + 4); // update PC
				}
			}
		}
		
		
		// write the operand to the appropriate destination
		if (destMode == DATA_REG_DIRECT) { //data register
			if (size == SIZE_BYTE ) { // byte
				model.setDataRegister(dest, (byte)operand);
			}
			else if (size == SIZE_LONG_WORD) { // long word
				model.setDataRegister(dest, operand);
			}
			else if (size == SIZE_WORD) { // word
				model.setDataRegister(dest, (short)operand);
			}
		}
		else if (destMode == ADDRESS_REG_DIRECT) {// address register
			if (size == SIZE_LONG_WORD) { // long word
				model.setAddressRegister(dest, operand);
			}
			else if (size == SIZE_WORD) { // word
				model.setAddressRegister(dest, (short) operand);
			}
		}
		else if (destMode == ADDRESS_REG_INDIRECT) { // address register indirect
			if (size == SIZE_BYTE) { // byte
				int contents = model.getAddressRegisterLongWord(dest);
				model.writeMemory(contents, (byte)operand);
			}
			else if (size == SIZE_LONG_WORD) { // long word
				int contents = model.getAddressRegisterLongWord(dest);
				model.writeMemory(contents, operand);
			}
			else if (size == SIZE_WORD) { // word
				int contents = model.getAddressRegisterLongWord(dest);
				model.writeMemory(contents, (short)operand);
			}
		}
		else if (destMode == ADDRESS_REG_INDIRECT_W_POSTINC) { // address register indirect with postincrement
			if (size == SIZE_BYTE) {
				int contents = model.getAddressRegisterLongWord(dest);
				model.writeMemory(contents, (byte) operand);
				model.setAddressRegister(dest, contents + 1);
			}
			else if (size == SIZE_LONG_WORD) {
				int contents = model.getAddressRegisterLongWord(dest);
				model.writeMemory(contents, operand);
				model.setAddressRegister(dest, contents + 4);
			}
			else if (size == SIZE_WORD) {
				int contents = model.getAddressRegisterLongWord(dest);
				model.writeMemory(contents, (short) operand);
				model.setAddressRegister(dest, contents + 2);
			}
		}
		else if (destMode == ADDRESS_REG_INDIRECT_W_PREDEC) { // address register indirect with predecrement
			if (size == SIZE_BYTE) {
				int contents = model.getAddressRegisterLongWord(dest);
				contents -= 1;
				model.writeMemory(contents, (byte) operand);
				model.setAddressRegister(dest, contents);
			}
			else if (size == SIZE_LONG_WORD) {
				int contents = model.getAddressRegisterLongWord(dest);
				contents -= 4;
				model.writeMemory(contents, operand);
				model.setAddressRegister(dest, contents);
			}
			else if (size == SIZE_WORD) {
				int contents = model.getAddressRegisterLongWord(dest);
				contents -= 2;
				model.writeMemory(contents, (short) operand);
				model.setAddressRegister(dest, contents);
			}
		}
		else if (destMode == ADDRESS_REG_INDIRECT_W_DISP) {//TODO test this!!!!!!! disp as source and dest?????????????
			if (size == SIZE_BYTE) { // byte
				int contents = model.getAddressRegisterLongWord(dest);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				model.writeMemory(contents, (byte)operand);
			}
			if (size == SIZE_LONG_WORD) { // long word
				int contents = model.getAddressRegisterLongWord(dest);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				model.writeMemory(contents, operand);
			}
			if (size == SIZE_WORD) { // word
				int contents = model.getAddressRegisterLongWord(dest);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				model.writeMemory(contents, (short)operand);
			}
		}
		else if (destMode == ADDRESS_REG_INDIRECT_W_INDEX_8BIT_DISP) {
			if (size == SIZE_BYTE) { // byte
				
			}
			if (size == SIZE_LONG_WORD) { // long word
				
			}
			if (size == SIZE_WORD) { // word
	
			}
		}
		
		
		
		// set SR flags
		model.setSROverflowBit(0); // overflow bit is always set to 0
		model.setSRCarryBit(0); // carry bit is always set to 0
		if (operand < 0) { // if operand is negative set N flag to 1 othersise set it to 0
			System.out.println(operand);
			model.setSRNegativeBit(1);
		}
		else model.setSRNegativeBit(0);
		if (operand == 0) { // if operand is 0 set Z flag to 1 otherwise 0
			model.setSRZeroBit(1);
		}
		else model.setSRZeroBit(0);
	}

}
