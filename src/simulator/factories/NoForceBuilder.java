package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws>{

	public NoForceBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}
	
	public NoForceBuilder() {
		super("nf", "no force applied");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		return new NoForce();
	}

	@Override
	protected JSONObject getData() {
		return new JSONObject();
	}

}
