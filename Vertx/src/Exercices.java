import java.nio.file.Paths;
import java.util.HashMap;

import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.RoutingContext;

public class Exercices {
	EventBus eb;

	public Exercices(EventBus eb) {
		this.listExercise = new HashMap<>();
		this.eb = eb;
	}

	private HashMap<Integer, Exercise> listExercise;

	public void send() {

	}

	public void put(Exercise exercise) {
		listExercise.put(listExercise.size(), exercise);
	}

	public void getExercice(RoutingContext rc) {
		int id = 0;

		id = Integer.valueOf(rc.request().getParam("id"));
		if (listExercise.containsKey(id)) {
			rc.response().putHeader("content-type", "application/json").setStatusCode(200)
					.end(listExercise.get(id).toString());

			// // Register to listen for messages coming IN to the server
			// eb.consumer("exercice.toServer").handler(message -> {
			// // Send the message back out to all clients with the timestamp
			// // prepended.
			// eb.publish("exercice.toClient",
			// MarkDownHandler.markdownUpdater(Paths.get("Exercice1.txt")));
			// });
			// eb.send("exercice", "mksqdjflksdjf");

			//
		} else {
			rc.response().end("Exercice introuvable");
		}

	}

	/*public void init() {
		listExercise.put(0, new Exercise(0, Paths.get("exercice0.txt")));
		listExercise.put(1, new Exercise(1, Paths.get("exercice1.txt")));
		listExercise.put(2, new Exercise(2, Paths.get("exercice2.txt")));
	}*/

}
