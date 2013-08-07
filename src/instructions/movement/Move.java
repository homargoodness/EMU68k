package instructions.movement;

import architecture.Chip;
import instructions.Instruction;

/**
 * This class implements the MOVE instruction for the MC68k mircoprocessor.
 *
 * TODO addressing modes
 * TODO flags affected
 */
public class Move implements Instruction {

	//source mode 0 = data reg
	//source mode 1 = address
	//source mode 2 = memory
	//source mode 3 = memory with postinc
	//source mode 4 = memory woth predec
	//source mode 5 = memory with displacement
	//source mode 6 = memory with index
	//source mode 7
	//source 0 = abs short
	//source 1 = abs long
	//source 2 = PC with disp
	//source 3 = PC with index
	//source 4 = immeadiate

	//01 byte
	//10 long
	//11word

	private int opCode; // stores the 16 bit operation code for the instruction

	/**
	 * Constructor
	 * @param aCode the operation code
	 */
	public Move(int aCode) {
		opCode = aCode;
	}


	/**
	 * Inherited method which executes the MOVE instruction.
	 * @param model the the model which is changed by this instruction.
	 */
	public void execute(Chip model) {

		int size = (opCode >>> 0xC) & 0x3; // calculate the size parameter of the instruction (b, w, or l)
		int destMode = (opCode >>> 0x6) & 0x7; // dictates how to find the destination of the move opoeration 
		int dest = (opCode >>> 0x9) & 0x7; // calculate where to put the value which is being moved
		int sourceMode = (opCode >>> 0x3) & 0x7; // dictates how to find the source of the value being moved
		int source = opCode & 0x7; // calculate where to get the value which is being moved
		int operand = 0; // the value being moved
		
		// get the operand from the appropriate source
		if (sourceMode == 0) { // data register direct
			if (size == 1) { //byte
				operand = model.getDataRegisterByte(source);
			}
			else if (size == 2) { //long word
				operand = model.getDataRegisterLongWord(source);
			}
			else if (size == 3) { // word
				operand = model.getDataRegisterWord(source);
			}
		}
		else if (sourceMode == 1) { // address register direct
			if (size == 2) { //long word
				operand = model.getAddressRegisterLongWord(source);
			}
			else if (size == 3) { // word
				operand = model.getAddressRegisterWord(source);
			}
		}
		else if (sourceMode == 2) { // address register indirect
			if (size == 1) { //byte
				operand = model.readMemoryByte(model.getAddressRegisterLongWord(source) & 0xFF);
			}
			else if (size == 2) { //long word
				operand = model.readMemoryLongWord(model.getAddressRegisterLongWord(source));
			}
			else if (size == 3) { // word
				operand = model.readMemoryWord(model.getAddressRegisterLongWord(source) & 0xFFFF);
			}
		}
		else if (sourceMode == 3) { // address register indirect with postincrement
			if (size == 1) { // byte
				int contents = model.getAddressRegisterLongWord(source);
				model.setAddressRegister(source, contents + 1);
				operand = model.readMemoryByte(contents);
			}
			else if (size == 2) { // long word
				int contents = model.getAddressRegisterLongWord(source);
				model.setAddressRegister(source, contents + 4);
				operand = model.readMemoryLongWord(contents);
			}
			else if (size == 3) { // word
				int contents = model.getAddressRegisterLongWord(source);
				model.setAddressRegister(source, contents + 2);
				operand = model.readMemoryWord(contents);
			}
		}
		else if (sourceMode == 4) { // address register indirect with predecrement
			if (size == 1) { // byte
				int contents = model.getAddressRegisterLongWord(source);
				contents -= 1;
				model.setAddressRegister(source, contents);
				operand = model.readMemoryByte(contents);
			}
			else if (size == 2) { // long word
				int contents = model.getAddressRegisterLongWord(source);
				contents -= 4;
				model.setAddressRegister(source, contents);
				operand = model.readMemoryLongWord(contents);
			}
			else if (size == 3) { // word
				int contents = model.getAddressRegisterLongWord(source);
				contents -= 2;
				model.setAddressRegister(source, contents);
				operand = model.readMemoryWord(contents);
			}
		}
		else if (sourceMode == 5) { // address register indirect with displacement
			if (size == 1) { // byte
				int contents = model.getAddressRegisterLongWord(source);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				operand = model.readMemoryByte(contents);
			}
			if (size == 2) { // long word
				int contents = model.getAddressRegisterLongWord(source);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				operand = model.readMemoryLongWord(contents);
			}
			if (size == 3) { // word
				int contents = model.getAddressRegisterLongWord(source);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				operand = model.readMemoryWord(contents);
			}
		}
		else if (sourceMode == 6) { // address register indirect with index 
			if (size == 1) { // byte
				
			}
			if (size == 2) { // long word
				
			}
			if (size == 3) { // word
	
			}
		}
		else if (sourceMode == 7) { 

			if (source == 4) { // immediate addressing

				if (size == 1) { // byte
					operand = model.readMemoryByte(model.getPC()) & 0xFF;
					model.setPC(model.getPC() + 1); // update PC
				}
				else if (size == 3) { // word
					operand = model.readMemoryWord(model.getPC());
					model.setPC(model.getPC() + 2); // update PC
				}
				else if (size == 2) { // long word
					operand = model.readMemoryLongWord(model.getPC());
					model.setPC(model.getPC() + 4); // update PC
				}
			}
		}
		
		
		// write the operand to the appropriate destination
		if (destMode == 0) { //data register
			if (size == 1 ) { // byte
				model.setDataRegister(dest, (byte)operand);
			}
			else if (size == 2) { // long word
				model.setDataRegister(dest, operand);
			}
			else if (size == 3) { // word
				model.setDataRegister(dest, (short)operand);
			}
		}
		else if (destMode == 1) {// address register
			if (size == 2) { // long word
				model.setAddressRegister(dest, operand);
			}
			else if (size == 3) { // word
				model.setAddressRegister(dest, (short) operand);
			}
		}
		else if (destMode == 2) { // address register indirect
			if (size == 1) { // byte
				int contents = model.getAddressRegisterLongWord(dest);
				model.writeMemory(contents, (byte)operand);
			}
			else if (size == 2) { // long word
				int contents = model.getAddressRegisterLongWord(dest);
				model.writeMemory(contents, operand);
			}
			else if (size == 3) { // word
				int contents = model.getAddressRegisterLongWord(dest);
				model.writeMemory(contents, (short)operand);
			}
		}
		else if (destMode == 3) { // address register indirect with postincrement
			if (size == 1) {
				int contents = model.getAddressRegisterLongWord(dest);
				model.writeMemory(contents, (byte) operand);
				model.setAddressRegister(dest, contents + 1);
			}
			else if (size == 2) {
				int contents = model.getAddressRegisterLongWord(dest);
				model.writeMemory(contents, operand);
				model.setAddressRegister(dest, contents + 4);
			}
			else if (size == 3) {
				int contents = model.getAddressRegisterLongWord(dest);
				model.writeMemory(contents, (short) operand);
				model.setAddressRegister(dest, contents + 2);
			}
		}
		else if (destMode == 4) { // address register indirect with predecrement
			if (size == 1) {
				int contents = model.getAddressRegisterLongWord(dest);
				contents -= 1;
				model.writeMemory(contents, (byte) operand);
				model.setAddressRegister(dest, contents);
			}
			else if (size == 2) {
				int contents = model.getAddressRegisterLongWord(dest);
				contents -= 4;
				model.writeMemory(contents, operand);
				model.setAddressRegister(dest, contents);
			}
			else if (size == 3) {
				int contents = model.getAddressRegisterLongWord(dest);
				contents -= 2;
				model.writeMemory(contents, (short) operand);
				model.setAddressRegister(dest, contents);
			}
		}
		else if (destMode == 5) {//TODO test this!!!!!!! disp as source and dest?????????????
			if (size == 1) { // byte
				int contents = model.getAddressRegisterLongWord(dest);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				model.writeMemory(contents, (byte)operand);
			}
			if (size == 2) { // long word
				int contents = model.getAddressRegisterLongWord(dest);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				model.writeMemory(contents, operand);
			}
			if (size == 3) { // word
				int contents = model.getAddressRegisterLongWord(dest);
				int disp = model.readMemoryWord(model.getPC());
				model.setPC(model.getPC() + 2); // update PC
				contents += disp;
				model.writeMemory(contents, (short)operand);
			}
		}
		else if (destMode == 6) {
			if (size == 1) { // byte
				
			}
			if (size == 2) { // long word
				
			}
			if (size == 3) { // word
	
			}
		}
		else if (destMode == 7) {
			
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
