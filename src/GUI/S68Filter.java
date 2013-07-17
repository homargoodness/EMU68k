package GUI;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class S68Filter extends FileFilter {

	private final String S68 = "s68";
	
	@Override
	public boolean accept(File f) {
		
		if (f.isDirectory()) {
			return true;
		}
		
		String ext = getExtension(f);
		if (ext != null) {
			if (getExtension(f).compareTo(S68) == 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "MC68000 SRecords (S68)";
	}
	
	private String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');
		
		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i+1).toLowerCase();
		}
		
		return ext;
	}
	
	

}
