package kuku.mainApi.application.domain;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestEntityInner {

    Long id;

    String value;
}
