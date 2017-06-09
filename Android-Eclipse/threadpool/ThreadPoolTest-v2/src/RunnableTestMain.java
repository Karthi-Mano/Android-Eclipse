

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RunnableTestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(2);

		/**
		 * execute(Runnable x) 没有返回值。可以执行任务，但无法判断任务是否成功完成。
		 */
		// pool.execute(new RunnableTest("Task1"));

		/**
		 * submit(Runnable x) 返回一个future。可以用这个future来判断任务是否成功完成。请看下面：
		 */

		Future future = pool.submit(new RunnableTest("Task2"));
		//阻塞
		//非阻塞
		
		try {
			//拿到结果.必须要任务执行完成
			Object result = future.get();//拿到执行的结果
			//任务已经执行完成
			if (result == null) {// 如果Future's get返回null，任务完成
				System.out.println("任务完成");
			}
		} catch (InterruptedException e) {
		} catch (ExecutionException e) {
			// 否则我们可以看看任务失败的原因是什么
			System.out.println(e.getCause().getMessage());
		}

	}

}

class RunnableTest implements Runnable {

	private String	taskName;

	public RunnableTest(final String taskName) {
		this.taskName = taskName;
	}

	
	public void run() {
		System.out.println("Inside " + taskName);
		throw new RuntimeException("RuntimeException from inside " + taskName);
	}

}
