package Instructions.Movement;

import Architecture.Chip;
import Instructions.Instruction;

public class Move implements Instruction {
	
	//source mode 0 = data reg
	//source mode 1 = address
	//source mode 2 = memory
	//source mode 3 = memory with postinc
	//source mode 4 = memory woth predec
	//source mode 5 = memory with displacement
	//source mode 6 = memory with index
	//source mode 7
		//source 0 = abs short
		//source 1 = abs long
		//source 2 = PC with disp
		//source 3 = PC with index
		//source 4 = immeadiate
	
	//01 byte
	//10 long
	//11word
	
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
		byte operand = 0;
		
		// Source
		if (sourceMode == 7) { 
			
			if (source == 4) { // immediate
				
				if (size == 1) { // byte
					model.setPC(model.getPC() + 1);
					operand = model.readMemory((int)model.getPC());
					model.setPC(model.getPC() + 1);
					
				}
				
				else if (size == 2) {
					
				}
				
				else if (size == 3) {
					
				}
				
			}
		}
		
		
		// Destination
		if (destMode == 0) { //data reg
			model.setDataRegister(dest, operand);
		}
		else if (destMode == 1) {
			model.setAddressRegister(dest, operand);
		}
		
		
	}

}
