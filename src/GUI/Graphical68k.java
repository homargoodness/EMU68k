package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Hashtable;

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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class Graphical68k extends EmulatorUI {
	
	private JFrame frame;
	
	/** main menu components **/
	private JMenuItem openItem;
	private JMenuItem exitItem;
	private JMenuItem aboutItem;
	
	/** data registers components **/
	private JTextField d0Field, d1Field, d2Field, d3Field, d4Field, d5Field, d6Field, d7Field;
	
	/** address registers components **/
	private JTextField a0Field, a1Field, a2Field, a3Field, a4Field, a5Field, a6Field, a7Field;
	
	/** status register components */
	private JTextField xBitField, nBitField, zBitField, vBitField, cBitField;
	
	/** program counter component */
	private JTextField pcField;
	
	/** memory table */
	private JTable memTable;
	
	/** area which displays source code */
	JTextArea codeDisp;
	
	/** emulator controls */
	private JButton startButton, stopButton;
	private JSlider speedSelect;
	
	public Graphical68k() {
		
		EventQueue.invokeLater(new Runnable() {
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
	
	private void layoutEast() {
		
		JPanel eastPanel = new JPanel(new BorderLayout());
		
		JPanel regPanel = new JPanel(new GridBagLayout());
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
		d0Field = new JTextField("000000", 6);
		d0Field.setEditable(false);
		dataPanel.add(d0Field, fields);
		
		labels.gridx= 2;
		dataPanel.add(new JLabel("D1", 2), labels);
		fields.gridx= 3;
		d1Field = new JTextField("000000", 6);
		d1Field.setEditable(false);
		dataPanel.add(d1Field, fields);
		
		labels.gridx = 0;
		labels.gridy = 1;
		dataPanel.add(new JLabel("D2",2), labels);
		fields.gridx = 1;
		fields.gridy = 1;
		d2Field = new JTextField("000000", 6);
		d2Field.setEditable(false);
		dataPanel.add(d2Field, fields);
		
		labels.gridx = 2;
		dataPanel.add(new JLabel("D3", 2), labels);
		fields.gridx = 3;
		d3Field = new JTextField("000000", 6);
		d3Field.setEditable(false);
		dataPanel.add(d3Field, fields);
		
		labels.gridx= 0;
		labels.gridy= 2;
		dataPanel.add(new JLabel("D4", 2), labels);
		fields.gridx= 1;
		fields.gridy = 2;
		d4Field = new JTextField("000000", 6);
		d4Field.setEditable(false);
		dataPanel.add(d4Field, fields);
		
		labels.gridx= 2;
		dataPanel.add(new JLabel("D5", 2), labels);
		fields.gridx= 3;
		d5Field = new JTextField("000000", 6);
		d5Field.setEditable(false);
		dataPanel.add(d5Field, fields);
		
		labels.gridx= 0;
		labels.gridy= 3;
		dataPanel.add(new JLabel("D6", 2), labels);
		fields.gridx= 1;
		fields.gridy = 3;
		d6Field = new JTextField("000000", 6);
		d6Field.setEditable(false);
		dataPanel.add(d6Field, fields);
		
		labels.gridx= 2;
		dataPanel.add(new JLabel("D7", 2), labels);
		fields.gridx= 3;
		d7Field = new JTextField("000000", 6);
		d7Field.setEditable(false);
		dataPanel.add(d7Field, fields);
		
		return dataPanel;
				
	}
	
	private JPanel layoutAddressRegisters() {
		
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
		a0Field = new JTextField("000000", 6);
		a0Field.setEditable(false);
		addressPanel.add(a0Field, fields);
		
		labels.gridx= 2;
		addressPanel.add(new JLabel("A1", 2), labels);
		fields.gridx= 3;
		a1Field = new JTextField("000000", 6);
		a1Field.setEditable(false);
		addressPanel.add(a1Field, fields);
		
		labels.gridx = 0;
		labels.gridy = 1;
		addressPanel.add(new JLabel("A2", 2), labels);
		fields.gridx = 1;
		fields.gridy = 1;
		a2Field = new JTextField("000000", 6);
		a2Field.setEditable(false);
		addressPanel.add(a2Field, fields);
		
		labels.gridx = 2;
		addressPanel.add(new JLabel("A3", 2), labels);
		fields.gridx = 3;
		a3Field = new JTextField("000000", 6);
		a3Field.setEditable(false);
		addressPanel.add(a3Field, fields);
		
		labels.gridx= 0;
		labels.gridy= 2;
		addressPanel.add(new JLabel("A4", 2), labels);
		fields.gridx= 1;
		fields.gridy = 2;
		a4Field = new JTextField("000000", 6);
		a4Field.setEditable(false);
		addressPanel.add(a4Field, fields);
		
		labels.gridx= 2;
		addressPanel.add(new JLabel("A5", 2), labels);
		fields.gridx= 3;
		a5Field = new JTextField("000000", 6);
		a5Field.setEditable(false);
		addressPanel.add(a5Field, fields);
		
		labels.gridx= 0;
		labels.gridy= 3;
		addressPanel.add(new JLabel("A6", 2), labels);
		fields.gridx= 1;
		fields.gridy = 3;
		a6Field = new JTextField("000000", 6);
		a6Field.setEditable(false);
		addressPanel.add(a6Field, fields);
		
		labels.gridx= 2;
		addressPanel.add(new JLabel("A7", 2), labels);
		fields.gridx= 3;
		a7Field = new JTextField("000000", 6);
		a7Field.setEditable(false);
		addressPanel.add(a7Field, fields);
		
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
		
		pcField = new JTextField("000000", 6);
		c.gridx = 1;
		pcPanel.add(pcField, c);
		
		return pcPanel;
	}
	
	private void layoutCenter() {
		
		JPanel memoryPanel = new JPanel(new GridLayout());
		memoryPanel.setBorder(new TitledBorder(new EtchedBorder(), "Main Memory"));
		
		memTable = new JTable(new MemoryTableModel());
		JScrollPane scrollPane = new JScrollPane(memTable);
		
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
		
		
		
		startButton = new JButton("Start");
		
		stopButton = new JButton("Stop");
		
		speedSelect = new JSlider(JSlider.HORIZONTAL,1, 3, 3);
		speedSelect.setMajorTickSpacing(1);
		speedSelect.setMinorTickSpacing(1);
		speedSelect.setPaintTicks(true);
		
		Hashtable labelTable = new Hashtable();
		labelTable.put(new Integer (1), new JLabel("Slow"));
		labelTable.put(new Integer (2), new JLabel("Medium"));
		labelTable.put(new Integer (3), new JLabel("Fast"));
		speedSelect.setLabelTable(labelTable);
		speedSelect.setPaintLabels(true);
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,30,10);
		
		
		contPanel.add(startButton, c);
		c.gridx = 1;
		contPanel.add(stopButton, c);
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
	
	public void setStopListener(final ActionListener listener) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				stopButton.addActionListener(listener);
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
	
	public void updateMemory(int address, byte data) {
		final int row = address /16;
		final int column = address % 16;
		final String hexString = String.format("%02X", data);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				memTable.setValueAt(hexString, row, column);
			}
		});
	}
	
	public void setSource(String source) {
		codeDisp.setText(source);
	}
	
	public void updatePC(long address) {

		final StringBuilder formatted = new StringBuilder(Long.toHexString(address).toUpperCase());
		while (formatted.length() < 6) {
			formatted.insert(0,'0');
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				pcField.setText(formatted.toString());
			}
		});
		
	}

	@Override
	public void updateDataRegisterDisplay(String register) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAddressRegisterDisplay(String register) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateXBit(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNBit(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateZBit(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateVBit(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCBit(boolean value) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

	

	

}
