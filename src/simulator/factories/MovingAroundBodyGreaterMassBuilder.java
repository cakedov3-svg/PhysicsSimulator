package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.MovingAroundBodyGreaterMass;

public class MovingAroundBodyGreaterMassBuilder  extends Builder<ForceLaws>{
	
	public MovingAroundBodyGreaterMassBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}
	
	public MovingAroundBodyGreaterMassBuilder() {
		super("mabgm", "Moving around the body with greater mass");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		if(data == null) throw new IllegalArgumentException("Algun parametro del JSONObject es incorrecto");
		if(data.has("G") && data.has("C")) return new MovingAroundBodyGreaterMass(data.getDouble("G"), data.getDouble("C"));
		else if(data.has("G")) return new MovingAroundBodyGreaterMass(data.getDouble("G"), 1);
		else if(data.has("C")) return new MovingAroundBodyGreaterMass(data.getDouble("C"), 2);
		else return new MovingAroundBodyGreaterMass();
	}

	@Override
	protected JSONObject getData() {
		JSONObject j = new JSONObject();
		j.put("G", "the gravitational constant (a number)");
		j.put("C", "the rotation factor (a number)");
		return j;
	}
}
