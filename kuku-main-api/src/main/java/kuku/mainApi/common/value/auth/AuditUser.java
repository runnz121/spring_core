package kuku.mainApi.common.value.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuditUser {

    private String auditName;

    @Builder
    public AuditUser(String auditName) {
        this.auditName = auditName;
    }

}
