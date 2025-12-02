package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.factories.Factory;
import simulator.misc.Vector2D;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.SimulatorObserver;

class ForceLawsDialog extends JDialog implements SimulatorObserver {

	private DefaultComboBoxModel<String> _lawsModel;
	
	private DefaultComboBoxModel<String> _groupsModel;
	
	private DefaultTableModel _dataTableModel;
	
	private Controller _ctrl;
	
	private List<JSONObject> _forceLawsInfo;
	
	private String[] _headers = { "Key", "Value", "Description" };
	
	// En caso de ser necesario, añadir los atributos aquí…
	
	JComboBox<String> groupsBox;
	
	JComboBox<String> lawsBox;
	
	JTable tabla;
	
	private int _status;
	
	ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		_ctrl = ctrl;
		_forceLawsInfo = ctrl.getForceLawsInfo();
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		// help
		JLabel help = new JLabel("<html><p>Select a force law and provide" +
		" values for the parametes in the <b>Value column</b> (default values" +
		" are used for parametes with no value).</p></html>");
		help.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(help);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		// Crea un JTable que use _dataTableModel, y añadirla al panel
		_dataTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 1; 
			}
		};
		// TODO crear un JTable que use _dataTableModel, y añadirla al panel
		_dataTableModel.setColumnIdentifiers(_headers);
		updateTableModel(0);
		tabla = new JTable(_dataTableModel) {
			private static final long serialVersionUID = 1L;

			// we override prepareRenderer to resize columns to fit to content
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		JScrollPane tabelScroll = new JScrollPane(tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(tabelScroll);
		
		JPanel ctrlPanel = new JPanel();
		ctrlPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(ctrlPanel);
		
		_lawsModel = new DefaultComboBoxModel<>();
		// Añadir la descripción de todas las leyes de fuerza a _lawsModel
		if(_forceLawsInfo != null)
			for(JSONObject j : _forceLawsInfo) {
				_lawsModel.addElement(j.getString("desc"));
			}
		// Crear un combobox que use _lawsModel y añadirlo al panel
		
		JPanel cajas = new JPanel();
		cajas.setAlignmentX(CENTER_ALIGNMENT);
		cajas.setLayout(new FlowLayout());
		
		JLabel cajaLaw = new JLabel("Force Law: ");
		cajas.add(cajaLaw);
		lawsBox = new JComboBox<String>(_lawsModel);
		lawsBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					updateTableModel(((JComboBox) e.getSource()).getSelectedIndex());
				}
			});
		cajas.add(lawsBox);
		
		JLabel cajaGroups = new JLabel("Group: ");
		cajas.add(cajaGroups);
		_groupsModel = new DefaultComboBoxModel<>();
		// Crear un combobox que use _groupsModel y añadirlo al panel
		groupsBox = new JComboBox<String>(_groupsModel);
		cajas.add(groupsBox);
		
		mainPanel.add(cajas);
		// Crear los botones OK y Cancel y añadirlos al panel
		
		JPanel botones = new JPanel();
		botones.setAlignmentX(CENTER_ALIGNMENT);
		botones.setLayout(new FlowLayout());
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ForceLawsDialog.this.setVisible(false);
			}
		});
		botones.add(cancelButton);
		
		botones.add(Box.createRigidArea(new Dimension(10,0)));
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 1;
				ForceLawsDialog.this.setVisible(false);
			}
		});
		botones.add(okButton);
		
		mainPanel.add(botones);
		
		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	private void updateTableModel(int index) {
		JSONObject j = (JSONObject) _forceLawsInfo.get(index).get("data");
		if(j != null) {
			int cont = 0;
			_dataTableModel.setNumRows(j.keySet().size());
			for(String o : j.keySet()) {
				_dataTableModel.setValueAt(o, cont, 0);
				_dataTableModel.setValueAt("", cont, 1);
				_dataTableModel.setValueAt(j.getString(o), cont, 2);
				cont++;
			}
		} else _dataTableModel.setNumRows(0);
		_dataTableModel.fireTableStructureChanged();
	}
	
	//TODO Se encarga de devolver si se ha pulsado OK o CANCEL
	public void open() {
		if (_groupsModel.getSize() != 0) {
		// Establecer la posición de la ventana de diálogo de tal manera
		//que se abra en el centro de la ventana principal
		
		pack();
		setVisible(true);
		
		if(_status == 1) {
			JSONObject fl = new JSONObject("{\"type\":" + _forceLawsInfo.get(lawsBox.getSelectedIndex()).getString("type")
					+ ", \"data\":" + getData() + "}");
			String gid = _groupsModel.getSelectedItem().toString();
			try {
				_ctrl.setForceLaws(gid, fl);
			} catch(Exception e) {
				Utils.showErrorMsg("Se han introducido datos invalidos en la creacion de una ley de fuerza");
			}
		}
		}
		setVisible(false);
	}
		// TODO el resto de métodos van aquí…
	
	public String getData() {
		StringBuilder s = new StringBuilder();
		s.append('{');
		for (int i = 0; i < _dataTableModel.getRowCount(); i++) {
			String k = _dataTableModel.getValueAt(i, 0).toString();
			String v = _dataTableModel.getValueAt(i, 1).toString();
			if (!v.isEmpty()) {
				s.append('"');
				s.append(k);
				s.append('"');
				s.append(':');
				s.append(v);
				s.append(',');
			}
		}

		if (s.length() > 1)
			s.deleteCharAt(s.length() - 1);
		s.append('}');

		return s.toString();
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_groupsModel.removeAllElements();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for(BodiesGroup g : groups.values()) _groupsModel.addElement(g.getId());
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groupsModel.addElement(g.getId());
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