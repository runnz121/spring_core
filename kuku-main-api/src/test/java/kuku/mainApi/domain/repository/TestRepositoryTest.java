package kuku.mainApi.domain.repository;

import jakarta.persistence.EntityManager;
import kuku.mainApi.domain.test.TestEntity;
import kuku.mainApi.domain.test.TestDomainService;
import kuku.mainApi.domain.test.repository.TestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@Transactional
public class TestRepositoryTest {

    @Autowired
    TestDomainService testDomainService;

    @Autowired
    TestRepository testRepository;

    @Autowired
    EntityManager em;

    @Test
    void 비관적_락_테스트() throws Exception {

        int countThread = 10;
        String value = "test";

        TestEntity entity = TestEntity.builder()
                .value(value)
                .totalQuantity(100)
                .build();

        TestEntity saveTest = testRepository.save(entity);

        System.out.println(saveTest);
//
//        em.flush();
//        em.clear();

        multiThreadTest(countThread, saveTest.getId());
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
    }

    private void decrease() {
//        TestEntity entity = testService.getTestEntityWithLock("test");
//        entity.decreaseQuantity(5);
    }
}
