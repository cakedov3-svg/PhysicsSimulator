package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	PhysicsSimulator simulador;
	Factory<Body> bodyFactories;
	Factory<ForceLaws> forceFactories;
	
	public Controller(PhysicsSimulator simulador, Factory<Body> bodyFactory, Factory<ForceLaws> forceFactories) {
		this.simulador = simulador;
		this.bodyFactories = bodyFactory;
		this.forceFactories = forceFactories;
	}
	
	public void loadData(InputStream in) {
		JSONObject input = new JSONObject(new JSONTokener(in));
		
		JSONArray ja = input.getJSONArray("groups");
		for(int i = 0; i < ja.length(); i++) {
			simulador.addGroup(ja.getString(i));
		}
		
		if(input.has("laws")) {
			ja = input.getJSONArray("laws");
			for(int i = 0; i < ja.length(); i++) {
				ForceLaws fl = forceFactories.createInstance(ja.getJSONObject(i).getJSONObject("laws"));
				simulador.setForceLaws(ja.getJSONObject(i).getString("id"), fl);
			}
		}
		
		ja = input.getJSONArray("bodies");
		for(int i = 0; i < ja.length(); i++) {
			Body b = bodyFactories.createInstance(ja.getJSONObject(i));
			simulador.addBody(b);
		}
	}
	
	public void reset() {
		simulador.reset();
	}
	
	public void run(int n, OutputStream out) {
		
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		
		p.println(simulador.getState());
		
		for(int i = 1; i <= n; i++) {
			simulador.advance();
			p.print(",");
			p.println(simulador.getState());
		}
		
		p.println("]");
		p.println("}");
	}

	public void setDeltaTime(double dt) {
		simulador.setDeltaTime(dt);
	}
	
	public void addObserver(SimulatorObserver o) {
		simulador.addObserver(o);
	}
	
	public void removeObserver(SimulatorObserver o) {
		simulador.removeObserver(o);
	}
	
	public List<JSONObject> getForceLawsInfo(){
		return forceFactories.getInfo();
	}
	
	public void setForceLaws(String gId, JSONObject info) {
		simulador.setForceLaws(gId, forceFactories.createInstance(info));
	}
	
	public void run(int n) {
		for(int i=0; i<n; i++)
			simulador.advance();
	}
}
