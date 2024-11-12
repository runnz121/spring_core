package org.example.gorilla.domain.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InputType {

    TEXT("FREETEXT", "텍스트"),
    DATE("DATE", "달력"),
    OPTION("OPTION", "셀렉트박스"),
    ;

    private final String code;
    private final String desc;
}
