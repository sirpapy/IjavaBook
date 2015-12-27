package Execise;

import java.util.ArrayList;
import java.util.List;

import jdk.jshell.JShell;
import jdk.jshell.SourceCodeAnalysis.CompletionInfo;

public class ParseCode {

	public static List<String> parseSourceCode(String source) {

		ArrayList<String> listCode = new ArrayList<>();

		try (JShell jshell = JShell.create()) {
			CompletionInfo info = jshell.sourceCodeAnalysis().analyzeCompletion(source);
			listCode.add(info.source);
			for (; info.remaining != "";) {
				info = jshell.sourceCodeAnalysis().analyzeCompletion(info.remaining);
				listCode.add(info.source);
			}
		}
		return listCode;
	}

}
