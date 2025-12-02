package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsTwoFixedPoints implements ForceLaws {
	
	private static final Vector2D DEFAULT_C = new Vector2D();
	private static final double DEFAULT_g = 9.81;
	

	private Vector2D c1;
	private Vector2D c2;
	private double g1;
	private double g2;
	
	public MovingTowardsTwoFixedPoints(Vector2D c1, Vector2D c2, double g1, double g2) {
		if(g1 <= 0 || g2 <= 0) throw new IllegalArgumentException("El valor de g es incorrecto");
		else if(c1 == null || c2 == null) throw new IllegalArgumentException("El valor de c es nulo");
		this.c1 = c1;
		this.g1 = g1;
		this.c2 = c2;
		this.g2 = g2;
	}
	
	public MovingTowardsTwoFixedPoints(Vector2D c1, Vector2D c2, double g, int i) {
		this(c1, c2, DEFAULT_g, DEFAULT_g);
		if(g <= 0) throw new IllegalArgumentException("El valor de g es incorrecto");
		if(i == 1) this.g1 = g;
		else this.g2 = g;
	}
	
	public MovingTowardsTwoFixedPoints(Vector2D c1, Vector2D c2) {
		this(c1, c2, DEFAULT_g, DEFAULT_g);
	}
	
	public MovingTowardsTwoFixedPoints(Vector2D c, int i) {
		this(DEFAULT_C, DEFAULT_C, DEFAULT_g, DEFAULT_g);
		if(c == null) throw new IllegalArgumentException("El valor de c es nulo");
		if(i == 1) this.c1 = c;
		else this.c2 = c;
	}
	
	public MovingTowardsTwoFixedPoints(Vector2D c, int i, double g1, double g2) {
		this(DEFAULT_C, DEFAULT_C, g1, g2);
		if(c == null) throw new IllegalArgumentException("El valor de c es nulo");
		if(i == 1) this.c1 = c;
		else this.c2 = c;
	}
	
	public MovingTowardsTwoFixedPoints(Vector2D c, int i, double g, int j) {
		this(DEFAULT_C, DEFAULT_C, DEFAULT_g, DEFAULT_g);
		if(g <= 0) throw new IllegalArgumentException("El valor de g es incorrecto");
		if(c == null) throw new IllegalArgumentException("El valor de c es nulo");
		if(i == 1) this.c1 = c;
		else this.c2 = c;
		if(j == 1) this.g1 = g;
		else this.g2 = g;
	}
	
	public MovingTowardsTwoFixedPoints(double g1, double g2) {
		this(DEFAULT_C, DEFAULT_C, g1, g2);
	}
	
	public MovingTowardsTwoFixedPoints(double g, int i) {
		this(DEFAULT_C, DEFAULT_C, DEFAULT_g, DEFAULT_g);
		if(g <= 0) throw new IllegalArgumentException("El valor de g es incorrecto");
		if(i == 1) this.g1 = g;
		else this.g2 = g;
	}
	
	public MovingTowardsTwoFixedPoints() {
		this(DEFAULT_C, DEFAULT_C, DEFAULT_g, DEFAULT_g);
	}
	
	@Override
	public void apply(List<Body> bs) {
		Vector2D f = new Vector2D();
		Vector2D d1, d2;
		for(Body B1 : bs) {
			d1 = c1.minus(B1.getPosition());
			d2 = c2.minus(B1.getPosition());
			d1 = d1.direction();
			d2 = d2.direction();
			d1 = d1.scale(g1);
			d2 = d2.scale(g2);
			f = d1.plus(d2);
			f = f.scale(B1.getMass());
			B1.addForce(f);
		}
	}
	
	@Override
	public String toString() {
		return "Moving towards two fixed points";
	}
}
