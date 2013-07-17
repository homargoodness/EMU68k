package controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Architecture.Chip;
import GUI.EmulatorInterface;

public class Controller {
	
	private EmulatorInterface view;
	private Chip model;
	
	private int speed = 1000; // speed of instruction execution
	
	public Controller(EmulatorInterface anInterface, Chip aChip) {
		
		model = aChip;
		view = anInterface;
		
		ActionListener startListener = new StartButtonListener();
		view.setStartListener(startListener);
		
		ActionListener openFileListener = new OpenFileListener();
		view.setOpenFileListener(openFileListener);
		
		ActionListener stopListener = new StopButtonListener();
		view.setStopListener(stopListener);
	}
	
	public void start() {
		
		System.out.println("Start");
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
