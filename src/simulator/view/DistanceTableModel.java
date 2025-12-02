package simulator.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class DistanceTableModel extends AbstractTableModel implements SimulatorObserver{
	
	private class Info{

		double dist;
		Vector2D lastPos;
		
		public Info(double d, Vector2D v) {
			dist = d;
			lastPos = v;
		}
		
		public double getDist() {
			return dist;
		}
		public Vector2D getLastPos() {
			return lastPos;
		}
		
	}
	
	String[] _header = { "Id", "Acumulated Distance" };
	private static final int NUM_COLS = 2;
	List<Body> _bodies;
	Map<Body, Info> mapa;
	
	DistanceTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		mapa = new HashMap<>();
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
			return mapa.get(_bodies.get(rowIndex)).getDist();
		}
		return null;
	}
	
	public void resetDistances() {
		for(Body b : _bodies) {
			mapa.put(b, new Info(0, b.getPosition()));
		}
		fireTableStructureChanged();
	}
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for(Body b : _bodies) {
			double dist = mapa.get(b).getDist() + b.getPosition().distanceTo(mapa.get(b).getLastPos());
			mapa.put(b, new Info(dist, b.getPosition()));
		}
		fireTableStructureChanged();
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_bodies.clear();
		mapa.clear();
		fireTableStructureChanged();
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for(BodiesGroup g : groups.values()) {
			for(Body b : g) {
				_bodies.add(b);
				mapa.put(b, new Info(0, b.getPosition()));
			}
		}
		fireTableStructureChanged();
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		for(Body b : g) {
			_bodies.add(b);
			mapa.put(b, new Info(0, b.getPosition()));
		}
		fireTableStructureChanged();
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_bodies.add(b);
		mapa.put(b, new Info(0, b.getPosition()));
		fireTableStructureChanged();
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
	}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}
}
