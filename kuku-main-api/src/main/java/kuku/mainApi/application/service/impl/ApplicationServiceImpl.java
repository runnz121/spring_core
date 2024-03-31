package kuku.mainApi.application.service.impl;

import kuku.mainApi.application.ApplicationService;
import kuku.mainApi.application.service.design.templateCallback.TemplateService;
import kuku.mainApi.application.service.design.templateCallback.consts.Template;
import kuku.mainApi.domain.test.TestEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * templateCallBackMethod 로 구현
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final TemplateService templateService;

    public TestEntity templateExecute() {
        // 로깅 --> 고정 템플릿 1
        log.info("========== start ============");

        TestEntity request = TestEntity.builder()
                .name("templateCallback")
                .value("value")
                .build();

        TestEntity result = TestEntity.EMPTY;

        // 이 부분의 서비스 로직이 가변적으로 변한다
        // enum 으로 동적으로 받도록 변경
        try {
            result = templateService.execute(request, test -> Template.applyFunction(Template.TEMPLATE_2, test));
            return result;
        } catch (Exception ex) {

        }
        // 로깅 ---> 고정 템플릿 2
        log.info("========== end ============");
        return result;
    }


    private TestEntity returnTestEntitySample1111(TestEntity request) {

        log.info("returnTestEntitySample1111 인입 request : {}", request);

        return TestEntity.builder()
                .name("responseTestEntity")
                .value("response")
                .build();
    }

    private TestEntity returnTestEntitySample22222(TestEntity request) {

        log.info("returnTestEntitySample22222 인입 request : {}", request);

        return TestEntity.builder()
                .name("responseTestEntity")
                .value("response")
                .build();
    }
}
