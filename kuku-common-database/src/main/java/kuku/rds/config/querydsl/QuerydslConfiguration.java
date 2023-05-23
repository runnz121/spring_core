package kuku.rds.config.querydsl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Configuration
public class QuerydslConfiguration {

    @PersistenceContext(unitName = "masterEntityManager")
    EntityManager masterEntityManager;

    @PersistenceContext(unitName = "slaveEntityManager")
    EntityManager slaveEntityManager;

    @Bean
    public JPAQueryFactory masterQueryFactory() {
        return new JPAQueryFactory(masterEntityManager);
    }

    @Bean(name="secondaryQueryFactory")
    public JPAQueryFactory slaveQueryFactory() {
        return new JPAQueryFactory(slaveEntityManager);
    }
}
