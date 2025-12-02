package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import simulator.control.Controller;

public class MainWindow extends JFrame{
	
	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		
		// TODO crear ControlPanel y a�adirlo en PAGE_START de mainPanel
		JPanel controlPanel = new ControlPanel(_ctrl);
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);
		
		// TODO crear StatusBar y a�adirlo en PAGE_END de mainPanel
		JPanel statusBar = new StatusBar(_ctrl);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);
		
		// Definici�n del panel de tablas (usa un BoxLayout vertical)
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
		// TODO crear la tabla de grupos y a�adirla a contentPanel.
		// Usa setPreferredSize(new Dimension(500, 250)) para fijar su tama�o
		InfoTable groupsTable = new InfoTable("Groups", new GroupsTableModel(_ctrl));
		groupsTable.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(groupsTable);
		
		// TODO crear la tabla de cuerpos y a�adirla a contentPanel.
		// Usa setPreferredSize(new Dimension(500, 250)) para fijar su tama�o
		InfoTable bodiesTable = new InfoTable("Bodies", new BodiesTableModel(_ctrl));
		bodiesTable.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(bodiesTable);
		
		InfoTableDistances distanceTable = new InfoTableDistances("Distancia recorrida", new DistanceTableModel(_ctrl));
		distanceTable.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(distanceTable);
		
		// TODO llama a Utils.quit(MainWindow.this) en el m�todo windowClosing
		this.addWindowListener (new WindowAdapter() {
				public void windowClosing(WindowEvent e) { Utils.quit(MainWindow.this); }
			}
		);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);		
	}
	
}
