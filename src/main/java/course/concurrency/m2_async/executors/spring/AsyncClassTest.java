package course.concurrency.m2_async.executors.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;


@Component
public class AsyncClassTest {

    @Autowired
    public ApplicationContext context;

    @Autowired
    private AsyncInternalTask asyncInternalTask;

//    @Autowired
//    @Qualifier("applicationTaskExecutor")
//    private ThreadPoolTaskExecutor executor;

    @Async(SpringAsyncConfig.THREAD_POOL_TASK_EXECUTOR_1)
    public void runAsyncTask() {
        System.out.println("runAsyncTask: " + Thread.currentThread().getName());
        asyncInternalTask.internalTask();
    }
}
