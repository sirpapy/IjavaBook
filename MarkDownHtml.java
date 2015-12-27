import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import org.pegdown.PegDownProcessor;

/**
 * Class convert Mardown in Html 
 * @version 1.0
 */
public class MarkDownHtml {
	
	/**
	 * @param path path of Markdown target file
	 * @return String a string of Markdown file in Html format
	 * @throws IOException
	 */
	public static String convertMarkdownToHtml(Path path) throws IOException {

		return new PegDownProcessor().markdownToHtml(new String(Files.readAllBytes(path)));
	}
	
}
