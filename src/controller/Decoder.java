package controller;

import Instructions.*;
import Instructions.Movement.*;

public class Decoder {
	
	/**
	 * Checks the first 4 bits of opcode and branches
	 * @param op
	 * @return
	 * @throws IllegalInstructionException 
	 */
	public static Instruction decode(int op) throws IllegalInstructionException {
		
		int op1 = (op >> 12);
		
		if (op1 <= 0x3) {
			if (op1 == 0) {
				
			}
			else{
				System.out.println("MOVE");
				return new Move(op);
			}
			
		}
		else if (op1 == 0x4) {
			
		}
		else if (op1 >= 0x5 && op1 <= 0x7) {
			
		}
		else if (op1 >= 0x8 && op1 <= 0xA) {
			
		}
		else if (op1 >= 0xC && op1 <= 0xD ) {
			
		}
		else if (op1 == 0xE) {
			
		}
		else if (op == 0xFFFF) {
			System.out.println("HALT");
			return null;
		}
		else {
			System.out.println("Illegal");
			throw new IllegalInstructionException();
		}
		
		return null;
	}

}
