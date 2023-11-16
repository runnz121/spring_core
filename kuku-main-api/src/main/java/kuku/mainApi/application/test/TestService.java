package kuku.mainApi.application.test;

import kuku.mainApi.application.event.TestEventPublisher;
import kuku.mainApi.common.value.auth.AuditUser;
import kuku.mainApi.domain.test.TestEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final AuditUser auditUser;
    private final TestEventPublisher testEventPublisher;

    public List<TestEntity> getTestEntityList() {
        return testRepository.findAll();
    }

    public void logTestService() {

        System.out.println(auditUser.getAuditName());
        log.info("logTestService : {}", auditUser.getAuditName());
    }

    public void sendEvent() {
        testEventPublisher.publish();
    }
}
