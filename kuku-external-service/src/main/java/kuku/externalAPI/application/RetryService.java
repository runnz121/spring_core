package kuku.externalAPI.application;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RetryService {

    // 항상 실패만 하고 Recover 로직으로 넘어가는 메서드
    private final AtomicInteger failCount = new AtomicInteger();

    @Retryable(
            value = RuntimeException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 200)
    )
    public String alwaysFail() {
        int attempt = failCount.incrementAndGet();
        throw new RuntimeException("failed attempt " + attempt);
    }

    @Recover
    public String recover(RuntimeException e) {
        // 재시도 후 Recover가 호출됐을 때 실행
        return "recovered after " + failCount.get() + " attempts";
    }

    // 3번째 시도에만 성공하는 메서드
    private final AtomicInteger tryCount = new AtomicInteger();

    @Retryable(
            value = RuntimeException.class,
            maxAttempts = 5,
            backoff = @Backoff(delay = 100)
    )
    public String succeedOnThirdAttempt() {
        int attempt = tryCount.incrementAndGet();
        if (attempt < 3) {
            throw new RuntimeException("still failing at " + attempt);
        }
        return "success at " + attempt;
    }
}
