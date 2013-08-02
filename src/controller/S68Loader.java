package controller;

import gui.EmulatorUI;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import architecture.Chip;


public class S68Loader implements Runnable {
	
	private String filename, source;
	private Chip model;
	private EmulatorUI view;
	private List<String> file;
	
	public S68Loader (String aFilename, Chip aModel, EmulatorUI aView) {
		view = aView;
		model = aModel;
		filename = aFilename;
		file = new ArrayList<String>();
	}

	@Override
	public void run() {

		readMachineCode();
		readSourceCode();
		int address;
		for (int i = 0; i < file.size(); i++) {

			if (file.get(i).charAt(1) == '0') { // handles S0 records

			}
			
			else if (file.get(i).charAt(1) == '1') { // handles S1 records
				int dataLength = file.get(i).length() - 2;
				address = Integer.parseInt(file.get(i).substring(4,8),16);
				for (int p = 8; p < dataLength; p += 2) {
					model.writeMemory(address, (byte)Integer.parseInt(file.get(i).substring(p, p + 2),16));
					address++;
				}
			}
			
			else if (file.get(i).charAt(1) == '2') { // handles S2 records
				int dataLength = file.get(i).length() - 2;
				address = Integer.parseInt(file.get(i).substring(4,10), 16);
				for (int p = 10; p < dataLength; p += 2) {
					model.writeMemory(address, (byte)Integer.parseInt(file.get(i).substring(p, p + 2),16));
					address++;
				}
			}
			
			else if (file.get(i).charAt(1) == '8') { // handles S8 records
				String addressText = file.get(i).substring(4,10);
				int startAddress = Integer.parseInt(addressText,16);
				model.setPC(startAddress);
			}
			
			else if (file.get(i).charAt(1) == '9') { // handles S9 records
				String addressText = file.get(i).substring(4,8);
				int startAddress = Integer.parseInt(addressText,16);
				model.setPC(startAddress);
			}
		}	
	}
	
	private void readMachineCode() {
		FileReader reader = null;
		Scanner in = null;
		
		try {
			try {
				
				reader = new FileReader(filename);
				in = new Scanner(reader);
				
				while (in.hasNextLine()) {
					file.add(in.nextLine());
				}
				
			}
			finally {
				
				if (reader != null)
						reader.close();
				if (in != null)
					in.close();
				
			}
		}
		catch(IOException x) {
			
		}
	}
	
	private void readSourceCode() {
		StringBuilder source = new StringBuilder();
		String sourceFile = filename.substring(0, filename.length() - 3) + "L68";
		FileReader reader = null;
		Scanner in = null;
		try {
			try {
				reader = new FileReader(sourceFile);
				in = new Scanner(reader);
				
				while (in.hasNextLine()) {
					source.append(in.nextLine());
					source.append("\n");
				}
			}
			finally {
				if (reader != null) {
					reader.close();
				}
				if (in != null) {
					in.close();
				}
			}
		}
		catch (IOException x) {
			source.append("Source file not found");
		}
		
		view.setSource(source.toString());
		
		
	}

}
