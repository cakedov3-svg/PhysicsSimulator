package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	
	private static final Vector2D DEFAULT_C = new Vector2D();
	private static final double DEFAULT_g = 9.81;
	

	private Vector2D c;
	private double g;
	
	public MovingTowardsFixedPoint(Vector2D c, double g) {
		if(g <= 0) throw new IllegalArgumentException("El valor de g es incorrecto");
		else if(c == null) throw new IllegalArgumentException("El valor de c es nulo");
		this.c = c;
		this.g = g;
	}
	
	public MovingTowardsFixedPoint(Vector2D c) {
		this(c, DEFAULT_g);
	}
	
	public MovingTowardsFixedPoint(double g) {
		this(DEFAULT_C, g);
	}
	
	public MovingTowardsFixedPoint() {
		this(DEFAULT_C, DEFAULT_g);
	}
	
	@Override
	public void apply(List<Body> bs) {
		Vector2D f = new Vector2D();
		Vector2D d;
		for(Body B1 : bs) {
			d = c.minus(B1.getPosition());
			d = d.direction();
			f = d.scale(B1.getMass() * g);
			B1.addForce(f);
		}
	}
	
	@Override
	public String toString() {
		return "Moving towards " + c + " with constant acceleration " + g;
	}
}
