package kuku.externalAPI.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class RetryServiceTest {

    @Autowired
    private RetryService retryService;

    @Test
    void testAlwaysFailAndRecover() {
        String result = retryService.alwaysFail();
        // maxAttempts = 3 이므로 Recover가 3번째 실패 후 호출된다
        assertThat(result).isEqualTo("recovered after 3 attempts");
    }

    @Test
    void testSucceedOnThirdAttempt() {
        String result = retryService.succeedOnThirdAttempt();
        // 3번째 시도에 성공하도록 구현했으므로 “success at 3” 반환
        assertThat(result).isEqualTo("success at 3");
    }
}