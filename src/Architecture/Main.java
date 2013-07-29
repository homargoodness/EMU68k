package Architecture;

import java.awt.EventQueue;

import GUI.Graphical68k;
import controller.Controller;

public class Main {
	
	public static void main(String [] args) {
		
		// TODO factories for view
		
		Chip model = new Chip68k();
		
		Graphical68k view = new Graphical68k();
		
		Controller controller = new Controller(view, model);
		
		
		
		model.writeMemory(0, (byte)0x12);
		model.writeMemory(1, (byte)0x3C);
		model.writeMemory(2, (byte)0x00);
		model.writeMemory(3, (byte)0x54);
		
		/*
		model.setDataRegister(0,81234);
		System.out.println(model.getDataRegisterLongWord(0));
		System.out.println(Integer.toBinaryString(model.getDataRegisterLongWord(0)));
		
		model.setDataRegister(0, (byte)0);
		
		System.out.println(model.getDataRegisterLongWord(0));
		System.out.println(Integer.toBinaryString(model.getDataRegisterLongWord(0)));
		
	
		
		//System.out.println((model.getDataRegisterLongWord(0)));
		//System.out.println(String.format("%04X", model.getDataRegisterByte(0)));
				
		*/	
	
		
		
		
	}

}
