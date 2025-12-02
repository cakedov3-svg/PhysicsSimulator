package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;
import simulator.model.MovingTowardsTwoFixedPoints;

public class MovingTowardsTwoFixedPointsBuilder extends Builder<ForceLaws>{

	public MovingTowardsTwoFixedPointsBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}
	
	public MovingTowardsTwoFixedPointsBuilder() {
		super("mt2fp", "force of moving towards two fixed point");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		if(data == null) throw new IllegalArgumentException("Algun parametro del JSONObject es incorrecto");
		if(data.has("g1") && data.has("c1") &&data.has("g2") && data.has("c2")) {
			double x = data.getJSONArray("c1").getDouble(0);
			double y = data.getJSONArray("c1").getDouble(1);
			Vector2D c1 = new Vector2D(x, y);
			x = data.getJSONArray("c2").getDouble(0);
			y = data.getJSONArray("c2").getDouble(1);
			Vector2D c2 = new Vector2D(x, y);
			return new MovingTowardsTwoFixedPoints(c1, c2, data.getDouble("g1"), data.getDouble("g2"));
		}
		else if(data.has("g1") && data.has("c1") &&data.has("g2")) {
			double x = data.getJSONArray("c1").getDouble(0);
			double y = data.getJSONArray("c1").getDouble(1);
			Vector2D c1 = new Vector2D(x, y);
			return new MovingTowardsTwoFixedPoints(c1, 1, data.getDouble("g1"), data.getDouble("g2"));
		}
		else if(data.has("g1") && data.has("c2") &&data.has("g2")) {
			double x = data.getJSONArray("c2").getDouble(0);
			double y = data.getJSONArray("c2").getDouble(1);
			Vector2D c2 = new Vector2D(x, y);
			return new MovingTowardsTwoFixedPoints(c2, 2, data.getDouble("g1"), data.getDouble("g2"));
		}
		else if(data.has("c1") && data.has("c2") && data.has("g1")) {
			double x = data.getJSONArray("c1").getDouble(0);
			double y = data.getJSONArray("c1").getDouble(1);
			Vector2D c1 = new Vector2D(x, y);
			x = data.getJSONArray("c2").getDouble(0);
			y = data.getJSONArray("c2").getDouble(1);
			Vector2D c2 = new Vector2D(x, y);
			return new MovingTowardsTwoFixedPoints(c1, c2, data.getDouble("g1"), 1);
		}
		else if(data.has("c1") && data.has("c2") && data.has("g2")) {
			double x = data.getJSONArray("c1").getDouble(0);
			double y = data.getJSONArray("c1").getDouble(1);
			Vector2D c1 = new Vector2D(x, y);
			x = data.getJSONArray("c2").getDouble(0);
			y = data.getJSONArray("c2").getDouble(1);
			Vector2D c2 = new Vector2D(x, y);
			return new MovingTowardsTwoFixedPoints(c1, c2, data.getDouble("g2"), 2);
		}
		else if(data.has("g1") && data.has("g2")) {
			return new MovingTowardsTwoFixedPoints(data.getDouble("g1"), data.getDouble("g2"));
		}
		else if(data.has("c1") && data.has("c2")) {
			double x = data.getJSONArray("c1").getDouble(0);
			double y = data.getJSONArray("c1").getDouble(1);
			Vector2D c1 = new Vector2D(x, y);
			x = data.getJSONArray("c2").getDouble(0);
			y = data.getJSONArray("c2").getDouble(1);
			Vector2D c2 = new Vector2D(x, y);
			return new MovingTowardsTwoFixedPoints(c1, c2);
		}
		else if(data.has("c1") && data.has("g1")) {
			double x = data.getJSONArray("c1").getDouble(0);
			double y = data.getJSONArray("c1").getDouble(1);
			Vector2D c = new Vector2D(x, y);
			return new MovingTowardsTwoFixedPoints(c, 1, data.getDouble("g1"), 1);
		}
		else if(data.has("c1") && data.has("g2")) {
			double x = data.getJSONArray("c1").getDouble(0);
			double y = data.getJSONArray("c1").getDouble(1);
			Vector2D c = new Vector2D(x, y);
			return new MovingTowardsTwoFixedPoints(c, 1, data.getDouble("g2"), 2);
		}
		else if(data.has("c2") && data.has("g1")) {
			double x = data.getJSONArray("c2").getDouble(0);
			double y = data.getJSONArray("c2").getDouble(1);
			Vector2D c = new Vector2D(x, y);
			return new MovingTowardsTwoFixedPoints(c, 2, data.getDouble("g1"), 1);
		}
		else if(data.has("c2") && data.has("g2")) {
			double x = data.getJSONArray("c2").getDouble(0);
			double y = data.getJSONArray("c2").getDouble(1);
			Vector2D c = new Vector2D(x, y);
			return new MovingTowardsTwoFixedPoints(c, 2, data.getDouble("g2"), 2);
		}
		else if(data.has("g1")) {
			return new MovingTowardsTwoFixedPoints(data.getDouble("g1"), 1);
		}
		else if(data.has("g2")) {
			return new MovingTowardsTwoFixedPoints(data.getDouble("g2"), 2);
		}
		else if(data.has("c1")) {
			double x = data.getJSONArray("c1").getDouble(0);
			double y = data.getJSONArray("c1").getDouble(1);
			Vector2D c1 = new Vector2D(x, y);
			return new MovingTowardsTwoFixedPoints(c1, 1);
		}
		else if(data.has("c2")) {
			double x = data.getJSONArray("c2").getDouble(0);
			double y = data.getJSONArray("c2").getDouble(1);
			Vector2D c2 = new Vector2D(x, y);
			return new MovingTowardsTwoFixedPoints(c2, 2);
		}
		else return new MovingTowardsFixedPoint();
	}

	@Override
	protected JSONObject getData() {JSONObject j = new JSONObject();
	j.put("c1", "the first point towards bodies move (a json list of 2 numbers, e.g., [100.0,50.0])");
	j.put("c2", "the second point towards bodies move (a json list of 2 numbers, e.g., [100.0,50.0])");
	j.put("g1", "the length of the first acceleration vector (a number)");
	j.put("g2", "the length of the second acceleration vector (a number)");
	return j;
	}

}

