package course.concurrency.m2_async.executors.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SpringAsyncConfig {
	public static final String THREAD_POOL_TASK_EXECUTOR_2 = "threadPoolTaskExecutor2";
	public static final String THREAD_POOL_TASK_EXECUTOR_1 = "threadPoolTaskExecutor1";

	@Bean(name = THREAD_POOL_TASK_EXECUTOR_1)
	public Executor executor1() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("rail");
		return executor;
	}

	@Bean(name = THREAD_POOL_TASK_EXECUTOR_2)
	public Executor executor2() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("slave-of-rail-");
		return executor;
	}
}
