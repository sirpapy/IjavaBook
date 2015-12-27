import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import Execise.FilterMarkdownFile;

public class WatchDirectory {

	private final Path path;
	private final WatchService watcher;
	private WatchKey key;
	private int idGenerate = 0;
	private SynchronizedBlockingBuffer<Exercise> bufferModifiedFile;

	public WatchDirectory(Path path) throws IOException {
		this.path = path;
		this.watcher = FileSystems.getDefault().newWatchService();
		bufferModifiedFile = new SynchronizedBlockingBuffer<>(10);
	}

	private void loadFile() throws InterruptedException {
		File[] files = new File(path.toString()).listFiles();

		for (File file : files) {
			if (FilterMarkdownFile.isMarkdownFile(file.toPath())) {
				bufferModifiedFile.put(new Exercise(++idGenerate, Paths.get(file.getName())));
			}
		}
	}

	private void kindEvents(WatchEvent.Kind<?> kind, Path fileName) throws InterruptedException {

		if (kind == ENTRY_CREATE || kind == ENTRY_DELETE) {
			bufferModifiedFile.put(new Exercise(++idGenerate, fileName));
		}
	}

	private void catchEvent() throws InterruptedException {
		for (WatchEvent<?> event : key.pollEvents()) {
			@SuppressWarnings("unchecked")
			WatchEvent<Path> ev = (WatchEvent<Path>) event;
			Path fileName = ev.context();

			if (FilterMarkdownFile.isMarkdownFile(fileName)) {
				kindEvents(event.kind(), fileName);
			}
		}
	}

	public Exercise takeModifiedExercise() throws InterruptedException {
		return bufferModifiedFile.take();
	}

	public void startDirectory() throws IOException, InterruptedException {
		path.register(watcher, ENTRY_CREATE, ENTRY_DELETE);

		loadFile();
		for (;;) {
			key = watcher.take();
			catchEvent();
			key.reset();
		}
	}
}