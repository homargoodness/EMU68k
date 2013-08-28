package gui;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.event.ChangeListener;


public abstract class EmulatorUI  { // TODO if there is no non-abstract methods, turn into interface 

	/**
	 * Method which gets filename of the program to run from the user
	 * @return the filename
	 */
	public abstract String getFileName();

	/**
	 * Method while allows a listener to register itself for notifications of when the user starts the emulation
	 * @param listener the listener which is to receive notifications
	 */
	public abstract void setStartListener(ActionListener listener);
	
	/**
	 * Method which allows a listener to register itself for notifications of when the user resets the emulator
	 * @param listener the listener which is to receive notifications
	 */
	public abstract void setResetListener(ActionListener listener);

	/**
	 * Method which allows a listener to register itself for notification of when the user opens a new program file for emulation
	 * @param listener the listener which is to receive notifications
	 */
	public abstract void setOpenFileListener(ActionListener listener);
	
	/**
	 * Method which allows a listener to register itself for notification of when the user changes emulation speed
	 * @param listener the listener which is to receive notifications
	 */
	public abstract void setSpeedListener(ChangeListener listener);
	
	/**
	 * Method to reset all view components
	 */
	public abstract void reset();
	
	/**
	 * Method which updates the memory display
	 * @param address the address of the memory location changed
	 * @param data the data to be updated in the memory location
	 */
	public abstract void updateMemory(int address, int data);
	
	/**
	 * Method to set the source code display
	 * @param source the code to be displayed
	 */
	public abstract void setSource(String source);
	
	/**
	 * Method to update the Program Counter display
	 * @param address the new value of the PC
	 */
	public abstract void updatePC(int address);
	
	/**
	 * Method to update one of the data register displays
	 * @param register the number of the register to update
	 * @param data the new value of the data register
	 */
	public abstract void updateDataRegisterDisplay(int register, int data);
	
	/**
	 * Method to update one of the address registers
	 * @param register the address register to be updated
	 * @param data the new value of the address register
	 */
	public abstract void updateAddressRegisterDisplay(int register, int data);
	
	/**
	 * Method to update the status register display
	 * @param value the new value of the SR
	 */
	public abstract void updateStatusRegister(int value);
	
	/**
	 * Resets the background colour of all components
	 */
	public abstract void resetBackgroundColour();
	

}
