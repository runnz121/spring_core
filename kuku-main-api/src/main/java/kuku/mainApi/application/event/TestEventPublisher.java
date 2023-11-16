package kuku.mainApi.application.event;

import kuku.mainApi.domain.test.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestEventPublisher {

    private final ApplicationEventPublisher publisher;

    public void publish(){

        publisher.publishEvent(TestEntity.builder()
                        .name("test")
                        .value("testValue")
                        .build()
        );
    }
}
