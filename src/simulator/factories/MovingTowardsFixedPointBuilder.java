package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{

	public MovingTowardsFixedPointBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}
	
	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "force of moving towards a fixed point");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		if(data == null) throw new IllegalArgumentException("Algun parametro del JSONObject es incorrecto");
		if(data.has("g") && data.has("c")) {
			double x = data.getJSONArray("c").getDouble(0);
			double y = data.getJSONArray("c").getDouble(1);
			Vector2D c = new Vector2D(x, y);
			return new MovingTowardsFixedPoint(c, data.getDouble("g"));
		}
		else if(data.has("g")) return new MovingTowardsFixedPoint(data.getDouble("g"));
		else if(data.has("c")) {
			double x = data.getJSONArray("c").getDouble(0);
			double y = data.getJSONArray("c").getDouble(1);
			Vector2D c = new Vector2D(x, y);
			return new MovingTowardsFixedPoint(c);
		}
		else return new MovingTowardsFixedPoint();
	}

	@Override
	protected JSONObject getData() {JSONObject j = new JSONObject();
	j.put("c", "the point towards which bodies move (e.g., [100.0,50.0])");
	j.put("g", "the length of the acceleration vector (a number)");
	return j;
	}

}
