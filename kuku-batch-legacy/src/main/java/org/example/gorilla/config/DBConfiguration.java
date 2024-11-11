package org.example.gorilla.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Conditional(JobNameCondition.class)
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "org.example.gorilla.repository"
        , entityManagerFactoryRef = "gorillaEntityManagerFactory"
        , transactionManagerRef = "gorillaTransactionManager")
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

    @Bean(name = "gorillaTransactionManager")
    public PlatformTransactionManager transactionManager(
            EntityManagerFactoryBuilder builder
    ) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory(builder, null).getObject());
        return transactionManager;
    }

    @Bean
    public DataSource routingDataSource() {
        return ReplicationRoutingDataSource.of(readDataSource(), writeDataSource());
    }

    @Bean(name = "gorillaReadDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource readDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "gorillaWriteDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    private Map<String, Object> getVendorProperties() {
        return new LinkedHashMap<>(this.hibernateProperties.determineHibernateProperties(
                getJpaProperties(),
                new HibernateSettings()
        ));
    }

    private String[] getMappingResource() {
        List<String> mappingResources = this.properties.getMappingResources();
        return (ObjectUtils.isEmpty(mappingResources) ?  null : StringUtils.toStringArray(mappingResources));
    }

    private Map<String, String> getJpaProperties() {
        Map<String, String> jpaPropertyMap = this.properties.getProperties();
        jpaPropertyMap.put("hibernate.physical_naming_strategy", HIBERNATE_PHYSICAL_NAMING_STRATEGY);
        return jpaPropertyMap;
    }
}
