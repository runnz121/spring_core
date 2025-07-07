package kuku.externalAPI.application;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
class RetryServiceRecoverTest {

    @SpyBean
    private RetryService retryService;

    @Test
    void whenAlwaysFail_thenRecoverIsInvokedOnce() {
        // 항상 실패하도록 구현된 메서드 호출
        String result = retryService.alwaysFail();

        // recover(Exception) 메서드가 한번 호출됐는지 검증
        verify(retryService, times(1)).recover(any(RuntimeException.class));

        // 반환값이 recover 로직의 결과인지 확인
        assertThat(result).isEqualTo("recovered after 3 attempts");
    }
}
