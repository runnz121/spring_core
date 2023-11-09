package kuku.mainApi.common.config.audit;

import jakarta.servlet.http.HttpServletRequest;
import kuku.mainApi.common.value.auth.AuditUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class AuditConfig {

    @Bean(name = "auditConfigUser")
    @Scope(value = "request" , proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AuditUser auditConfig() {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        return (AuditUser) request.getAttribute("auditUser");
    }
}
