package instructions;

import static_variables.References.DataSize;
import architecture.ProcessorModel;
import static static_variables.References.*;

/**
 * Abstract class which all instruction classes must extend.
 * Also contains methods which help instructions derive the new status flag values
 * after an instruction has been performed.
 */
public abstract class Instruction {
	
	/**
	 * Contains all logic required to carry out an instruction.
	 * @param model The model which is to be manipulated by the instruction
	 * @throws IllegalInstructionException
	 */
	public abstract void execute(ProcessorModel model) throws IllegalInstructionException;
	
	/**
	 * Method which evaluates the values passed to it and return the value that the 
	 * Negative bit flag should be changed to
	 * @param dataSize the size of the operation (byte, word or long word)
	 * @param value the value to be checked
	 * @return the bit value for the N flag
	 */
	protected int evaluateNFlag(DataSize dataSize, long value) {
		if (dataSize == DataSize.BYTE ) {
			//set N bit
			if ((value & 0x80) < 0) return 0;
			else return 1;
		}
		
		else if (dataSize == DataSize.WORD ) {
			if ((value & 0x8000) < 0) return 0;
			else return 1;
		}
		
		else {
			if ((value & 0x80000000) < 0) return 0;
			else return 1;
		}
	}
	
	/**
	 * Method which evaluates the value passed to it and returns the appropriate
	 * value of the Zero bit flag.
	 * @param dataSize the size of the operation
	 * @param value the value to be evaluated
	 * @return the bit value of the Z flag
	 */
	protected int evaluateZFlag(DataSize dataSize, long value) {
		if (dataSize == DataSize.BYTE ) {
			if ((value & BYTE_MASK) == 0) return 1;
			else return 0;
			
		}
		else if (dataSize == DataSize.WORD ) {
			if ((value & WORD_MASK) == 0) return 1;
			else return 0;
		}
		else {
			if ((value & LONG_WORD_MASK) == 0) return 1;
			else return 0;
		}
	}
	
	/**
	 * Method which evaluates the value passed to it and returns the appropriate
	 * value of the Carry bit flag.
	 * @param dataSize
	 * @param value
	 * @return the bit value of the C flag
	 */
	protected int evaluateCFlag(DataSize dataSize, long value) {
		if (dataSize == DataSize.BYTE ) {
			if ((value & (~BYTE_MASK)) > 0) return 1;
			else return 0;
		}
		else if (dataSize == DataSize.BYTE ) {
			if ((value & (~WORD_MASK)) > 0) return 1;
			else return 0;
		}
		else {
			if ((value & (~LONG_WORD_MASK)) > 0) return 1;
			else return 0;
		}
	}
	
	/**
	 * Method which evaluates the value passed to it and returns the appropriate
	 * value of the Extend bit flag.
	 * @param dataSize
	 * @param value
	 * @return the bit value of the X flag
	 */
	protected int evaluateXFlag(DataSize dataSize, long value) {
		return 0;
	}
	
	/**
	 * Method which evaluates the value passed to it and returns the appropriate
	 * value of the overflow bit flag.
	 * @param dataSize
	 * @param value
	 * @return the bit value of the V flag
	 */
	protected int evaluateVFlag(DataSize dataSize, long value) {
		return 0;
	}

}
