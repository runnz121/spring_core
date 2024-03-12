package kuku.mainApi.common.config.async.Excpetion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class KukuAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.info("invoke async handlerException - " + ex.getMessage());
        log.info("method name - " + method.getName());
        Arrays.stream(params).forEach(param -> log.info("param info - " + param));
    }
}
