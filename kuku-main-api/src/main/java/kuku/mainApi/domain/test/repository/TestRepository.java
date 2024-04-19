package kuku.mainApi.domain.test.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import kuku.mainApi.domain.test.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
.get
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "jakarta.persistence.lock.timeout", value = "100")
    })
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from TestEntity i where i.id = :id")
    Optional<TestEntity> findByTestEntityWithPessimisticWrite(@Param("id") Long id);

    @Query("select i from TestEntity i where i.value = :value")
    Optional<TestEntity> findByTestEntity(@Param("value") String value);
}
