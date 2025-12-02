package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body>{

	public MovingBodyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}
	
	public MovingBodyBuilder() {
		super("mv_body", "moving body");
	}
	
	//TODO Revisar dispositiva 14 del enunciado 

	@Override
	protected Body createInstance(JSONObject data) {
		if(data == null || !data.has("id") || !data.has("gid") || !data.has("p") || !data.has("v") || !data.has("m")) 
			throw new IllegalArgumentException("El numero de parametros no es el requerido");
		
		String id = data.getString("id");
		String gid = data.getString("gid");
		JSONArray p = data.getJSONArray("p");
		JSONArray v = data.getJSONArray("v");
		double mass = data.getDouble("m");
		
		if(id == null || id.trim().length() == 0 || gid == null || gid.trim().length() == 0 || p.length() != 2 || v.length() != 2 || mass <= 0) 
			throw new IllegalArgumentException("Algun parametro del JSONObject es incorecto");
		
		Vector2D pos = new Vector2D((Double) p.get(0), (Double) p.get(1));
		Vector2D vel = new Vector2D((Double) v.get(0), (Double) v.get(1));
		return new MovingBody(id, gid, pos, vel, mass);
	}

	@Override
	protected JSONObject getData() {
		return null;
	}

}
