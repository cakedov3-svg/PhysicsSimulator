package simulator.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	
	private Map<String,Builder<T>> builders;
	private List<JSONObject> buildersInfo;
	
	public BuilderBasedFactory() {
		builders = new HashMap<String, Builder<T>>();
		buildersInfo = new LinkedList<JSONObject>();
	}
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		// call addBuilder(b) for each builder b in builder
		// ...
		for(Builder<T> b : builders) {
			this.addBuilder(b);
		}
	}
	
	public void addBuilder(Builder<T> b) {
		builders.put(b.getTypeTag(), b);
		buildersInfo.add(b.getInfo());
	}
		
	@Override
	public T createInstance(JSONObject info) {
		if (info == null) {
			throw new IllegalArgumentException("Invalid value for createInstance:null");
		}
		String type = info.getString("type");

		if(builders.containsKey(type)) 
			return builders.get(type).createInstance(info.has("data") ? info.getJSONObject("data") : new JSONObject());
		
		
		throw new IllegalArgumentException("Invalid value for createInstance: " + info.toString());
	}
	
	@Override
	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(buildersInfo);
	}
		
}