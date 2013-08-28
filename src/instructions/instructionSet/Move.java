package instructions.instructionSet;

import architecture.ProcessorModel;
import instructions.*;
import instructions.addressingModes.*;
//import static instructions.StaticReferences.DataSize;
//import static instructions.StaticReferences.BYTE_MASK;
//import static instructions.StaticReferences.WORD_MASK;
import static instructions.References.*;

/**
 * This class implements the MOVE instruction for the MC68k microprocessor.
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
	public void execute(ProcessorModel model) throws IllegalInstructionException {

		int size = (opCode >>> 0xC) & 0x3; // calculate the size parameter of the instruction (b, w, or l)
		int destMode = (opCode >>> 0x6) & 0x7; // dictates how to find the destination of the move opoeration 
		int dest = (opCode >>> 0x9) & 0x7; // calculate where to put the value which is being moved
		int sourceMode = (opCode >>> 0x3) & 0x7; // dictates how to find the source of the value being moved
		int source = opCode & 0x7; // calculate where to get the value which is being moved
		int operand = 0; // the value being moved TODO make into long
		
		DataSize dataSize = null;
		//int mask;
		if (size == SIZE_BYTE) {
			dataSize = DataSize.BYTE;
			//mask = BYTE_MASK;
			
		}
		else if (size == SIZE_WORD) {
			dataSize = DataSize.WORD;
			//mask = WORD_MASK;
		}
		else if (size == SIZE_LONG_WORD) {
			dataSize = DataSize.LONGWORD;
		}
		
		// get the value to move
		AddressingMode addressingMode = AddressingModeFactory.getMode(sourceMode, source);
		operand = addressingMode.use(dataSize, source, model);
		
		// move the value to the appropriate destination
		addressingMode = AddressingModeFactory.getMode(destMode, dest);
		addressingMode.use(dataSize, dest, operand, model);
		/*
		// get the operand from the appropriate source TODO abstract this to a seperate class which returns the appropriate address mode class????
		if (sourceMode == DATA_REG_DIRECT) { // data register direct
			operand = getDataRegisterDirect(dataSize, source, model);
		}
		else if (sourceMode == ADDRESS_REG_DIRECT) { // address register direct
			operand = getAddressRegisterDirect(dataSize, source, model);
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT) { // address register indirect
			operand = getAddressRegisterIndirect(dataSize, source, model);
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT_W_POSTINC) { // address register indirect with postincrement
			operand = getAddressRegisterIndirectWPostInc(dataSize, source, model);
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT_W_PREDEC) { // address register indirect with predecrement
			operand = getAddressRegisterIndirectWPreDec(dataSize, source, model);
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT_W_DISP) { // address register indirect with displacement
			operand = getAddressRegisterIndirectWDisp(dataSize, source, model);
		}
		else if (sourceMode == ADDRESS_REG_INDIRECT_W_INDEX_8BIT_DISP) { // 6
			throw new IllegalInstructionException();
		}
		else if (sourceMode == IMMEDIATE_MODE_FIELD) { 

			if (source == IMMEDIATE_REG_FIELD) { // immediate addressing
				operand = immediate(dataSize, source, model);
			}
		}
		else throw new IllegalInstructionException("MOVE source addressing mode not found");
		
		
		// write the operand to the appropriate destination
		if (destMode == DATA_REG_DIRECT) { //data register
			setDataRegisterDirect(dataSize, dest, operand, model);
		}
		else if (destMode == ADDRESS_REG_DIRECT) {// address register
			setAddressRegisterDirect(dataSize, dest, operand, model);
		}
		else if (destMode == ADDRESS_REG_INDIRECT) { // address register indirect
			setAddressRegisterIndirect(dataSize, dest, operand, model);
		}
		else if (destMode == ADDRESS_REG_INDIRECT_W_POSTINC) { // address register indirect with postincrement
			setAddressRegisterIndirectWPostInc(dataSize, dest, operand, model);
		}
		else if (destMode == ADDRESS_REG_INDIRECT_W_PREDEC) { // address register indirect with predecrement
			setAddressRegisterIndirectWPreDec(dataSize, dest, operand, model);
		}
		else if (destMode == ADDRESS_REG_INDIRECT_W_DISP) {//TODO test this!!!!!!! disp as source and dest?????????????
			setAddressRegisterIndirectWDisp(dataSize, dest, operand, model);
		}
		else if (destMode == ADDRESS_REG_INDIRECT_W_INDEX_8BIT_DISP) {
			throw new IllegalInstructionException();
		}
		else throw new IllegalInstructionException("MOVE destination addressing mode not found");
		
		*/
		
		// set SR flags
		model.setSROverflowBit(0); // overflow bit is always set to 0
		model.setSRCarryBit(0); // carry bit is always set to 0
		if (operand < 0) { // if operand is negative set N flag to 1 othersise set it to 0
			model.setSRNegativeBit(1);
		}
		else model.setSRNegativeBit(0);
		if (operand == 0) { // if operand is 0 set Z flag to 1 otherwise 0
			model.setSRZeroBit(1);
		}
		else model.setSRZeroBit(0);
	}

}
