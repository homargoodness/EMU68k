package instructions.movement;

import architecture.Chip;
import instructions.Instruction;

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

	private int opCode;

	/**
	 * 
	 * @param aCode
	 */
	public Move(int aCode) {
		opCode = aCode;
	}


	@Override
	public void execute(Chip model) {

		int size = (opCode >>> 0xC) & 0x3;
		int destMode = (opCode >>> 0x6) & 0x7;
		int dest = (opCode >>> 0x9) & 0x7;
		int sourceMode = (opCode >>> 0x3) & 0x7;
		int source = opCode & 0x7;
		int operand = 0;
		
		// Source
		if (sourceMode == 0) { //data register direct
			if (size == 1) { //byte
				operand = model.getDataRegisterByte(source);
			}
			if (size == 2) { //long word
				operand = model.getDataRegisterLongWord(source);
			}
			if (size == 3) { // word
				operand = model.getDataRegisterWord(source);
			}
		}
		else if (sourceMode == 1) {// address register direct

		}
		else if (sourceMode == 2) { // address register indirect

		}
		else if (sourceMode == 3) {

		}
		else if (sourceMode == 4) {

		}
		else if (sourceMode == 5) {

		}
		else if (sourceMode == 6) {

		}
		else if (sourceMode == 7) { 

			if (source == 4) { // immediate

				if (size == 1) { // byte
					operand = model.readMemoryByte(model.getPC()) & 0xFF;
				}
				else if (size == 3) { // word
					operand = model.readMemoryWord(model.getPC());
				}
				else if (size == 2) { // long word
					operand = model.readMemoryLongWord(model.getPC());
					
				}

			}
		}
		
		
		// Destination
		if (destMode == 0) { //data reg
			if (size == 1 ) {
				model.setDataRegister(dest, (byte)operand);
			}
			else if (size == 2) {
				model.setDataRegister(dest, operand);
			}
			else if (size == 3) {
				model.setDataRegister(dest, (short)operand);
			}
		}
		else if (destMode == 1) {// address register
			if (size == 2) {
				model.setAddressRegister(dest, (short)operand);
			}
			else if (size == 3) {
				model.setAddressRegister(dest, operand);
			}
		}
		
		
		// set SR flags
		model.setSROverflowBit(0);
		model.setSRCarryBit(0);
		if (operand < 0) {
			System.out.println(operand);
			model.setSRNegativeBit(1);
		}
		else model.setSRNegativeBit(0);
		if (operand == 0) {
			model.setSRZeroBit(1);
		}
		else model.setSRZeroBit(0);


	}

}
