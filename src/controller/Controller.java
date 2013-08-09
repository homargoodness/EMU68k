package controller;

import gui.EmulatorUI;

import instructions.IllegalInstructionException;
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

/**
 * Controller class mediates interaction between the model and view
 * 
 * It also contains the main CPU loop and is responsible for fetching, decoding and 
 * executing 68k instructions held in memory
 * 
 * It has references to both the model (Chip68k) and view (Graphical68k)
 * 
 * Action events from the view are caught in this class which are processed and acted upon
 * 
 * Property change events from the model are also caught here which are passed onto the 
 * relevant method in the view in order to update the user interface
 * 
 * @author Omar Manir
 *
 */
public class Controller implements PropertyChangeListener {
	
	private EmulatorUI view;
	private Chip model;
	
	private String filename; // holds the filename of the program being run on the emulator
	
	private int speed = 1000; // speed of instruction execution
	private boolean pause = false; // indicated if the CPU should be paused
	private boolean running = false; // indicates if the CPU is executing instructions
	
	/**
	 * Constructor
	 * 
	 * @param anInterface - the view class
	 * @param aChip - the model class
	 */
	public Controller(EmulatorUI anInterface, Chip aChip) {
		
		model = aChip;
		model.addListener(this); // adds this class as a listener for property change events from model
		
		view = anInterface;
		
		view.setStartListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (running) {
					setPause();
				}
				else {
					startCpuLoop();
					running = true;
				}
			}	
		});
		
		view.setOpenFileListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (filename != null) {
					model.reset();
				}
				filename = view.getFileName();
				openFile();
			}
		});
		
		view.setResetListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (filename != null) {
					running = false;
					pause = false;
					model.reset();
					openFile();
				}
			}
		});
		
		view.setSpeedListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					speed = (int)source.getValue() * 1000;
				}
			}
		});
	}
	
	/**
	 * This method creates a thread which fetches, decodes and executes instructions held in the
	 *  model memory for as long as there are instructions to be executed or until the user resets the emulator.
	 *   It also checks the pause boolean and pauses if it is set.
	 */
	public synchronized void startCpuLoop() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					for (;;) { // keep looping until HALT instruction or running is set to false
						checkPause(); // pauses if pause flag is set to true
						int opCode = (model.readMemoryWord(model.getPC()) & 0xFFFF); // fetches the next operation code
						model.setPC(model.getPC() + 2);
						Instruction inst = Decoder.decode(opCode); // decodes the op code and returns the appropriate instruction
						
						if (inst != null && running) { // if there is a valid instruction and emulation has not been stopped
							inst.execute(model); // execute the instruction
						}
						else { // else break out of CPU loop
							break;
						}
						
						try {
							Thread.sleep(speed); // send thread to sleep according to the speed variable set by user
						} catch (InterruptedException e) {}
					}
				}
				catch (IllegalInstructionException x) { // catches the exception thrown by Decoder class if an illegal instruction is read from memory
					System.out.println(x.getMessage());
				}
				running = false; // indicate that the CP is no longer executing instructions
			}
		});
		
		thread.start(); // starts the thread defined above
	}
	
	/**
	 * Helper method which checks to see if the CPU should be paused according to user commands
	 * Sets the thread to a wait state if the pause boolean is set to true
	 */
	private synchronized void checkPause() {
		if (pause) { // if pause is set to true
			try {
				wait(); // makes the thread wait until it's notified to start by another thread
			} catch (InterruptedException e) {}
		}
	}
	
	/**
	 * Helper method which determines if the CPU loop should be paused or resumed according to user actions
	 */
	private synchronized void setPause() {
		if (pause) { // if pause is true
			pause = false; // set it to false
			notifyAll(); //  and notify paused thread to resume
			System.out.println("RESUME");
		}
		else { // else if pause is set to false
			pause = true; // set it to true
			System.out.println("PAUSE");
		}
	}
	
	/**
	 * Helper method which reads in a given file and loads the contents to memory
	 */
	public void openFile() {
		if (filename != null) { // if filename has been specified
			Thread th = new Thread(new S68Loader(filename, model, view)); // create new thread which reads in file and sets up model
			th.start(); // start thread
		}
	}
	
	/**
	 * Method which captures property changes fired from model and calls the relevant method in view to update the interface
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().compareTo("Memory") == 0) { // if memory n model has been updated
			view.updateMemory(((IndexedPropertyChangeEvent) evt).getIndex(), (byte)evt.getNewValue()); // call method in view to update display
		}
		else if (evt.getPropertyName().compareTo("ProgramCounter") == 0) { // if program counter in model has been updated
			view.updatePC((int)evt.getNewValue()); // call method in method in view to update display
		}
		else if (evt.getPropertyName().compareTo("DataRegister") == 0) { // if a data register in model has been updated
			view.updateDataRegisterDisplay(((IndexedPropertyChangeEvent) evt).getIndex(), (int)evt.getNewValue()); // call method in method in view to update display
		}
		else if (evt.getPropertyName().compareTo("AddressRegister") == 0) { // if an address register in model has been updated
			view.updateAddressRegisterDisplay(((IndexedPropertyChangeEvent) evt).getIndex(), (int)evt.getNewValue()); // call method in method in view to update display
		}
		else if (evt.getPropertyName().compareTo("StatusRegister") == 0) { // if the status register in model has been updated
			view.updateStatusRegister((short)evt.getNewValue()); // call method in method in view to update display
		}
		else if (evt.getPropertyName().compareTo("Reset") == 0) { // if view has been reset
			view.reset(); // call method in method in view to update display
		}
	}
	
}
