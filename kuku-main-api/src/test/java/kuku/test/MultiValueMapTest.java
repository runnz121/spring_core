package kuku.test;

import kuku.mainApi.application.TestService;
import kuku.mainApi.data.SampleResponseData;
import kuku.mainApi.data.SampleResultData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SpringBootTest(classes = TestService.class)
public class MultiValueMapTest {

    @Autowired
    TestService testService;

    @Test
    void multiValueMapTest() {

        SampleResultData data1 = SampleResultData.builder()
                .schedulerReviewKey(1L)
                .googlePlaceId("123")
                .build();

        SampleResultData data2 = SampleResultData.builder()
                .schedulerReviewKey(1L)
                .googlePlaceId("123")
                .build();

        SampleResultData data3 = SampleResultData.builder()
                .schedulerReviewKey(2L)
                .googlePlaceId("abc")
                .build();

        SampleResultData data4 = SampleResultData.builder()
                .schedulerReviewKey(2L)
                .googlePlaceId("abc")
                .build();

        List<SampleResultData> exampleLists = List.of(data1, data2, data3, data4);

        AtomicInteger start = new AtomicInteger();

        List<SampleResponseData> hi = exampleLists
                .stream()
                .map(review -> {
                    List<String> strings = testService.testPlaceImages(String.valueOf(start.get()));
                    start.addAndGet(1);
                    return SampleResponseData.of(review, strings);
                })
                .collect(Collectors.toList());

        System.out.println(hi);
    }

    @Test
    void multiValueMap() {
        MultiValueMap<String, Integer> multiValueMap = new LinkedMultiValueMap<>();

        List<Integer> intList = new LinkedList<>();

        intList.add(1);
        intList.add(2);
        intList.add(3);

        multiValueMap.put("putTest", intList);

        System.out.println("multiValueMap = " + multiValueMap);
    }
}
