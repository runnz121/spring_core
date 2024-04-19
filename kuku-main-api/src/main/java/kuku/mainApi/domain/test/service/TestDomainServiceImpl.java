package kuku.mainApi.domain.test.service;

import kuku.mainApi.domain.test.TestEntity;
import kuku.mainApi.domain.test.TestDomainService;
import kuku.mainApi.domain.test.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestDomainServiceImpl implements TestDomainService {

    private final TestRepository testRepository;

    @Transactional
    public TestEntity getTestEntityWithLock(Long id) {
        return testRepository.findByTestEntityWithPessimisticWrite(id)
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    public TestEntity getTestEntity(String value) {
        return testRepository.findByTestEntity(value)
                .orElseThrow(RuntimeException::new);
    }
}
