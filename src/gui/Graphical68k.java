package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Hashtable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Class which implements the graphical user interface which includes textfields for the registers,
 * a table for the memory and a text area which displays the source code if the file was found.
 * 
 *  This class also sends notifications through ActionEvents to registered listeners when the user interacts with the interface.
 */
public class Graphical68k extends EmulatorUI {
	
	private JFrame frame;
	
	// menu bar items
	private JMenuItem openItem;
	private JMenuItem exitItem;
	private JMenuItem aboutItem;
	
	Color originalColor;
	
	JTextField [] dataRegisterField; // array of data register display fields
	
	JTextField [] addressRegisterField; // array of address register display fields
	
	private JTextField xBitField, nBitField, zBitField, vBitField, cBitField; // status register flag display fields
	
	private JTextField pcField; // program counter display field
	
	private JTable memTable; // table which displays memory
	
	JTextArea codeDisp; // text area which displays source code
	
	private JButton startButton, resetButton; // buttons to start, pause and reset the emulator
	private JSlider speedSelect; // slider to allow user to select emulation speed
	
	/**
	 * Constructor which initialises the display with helper methods in the EDT thread
	 */
	public Graphical68k() {
		
		EventQueue.invokeLater(new Runnable() { // create a new runnable and run on EDT thread as Swing is not thread safe
			public void run() {
				frame = new JFrame();
				
				frame.setTitle("68k Emulator");
				frame.setSize(1400,700);
				//frame.setResizable(false);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				layoutMenu();
				layoutEast();
				layoutCenter();
				layoutWest();
				
				frame.setVisible(true);
			}
		});
		
	}
	
