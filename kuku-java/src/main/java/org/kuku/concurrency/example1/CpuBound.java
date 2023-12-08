package org.kuku.concurrency.example1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * cpu 당 heavy 한 연산이 할당 되는 것으로 처리 -> 병렬설 대응
 *
 */
public class CpuBound {

    public static void main(String[] args) throws InterruptedException {

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        long startTime2 = System.currentTimeMillis();
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            Future<?> future = executorService.submit(() -> {

                long result = 0;
                for (long j = 0; j < 1000000000L; j++) {
                    result += j;
                }

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("스레드: " + Thread.currentThread().getName() + ", " + result);
            });
            futures.add(future);
        }
        futures.forEach(f -> {
            try {
                f.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        long endTime2 = System.currentTimeMillis();
        System.out.println("CPU 개수를 초과하는 데이터를 병렬로 처리하는 데 걸린 시간: " + (endTime2 - startTime2) + "ms");
        executorService.shutdown();
    }
}
