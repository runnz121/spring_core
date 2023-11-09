package kuku.mainApi.infra.api;

import kuku.mainApi.application.test.TestRepository;
import kuku.mainApi.application.test.TestService;
import kuku.mainApi.domain.test.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping("/list")
    public ResponseEntity<List<TestEntity>> getList() {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(testService.getTestEntityList());
    }
}
