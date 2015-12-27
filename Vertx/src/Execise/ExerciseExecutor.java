package Execise;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import jdk.jshell.JShell;

public class ExerciseExecutor {

	private List<String> listCode;

	public String execute(List<String> sourceCode) throws IOException {
		StringBuilder st = new StringBuilder();
		PrintStream out = new PrintStream("out.txt");
		PrintStream err = new PrintStream("err.txt");
		JShell jshell = JShell.builder().out(out).build();
		System.setOut(out);
		System.setErr(err);
		for (String code : sourceCode) {
			jshell.eval(code).stream().filter(s -> s.status().toString().equals("REJECTED"))
					.forEach(s -> err.println(code));
		}
		try (Stream<String> stream = Files.readAllLines(Paths.get("out.txt")).stream()) {
			stream.forEach(e -> st.append(e));
			stream.close();
			jshell.close();
		}
		return st.toString();
	}

	public void HandlerParsing(RoutingContext rc) {
		HttpServerRequest request = rc.request();
		request.bodyHandler(req -> {
			// System.out.println(req);
			listCode = ParseCode.parseSourceCode(req.toString());
		});
		try {
			rc.response().putHeader("content-type", "text/plain").end(execute(listCode));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
