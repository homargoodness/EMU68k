package GUI;

import java.awt.EventQueue;
import java.awt.List;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import Architecture.Memory.Memory;
import Architecture.Memory.MemoryAccessException;

/**
 * TODO implement tool tip for each cell
 * @author admin
 *
 */
public class MemoryTableModel extends AbstractTableModel {
	
	private ArrayList<Cell> data;
	
	
	public MemoryTableModel() {
		
		super();
		data = new ArrayList<Cell>();
	}
	
	private String [] columnNames = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"A", "B", "C", "D", "E", "F"};


	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return 16777215/15 - 1;
	}
	
	public String getColumnName(int colIndex) {
		return columnNames[colIndex];
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		for (int i = 0; i < data.size(); i++) {
			if ((data.get(i).row == row) && (data.get(i).col == col)) {
				return data.get(i).content;
			}
		}
		
		return "FF";	
	}
	
	public void setValueAt(Object value, int row, int col) {
		data.add(new Cell((String) value, row, col)); //TODO search through list and overwrite if location already exists!!!!!!!!!!!!!!!
		this.fireTableCellUpdated(row, col);
	}
	
	
	private class Cell {
		
		private String content;
		private int row;
		private int col;
		
		private Cell(String data, int aRow, int aCol) {
			content = data;
			row = aRow;
			col = aCol;
		}
		
	}

}
