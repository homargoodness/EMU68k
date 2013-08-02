package controller;

import instructions.*;
import instructions.movement.*;

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
				System.out.println("Illegal");
				throw new IllegalInstructionException();
			}
			else{
				System.out.println("MOVE");
				return new Move(op);
			}
			
			
		}
		else if (op1 == 0x4) {
			System.out.println("Illegal");
			throw new IllegalInstructionException();
			
		}
		else if (op1 >= 0x5 && op1 <= 0x7) {
			if (op1 == 7) {
				return new MoveQ(op);
			}
			else {
				System.out.println("Illegal");
				throw new IllegalInstructionException();
			}
			
		}
		else if (op1 >= 0x8 && op1 <= 0xA) {
			System.out.println("Illegal");
			throw new IllegalInstructionException();
			
		}
		else if (op1 >= 0xC && op1 <= 0xD ) {
			System.out.println("Illegal");
			throw new IllegalInstructionException();
			
		}
		else if (op1 == 0xE) {
			System.out.println("Illegal");
			throw new IllegalInstructionException();
			
		}
		else if (op == 0xFFFF) {
			System.out.println("HALT");
			return null;
		}
		else {
			System.out.println("Illegal");
			throw new IllegalInstructionException();
		}
		
	
	}

}
