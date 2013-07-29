package controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JButton;

import Architecture.Chip;
import GUI.EmulatorUI;
import Instructions.Instruction;

public class Controller implements PropertyChangeListener {
	
	private EmulatorUI view;
	private Chip model;
	
	private final int D0 = 0x0;
	private final int D1 = 0x200;
	private final int D2 = 0x400;
	private final int D3 = 0x600;
	private final int D4 = 0x800;
	private final int D5 = 0xA00;
	private final int D6 = 0xC00;
	private final int D7 = 0xE00;
	
	
	private int speed = 1000; // speed of instruction execution
	
public Controller(EmulatorUI anInterface, Chip aChip) {
		
		
		
		model = aChip;
		model.addListener(this);
		
		view = anInterface;
		
		view.setStartListener(new StartButtonListener());
		
		view.setOpenFileListener(new OpenFileListener());
		
		view.setStopListener(new StopButtonListener());
	}
	
	public void start() { // nwe thread!!!!!!!!!!!!!
		
		int opCode = fetch();
		Instruction inst = Decoder.decode(opCode); // TODO static, is this right?!?
		if (inst != null) {
			inst.execute(model);
		}
		else {
			System.out.println("illegal instruction");
		}
		
		// if op is valid (not null) execute
		
		//int op = (model.readMemory((int)model.getPC()));
		//model.setPC(model.getPC() + 1);
		 
		
		//loop, inside loop is an object that can fetch, decode and execute, each itteration of loop checks for a pause or stop sign.
		
		// first 2 bytes read in, passed into a class that looksup value in array, passes back instruction, pass model into instruction to execute. loop
		
		//if (op >= 0x1 && op <= 0x3) {
			
		//}
		
		/*
		int op = (model.readMemory((int)model.getPC()));
		op = ((op/0x10) * 0x1000) + ((op % 0x10) * 0x100);
		
		op += (model.readMemory((int)model.getPC() + 1));
		
		if (op >= 0x1000 && op <= 0x3FFF) {
			op = op % 0x1000; // strip first byte 3 bytes left
			if (op >= D0 && op < D1) {
				// move to D0
			}
			else if (op >= D1 && op < D2 ) {
				System.out.println(op); // d1 TRUN TO STRING AT THIS POINT???
			}
			else if (op >= D2 && op < D3 ) {
				//move to D2
			}
			else if (op >= D3 && op < D4 ) {
				//move to D3
			}
			else if (op >= D4 && op < D5 ) {
				//move to D4
			}
			else if (op >= D5 && op < D6 ) {
				//move to D5
			}
			else if (op >= D6 && op < D7 ) {
				//move to D6
			}
			else {
				//move to D7
			}
			
		}
		*/
		/*
		
		// Running in EDT!!!!!!!!!!!!!!!!!!!!!!!!!
		StringBuilder opCode = new StringBuilder(Integer.toBinaryString(model.readMemory((int)model.getPC()))); // combine in loop
		while (opCode.length() < 8) {
			opCode.insert(0,'0');
		}
		model.setPC(model.getPC() + 1);
		StringBuilder opCode2 = new StringBuilder(Integer.toBinaryString(model.readMemory((int)model.getPC()))); // combine in loop
		while (opCode.length() < 8) {
			opCode.insert(0,'0');
		}
		model.setPC(model.getPC() + 1);
		
		opCode.append(opCode2);
		*/
		
	}
	
	/**
	 * Helper method to fetch the next 2 bytes to decode
	 * @return
	 */
	private int fetch() {
		int op = (model.readMemory((int)model.getPC()));
		model.setPC(model.getPC() + 1);
		op = (op *256) + (model.readMemory((int)model.getPC()));
		model.setPC(model.getPC() + 1);
		
		
		return op;
	}
	
	public void stop() {
		System.out.println("Stop");
		
	}
	
	public void pause() {
		System.out.println("Pause");
		
	}
	
	public void setSpeed(int newSpeed) {
		
	}
	
	public void openFile() {
		
		String filename = view.getFileName();
		if (filename != null) {
			Thread th = new Thread(new S68Loader(filename, model, view));
			th.start();
		}
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().compareTo("memory") == 0) {
			view.updateMemory(((IndexedPropertyChangeEvent) evt).getIndex(), (byte)evt.getNewValue());
		}
		else if (evt.getPropertyName().compareTo("pc") == 0) {
			view.updatePC((int)evt.getNewValue());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	/** Inner classes **/
	public class StartButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			start();
			
		}
		
	}
	
	public class StopButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			stop();
			
		}
		
	}
	
	public class OpenFileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			openFile();
		}
	}
	

}
