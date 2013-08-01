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
		
				
	}

}
