package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup implements Iterable<Body> {
	private String id;
	private ForceLaws force;
	private List<Body> bodies;
	private List<Body> bodiesRO;
	
	public BodiesGroup(String id, ForceLaws force) {
		if(id == null || force == null || id.trim().length() == 0) 
			throw new IllegalArgumentException("Parametros no validos en el constructor de un BodiesGroup");
		this.id = id;
		this.force = force;
		bodies = new ArrayList<Body>();
		bodiesRO = Collections.unmodifiableList(bodies);
	}
	
	public String getId() {
		return id;
	}
	
	void setForceLaws(ForceLaws fl) {
		if(fl == null) throw new IllegalArgumentException("La fuerza es nula");
		force = fl;
	}
	
	void addBody(Body b) {
		if(b != null && !this.bodies.contains(b)) {
			bodies.add(b);
		} 
		else throw new IllegalArgumentException("Error al intentar a�adir un Body a un BodiesGroup");
	}
	
	void advance(double dt) {
		if(dt <= 0) throw new IllegalArgumentException("Delta time incorrecto");
		for(Body B : bodies) {
			B.resetForce();
		}
		force.apply(bodies);
		for(Body B : bodies) {
			B.advance(dt);
		}
	}
	
	public JSONObject getState() {
		JSONObject j = new JSONObject();
		j.putOnce("id",  id);
		JSONArray jBodies = new JSONArray();
		for(Body b : bodies) {
			jBodies.put(b.getState());
		}
		j.putOnce("bodies", jBodies);
		return j;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	public String getForceLawsInfo() {
		return force.toString();
	}

	@Override
	public Iterator<Body> iterator() {
		return bodiesRO.iterator();
	}
}
