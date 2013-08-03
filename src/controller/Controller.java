package controller;

import gui.EmulatorUI;

import instructions.Instruction;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Method;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import architecture.Chip;


public class Controller implements PropertyChangeListener {
	
	private EmulatorUI view;
	private Chip model;
	
	private String filename;
	
	
	private int speed = 1000; // speed of instruction execution
	private boolean pause = false;
	private boolean running = false;
	
public Controller(EmulatorUI anInterface, Chip aChip) {
		
		model = aChip;
		model.addListener(this);
		
		view = anInterface;
		
		view.setStartListener(new StartButtonListener());
		
		view.setOpenFileListener(new OpenFileListener());
		
		view.setResetListener(new ResetButtonListener());
		
		view.setSpeedListener(new SpeedListener());
	}
	
	public synchronized void startCpuLoop() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					for (;;) {
						checkPause();
						int opCode = (model.readMemoryWord(model.getPC()) & 0xFFFF);
						Instruction inst = Decoder.decode(opCode);
						
						if (inst != null && running) {
							inst.execute(model);
						}
						else {
							break;
						}
						
						try {
							Thread.sleep(speed);
						} catch (InterruptedException e) {}
					}
				}
				catch (IllegalInstructionException x) {
					System.out.println("illegal instruction");
				}
				running = false;
			}
		});
		
		thread.start();
	}
	
	public synchronized void checkPause() {
		if (pause) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
	}
	
	public synchronized void setPause() {
		if (pause) {
			pause = false;
			notifyAll();
			System.out.println("RESUME");
		}
		else {
			pause = true;
			System.out.println("PAUSE");
		}
	}
	
	public void openFile() {
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
		else if (evt.getPropertyName().compareTo("Reset") == 0) {
			view.reset();
		}
	}
	
	
	
	
	/** Inner classes **/
	public class StartButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (running) {
				setPause();
			}
			else {
				startCpuLoop();
				running = true;
			}
		}	
	}
	
	public class ResetButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (filename != null) {
				System.out.println("Reset + Recreate memory");
				running = false;
				pause = false;
				model.reset();
				openFile();
			}
		}
	}
	
	public class OpenFileListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (filename != null) {
				model.reset();
			}
			filename = view.getFileName();
			openFile();
		}
	}
	
	public class SpeedListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			if (!source.getValueIsAdjusting()) {
				speed = (int)source.getValue() * 1000;
				System.out.println((int)source.getValue());
				System.out.println(speed);
			}
		}
	}
	

}
