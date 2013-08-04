package controller;

import gui.Graphical68k;

import java.awt.EventQueue;

import architecture.Chip;
import architecture.Chip68k;


public class Main {
	
	public static void main(String [] args) {
		
		// TODO factories for view
		
		Chip model = new Chip68k();
		
		Graphical68k view = new Graphical68k();
		
		Controller controller = new Controller(view, model);
		
		
		//model.writeMemory(0, (byte)0x70);
		//model.writeMemory(1, (byte)0xA0);
		/*
		for (int i = 0; i < 100; i += 2) {
			model.writeMemory(i, (byte)0x70);
			model.writeMemory(i+ 1, (byte)0xA0);
		}
		*/
				
	}

}
