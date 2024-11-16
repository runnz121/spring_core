package org.example.vendor.domain.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuantityType {

    BASIC("BASIC", "기본 수량"),
    DAY("DAY", "날짜별 수량"),
    ;

    private final String code;
    private final String desc;

    public boolean isBasic() {
        return this == BASIC;
    }
}
