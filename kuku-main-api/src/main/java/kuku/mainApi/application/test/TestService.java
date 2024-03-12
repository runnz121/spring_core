package kuku.mainApi.application.test;

import kuku.mainApi.application.event.TestEventPublisher;
import kuku.mainApi.common.value.auth.AuditUser;
import kuku.mainApi.domain.test.TestEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final AuditUser auditUser;
    private final TestEventPublisher testEventPublisher;
    private final TestAsyncService testAsyncService;

    public List<TestEntity> getTestEntityList() {
        return testRepository.findAll();
    }

    public void logTestService() {

        System.out.println(auditUser.getAuditName());
        log.info("logTestService : {}", auditUser.getAuditName());
    }

    @Transactional
    public void sendEventPublish() {

        // 1. @TransactionalEventListener 가 적용된 listener는 (listenAfterCommit)
        testEventPublisher.eventTransactionPublish();

        // 2. 해당 에러 발생시 eventListener 로그가 찍히지 않는다.
        throw new RuntimeException();

        // 3. 즉 이 메서드의 transactional이 끝나면 그 다음에 event publish 된다..
    }

    public void sendEvent() {

        testEventPublisher.eventPublish();

        log.info("event publish 완료");
    }

    @Transactional
    public void async() {
        log.info("====async testService start===");
        testAsyncService.asyncNormal();
        log.info("====async testService end===");

        testRepository.save(TestEntity.builder().name("async").value("async").build());
    }
}
