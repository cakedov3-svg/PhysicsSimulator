package simulator.view;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;

public class TotalForcesDialog extends JDialog{
	
	Controller _ctrl;
	private AbstractTableModel _totalForcesTableModel;
	
	TotalForcesDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		setTitle("Total Forces Dialog");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		_totalForcesTableModel = new TotalForcesTableModel(_ctrl);
		JTable tabla = new JTable(_totalForcesTableModel);
		JScrollPane scroll = new JScrollPane(tabla);
		mainPanel.add(scroll);
		
		JPanel boton = new JPanel();
		boton.setAlignmentX(CENTER_ALIGNMENT);
		boton.setLayout(new FlowLayout());
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TotalForcesDialog.this.setVisible(false);
			}
		});
		boton.add(okButton);
		mainPanel.add(boton);
		
		pack();
		//setResizable(false);
		setLocationRelativeTo(null);
		setVisible(false);
	}
	
	public void open() {
		pack();
		setVisible(true);
	}
}
