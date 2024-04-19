package kuku.mainApi.application.event;

import kuku.mainApi.domain.test.TestEntity;
import kuku.mainApi.domain.test.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestEventListener {

    private final TestRepository testRepository;

    @EventListener
    @Async("eventAsync")
    public void listen(TestEntity entity) {

        log.info("event 수신 : {}" , entity);

        Long savedID = testRepository.save(entity).getId();
        throw new RuntimeException();

//        log.info("savedId : {}", savedID);
    }

//    @TransactionalEventListener
//    @Async("eventAsync")
//    public void listenAfterCommit(TestEntity entity) {
//
//        log.info("TransactionalEventListener 수신 : {}" , entity);
//
//        Long savedID = testRepository.save(entity).getId();
//
//        log.info("TransactionalEventListener : {}", savedID);
//    }
}
