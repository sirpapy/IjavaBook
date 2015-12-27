package Execise;

import java.nio.file.Path;

public class Exercise {
	private int idExercise;
	private Path path;

	public Exercise(int idExercise, Path path) {
		this.idExercise = idExercise;
		this.path = path;
	}
	
	public int getIdExercice(){
		return idExercise;
	}
	
	public Path getPath(){
		return path;
	}
	
}
