package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{
	
	private static final double DEFAULT_G = 6.67e-11;
	
	private double G;
	
	public NewtonUniversalGravitation(double G){
		if(G <= 0) throw new IllegalArgumentException("El valor de G es incorrecto");
		this.G = G;
	}
	
	public NewtonUniversalGravitation(){
		this(DEFAULT_G);
	}

	@Override
	public void apply(List<Body> bs) {
		Vector2D f;
		double module;
		Vector2D d;
		for(Body B1 : bs) {
			f = new Vector2D();
			for(Body B2 : bs) {
				d = B2.getPosition().minus(B1.getPosition());
				if(!d.equals(new Vector2D())) {
					Double dist = d.magnitude();
					module = G * B1.getMass() * B2.getMass() / (dist * dist);
					d = d.direction();
					f = f.plus(d.scale(module));
				}
			}
			B1.addForce(f);
		}
	}

	@Override
	public String toString() {
		return "Newtons Universal Gravitation with G=" + G;
	}
}
