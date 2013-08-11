package controller;

import instructions.*;
import instructions.arithmetic.Add;
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
		
		int op1 = (op >> 12);
		
		if (op1 <= 0x3) {
			
			if (op1 == 0) {
				throw new IllegalInstructionException("Not yet implemented in decoder");
			}
			else{
				System.out.println("MOVE");
				return new Move(op);
			}
		}
		else if (op1 == 0x4) {
			throw new IllegalInstructionException("Not yet implemented in decoder");
			
		}
		else if (op1 >= 0x5 && op1 <= 0x7) {
			if (op1 == 7) {
				System.out.println("MOVEQ");
				return new MoveQ(op);
			}
			else {
				throw new IllegalInstructionException("Not yet implemented in decoder");
			}
			
		}
		else if (op1 >= 0x8 && op1 <= 0xA) {
			throw new IllegalInstructionException("Not yet implemented in decoder");
			
		}
		else if (op1 >= 0xC && op1 <= 0xD ) {
			if (op1 == 0xD) { // TODO NEED TO BREAK THIS DOWN FURTHER
				System.out.println("ADD");
				return new Add(op);
			}
			else {
				throw new IllegalInstructionException("Not yet implemented in decoder");
			}
			
		}
		else if (op1 == 0xE) {
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
