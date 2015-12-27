import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayDeque;

import Execise.Exercise;
import Execise.FilterMarkdownFile;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.IOException;

public class WatchDirectory {

	private final Path path;
	private final WatchService watcher;
	private final ArrayDeque<Exercise> bufferFile = new ArrayDeque<>();
	private WatchKey key;
	private int idGenerate = 0;

	public WatchDirectory(Path path) throws IOException {
		this.path = path;
		this.watcher = FileSystems.getDefault().newWatchService();
	}

	private void loadFile() {
		File[] files = new File(path.toString()).listFiles();

		for (File file : files) {
			if (FilterMarkdownFile.isMarkdownFile(file.toPath())) {
				synchronized (path) {
					bufferFile.addLast(new Exercise(++idGenerate, Paths.get(file.getName())));
				}
			}
		}
	}

	private void catchEvent() {
		for (WatchEvent<?> event : key.pollEvents()) {
			WatchEvent.Kind<?> kind = event.kind();
			@SuppressWarnings("unchecked")
			WatchEvent<Path> ev = (WatchEvent<Path>) event;
			Path fileName = ev.context();
			
			if (kind == OVERFLOW) {
	            continue;
	        } else if (kind == ENTRY_CREATE) {
	 
	            // process create event
	 
	        } else if (kind == ENTRY_DELETE) {
	 
	            // process delete event
	 
	        } else if (kind == ENTRY_MODIFY) {
	 
	            // process modify event
	 
	        }
			if(FilterMarkdownFile.isMarkdownFile(fileName)){
				synchronized (path) {
					bufferFile.addLast(new Exercise(++idGenerate, fileName));
				}
			}
		}
	}

	public void watch() {
		for(;;){
			synchronized (path) {
				for (Exercise e : bufferFile) {
					System.out.println("exercise " + e.getIdExercice() + "  " + e.getPath());
				}
			}
		}
	}

	public void startDirectory() throws IOException, InterruptedException {
		path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

		loadFile();
		for (;;) {
			key = watcher.take();
			catchEvent();
			key.reset();
		}
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		Path path = Paths.get("/home/maugan/Bureau");
		WatchDirectory wd = new WatchDirectory(path);

		wd.startDirectory();
		
		/*
		new Thread(() -> {
			wd.watch();
		}).start();

		
		new Thread(() -> {
			try {
				wd.startDirectory();
			} catch (Exception e) {}
		}).start();
	*/
	}
}
