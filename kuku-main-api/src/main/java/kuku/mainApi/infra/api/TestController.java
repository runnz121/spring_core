package kuku.mainApi.infra.api;

import kuku.mainApi.application.ApplicationService;
import kuku.mainApi.application.service.design.templateCallback.TemplateService;
import kuku.mainApi.application.test.TestAsyncService;
import kuku.mainApi.application.test.TestService;
import kuku.mainApi.common.config.aop.TimeCheck;
import kuku.mainApi.domain.test.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final TestAsyncService testAsyncService;
    private final ApplicationService applicationService;

    @TimeCheck
    @GetMapping("/list")
    public ResponseEntity<List<TestEntity>> getList() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(testService.getTestEntityList());
    }

    @GetMapping("/save")
    public void saveEntity() {
        testService.saveEntity();
    }

    @GetMapping("/concurrent/{id}")
    public void concurrent(@PathVariable("id") Long id) throws InterruptedException {
        testService.decreaseConcurrent(id);
    }

    @GetMapping("/log")
    public void getLog() {
        testService.logTestService();
    }

    @GetMapping("/log/async")
    public void getAsyncLog() {
        testAsyncService.logAsyncTestService();
    }


    @GetMapping("/event")
    public void sendEvent() {
        testService.sendEventPublish();
    }

    @GetMapping("/event/async")
    public void asyncEvent() {
        testService.sendEvent();
    }

    @GetMapping("/async")
    public void async() {
        testService.async();
    }

    @GetMapping("/template")
    public TestEntity template() {
        return applicationService.templateExecute();
    }
}
