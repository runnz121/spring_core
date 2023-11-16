package kuku.mainApi.application.event;

import kuku.mainApi.application.test.TestRepository;
import kuku.mainApi.domain.test.TestEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestEventListener {

    private final TestRepository testRepository;

    @EventListener
    public void listen(TestEntity entity) {

        log.info("event 수신 : {}" , entity);

        Long savedID = testRepository.save(entity).getId();

        log.info("savedId : {}", savedID);
    }
}
