import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class Exercices extends Handler{
	private HashMap<Integer, String> exercice;
	
	public Exercices(){
		this.exercice = new HashMap<>();
	}
	public void getExercice(RoutingContext rc){
		int id=0;
		HttpServerResponse response = rc.response();
		response.putHeader("content-type", "text/plain");
		//response.setStatusCode(200);
		id=Integer.valueOf(rc.request().getParam("id"));
		System.out.println(id);
		if(exercice.containsKey(id)){			
			response.end(exercice.get(id));
		}else{
			response.end("Exercice introuvable");
		}
		
	}
	
	public void init(){
		exercice.put(0, "Exercice 1");
		exercice.put(1, "Exercice 2");
		exercice.put(2, "Exercice 3");
	}
	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void publish(LogRecord record) {
		// TODO Auto-generated method stub
		
	}
}
