import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThreadExecutor {

	public static void main(String[] args) {
	/*	ExecutorService fixedThreadPool = Executors.newFixedThreadPool(

		Runtime.getRuntime().availableProcessors()
	
	);  */
		ExecutorService fixedThreadPool = Executors.newCachedThreadPool();   
		for ( int i = 0; i < 1000000; i++) {

			
			fixedThreadPool.execute(   
					
					
					
					new Runnable() {
						
						public void run() {
							System.out.println(Thread.currentThread().getName());
							
						}
					}
					
					
					);
		}
		
		System.out.println("完成");
		
	}

}
