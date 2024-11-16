package org.example.vendor.domain.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum GenderType {

    MALE("MALE", "남성"),
    FEMALE("FEMALE", "여성"),
    NONE("NONE", "성별 무관"),
    ;

    private final String code;
    private final String desc;

    public static final String GOROO_MALE_VALUE = "1";
    public static final String GOROO_FEMALE_VALUE = "2";

    public static GenderType of(String name) {
        if (Strings.isBlank(name)) {
            return NONE;
        }

        return Arrays.stream(GenderType.values())
                .filter(genderOption -> genderOption.name().equals(name.toUpperCase()))
                .findFirst()
                .orElse(NONE);
    }

    public boolean isNone() {
        return this == NONE;
    }
}
