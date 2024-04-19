package kuku.mainApi.domain.test;

public interface TestDomainService {

    TestEntity getTestEntityWithLock(Long id);

    TestEntity getTestEntity(String value);
}
