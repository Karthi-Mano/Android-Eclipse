
public class newSingleThreadExecutor extends Thread{

	public void run() {
		
		System.out.println(Thread.currentThread().getName()+"正在执行。。。");
		
		
		super.run();
	}

	

}
