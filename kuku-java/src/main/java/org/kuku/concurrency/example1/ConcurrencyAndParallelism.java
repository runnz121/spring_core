package org.kuku.concurrency.example1;

import java.util.ArrayList;
import java.util.List;

public class ConcurrencyAndParallelism {

    public static void main(String[] args) {

//        int cpuCores = Runtime.getRuntime().availableProcessors() * 2;
        int cpuCores = 13;

        // CPU 개수를 초과하는 데이터를 생성
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < cpuCores; i++) {
            data.add(i);
        }

        // CPU 개수를 초과하는 데이터를 병렬로 처리
        long startTime2 = System.currentTimeMillis();
//        long sum2 = data.stream()
        long sum2 = data.parallelStream()
                .mapToLong(i -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return i * i;
                })
                .sum();

        long endTime2 = System.currentTimeMillis();

        System.out.println("CPU 개수를 초과하는 데이터를 병렬로 처리하는 데 걸린 시간: " + (endTime2 - startTime2) + "ms");
        System.out.println("결과2: " + sum2);
    }

    // 동시성 : 효율 극대화 -> cpu 하나에 여러 작업을 처리하기 위해 나눠서 진행
    // 병렬성 : 하나의 코어 하나의 스레드 하나의 작업 -> 다중 코어일 수록 효율이 높다 -> 코어보다 많은 작업이 존재할 경우 잔여 작업 대기
}
