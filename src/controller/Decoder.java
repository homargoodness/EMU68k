package controller;

import Instructions.*;
import Instructions.Movement.*;

public class Decoder {
	
	/**
	 * Checks the first 4 bits of opcode and branches
	 * @param op
	 * @return
	 */
	public static Instruction decode(int op) {
		
		if (op/0xFFF <= 0x3) {
			if (op/0xFFF == 0) {
				
			}
			else return new Move(op);
			
		}
		else if (op/0xFFF == 0x4) {
			
		}
		else if (op/0xFFF >= 0x5 && op/0xFFF <= 0x7) {
			
		}
		else if (op/0xFFF >= 0x8 && op/0xFFF <= 0xA) {
			
		}
		else if (op/0xFFF >= 0xC && op/0xFFF <= 0xD ) {
			
		}
		else if (op/0xFFF == 0xE) {
			
		}
	
		return null;
		
	}

}
