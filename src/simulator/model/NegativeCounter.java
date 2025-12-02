package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simulator.control.Controller;

public class NegativeCounter implements SimulatorObserver{
	
	private class Info{

		private int countF;
		private int countV;
		
		public Info() {
			countF = 0;
			countV = 0;
		}

		public int getCountF() {
			return countF;
		}

		public int getCountV() {
			return countV;
		}
		
		public void addF (int x) {
			countF += x;
		}
		
		public void addV (int x) {
			countV += x;
		}
	}

	private int i;
	Map<Body, Info> mapa;
	List<Body> list;
	private Controller ctrl;
	
	public NegativeCounter(Controller c, int x) {
		ctrl = c;
		mapa = new HashMap<>();
		list = new ArrayList<>();
		i = x;
		c.addObserver(this);
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for(Body b : list) {
			Info aux = mapa.get(b);
			if(i == 0) {
				if(b.getForce().getX() < 0) aux.addF(1);
				if(b.getVelocity().getX() < 0) aux.addV(1);
			}
			else {
				if(b.getForce().getY() < 0) aux.addF(1);
				if(b.getVelocity().getY() < 0) aux.addV(1);
			}
			mapa.put(b, aux);
		}
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		list.clear();
		mapa.clear();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for(BodiesGroup g : groups.values()) {
			for(Body b : g) {
				list.add(b);
				mapa.put(b, new Info());
			}
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		for(Body b : g) {
			list.add(b);
			mapa.put(b, new Info());
		}
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		list.add(b);
		mapa.put(b, new Info());
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("Numero de vces que la coordenada ");
		if(i == 0) sb.append("X es negativa:\n");
		else sb.append("Y es negativa:\n");
		
		for(Body b : list) {
			Info aux = mapa.get(b);
			sb.append("    " + b.getId() + ':' + b.getgId());
			sb.append(" = Fuerza " + aux.getCountF() + ", Velocidad " + aux.getCountV() + '\n');
		}
		
		return sb.toString();
	}
}
