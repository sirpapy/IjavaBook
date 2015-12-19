import java.nio.file.Paths;
import java.util.HashMap;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.RoutingContext;

public class Exercices {
	public Exercices() {
		this.exercice = new HashMap<>();
	}

	private HashMap<Integer, Exercice> exercice;

	public void getExercice(RoutingContext rc) {
		int id = 0;
		id = Integer.valueOf(rc.request().getParam("id"));
		if (exercice.containsKey(id)) {
			rc.response().putHeader("content-type", "application/json").setStatusCode(200)
					.end(exercice.get(id).toString());
			System.out.println(id);
		} else {
			rc.response().end("Exercice introuvable");
		}

	}

	public void init() {
		exercice.put(0, new Exercice(0, Paths.get("Exercice 0")));
		exercice.put(1, new Exercice(0, Paths.get("Exercice 1")));
		exercice.put(2, new Exercice(0, Paths.get("Exercice 2")));
	}

}
