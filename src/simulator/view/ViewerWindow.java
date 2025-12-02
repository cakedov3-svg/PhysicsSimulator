package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ViewerWindow extends JFrame implements SimulatorObserver {
	private Controller _ctrl;
	private SimulationViewer _viewer;
	private JFrame _parent;
	Viewer viewer;
	
	ViewerWindow(JFrame parent, Controller ctrl) {
		super("Simulation Viewer");
		_ctrl = ctrl;
		_parent = parent;
		intiGUI();
		// Registrar this como observador
		ctrl.addObserver(this);
	}
	private void intiGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		// Poner contentPane como mainPanel con scrollbars(JScrollPane)
		JScrollPane scroll = new JScrollPane(mainPanel);
		this.getContentPane().add(scroll);
		
		// Crear el viewer y añadirlo a mainPanel (en el centro)
		viewer = new Viewer();
		mainPanel.add(viewer);
		
		// Añadir WindowListener y en el método windowClosing, eliminar ‘this’
		//de los observadores
		addWindowListener(new WindowListener() {

			@Override
			public void windowClosing(WindowEvent e) {
				elimObserver();
			}

			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			
		});
		pack();
		if (_parent != null)
		setLocation(_parent.getLocation().x + _parent.getWidth()/2 -
		getWidth()/2,_parent.getLocation().y + _parent.getHeight()/2
		- getHeight()/2);
		setVisible(true);
	}
	
	private void elimObserver() { _ctrl.removeObserver(this);}
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		viewer.repaint();
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		viewer.reset();
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for(BodiesGroup g : groups.values()) viewer.addGroup(g);
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		viewer.addGroup(g);
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		viewer.addBody(b);
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
	}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}
}
