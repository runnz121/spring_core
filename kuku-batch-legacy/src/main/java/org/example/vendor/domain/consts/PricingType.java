package org.example.vendor.domain.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PricingType {

    PERSON("PERSON", "인원타입"),
    PLACE("PLACE", "장소타입"),
    CUSTOM("CUSTOM", "커스텀타입"),
    ;

    private final String code;
    private final String desc;
}
