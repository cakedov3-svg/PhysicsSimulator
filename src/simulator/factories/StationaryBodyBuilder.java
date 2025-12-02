package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body>{

	public StationaryBodyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}
	
	public StationaryBodyBuilder() {
		super("st_body", "stationary body");
	}

	@Override
	protected Body createInstance(JSONObject data) {
		if(data == null || !data.has("id") || !data.has("gid") || !data.has("p") || !data.has("m")) 
			throw new IllegalArgumentException("El numero de parametros no es el requerido");
		
		String id = data.getString("id");
		String gid = data.getString("gid");
		JSONArray p = data.getJSONArray("p");
		double mass = data.getDouble("m");
		
		if(id == null || id.trim().length() == 0 || gid == null || gid.trim().length() == 0 || p.length() != 2 || mass <= 0) 
			throw new IllegalArgumentException("Algun parametro del JSONObject es incorecto");
		
		Vector2D pos = new Vector2D((Double) p.get(0), (Double) p.get(1));
		return new StationaryBody(id, gid, pos, mass);
	}

	@Override
	protected JSONObject getData() {
		return null;
	}

}
