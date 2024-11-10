package org.example.gorilla.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Conditional(JobNameCondition.class)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "org.example.gorilla.repository"
        , entityManagerFactoryRef = "fndEntityManagerFactory"
        , transactionManagerRef = "fndTransactionManager")
@Configuration
public class DBConfiguration {

    private static final String HIBERNATE_PHYSICAL_NAMING_STRATEGY = "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl";
    private static final String PACKAGE = "org.example.gorilla.domain";

    private final HibernateProperties hibernateProperties;
    private final JpaProperties properties;

    @Bean(name = "gorillaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder,
            ConfigurableListableBeanFactory beanFactory
    ) {
        return builder.dataSource(routingDataSource())
                .packages(PACKAGE)
                .properties(getVendorProperties())
                .mappingResources(getMappingResource())
                .build();
    }

    @Bean
    public DataSource routingDataSource() {
        return ReplicationRoutingDataSource.of(readDataSource(), writeDataSource());
    }
}
