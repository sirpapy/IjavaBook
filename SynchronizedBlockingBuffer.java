import java.util.ArrayDeque;

public class SynchronizedBlockingBuffer<T> {

	private final ArrayDeque<T> buffer = new ArrayDeque<>();
	private final int size;
	
	public SynchronizedBlockingBuffer(int size){
		if(size<1){
			throw new IllegalArgumentException(); 
		}
		this.size = size;
	}
	
	public void put(T t) throws InterruptedException{
		
		synchronized(buffer){
			while(buffer.size() == size){
				buffer.wait();
			}
			buffer.notifyAll();
			buffer.addLast(t);
		}
		
	}
	
	public T take() throws InterruptedException{
		
		synchronized(buffer){
			while(buffer.size() == 0){
				buffer.wait();
			}
			buffer.notifyAll();
			return buffer.removeFirst();
		}
		
	}
}
