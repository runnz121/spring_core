package kuku.rds.config.querydsl;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public abstract class SlaveQuerydslRepositorySupport extends QuerydslRepositorySupport {

    public SlaveQuerydslRepositorySupport(Class<?> domainClass) {super(domainClass);}

    @Override
    @PersistenceContext(unitName = "slaveEntityManager")
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }
}
