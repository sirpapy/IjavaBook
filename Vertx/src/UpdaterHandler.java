import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.ServerWebSocket;

public class UpdaterHandler extends AbstractVerticle {

	@Override
	public void start() throws Exception {

		vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
			@Override
			public void handle(final ServerWebSocket ws) {
				switch (ws.path()) {
				case "":
					System.out.println("Systeme lancé");
					updateSender(ws);
				default:
					ws.writeFinalTextFrame("404 pas FOUND!");
					System.out.println("Unexpected-connection-detected!");
					return;
				}
			}
		}).listen(8080);
	}

	private void updateSender(final ServerWebSocket ws) {
		ws.handler(new Handler<Buffer>() {
			@Override
			public void handle(final Buffer data) {
				while (true) {
					ws.writeFinalTextFrame(data.toString());
				}

			}
		});

	}

}