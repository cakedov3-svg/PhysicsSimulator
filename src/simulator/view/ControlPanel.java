package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver{
	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true; // utilizado en los botones de run/stop
	private JButton _quitButton;
	private JButton _fileButton;
	private JButton _lawsButton;
	private JButton _viewerButton;
	private JButton _totalForcesButton;
	private JButton _runButton;
	private JButton _stopButton;
	private JLabel steps;
	private JSpinner _spinner;
	private JLabel dt;
	private JTextField _textF;
	private ForceLawsDialog lawsDialog;
	private TotalForcesDialog totalDialog;
	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		_toolaBar = new JToolBar();
		add(_toolaBar, BorderLayout.PAGE_START);
		// TODO crear los diferentes botones/atributos y a�adirlos a _toolaBar.
		// Todos ellos han de tener su correspondiente tooltip. Puedes utilizar
		// _toolaBar.addSeparator() para a�adir la l�nea de separaci�n vertical
		// entre las componentes que lo necesiten
		
		_fileButton = new JButton();
		_fileButton.setToolTipText("Choose input file");
		_fileButton.setIcon(loadImage("resources/icons/open.png"));
		_fileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = _fc.showOpenDialog(getParent());
				if(JFileChooser.APPROVE_OPTION == n) {
					try {
						_ctrl.reset();
						_ctrl.loadData(new FileInputStream(_fc.getSelectedFile()));
					} catch (FileNotFoundException e1) {
						Utils.showErrorMsg("Archivo seleccionado incorrecto.");
					}
				}
			}
		});
		_toolaBar.add(_fileButton);

		_toolaBar.addSeparator();
		
		
		
		_lawsButton = new JButton();
		_lawsButton.setToolTipText("Choose law to apply");
		_lawsButton.setIcon(loadImage("resources/icons/physics.png"));
		_lawsButton.addActionListener((e) -> openForceLaws());
		_toolaBar.add(_lawsButton);
		
		_viewerButton = new JButton();
		_viewerButton.setToolTipText("Show viewer window");
		_viewerButton.setIcon(new ImageIcon("resources/icons/viewer.png"));
		_viewerButton.addActionListener((e) -> openViewer());
		_toolaBar.add(_viewerButton);
		

		_toolaBar.addSeparator();
		
		_totalForcesButton = new JButton("Total forces");
		_totalForcesButton.setToolTipText("Total forces per body");
		_totalForcesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openTotalForces();
			}
		});
		_toolaBar.add(_totalForcesButton);
		
		_toolaBar.addSeparator();
		
		_runButton = new JButton();
		_runButton.setToolTipText("Run the simulation");
		_runButton.setIcon(loadImage("resources/icons/run.png"));
		_runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO Llamar a metodo run
				try {
				Double d = Double.parseDouble(_textF.getText());
				_textF.setText(d.toString());
				_ctrl.setDeltaTime(d);
				_fileButton.setEnabled(false);
				_lawsButton.setEnabled(false);
				_viewerButton.setEnabled(false);
				_totalForcesButton.setEnabled(false);
				_runButton.setEnabled(false);
				_quitButton.setEnabled(false);
				_stopped = false;
				run_sim((Integer) _spinner.getValue());
				} catch (Exception e1) {
					Utils.showErrorMsg("Invalid delta time");
				}
			}
		});
		_toolaBar.add(_runButton);
		
		_stopButton = new JButton();
		_stopButton.setToolTipText("Pause the simulation");
		_stopButton.setIcon(loadImage("resources/icons/stop.png"));
		_stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_stopped = true;
			}
		});
		_toolaBar.add(_stopButton);
		
		steps = new JLabel("Steps:");
		_toolaBar.add(steps);
		
		_spinner = new JSpinner(new SpinnerNumberModel(1000, 1, 10000, 100));
		_spinner.setPreferredSize(new Dimension(70, 40));
		_spinner.setMaximumSize(new Dimension(100, 40));
		_toolaBar.add(_spinner);
		
		dt = new JLabel("Delta-Time:");
		_toolaBar.add(dt);
		
		_textF = new JTextField();
		_textF.setPreferredSize(new Dimension(70, 40));
		_textF.setMaximumSize(new Dimension(100, 40));
		_textF.setText("250.0");
		_toolaBar.add(_textF);
		
		
		// Quit Button
		_toolaBar.add(Box.createGlue()); // this aligns the button to the right
		_toolaBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(loadImage("resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> Utils.quit(this));
		_toolaBar.add(_quitButton);
		//crear el selector de ficheros
		_fc = new JFileChooser();
		_fc.setCurrentDirectory(new File ("."));
		}
	
	public void openForceLaws() {
		if(lawsDialog == null) lawsDialog = new ForceLawsDialog(Utils.getWindow(this), _ctrl);
		lawsDialog.open();
	}
	
	public void openTotalForces() {
		if(totalDialog == null) totalDialog = new TotalForcesDialog(Utils.getWindow(this), _ctrl);
		totalDialog.open();
	}
	
	public void openViewer() {
		new ViewerWindow((JFrame) SwingUtilities.getWindowAncestor(this), _ctrl);
	}
	
	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
	}

	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
		try {
		_ctrl.run(1);
		} catch (Exception e) {
			Utils.showErrorMsg(e.getMessage());
			_fileButton.setEnabled(true);
			_lawsButton.setEnabled(true);
			_viewerButton.setEnabled(true);
			_totalForcesButton.setEnabled(true);
			_runButton.setEnabled(true);
			_quitButton.setEnabled(true);
			_stopped = true;
		return;
		}
		SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} else {
			_fileButton.setEnabled(true);
			_lawsButton.setEnabled(true);
			_viewerButton.setEnabled(true);
			_totalForcesButton.setEnabled(true);
			_runButton.setEnabled(true);
			_quitButton.setEnabled(true);
			_stopped = true;
		}
		}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		
	}


}
