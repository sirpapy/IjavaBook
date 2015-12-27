import java.io.PrintStream;
import java.nio.file.Paths;

import Execise.ExerciseExecutor;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

public class MainVertx extends AbstractVerticle {
	private WatchService watcher = new WatchService();
	private String codeActuel;
	public final Object monitor = new Object();
	private Exercices exercises;
	private BridgeOptions opts;
	private SockJSHandler ebHandler;
	private EventBus eb;

	// public void JshellHandler(RoutingContext rc, Buffer buf) {
	// System.out.println(codeActuel = buf.toString());
	// HttpServerResponse resp = rc.response();
	// resp.putHeader("content-type", "text/plain");
	// try {
	// resp.end(reponseServeur(buf.toString()));
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// }
	// }

	// public String reponseServeur(String buf) throws FileNotFoundException {
	// PrintStream ps = new PrintStream("reponse.txt");
	// StringBuilder st = new StringBuilder();
	// System.setOut(ps);
	// JShell jshell = JShell.create();
	// for (String s : buf.split("\n")) {
	// jshell.eval(s);
	// }
	//
	// try {
	// Files.readAllLines(Paths.get("reponse.txt")).stream().forEach(e ->
	// st.append(e));
	// return st.toString();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// throw new FileNotFoundException();
	// }
	// }

	@Override
	public void start() throws Exception {
		PrintStream out = new PrintStream("out.txt");
		PrintStream err = new PrintStream("err.txt");
		ExerciseExecutor ex = new ExerciseExecutor();
		exercises = new Exercices(vertx.eventBus());
		exercises.init();
		Router router = Router.router(vertx);
		router.post("/exercice/:id").handler(exercises::getExercice);
		router.route().handler(StaticHandler.create());

		router.route("/eventbus/").handler(RoutingContext -> ex.HandlerParsing(RoutingContext));
		HttpServer server = vertx.createHttpServer();
		opts = new BridgeOptions().addOutboundPermitted(new PermittedOptions().setAddress("exercice"));
		ebHandler = SockJSHandler.create(vertx).bridge(opts);
		eb = vertx.eventBus();
		// System.out.println("Serveur lancé");

		server.requestHandler(router::accept).listen(8989);
		updateExercice(1);

	}

	private void updateExercice(int i) {
		// vertx.setPeriodic(1000l, t -> {
		// if (watcher.isModified(1) == true) {
		eb.publish("exercice", MarkDownHandler.markdownUpdater(Paths.get("Exercice1.txt")));
		// System.out.println("Modification detecté!!!!!");
		// }
		// });

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