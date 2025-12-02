package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator implements Observable<SimulatorObserver>{
	
	private double timeAct;
	private double dt;
	private ForceLaws ley;
	private Map<String, BodiesGroup> groups;
	private List<String> groupIds;
	private List<SimulatorObserver> observadores;
	private Map<String, BodiesGroup> groupsRO;

	
	public PhysicsSimulator(ForceLaws l, Double dt) {
		if(l == null || dt == null || dt <= 0) 
			throw new IllegalArgumentException("Los parametros del constuctor de PhysicsSimulator son invalidos");
		this.dt = dt;
		ley = l;
		groups = new HashMap<String, BodiesGroup>();
		groupsRO = Collections.unmodifiableMap(groups);
		groupIds = new ArrayList<String>();
		observadores = new ArrayList<SimulatorObserver>();
	}
	
	public void advance() {
		for(BodiesGroup b : groups.values()) {
			b.advance(dt);
		}
		timeAct += dt;
		for(SimulatorObserver o : observadores) {
			o.onAdvance(groups, timeAct);
		}
	}
	
	public void addGroup(String id) {
		if(groups.containsKey(id)) throw new IllegalArgumentException("Ya existe un grupo con ese id");
		else {
			BodiesGroup nuevo = new BodiesGroup(id, ley);
			groups.put(id, nuevo);
			groupIds.add(id);
			for(SimulatorObserver o : observadores) {
				o.onGroupAdded(groupsRO, nuevo);
			}
		}		
	}
	
	public void addBody(Body b) {
		String gid = b.getgId();
		if(!groups.containsKey(gid)) throw new IllegalArgumentException("No existe un grupo con ese identificador");
		else {
			groups.get(gid).addBody(b);
			for(SimulatorObserver o : observadores) {
				o.onBodyAdded(groupsRO, b);
			}
		}
	}
	
	public void setForceLaws(String id, ForceLaws fl) {
		if(!groups.containsKey(id)) throw new IllegalArgumentException("No existe un grupo con ese identificador");
		else {
			groups.get(id).setForceLaws(fl);
			for(SimulatorObserver o : observadores) {
				o.onForceLawsChanged(groups.get(id));
			}
		}
	}
	
	public JSONObject getState() {
		JSONObject j = new JSONObject();
		j.putOnce("time", timeAct);
		
		JSONArray ja = new JSONArray();
		for(String id : groupIds) {
			ja.put(groups.get(id).getState());
		}
		j.putOnce("groups", ja);
		
		return j;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	public void reset() {
		groups.clear();
		groupIds.clear();
		for(SimulatorObserver o : observadores) {
			o.onReset(groupsRO, timeAct, dt);
		}
	}
	
	public void setDeltaTime (double dt) {
		if(dt <= 0) throw new IllegalArgumentException("El delta time no es positivo");
		else{
			this.dt = dt;
			for(SimulatorObserver o : observadores) {
				o.onDeltaTimeChanged(dt);
			}
		}
	}
	
	public void addObserver(SimulatorObserver o) {
		if(!observadores.contains(o)) {
			observadores.add(o);
			o.onRegister(groupsRO, timeAct, dt);
		}
		else throw new IllegalArgumentException("No se pueden duplicar observadores");
	}
	
	public void removeObserver(SimulatorObserver o) {
		if(observadores.contains(o)) observadores.remove(o);
	}

}