	/**
	 * Helper method which lays out the menu bar
	 */
	private void layoutMenu() {
		
		JMenuBar mainMenu = new JMenuBar(); // TODO move back up to instance vars
		frame.setJMenuBar(mainMenu);
		
		JMenu fileMenu = new JMenu("File");
		mainMenu.add(fileMenu);
		
		JMenu viewMenu = new JMenu("View");
		mainMenu.add(viewMenu);
		
		JMenu aboutMenu = new JMenu("About");
		mainMenu.add(aboutMenu);
		
		openItem = new JMenuItem("Open");
	
		fileMenu.add(openItem);
		
		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Exit");
			}
		});
		fileMenu.add(exitItem);
		
	}
	
	/**
	 * Helper method that lays out east side of frame which holds register and control display
	 */
	private void layoutEast() {
		
		JPanel eastPanel = new JPanel(new BorderLayout()); // create a new panel for east of frame
		
		JPanel regPanel = new JPanel(new GridBagLayout()); // set Gridbag layout for new panel
		regPanel.setBorder(new TitledBorder(new EtchedBorder(), "Internal Registers"));
		
		GridBagConstraints panelConst = new GridBagConstraints();
		panelConst.gridx = 0;
		panelConst.gridy = 0;
		panelConst.fill = GridBagConstraints.HORIZONTAL;
		panelConst.insets = new Insets(5,0,5,0);
		panelConst.anchor = GridBagConstraints.FIRST_LINE_START;
		
		regPanel.add(layoutDataRegisters(), panelConst);
		panelConst.gridy = 1;
		regPanel.add(layoutAddressRegisters(), panelConst);
		panelConst.gridy = 2;
		regPanel.add(layoutPCRegister(), panelConst);
		panelConst.gridy = 3;
		panelConst.insets = new Insets(5,0,5,0);
		regPanel.add(layoutStatusRegister(), panelConst);
		
		eastPanel.add(regPanel, BorderLayout.NORTH);
		eastPanel.add(layoutControlArea(), BorderLayout.CENTER);
		
		frame.add(eastPanel,BorderLayout.EAST);
		
	}
	
	private JPanel layoutDataRegisters() {
		
		dataRegisterField = new JTextField[8];
		
		JPanel dataPanel = new JPanel(new GridBagLayout());
		dataPanel.setBorder(new TitledBorder(new EtchedBorder(), "Data Registers"));
		
		GridBagConstraints labels = new GridBagConstraints();
		labels.ipadx = 5;
		labels.ipady = 10;
		labels.weightx = 1;
		labels.anchor = GridBagConstraints.PAGE_END;
		labels.insets = new Insets(0,20,0,0);
		labels.gridx = 0;
		labels.gridy = 0;
		
		GridBagConstraints fields = new GridBagConstraints();
		fields.ipadx = 5;
		fields.ipady = 10;
		fields.gridx = 0;
		fields.gridy = 0;
		
		dataPanel.add(new JLabel("D0", 2),labels);
		fields.gridx= 1;
		dataRegisterField[0] = new JTextField("00000000", 8);
		dataRegisterField[0].setEditable(false);
		dataPanel.add(dataRegisterField[0], fields);
		
		labels.gridx= 2;
		dataPanel.add(new JLabel("D1", 2), labels);
		fields.gridx= 3;
		dataRegisterField[1] = new JTextField("00000000", 8);
		dataRegisterField[1].setEditable(false);
		dataPanel.add(dataRegisterField[1], fields);
		
		labels.gridx = 0;
		labels.gridy = 1;
		dataPanel.add(new JLabel("D2",2), labels);
		fields.gridx = 1;
		fields.gridy = 1;
		dataRegisterField[2] = new JTextField("00000000", 8);
		dataRegisterField[2].setEditable(false);
		dataPanel.add(dataRegisterField[2], fields);
		
		labels.gridx = 2;
		dataPanel.add(new JLabel("D3", 2), labels);
		fields.gridx = 3;
		dataRegisterField[3] = new JTextField("00000000", 8);
		dataRegisterField[3].setEditable(false);
		dataPanel.add(dataRegisterField[3], fields);
		
		labels.gridx= 0;
		labels.gridy= 2;
		dataPanel.add(new JLabel("D4", 2), labels);
		fields.gridx= 1;
		fields.gridy = 2;
		dataRegisterField[4] = new JTextField("00000000", 8);
		dataRegisterField[4].setEditable(false);
		dataPanel.add(dataRegisterField[4], fields);
		
		labels.gridx= 2;
		dataPanel.add(new JLabel("D5", 2), labels);
		fields.gridx= 3;
		dataRegisterField[5] = new JTextField("00000000", 8);
		dataRegisterField[5].setEditable(false);
		dataPanel.add(dataRegisterField[5], fields);
		
		labels.gridx= 0;
		labels.gridy= 3;
		dataPanel.add(new JLabel("D6", 2), labels);
		fields.gridx= 1;
		fields.gridy = 3;
		dataRegisterField[6] = new JTextField("00000000", 8);
		dataRegisterField[6].setEditable(false);
		dataPanel.add(dataRegisterField[6], fields);
		
		labels.gridx= 2;
		dataPanel.add(new JLabel("D7", 2), labels);
		fields.gridx= 3;
		dataRegisterField[7] = new JTextField("00000000", 8);
		dataRegisterField[7].setEditable(false);
		dataPanel.add(dataRegisterField[7], fields);
		
		originalColor = dataRegisterField[0].getBackground();
		
		return dataPanel;
				
	}
	
	private JPanel layoutAddressRegisters() {
		
		addressRegisterField = new JTextField [8];
		
		JPanel addressPanel = new JPanel(new GridBagLayout());
		addressPanel.setBorder(new TitledBorder(new EtchedBorder(), "Address Registers"));
		
		GridBagConstraints labels = new GridBagConstraints();
		labels.ipadx = 5;
		labels.ipady = 10;
		labels.weightx = 1;
		labels.anchor = GridBagConstraints.PAGE_END;
		labels.insets = new Insets(0,20,0,0);
		labels.gridx = 0;
		labels.gridy = 0;
		
		GridBagConstraints fields = new GridBagConstraints();
		fields.ipadx = 5;
		fields.ipady = 10;
		fields.gridx = 0;
		fields.gridy = 0;
		
		addressPanel.add(new JLabel("A0", 2),labels);
		fields.gridx= 1;
		addressRegisterField[0] = new JTextField("00000000", 8);
		addressRegisterField[0].setEditable(false);
		addressPanel.add(addressRegisterField[0], fields);
		
		labels.gridx= 2;
		addressPanel.add(new JLabel("A1", 2), labels);
		fields.gridx= 3;
		addressRegisterField[1] = new JTextField("00000000", 8);
		addressRegisterField[1].setEditable(false);
		addressPanel.add(addressRegisterField[1], fields);
		
		labels.gridx = 0;
		labels.gridy = 1;
		addressPanel.add(new JLabel("A2", 2), labels);
		fields.gridx = 1;
		fields.gridy = 1;
		addressRegisterField[2] = new JTextField("00000000", 8);
		addressRegisterField[2].setEditable(false);
		addressPanel.add(addressRegisterField[2], fields);
		
		labels.gridx = 2;
		addressPanel.add(new JLabel("A3", 2), labels);
		fields.gridx = 3;
		addressRegisterField[3] = new JTextField("00000000", 8);
		addressRegisterField[3].setEditable(false);
		addressPanel.add(addressRegisterField[3], fields);
		
		labels.gridx= 0;
		labels.gridy= 2;
		addressPanel.add(new JLabel("A4", 2), labels);
		fields.gridx= 1;
		fields.gridy = 2;
		addressRegisterField[4] = new JTextField("00000000", 8);
		addressRegisterField[4].setEditable(false);
		addressPanel.add(addressRegisterField[4], fields);
		
		labels.gridx= 2;
		addressPanel.add(new JLabel("A5", 2), labels);
		fields.gridx= 3;
		addressRegisterField[5] = new JTextField("00000000", 8);
		addressRegisterField[5].setEditable(false);
		addressPanel.add(addressRegisterField[5], fields);
		
		labels.gridx= 0;
		labels.gridy= 3;
		addressPanel.add(new JLabel("A6", 2), labels);
		fields.gridx= 1;
		fields.gridy = 3;
		addressRegisterField[6] = new JTextField("00000000", 8);
		addressRegisterField[6].setEditable(false);
		addressPanel.add(addressRegisterField[6], fields);
		
		labels.gridx= 2;
		addressPanel.add(new JLabel("A7", 2), labels);
		fields.gridx= 3;
		addressRegisterField[7] = new JTextField("00000000", 8);
		addressRegisterField[7].setEditable(false);
		addressPanel.add(addressRegisterField[7], fields);
		
		return addressPanel;
		
	}
	
	private JPanel layoutStatusRegister() {
		
		JPanel srPanel = new JPanel(new GridBagLayout());
		srPanel.setBorder(new TitledBorder(new EtchedBorder(), "Status Register"));
		
		GridBagConstraints labels = new GridBagConstraints();
		labels.ipadx = 0;
		labels.ipady = 0;
		labels.anchor = GridBagConstraints.PAGE_END;
		labels.insets = new Insets(0,10,0,10);
		labels.gridx = 0;
		labels.gridy = 0;
		
		GridBagConstraints fields = new GridBagConstraints();
		fields.ipadx = 0;
		fields.ipady = 0;
		fields.insets = new Insets(0,10,0,10);
		fields.gridx = 0;
		fields.gridy = 1;
		
		srPanel.add(new JLabel("X"), labels);
		
		xBitField = new JTextField("0", 1);
		xBitField.setEditable(false);
		srPanel.add(xBitField, fields);
		
		labels.gridx = 1;
		srPanel.add(new JLabel("N"), labels);
		
		nBitField = new JTextField("0", 1);
		nBitField.setEditable(false);
		fields.gridx = 1;
		srPanel.add(nBitField, fields);
		
		labels.gridx = 2;
		srPanel.add(new JLabel("Z"), labels);
		
		zBitField = new JTextField("0", 1);
		zBitField.setEditable(false);
		fields.gridx = 2;
		srPanel.add(zBitField, fields);
		
		labels.gridx = 3;
		srPanel.add(new JLabel("V"), labels);
		
		vBitField = new JTextField("0", 1);
		vBitField.setEditable(false);
		fields.gridx = 3;
		srPanel.add(vBitField, fields);
		
		labels.gridx = 4;
		srPanel.add(new JLabel("C"), labels);
		
		cBitField = new JTextField("0", 1);
		cBitField.setEditable(false);
		fields.gridx = 4;
		srPanel.add(cBitField, fields);
		
		return srPanel;
	}
	
	private JPanel layoutPCRegister() {
		
		JPanel pcPanel = new JPanel(new GridBagLayout());
		pcPanel.setBorder(new TitledBorder(new EtchedBorder(), "Program Counter"));
		
		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 5;
		c.ipady = 0;
		//c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(0,0,0,0);
		c.gridx = 0;
		c.gridy = 0;
		
		pcPanel.add(new JLabel("PC"), c);
		
		pcField = new JTextField("00000000", 8);
		pcField.setEditable(false);
		c.gridx = 1;
		pcPanel.add(pcField, c);
		
		return pcPanel;
	}
	
	private void layoutCenter() {
		
		JPanel memoryPanel = new JPanel(new GridLayout());
		memoryPanel.setBorder(new TitledBorder(new EtchedBorder(), "Main Memory"));
		
		memTable = new JTable(new MemoryTableModel());
		JScrollPane scrollPane = new JScrollPane(memTable);
		JTable rowTable = new RowNumberTable(memTable);
		scrollPane.setRowHeaderView(rowTable);
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,
			    rowTable.getTableHeader());
		
		memoryPanel.add(scrollPane);
		
		frame.add(memoryPanel, BorderLayout.CENTER);
	}
	
	private void layoutWest() {
		
		JPanel codePanel = new JPanel(new GridLayout());
		codePanel.setBorder(new TitledBorder(new EtchedBorder(), "Source Code"));
		
		codeDisp = new JTextArea(0,60);
		
		JScrollPane scrollPane = new JScrollPane(codeDisp);
		
		codePanel.add(scrollPane);
		
		frame.add(codePanel, BorderLayout.WEST);
	}
	
	private JPanel layoutControlArea() {
		
		JPanel contPanel = new JPanel(new GridBagLayout());
		contPanel.setBorder(new TitledBorder(new EtchedBorder(), "Emulator Controls"));
		
		startButton = new JButton("Start/Pause");
		
		resetButton = new JButton("Reset");
		
		speedSelect = new JSlider(JSlider.HORIZONTAL,0, 2, 1);
		speedSelect.setMajorTickSpacing(1);
		speedSelect.setMinorTickSpacing(1);
		speedSelect.setPaintTicks(true);
		
		Hashtable labelTable = new Hashtable();
		labelTable.put(new Integer (2), new JLabel("Fastest"));
		labelTable.put(new Integer (1), new JLabel("Medium"));
		labelTable.put(new Integer (0), new JLabel("Slowest"));
		speedSelect.setLabelTable(labelTable);
		speedSelect.setPaintLabels(true);
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,30,10);
		
		
		contPanel.add(startButton, c);
		c.gridx = 1;
		contPanel.add(resetButton, c);
		c.gridy = 1;
		c.gridx = 0;
		c.gridwidth = 2;
		contPanel.add(speedSelect, c);
		
		return contPanel;
		
	}
	
	public String getFileName() {
		
		String filename = null;
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setFileFilter(new S68Filter());
		int option = fc.showOpenDialog(null);
		if (option == JFileChooser.APPROVE_OPTION) {
			filename = fc.getSelectedFile().getPath();
		}

		return filename;
	}
	
	public void setStartListener(final ActionListener listener) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				startButton.addActionListener(listener);
			}
		});
		
	}
	
	public void setResetListener(final ActionListener listener) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				resetButton.addActionListener(listener);
			}
		});
		
	}
	
	public void setOpenFileListener(final ActionListener listener) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
			openItem.addActionListener(listener);
			}
		});
	}
	
	public void setSpeedListener(final ChangeListener listener) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				speedSelect.addChangeListener(listener);
			}
		});
	}
	
	public void reset() {//TODO dont need this!!!!!!!!!!!!!!!!!!!!!!!
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				for (int i = 0; i < 8; i++) {
					dataRegisterField[i].setText("00000000");
					addressRegisterField[i].setText("00000000");
				}
				pcField.setText("00000000");
				xBitField.setText("0");
				nBitField.setText("0");
				zBitField.setText("0");
				vBitField.setText("0");
				cBitField.setText("0");
				memTable.setModel(new MemoryTableModel());
			}
		});
	}
	
	public void setSource(final String source) {
		EventQueue.invokeLater(new Runnable () {
			public void run() {
				codeDisp.setText(source);
			}
		});
		
	}
	
	public void updateMemory(int address, int data) {
		final int row = address /16;
		final int column = address % 16;
		final String hexString = String.format("%02X", data);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				memTable.setValueAt(hexString, row, column);
			}
		});
	}
	
	public void updatePC(final int address) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				pcField.setText((String.format("%08x", address)).toUpperCase());
				pcField.setBackground(Color.GREEN);
			}
		});
		
	}

	public void updateDataRegisterDisplay(final int register, final int data) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				dataRegisterField[register].setText((String.format("%08x", data)).toUpperCase());
				dataRegisterField[register].setBackground(Color.GREEN);
			}
		});
	}

	public void updateAddressRegisterDisplay(final int register, final int data) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				addressRegisterField[register].setText((String.format("%08x", data)).toUpperCase());
				addressRegisterField[register].setBackground(Color.GREEN);
			}
		});
		
	}

	
	public void updateStatusRegister(final short value) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				cBitField.setText((value & 0x1) + "");
				vBitField.setText(((value & 0x2) >>> 1) + "");
				zBitField.setText(((value & 0x4) >>> 2) + "");
				nBitField.setText(((value & 0x8) >>> 3) + "");
				xBitField.setText(((value & 0x10) >>> 4) + "");
		
			}
		});
	}

	@Override
	public void resetBackgroundColour() {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				for (int i = 0; i < 8; i++) {
					dataRegisterField[i].setBackground(originalColor);
					addressRegisterField[i].setBackground(originalColor);
				}
				pcField.setBackground(originalColor);
				xBitField.setBackground(originalColor);
				nBitField.setBackground(originalColor);
				zBitField.setBackground(originalColor);
				vBitField.setBackground(originalColor);
				cBitField.setBackground(originalColor);
			}
		});
	}
}
