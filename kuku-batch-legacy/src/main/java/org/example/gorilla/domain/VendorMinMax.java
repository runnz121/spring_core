package org.example.gorilla.domain;

import lombok.*;

import javax.persistence.Embeddable;
import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Embeddable
public class VendorMinMax {

    private static final int DEFAULT_MIN_VALUE = 1;
    private static final int DEFAULT_MAX_VALUE = 99;

    private Integer min;
    private Integer max;

    public static VendorMinMax of(Integer min, Integer max) {
        return VendorMinMax.builder()
                .min(Optional.ofNullable(min).orElse(DEFAULT_MIN_VALUE))
                .max(Optional.ofNullable(max).orElse(DEFAULT_MAX_VALUE))
                .build();
    }
}
