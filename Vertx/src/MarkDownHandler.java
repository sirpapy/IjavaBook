import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.pegdown.PegDownProcessor;

public class MarkDownHandler {
	public static String markdownUpdater(String path) {
		String markDown = "";
		try {
			markDown = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PegDownProcessor pegy = new PegDownProcessor();
		return pegy.markdownToHtml(markDown);

	}
}
