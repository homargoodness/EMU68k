package instructions;

import architecture.Chip;

public interface Instruction {
	
	//int [] dataRegisterList = {
	
	public void execute(Chip model);

}
