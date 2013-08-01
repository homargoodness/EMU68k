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
	
	public void start() {
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					for (;;) {
						int opCode = (model.readMemoryWord(model.getPC()) & 0xFFFF);
						Instruction inst = Decoder.decode(opCode);
						if (inst != null) {
							inst.execute(model);
						}
						else {
							System.out.println("Finished");
							break;
						}
					}
				}
				catch (IllegalInstructionException x) {
					System.out.println("illegal instruction");
				}
			}
		});
		
		thread.start();
		
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
		if (evt.getPropertyName().compareTo("Memory") == 0) {
			view.updateMemory(((IndexedPropertyChangeEvent) evt).getIndex(), (byte)evt.getNewValue());
		}
		else if (evt.getPropertyName().compareTo("ProgramCounter") == 0) {
			view.updatePC((int)evt.getNewValue());
		}
		else if (evt.getPropertyName().compareTo("DataRegister") == 0) {
			view.updateDataRegisterDisplay(((IndexedPropertyChangeEvent) evt).getIndex(), (int)evt.getNewValue());
		}
		else if (evt.getPropertyName().compareTo("AddressRegister") == 0) {
			view.updateAddressRegisterDisplay(((IndexedPropertyChangeEvent) evt).getIndex(), (int)evt.getNewValue());
		}
		else if (evt.getPropertyName().compareTo("StatusRegister") == 0) {
			view.updateStatusRegister((short)evt.getNewValue());
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
