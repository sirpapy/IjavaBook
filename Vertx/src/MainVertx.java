import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import jdk.jshell.JShell;

public class MainVertx extends AbstractVerticle {
	WatchService watcher = new WatchService();
	String codeActuel;
	public final Object monitor = new Object();
	private Exercices exercises = new Exercices();

	public void JshellHandler(RoutingContext rc, Buffer buf) {
		System.out.println(codeActuel = buf.toString());
		HttpServerResponse resp = rc.response();
		resp.putHeader("content-type", "text/plain");
		try {
			resp.end(reponseServeur(buf.toString()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String reponseServeur(String buf) throws FileNotFoundException {
		PrintStream ps = new PrintStream("reponse.txt");
		StringBuilder st = new StringBuilder();
		System.setOut(ps);
		JShell jshell = JShell.create();
		for (String s : buf.split("\n")) {
			jshell.eval(s);
		}

		try {
			Files.readAllLines(Paths.get("reponse.txt")).stream().forEach(e -> st.append(e));
			return st.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new FileNotFoundException();
		}
	}

	@Override
	public void start() throws Exception {
		HttpServer server = vertx.createHttpServer();
		System.out.println("Serveur lancé");
		exercises.init();
		Router router = Router.router(vertx);

		router.route("/").handler(BodyHandler.create());
		router.route("/exercice/*").handler(StaticHandler.create("webroot"));
		router.get("/exercice/:id").handler(exercises::getExercice);
		Route route2 = router.post("/eventbus/");
		route2.handler(RoutingContext -> {
			HttpServerRequest request = RoutingContext.request();
			request.bodyHandler(req -> JshellHandler(RoutingContext, req));
		});
		router.route("/exercice").handler(RoutingContext -> {
			// String auctionId = RoutingContext.request().getParam("id");
			HttpServerResponse response = RoutingContext.response();
			response.setStatusCode(200);
			response.write("route2\n");
			System.out.println("jmlkjmlkj");
		});
		server.requestHandler(router::accept).listen(8080);
		updateExercice(1);

	}

	private void updateExercice(int i) {

		vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
			@Override
			public void handle(final ServerWebSocket ws) {
				vertx.setPeriodic(1000l, t -> {
					if (watcher.isModified(1) == true) {
						ws.writeFinalTextFrame(MarkDownHandler.markdownUpdater(Paths.get("Exercice1.txt")));
						System.out.println("Modification detecté!!!!!");
					}
				});
			}

		}).listen(8080);
	}

	public static void main(String args[]) {
		Vertx vertx = Vertx.vertx();
		/*
		 * VertxOptions options = new VertxOptions();
		 * options.setMaxEventLoopExecuteTime(Long.MAX_VALUE); vertx =
		 * Vertx.vertx(options);
		 */
		vertx.deployVerticle(new MainVertx());
	}
}

// System.out.println(routingContext.getBodyAsString().toString());