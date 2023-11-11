package kuku.mainApi.common.value.auth;

import lombok.*;

import java.util.Objects;

@Getter
@ToString
@NoArgsConstructor
public class AuditUser {

    private static final String ANONYMOUS = "anonymous";

    private String auditName;

    @Builder
    public AuditUser(String auditName) {
        this.auditName = auditName;
    }

    public static AuditUser from(String auditName) {
        return AuditUser.builder()
                .auditName(auditName)
                .build();
    }

    public String getAuditName() {

        if (Objects.isNull(auditName)) {
            return ANONYMOUS;
        }

        return auditName;
    }
}
