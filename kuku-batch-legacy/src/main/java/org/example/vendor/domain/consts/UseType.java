package org.example.vendor.domain.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UseType {

    TYPE("TYPE", "타입기준사용"),
    OPTION("OPTION", "옵션기준사용"),
    ;

    private final String code;
    private final String desc;
}
