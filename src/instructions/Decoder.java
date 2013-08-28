package instructions;

import instructions.*;
import instructions.instruction_set.*;

/**
 * Class which contains a single static method used to decode operation codes
 */
public class Decoder {
	
	/**
	 * Checks the first 4 or 8 bits (as appropriate) of the op code and returns the required
	 * instruction object
	 * @param op the opcode read in from memory
	 * @return the instruction object
	 * @throws IllegalInstructionException 
	 */
	public static Instruction decode(int op) throws IllegalInstructionException {
		
		int firstNibble = (op >>> 12); // the first 4 bits of the op code
		
		// analyse the first 4 bits of the op code to narrow down the instruction type
		// after the first 4 bits have been checked the rest of the bits are checked until the instruction type is determined
		if (firstNibble <= 0x3) {
			
			if (firstNibble == 0) {
				throw new IllegalInstructionException("Not yet implemented in decoder");
			}
			else{
				System.out.println("MOVE");
				return new Move(op);
			}
		}
		else if (firstNibble == 0x4) {
			if ((op & 0xFFF) == 0xE70) {
				throw new IllegalInstructionException("RESET instruction not yet implemented");
			}
			else if ((op & 0xFFF) == 0xE71) {
				throw new IllegalInstructionException("NOP instruction not yet implemented");
			}
			else if ((op & 0xFFF) == 0xE72) {
				throw new IllegalInstructionException("STOP instruction not yet implemented");
			}
			else if (((op >>> 6) & 0x3F) == 0x3B) {
				return new JMP(op);
			}
			throw new IllegalInstructionException("Not yet implemented in decoder");
		}
		else if (firstNibble >= 0x5 && firstNibble <= 0x7) {
			if (firstNibble == 6) {
				System.out.println("BCC");
				return new Bcc(op);
			}
			if (firstNibble == 7) {
				System.out.println("MOVEQ");
				return new MoveQ(op);
			}
			else {
				throw new IllegalInstructionException("Not yet implemented in decoder");
			}
			
		}
		else if (firstNibble >= 0x8 && firstNibble <= 0xB) {
			if (firstNibble == 0xB) {
				System.out.println("CMP");
				return new CMP(op);
			}
			throw new IllegalInstructionException("Not yet implemented in decoder");
			
		}
		else if (firstNibble >= 0xC && firstNibble <= 0xD ) {
			if (firstNibble == 0xD) { // TODO NEED TO BREAK THIS DOWN FURTHER
				System.out.println("ADD");
				return new Add(op);
			}
			else {
				throw new IllegalInstructionException("Not yet implemented in decoder");
			}
			
		}
		else if (firstNibble == 0xE) {
			throw new IllegalInstructionException("Not yet implemented in decoder");
			
		}
		else if (op == 0xFFFF) {
			
			System.out.println("HALT");
			return null;
		}
		else {
			throw new IllegalInstructionException("Invalid operation code");
		}
		
	
	}

}
