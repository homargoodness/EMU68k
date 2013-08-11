package controller;

import instructions.*;
import instructions.arithmetic.*;
import instructions.control.*;
import instructions.movement.*;

/**
 * Class which contains a single static method use to decode operation codes
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
		
		int opMSB = (op >> 12); // the first 4 bits of the op code
		
		if (opMSB <= 0x3) {
			
			if (opMSB == 0) {
				throw new IllegalInstructionException("Not yet implemented in decoder");
			}
			else{
				System.out.println("MOVE");
				return new Move(op);
			}
		}
		else if (opMSB == 0x4) {
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
				return new Jump(op);
			}
			throw new IllegalInstructionException("Not yet implemented in decoder");
		}
		else if (opMSB >= 0x5 && opMSB <= 0x7) {
			if (opMSB == 7) {
				System.out.println("MOVEQ");
				return new MoveQ(op);
			}
			else {
				throw new IllegalInstructionException("Not yet implemented in decoder");
			}
			
		}
		else if (opMSB >= 0x8 && opMSB <= 0xA) {
			throw new IllegalInstructionException("Not yet implemented in decoder");
			
		}
		else if (opMSB >= 0xC && opMSB <= 0xD ) {
			if (opMSB == 0xD) { // TODO NEED TO BREAK THIS DOWN FURTHER
				System.out.println("ADD");
				return new Add(op);
			}
			else {
				throw new IllegalInstructionException("Not yet implemented in decoder");
			}
			
		}
		else if (opMSB == 0xE) {
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
