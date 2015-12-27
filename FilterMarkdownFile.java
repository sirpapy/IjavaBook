package Execise;

import java.nio.file.Path;

/**
 * Classe who watch if file is in Markdown
 */
public class FilterMarkdownFile {

	/**
	 * Check if the file is in Markdown format
	 * @return true if file have  .markdown or .md in extension
	 */
	public static boolean isMarkdownFile(Path path){
		String extension = "";
		int i = path.toString().lastIndexOf('.');
		if (i > 0) {
		    extension = path.toString().substring(i+1);
		}		
		return extension.equals("markdown") || extension.equals("md");
	}
}
