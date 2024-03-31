package kuku.mainApi.application.service.design.templateCallback.consts;

import kuku.mainApi.domain.test.TestEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum Template {

    TEMPLATE_1(Template::returnTestEntitySample1111),
    TEMPLATE_2(Template::returnTestEntitySample22222)
    ;

    public final Function<TestEntity, TestEntity> function;

    public static TestEntity applyFunction(Template template, TestEntity testEntity) {
        return template.function.apply(testEntity);
    }

    private static TestEntity returnTestEntitySample1111(TestEntity request) {

        log.info("returnTestEntitySample1111 인입 request : {}", request);

        return TestEntity.builder()
                .name("responseTestEntity11111")
                .value("response")
                .build();
    }

    private static TestEntity returnTestEntitySample22222(TestEntity request) {

        log.info("returnTestEntitySample22222 인입 request : {}", request);

        return TestEntity.builder()
                .name("responseTestEntity22222")
                .value("response")
                .build();
    }

}
