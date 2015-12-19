import java.nio.file.Path;
import java.nio.file.Paths;

import io.vertx.core.json.JsonObject;

public class Exercice {
	private int id;
	private String content;

	public Exercice(int id, Path path) {
		this.id = id;
		this.content = setContent(path);
	}

	private String setContent(Path path) {
		return MarkDownHandler.markdownUpdater(Paths.get("Exercice1.txt"));
	}

	public JsonObject toJson() {
		return new JsonObject().put(String.valueOf(this.id), this.content);

	}

	public String toString() {
		return this.toJson().toString();
	}
}
