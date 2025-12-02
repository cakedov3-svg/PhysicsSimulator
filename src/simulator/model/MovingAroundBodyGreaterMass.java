package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingAroundBodyGreaterMass implements ForceLaws{

	private static final double DEFAULT_G = 6.67e-11;
	private static final double DEFAULT_C = 0.25;
	
	private double G;
	private double C;
	
	public MovingAroundBodyGreaterMass(double G, double C){
		if(G <= 0 || C < 0 || C >= 1) throw new IllegalArgumentException("El valor de G o de C es incorrecto");
		this.G = G;
		this.C = C;
	}
	
	public MovingAroundBodyGreaterMass(double x, int i){
		this();
		if(G <= 0 || C < 0 || C >= 1) throw new IllegalArgumentException("El valor de G o de C es incorrecto");
		if(x == 1) this.G = x;
		else this.C = x;
	}
	
	public MovingAroundBodyGreaterMass(){
		this(DEFAULT_G, DEFAULT_C);
	}

	@Override
	public void apply(List<Body> bs) {
		if(bs.size() > 0) {
			Body greaterMass = bs.get(0);
			for(Body b : bs) {
				if(b.getMass() > greaterMass.getMass()) greaterMass = b;
			}
			Vector2D f, d;
			double distance, force;
			for(Body b: bs) {
				if(b.getMass() < greaterMass.getMass()) {
					d = greaterMass.getPosition().minus(b.getPosition());
					distance = d.magnitude();
					force = G * greaterMass.getMass() * b.getMass() * (1 - C) / (distance * distance);
					d = new Vector2D(-d.getY(), d.getX());
					d = d.direction();
					f = d.scale(force);
					b.addForce(f);
				}
				else b.addForce(new Vector2D());
			}
		}
	}

	@Override
	public String toString() {
		return "Moving around body with greater mass with G=" + G + " and C=" + C;
	}
}
