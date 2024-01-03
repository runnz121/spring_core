package kuku.mainApi.application.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TestEntity {

    Long id;

    String value;

    List<TestEntityInner> testEntityInnerList;
}
