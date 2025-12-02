package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public abstract class Body {

	protected String id;
	protected String gid;
	protected Vector2D v;
	protected Vector2D f;
	protected Vector2D p;
	protected double mass;
	
	Body(String id, String gid, Vector2D p, Vector2D v, double mass){
		if(id == null || gid == null || v == null || p == null || id.trim().length() == 0 || gid.trim().length() == 0 || mass <= 0) 
			throw new IllegalArgumentException("Parametros no validos en el constructor de un Body");
		this.id = id;
		this.gid = gid;
		this.v = v;
		this.f = new Vector2D();
		this.p = p;
		this.mass = mass;
	}
	
	public String getId() {
		return id;
	}
	
	public String getgId() {
		return gid;
	}
	
	public Vector2D getVelocity() {
		return v;
	}
	
	public Vector2D getForce() {
		return f;
	}
	
	public Vector2D getPosition() {
		return p;
	}
	
	public double getMass() {
		return mass;
	}
	
	void addForce(Vector2D f) {
		this.f = this.f.plus(f);
	}
	
	void resetForce() {
		f = new Vector2D();
	}
	
	abstract void advance(double dt);
	
	public JSONObject getState() {
		JSONObject j = new JSONObject();
		j.putOnce("id",  id);
		j.putOnce("m",  mass);
		j.putOnce("p", p.asJSONArray());
		j.putOnce("v",  v.asJSONArray());
		j.putOnce("f",  f.asJSONArray());
		return j;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (!(obj instanceof Body ))
			return false;
		
		Body other = (Body) obj;
		
		if (id == null || other.id == null) {
				return false;
		} 
		else if (!id.equals(other.id))
			return false;
		
		return true;

	}
}
