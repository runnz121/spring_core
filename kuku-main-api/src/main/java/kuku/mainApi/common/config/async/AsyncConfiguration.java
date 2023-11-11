package kuku.mainApi.common.config.async;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

@Slf4j
@EnableAsync
@RequiredArgsConstructor
public class AsyncConfiguration implements AsyncConfigurer {

    private final TaskExecutionProperties taskExecutionProperties;

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        log.info("custom AsyncConfiguration");

        return getExecutor(
                taskExecutionProperties.getPool(),
                taskExecutionProperties.getThreadNamePrefix()
        );
    }

    private Executor getExecutor(TaskExecutionProperties.Pool pool, String threadNamePrefix) {
        ContextAwarePoolExecutor executor = new ContextAwarePoolExecutor();

        executor.setCorePoolSize(pool.getCoreSize());
        executor.setMaxPoolSize(pool.getMaxSize());
        executor.setQueueCapacity(pool.getQueueCapacity());
        executor.setThreadNamePrefix(threadNamePrefix);

        return executor;
    }
}
