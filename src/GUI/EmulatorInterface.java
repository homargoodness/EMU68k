package GUI;

import java.awt.event.ActionListener;

import controller.Controller;
import controller.Controller.StartButtonListener;

public interface EmulatorInterface {


	String getFileName();


	void setStartListener(ActionListener listener);
	
	void setStopListener(ActionListener listener);

	void setOpenFileListener(ActionListener openFileListener);
	
	void updateTable(int address, String data);
	
	

}
