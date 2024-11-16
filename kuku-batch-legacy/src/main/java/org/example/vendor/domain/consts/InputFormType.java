package org.example.vendor.domain.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InputFormType {

    PERSON("PERSON", "인원별 입력"),
    GROUP("GROUP", "대표입력"),
    OPTION("OPTION", "옵션별 입력"),
    ;

    private final String code;
    private final String desc;

}
