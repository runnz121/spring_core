package kuku.test;

import kuku.mainApi.domain.test.TestEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TryCatchTest {

    @DisplayName("tryCatch test")
    @Test
    void trayCatchTest() {

        TestEntity testEntity = new TestEntity();

        try {

            testEntity = TestEntity.builder()
                    .value("test")
                    .name("error")
                    .build();

            throw new Exception("error");

        } catch (Exception e) {

            System.out.println(testEntity);

        }

    }
  
}