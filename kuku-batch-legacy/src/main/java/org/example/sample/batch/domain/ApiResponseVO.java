package org.example.sample.batch.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseVO {

    private int status;
    private String message;
}
