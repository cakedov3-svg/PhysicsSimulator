package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body{

	public MovingBody(String id, String gid, Vector2D p, Vector2D v, double mass) {
		super(id, gid, p, v, mass);
	}

	@Override
	void advance(double dt) {
		Vector2D a = new Vector2D();
		if(mass != 0) a = f.scale(1.0/mass);
		Vector2D v_ini = v.scale(dt);
		v_ini = v_ini.plus(a.scale(0.5 * dt * dt));
		p = p.plus(v_ini);
		v = v.plus(a.scale(dt));
	}

}
