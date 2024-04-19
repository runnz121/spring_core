package kuku.mainApi.application.test;

import kuku.mainApi.application.event.TestEventPublisher;
import kuku.mainApi.common.value.auth.AuditUser;
import kuku.mainApi.domain.test.TestDomainService;
import kuku.mainApi.domain.test.TestEntity;
import kuku.mainApi.domain.test.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final TestDomainService testDomainService;
    private final AuditUser auditUser;
    private final TestEventPublisher testEventPublisher;
    private final TestAsyncService testAsyncService;

    public List<TestEntity> getTestEntityList() {
        return new ArrayList<>();
//        return testRepository.findAll().iterator();
    }

    public void logTestService() {

        System.out.println(auditUser.getAuditName());
        log.info("logTestService : {}", auditUser.getAuditName());
    }

    @Transactional
    public void saveEntity() {
        testRepository.save(TestEntity.builder().value("test").totalQuantity(1000).build());
    }

    @Transactional
    public void decreaseConcurrent(Long id) throws InterruptedException {
        multiThreadTest(10, id);
    }

    private void multiThreadTest(int count, Long id) throws InterruptedException {

        int numThreads = 5;

        CountDownLatch doneSignal = new CountDownLatch(numThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        for (int i = 0; i < count; i++) {
            executorService.execute(() -> {
                try{
                    TestEntity actual = testDomainService.getTestEntityWithLock(id);
                    actual.decreaseQuantity(5);
                    System.out.println(actual);
                    successCount.getAndIncrement();
                }catch (Exception e){
                    e.printStackTrace();
                    failCount.getAndIncrement();
                } finally {
                    doneSignal.countDown();
                }
            });
        }
        doneSignal.await();
        executorService.shutdown();

        System.out.println(successCount);
        System.out.println(failCount);
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
