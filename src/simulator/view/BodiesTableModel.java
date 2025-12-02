package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	String[] _header = { "Id", "gId", "Mass", "Velocity", "Position", "Force" };
	private static final int NUM_COLS = 6;
	List<Body> _bodies;
	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		// Registra this como observador
		ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return _bodies.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return _header[col];
	}
	
	@Override
	public int getColumnCount() {
		return _header.length;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex < 0 || rowIndex >= _bodies.size() || columnIndex < 0 || columnIndex >= NUM_COLS) 
			throw new IllegalArgumentException("Posicion ilegal de la tabla seleccionada");
		switch(columnIndex) {
		case 0:
			return _bodies.get(rowIndex).getId();
		case 1:
			return _bodies.get(rowIndex).getgId();
		case 2:
			return _bodies.get(rowIndex).getMass();
		case 3:
			return _bodies.get(rowIndex).getVelocity();
		case 4:
			return _bodies.get(rowIndex).getPosition();
		case 5:
			return _bodies.get(rowIndex).getForce();
		}
		return null;
	}
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		fireTableStructureChanged();
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_bodies.clear();
		fireTableStructureChanged();
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for(BodiesGroup g : groups.values()) {
			for(Body b : g) _bodies.add(b);
		}
		fireTableStructureChanged();
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		for(Body b : g) {
			_bodies.add(b);
		}
		fireTableStructureChanged();
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_bodies.add(b);
		fireTableStructureChanged();
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
	}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}
}
