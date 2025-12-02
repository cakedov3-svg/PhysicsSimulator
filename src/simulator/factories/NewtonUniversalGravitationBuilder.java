package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws>{

	public NewtonUniversalGravitationBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}
	
	public NewtonUniversalGravitationBuilder() {
		super("nlug", "newtons universal gravitation law");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		if(data == null) throw new IllegalArgumentException("Algun parametro del JSONObject es incorrecto");
		if(!data.has("G")) return new NewtonUniversalGravitation();
		else return new NewtonUniversalGravitation(data.getDouble("G"));
	}

	@Override
	protected JSONObject getData() {
		JSONObject j = new JSONObject();
		j.put("G", "the gravitational constant (a number)");
		return j;
	}

}
