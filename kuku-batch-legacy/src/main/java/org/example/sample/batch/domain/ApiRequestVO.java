package org.example.sample.batch.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiRequestVO {

    private long id;
    private ProductVO productVO;
}
