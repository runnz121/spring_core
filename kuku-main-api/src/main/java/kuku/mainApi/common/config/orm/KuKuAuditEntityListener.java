package kuku.mainApi.common.config.orm;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;

public class KuKuAuditEntityListener {

    private static final ReflectionUtils.AnnotationFieldFilter CREATED_BY_FILTER = new ReflectionUtils.AnnotationFieldFilter(CreatedBy.class);
    private static final ReflectionUtils.AnnotationFieldFilter CREATED_DATE_FILTER = new ReflectionUtils.AnnotationFieldFilter(CreatedDate.class);
    private static final ReflectionUtils.AnnotationFieldFilter LAST_MODIFIED_BY_FILTER = new ReflectionUtils.AnnotationFieldFilter(LastModifiedBy.class);
    private static final ReflectionUtils.AnnotationFieldFilter LAST_MODIFIED_DATE_FILTER = new ReflectionUtils.AnnotationFieldFilter(LastModifiedDate.class);

    private final ObjectFactory<AuditingHandler> handler;
    private final AuditorAware<String> auditorAware;

    public KuKuAuditEntityListener(ObjectFactory<AuditingHandler> handler, AuditorAware<String> auditorAware) {
        this.handler = handler;
        this.auditorAware = auditorAware;
    }

    @PrePersist
    public void touchForCreate(Object target) {

        Assert.notNull(target, "Entity must not be null");

        if (isManualAuditorField(target, CREATED_BY_FILTER)) {
            setAuditDate(target, CREATED_DATE_FILTER);
            setAuditDate(target, LAST_MODIFIED_DATE_FILTER);
            return;
        }

        if (handler != null) {
            AuditingHandler object = handler.getObject();
            if (object != null) {
                object.markCreated(target);
            }
        }
    }

    @PreUpdate
    public void touchForUpdate(Object target) {

        Assert.notNull(target, "Entity must not be null");

        if (isManualAuditorField(target, LAST_MODIFIED_BY_FILTER)) {
            setAuditDate(target, LAST_MODIFIED_DATE_FILTER);
            return;
        }

        if (handler != null) {
            AuditingHandler object = handler.getObject();
            if (object != null) {
                object.markModified(target);
            }
        }
    }

    private boolean isManualAuditorField(Object target, ReflectionUtils.AnnotationFieldFilter filter) {

        Optional<Field> findAuditorField = Optional.ofNullable(ReflectionUtils.findField(target.getClass(), filter));
        if (findAuditorField.isEmpty()) {
            return false;
        }

        Field auditorField = findAuditorField.get();
        org.springframework.util.ReflectionUtils.makeAccessible(auditorField);
        Optional<Object> auditorValue = Optional.ofNullable(org.springframework.util.ReflectionUtils.getField(auditorField, target));
        return auditorValue.isPresent() && !auditorValue.get().equals(this.auditorAware.getCurrentAuditor().orElse(null));
    }

    private void setAuditDate(Object target, ReflectionUtils.AnnotationFieldFilter filter) {

        Optional<Field> findCreatedDate = Optional.ofNullable(ReflectionUtils.findField(target.getClass(), filter));
        if (findCreatedDate.isEmpty()) {
            return;
        }

        Field auditDateField = findCreatedDate.get();
        if (!auditDateField.getType().isAssignableFrom(LocalDateTime.class)) {
            return;
        }

        ReflectionUtils.setField(auditDateField, target, LocalDateTime.now());
    }
}
