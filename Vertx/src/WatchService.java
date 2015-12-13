import java.io.IOException;
import java.nio.file.FileSystems;

import java.nio.file.*;

public class WatchService 
{
	public boolean isModified(int id){
		java.nio.file.WatchService watcher = null;
		try {
			watcher = FileSystems.getDefault().newWatchService();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Path dir = Paths.get("");
		try {
			dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY);
			while (true) {
				WatchKey watchKey = null;
				try {
					watchKey = watcher.take();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (WatchEvent<?> event : watchKey.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();
					@SuppressWarnings("unchecked")
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path fileName = ev.context();
					System.out.println(kind.name() + ": " + fileName);
					if ((kind == StandardWatchEventKinds.ENTRY_MODIFY)
							&& (fileName.toString().equals("Exercice" + id + ".txt"))) {
						return true;
					}
				}
				boolean valid = watchKey.reset();
				if (!valid) {
					break;
				}
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
		return false;
	}
}
