package gui;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.event.ChangeListener;


public abstract class EmulatorUI  { // if there is no non-abstract methods, turn into interface 


	public abstract String getFileName();

	public abstract void setStartListener(ActionListener listener);
	
	public abstract void setStopListener(ActionListener listener);

	public abstract void setOpenFileListener(ActionListener listener);
	
	public abstract void setSpeedListener(ChangeListener listener);
	
	public abstract void reset();
	
	public abstract void updateMemory(int address, byte data);
	
	public abstract void setSource(String source);
	
	public abstract void updatePC(int address);
	
	public abstract void updateDataRegisterDisplay(int register, int data);
	
	public abstract void updateAddressRegisterDisplay(int register, int data);
	
	public abstract void updateStatusRegister(short value);
	

}
