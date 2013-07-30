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
		
		
		
		model.writeMemory(0, (byte)0x10); //d0
		model.writeMemory(1, (byte)0x3C);
		model.writeMemory(2, (byte)0x00);
		model.writeMemory(3, (byte)0xC8);
		
		model.writeMemory(4, (byte)0x12);//d1
		model.writeMemory(5, (byte)0x3C);
		model.writeMemory(6, (byte)0x00);
		model.writeMemory(7, (byte)0xC8);
		
		model.writeMemory(8, (byte)0x14);//d2
		model.writeMemory(9, (byte)0x3C);
		model.writeMemory(10, (byte)0x00);
		model.writeMemory(11, (byte)0xC8);
		
		model.writeMemory(12, (byte)0x16);//d3
		model.writeMemory(13, (byte)0x3C);
		model.writeMemory(14, (byte)0x00);
		model.writeMemory(15, (byte)0xC8);
		
		model.writeMemory(16, (byte)0x18);//d4
		model.writeMemory(17, (byte)0x3C);
		model.writeMemory(18, (byte)0x00);
		model.writeMemory(19, (byte)0xC8);
		
		model.writeMemory(20, (byte)0x1A);//d5
		model.writeMemory(21, (byte)0x3C);
		model.writeMemory(22, (byte)0x00);
		model.writeMemory(23, (byte)0xC8);
		
		model.writeMemory(24, (byte)0x1C);//d6
		model.writeMemory(25, (byte)0x3C);
		model.writeMemory(26, (byte)0x00);
		model.writeMemory(27, (byte)0xC8);
		
		model.writeMemory(28, (byte)0x1E);//d7
		model.writeMemory(29, (byte)0x3C);
		model.writeMemory(30, (byte)0x00);
		model.writeMemory(31, (byte)0xC8);
		
		
		
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
