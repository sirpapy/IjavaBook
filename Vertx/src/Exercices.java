import java.nio.file.Paths;
import java.util.HashMap;

import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.RoutingContext;

public class Exercices {
	EventBus eb;

	public Exercices(EventBus eb) {
		this.exercice = new HashMap<>();
		this.eb = eb;
	}

	private HashMap<Integer, Exercice> exercice;

	public void send() {

	}

	public void getExercice(RoutingContext rc) {
		int id = 0;

		id = Integer.valueOf(rc.request().getParam("id"));
		if (exercice.containsKey(id)) {
			 rc.response().putHeader("content-type",
			"application/json").setStatusCode(200)
			.end(exercice.get(id).toString());
			

			// // Register to listen for messages coming IN to the server
			// eb.consumer("exercice.toServer").handler(message -> {
			// // Send the message back out to all clients with the timestamp
			// // prepended.
			// eb.publish("exercice.toClient",
			// MarkDownHandler.markdownUpdater(Paths.get("Exercice1.txt")));
			// });
			//eb.send("exercice", "mksqdjflksdjf");

			//
		} else {
			rc.response().end("Exercice introuvable");
		}

	}

	public void init() {
		exercice.put(0, new Exercice(0, Paths.get("exercice0.txt")));
		exercice.put(1, new Exercice(1, Paths.get("exercice1.txt")));
		exercice.put(2, new Exercice(2, Paths.get("exercice2.txt")));
	}

}
