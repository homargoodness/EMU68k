package instructions.instructionSet;

import architecture.ProcessorModel;
import instructions.addressingModes.AddressingModeFactory;
import instructions.IllegalInstructionException;
import instructions.Instruction;
import instructions.addressingModes.AddressingMode;
import instructions.addressingModes.DataRegisterDirect;
import static instructions.References.*;

/**
 * Class which implements the ADD instruction
 * 
 * Bits 7 to 9 define the opmode. Bit 9 defines the result is written to. If the bit is 1, the result is written to the location
 * defined by the addressing mode. Bits 7 and 8 define the size of the instruction
 * 
 * Bits 10 to 12 define the data register to be used.
 * 
 * The first 6 bits define the addressing mode.
 * 
 * First 4 bits are always set to D for this instruction.
 * 
 * 
 */
public class Add extends Instruction {

	private final int DIRECTION_EA_TO_DN = 0;
	private final int DIRECTION_DN_TO_EA = 1;

	private final int OPMODE_SIZE_BYTE = 0;
	private final int OPMODE_SIZE_WORD = 1;
	private final int OPMODE_SIZE_LONG_WORD = 2;

	private int opCode, direction, size, dataReg, eaMode, eaReg, operand1, operand2;
	private ProcessorModel model;
	private DataSize dataSize;

	public Add(int aCode) {
		opCode = aCode;
	}

	@Override
	public void execute(ProcessorModel aModel) throws IllegalInstructionException {

		model = aModel;

		direction = (opCode >>> 8) & 0x1;
		size = (opCode >>> 6) & 0x3;
		dataReg = (opCode >>> 9) & 0x7;
		eaMode = (opCode >> 3) & 0x7;
		eaReg = opCode & 0x7;
		
		if (size == OPMODE_SIZE_BYTE) dataSize = DataSize.BYTE;
		else if (size == OPMODE_SIZE_WORD) dataSize = DataSize.WORD;
		else dataSize = DataSize.LONGWORD;

		operand1 = getDataRegisterOperand();
		operand2 = getEffectiveAddressOperand();
	
		long result = operand1 + operand2 ;
		setFlags(result);
		writeResult((int)result & 0xFFFFFFFF);
	}

	/**
	 * Helper method which gets the operand from the sepcified data register
	 * @return the operand.
	 * @throws IllegalInstructionException
	 */
	private int getDataRegisterOperand() throws IllegalInstructionException {
		AddressingMode dataRegisterDirect = new DataRegisterDirect();
		return dataRegisterDirect.use(dataSize, dataReg, model);
	}
	
	/**
	 * Helper method which uses the addressing mode specified by the instruction to retrieve
	 * the operand.
	 * @return the operand
	 * @throws IllegalInstructionException
	 */
	private int getEffectiveAddressOperand() throws IllegalInstructionException {
		AddressingMode addressMode = AddressingModeFactory.getMode(eaMode, eaReg);
		return addressMode.use(dataSize, eaReg, model);
	}
	
	/**
	 * Helper method which writes the result in the correct destination specified by the direction
	 * dictated by the opcode. The destination will either be a data register or specified by the addressing mode.
	 * @param result the result of the add operation
	 * @throws IllegalInstructionException
	 */
	private void writeResult(int result) throws IllegalInstructionException {
		if (direction == DIRECTION_EA_TO_DN) {
			AddressingMode dataRegisterDirect = new DataRegisterDirect();
			dataRegisterDirect.use(dataSize, dataReg, result, model);
		}
		else {
			AddressingMode addressMode = AddressingModeFactory.getMode(eaMode, eaReg);
			addressMode.use(dataSize, eaReg, result, model);
		}
	}
	
	/**
	 * Helper method which sets the flags for this instruction
	 * @param result the reult off the add operation
	 */
	private void setFlags(long result) {
		
		model.setSRNegativeBit(super.evaluateNFlag(dataSize, result));
		model.setSRZeroBit(super.evaluateZFlag(dataSize, result));
	}
	
	



}







