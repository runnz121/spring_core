package kuku.test;

import kuku.mainApi.domain.TestEntity;
import kuku.mainApi.domain.TestEntityInner;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class StreamTest {

    @Test
    void mapTest() {

        Map<Integer, String> sampleMap = Map.of(
                1, "a",
                2, "b",
                3, "c",
                4, "d"
        );

        // key 기준으로 모음
        List<Integer> entryKey = sampleMap.entrySet()
                .stream()
                .map(Map.Entry::getKey)
                .toList();

        // [2, 3, 4, 1]
        System.out.println(entryKey);

        // value 기준으로 모음
        List<String> entryValue = sampleMap.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .toList();

        // [b, c, d, a]
        System.out.println(entryValue);
    }

    @Test
    void listTest() {

        TestEntityInner inner1 = TestEntityInner.builder()
                .id(1L)
                .value("1")
                .build();

        TestEntityInner inner2 = TestEntityInner.builder()
                .id(2L)
                .value("2")
                .build();

        List<TestEntityInner> innerList = List.of(inner1, inner2);

        TestEntity outer1 = TestEntity.builder()
                .id(1L)
                .value("1")
                .testEntityInnerList(innerList)
                .build();

        TestEntity outer2 = TestEntity.builder()
                .id(2L)
                .value("2")
                .testEntityInnerList(innerList)
                .build();

        List<TestEntity> outerList = List.of(outer1, outer2);

        // 배열 -> 객체 1 -> 배열 -> 객체 2만을 모음
        List<TestEntityInner> innerListCollected = outerList.stream()
                .map(TestEntity::getTestEntityInnerList)
                .flatMap(Collection::stream)
                .toList();

        // [TestEntityInner(id=1, value=1), TestEntityInner(id=2, value=2), TestEntityInner(id=1, value=1), TestEntityInner(id=2, value=2)]
        System.out.println(innerListCollected);
    }

    @Test
    void emtpyStreamTest() {
        List<String> emptyString = new ArrayList<>();

        List<Integer> actual = emptyString
                .stream()
                .map(String::length)
                .toList();

        System.out.println(actual);
    }
}
