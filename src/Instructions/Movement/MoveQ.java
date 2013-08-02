package instructions.movement;

import architecture.Chip;
import instructions.Instruction;



public class MoveQ implements Instruction {
	
	private int opCode;
	
	public MoveQ(int aCode) {
		opCode = aCode;
	}

	@Override
	public void execute(Chip model) {
		int dest = (opCode >>> 9) & 0x7;
		int data =(byte) (opCode & 0xFF);
		
		model.setDataRegister(dest, data);
		
		// set SR flags
		model.setSROverflowBit(0);
		model.setSRCarryBit(0);
		if (data < 0) {
			System.out.println(data);
			model.setSRNegativeBit(1);
		}
		else model.setSRNegativeBit(0);
		if (data == 0) {
			model.setSRZeroBit(1);
		}
		else model.setSRZeroBit(0);
		
	}

}
