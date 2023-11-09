package kuku.mainApi.application.test;

import kuku.mainApi.domain.test.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public List<TestEntity> getTestEntityList() {

        return testRepository.findAll();
    }
}
