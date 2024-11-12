package org.example.gorilla.domain.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AgeGroup {

    ADULT("ADULT", "성인", 1),
    CHILD("CHILD", "아동", 2),
    NONE("NONE", "연령 무관", 0),
    ;

    private final String code;
    private final String desc;
    private final int order;

    public boolean isNone() {
        return NONE == this;
    }

    public boolean isAdult() {
        return ADULT == this;
    }

    public boolean isAvailableOrder() {
        return isAdult() || isNone();
    }
}
