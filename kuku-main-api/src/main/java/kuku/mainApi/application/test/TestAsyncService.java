package kuku.mainApi.application.test;

import kuku.mainApi.common.value.auth.AuditUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestAsyncService {

    private final AuditUser auditUser;

    @Async
    public void logAsyncTestService() {
        System.out.println(auditUser.getAuditName());
        log.info("logAsyncTestService : {}", auditUser.getAuditName());
    }

    @Async("asyncNormal")
    public void asyncNormal() {
        log.info("===testAsyncService start====");
        throw new RuntimeException();
    }
}
