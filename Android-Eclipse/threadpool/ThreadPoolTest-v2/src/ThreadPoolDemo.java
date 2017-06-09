

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
	public static void main(String[] args) {
		// ExecutorService es = Executors.newSingleThreadExecutor();
		// ExecutorService es = Executors.newFixedThreadPool(2);
		// ExecutorService es = Executors.newCachedThreadPool();
		ScheduledExecutorService es = Executors.newScheduledThreadPool(2);
		MyThread task = new MyThread();
		es.scheduleAtFixedRate(task, 0, 1000, TimeUnit.MILLISECONDS);

		/*for (int i = 0; i < 111; i++) {
			MyThread task = new MyThread();
			es.execute(task);
		}*/
	}
}

class MyThread implements Runnable {

	
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("MyThread-task");
	}
	

}
