package kuku.mainApi.common.config.async;


import kuku.mainApi.common.config.async.Excpetion.KukuAsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class EventAsyncConfig implements AsyncConfigurer {

    private final int CORE_POOL_SIZE = 3;
    private final int MAX_POOL_SIZE = 20;
    private final int QUEUE_CAPACITY = 15;
    private final String CUSTOM_THREAD_NAME_PREFIX = "ASYNC-EVENT-";
    private final String CUSTOM_THREAD_ASYNC_NAME_PREFIX = "ASYNC-";

    @Bean(name = "eventAsync")
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        taskExecutor.setThreadNamePrefix(CUSTOM_THREAD_NAME_PREFIX);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean(name = "asyncNormal")
    public Executor asyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutor.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutor.setQueueCapacity(QUEUE_CAPACITY);
        taskExecutor.setThreadNamePrefix(CUSTOM_THREAD_ASYNC_NAME_PREFIX);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new KukuAsyncUncaughtExceptionHandler();
    }
}
