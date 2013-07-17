package controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Architecture.Chip;
import GUI.EmulatorInterface;

public class S68Loader implements Runnable {
	
	private String filename;
	private Chip model;
	private EmulatorInterface view;
	private List<String> file;
	
	public S68Loader (String aFilename, Chip aModel, EmulatorInterface aView ) {
		view = aView;
		model = aModel;
		filename = aFilename;
		file = new ArrayList<String>();
	}

	@Override
	public void run() {

		reader();
		int address;
		for (int i = 0; i < file.size(); i++) {

			if (file.get(i).charAt(1) == '0') {

			}
			else if (file.get(i).charAt(1) == '1') {
				int dataLength = file.get(i).length() - 2;
				String addressText = file.get(i).substring(4,8);
				address = Integer.parseInt(addressText, 16);
				for (int p = 8; p < dataLength; p += 2) {
					System.out.println(file.get(1).substring(p, p + 2));
					model.writeMemory(address, file.get(i).substring(p, p + 2));
					view.updateTable(address, file.get(i).substring(p, p + 2));
					address++;
				}
			}
			else if (file.get(i).charAt(1) == '2') {
				int dataLength = file.get(i).length() - 2;
				String addressText = file.get(i).substring(4,10);
				address = Integer.parseInt(addressText, 16);
				for (int p = 10; p < dataLength; p += 2) {
					System.out.println(file.get(1).substring(p, p + 2));
					model.writeMemory(address, file.get(i).substring(p, p + 2));
					view.updateTable(address, file.get(i).substring(p, p + 2));
					address++;
				}
			}
			else if (file.get(i).charAt(1) == '8') { // use to put address in PC

			}
			else if (file.get(i).charAt(1) == '9') { // use to put address in PC

			}
		}	
	}
	
	private void reader() {
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

}
