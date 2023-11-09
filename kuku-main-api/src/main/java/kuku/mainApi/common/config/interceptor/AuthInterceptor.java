package kuku.mainApi.common.config.interceptor;

import kuku.mainApi.common.value.auth.AuditUser;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthInterceptor implements HandlerInterceptor {

    private static final String ANONYMOUS = "anonymous";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String jwtAuthToken = request.getHeader("Authorization");
      //  boolean isActiveToken = checkJwtToken(jwtAuthToken);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        AuditUser auditUser = null;

        if (Objects.isNull(authentication) || ! StringUtils.hasText(authentication.getName())) {
            auditUser =  AuditUser.builder().auditName(ANONYMOUS).build();
        } else {
            auditUser = AuditUser.builder().auditName(authentication.getName()).build();
        }

        MDC.put("user", auditUser.getAuditName());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // private boolean checkJwtToken(String authToken) {
    //
    //
    // }
    //
    // private





}