package kuku.mainApi.common.config.orm;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
//@EnableJpaAuditing
@Configuration(proxyBeanMethods = false)
public class DefaultAuditAwareConfig {

    @PostConstruct
    public void init() {
    }

    @Bean
    @ConditionalOnMissingBean
    public AuditorAware<String> auditorAware() {
        return new DefaultAuditAware();
    }
}
