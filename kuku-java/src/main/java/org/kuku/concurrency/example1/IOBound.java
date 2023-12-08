package org.kuku.concurrency.example1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 동시성에 대응
 *
 */
public class IOBound {

    public static void main(String[] args) {

        int numThreads = Runtime.getRuntime().availableProcessors() * 2;

        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                try {

                    for (int j = 0; j < 5; j++) {
                        Files.readAllLines(Path.of("/Users/pupu/Desktop/study/study_solo_java/spring_core/kuku-java/src/main/resources/sample.txt"));
                        System.out.println("스레드: " + Thread.currentThread().getName() +", " +j); // IO Bound 일때 ContextSwitching 이 일어난다
                    }

                    int result = 0;
                    for (long j = 0; j < 10; j++) {
                        result += j;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executorService.shutdown();
    }
}
