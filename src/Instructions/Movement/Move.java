package Instructions.Movement;

import Architecture.Chip;
import Instructions.Instruction;

public class Move implements Instruction {
	
	private int opCode;
	
	public Move(int aCode) {
		opCode = aCode;
	}
	

	@Override
	public void execute(Chip model) {
		
		int size = (opCode >> 0xC) & 0x3;
		int destMode = (opCode >> 0x6) & 0x7;
		int dest = (opCode >> 0x9) & 0x7;
		int sourceMode = (opCode >> 0x3) & 0x7;
		int source = opCode & 0x7;
		
		if (sourceMode == 7) { // TODO this needs further options
			if (source == 4) {
				System.out.println("immeadiate addressing");
				if (size == 1) {
					int [] operand = new int[2];
					operand[0] = model.readMemory((int)model.getPC()); // TODO any reason to get first byte?????
					model.setPC(model.getPC() + 1);
					operand[1] = model.readMemory((int)model.getPC());
					
					
				}
				
			}
		}
		
		
	}

}
