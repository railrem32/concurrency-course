package course.concurrency.m2_async.executors.spring;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncInternalTask {

	@Async(SpringAsyncConfig.THREAD_POOL_TASK_EXECUTOR_2)
	public void internalTask() {
		System.out.println("internalTask: " + Thread.currentThread().getName());
	}
}
