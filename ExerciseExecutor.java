package Execise;
import java.io.PrintStream;
import java.util.List;

import jdk.jshell.JShell;


public class ExerciseExecutor {

	private final int idExercise;
	private final JShell jshell;
	private final PrintStream err;
	
	public ExerciseExecutor(int idExercise,PrintStream out,PrintStream err){
		
		this.idExercise = idExercise;
		jshell = JShell.builder().out(out).build();
		this.err = err;
	}
	
	public ExerciseExecutor(int idExercice){
		this(idExercice,System.out,System.err);
	}
	
	public void execute(List<String> sourceCode){
		for(String code:sourceCode){
			jshell.eval(code).stream().filter(s -> s.status().toString().equals("REJECTED"))
			.forEach(s -> err.println(code));
		}
	}
	
	public void closeExerciseExecutor(){
		jshell.close();
	}
	
	public int getIdExercise(){
		return idExercise;
	}
}
