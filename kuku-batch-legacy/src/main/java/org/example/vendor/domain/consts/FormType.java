package org.example.vendor.domain.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FormType {

    HANATOUR("HANATOUR", "하나투어양식"),
    VENDOR("VENDOR", "공급업체양식"),
    EXTRA("EXTRA", "추가입력양식"),
    ;

    private final String code;
    private final String desc;
}
