package controller;

import gui.EmulatorUI;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import architecture.Chip;

/**
 * Runnable class which reads in a program from source code file and the compiled program (SRecord)
 * and places it in the correct place in memory
 */
public class S68Loader implements Runnable {
	
	private String filename; // names of the file to read
	private Chip model;
	private EmulatorUI view;
	private List<String> file; // lines in the SRecord
	
	public S68Loader (String aFilename, Chip aModel, EmulatorUI aView) {
		view = aView;
		model = aModel;
		filename = aFilename;
		file = new ArrayList<String>();
	}

	/**
	 * Method to write the SRecord to memory
	 */
	@Override
	public void run() {

		readMachineCode(); // reads in SRecord
		readSourceCode(); //  reads in the source code
		int address; // the address where the next byte of data is to written to in memory
		for (int i = 0; i < file.size(); i++) { // loops through each line in the SRecord

			if (file.get(i).charAt(1) == '0') { // if line is an S0 record

			}
			
			else if (file.get(i).charAt(1) == '1') { // if line is an S1 record
				int dataLength = file.get(i).length() - 2; // get length of line minus checksum
				address = Integer.parseInt(file.get(i).substring(4,8),16); // find destination address
				for (int p = 8; p < dataLength; p += 2) { // for each byte in line
					model.writeMemoryByte(address, Integer.parseInt(file.get(i).substring(p, p + 2),16)); // write to address
					address++; // increment address for next instruction
				}
			}
			
			else if (file.get(i).charAt(1) == '2') { // if line is an S2 record
				int dataLength = file.get(i).length() - 2; // get length of line minus checksum
				address = Integer.parseInt(file.get(i).substring(4,10), 16); // find destination address
				for (int p = 10; p < dataLength; p += 2) { // for each byte in line
					model.writeMemoryByte(address, Integer.parseInt(file.get(i).substring(p, p + 2),16)); // write to address
					address++; // increment address for next instruction
				}
			}
			
			else if (file.get(i).charAt(1) == '8') { // if line is an S8 record
				String addressText = file.get(i).substring(4,10); // find text for address
				int startAddress = Integer.parseInt(addressText,16); // parse address text to an integer
				model.setPC(startAddress); // set the PC to the correct starting address
			}
			
			else if (file.get(i).charAt(1) == '9') { // if line is an S9 record
				String addressText = file.get(i).substring(4,8); // find text for address
				int startAddress = Integer.parseInt(addressText,16); // parse address text to an integer
				model.setPC(startAddress); // set the PC to the correct starting address
			}
		}	
	}
	
	/**
	 * Helper method which reads in the SRecord.
	 */
	private void readMachineCode() {
		// declare new reader and scanner
		FileReader reader = null;
		Scanner in = null;
		
		try {
			try {
				//open file to start reading
				reader = new FileReader(filename);
				in = new Scanner(reader);
				
				while (in.hasNextLine()) { // while there is still a line to read
					file.add(in.nextLine()); // add the next line to array of lines
				}
				
			}
			finally {
				// close the reader and scanner
				if (reader != null)
						reader.close();
				if (in != null)
					in.close();
				
			}
		}
		catch(IOException x) {
			
		}
	}
	
	/**
	 * Helper method which reads source code from a and displays it in the view. If the souce
	 * code is not found an appropriate message is displayed instead.
	 */
	private void readSourceCode() {
		StringBuilder source = new StringBuilder(); // holds the entire source code
		String sourceFile = filename.substring(0, filename.length() - 3) + "L68"; // create filename to open
		FileReader reader = null; // declare new reader and scanner
		Scanner in = null;
		try {
			try {
				reader = new FileReader(sourceFile); // open file to start reading
				in = new Scanner(reader);
				
				while (in.hasNextLine()) { // while there is another line to read
					source.append(in.nextLine()); // append next line
					source.append("\n"); // start new line
				}
			}
			finally {
				if (reader != null) { // close file
					reader.close();
				}
				if (in != null) {
					in.close();
				}
			}
		}
		catch (IOException x) { // if source file not found
			source.append("Source file not found"); // show message
		}
		
		view.setSource(source.toString()); // diaplay message
		
		
	}

}
