package org.kuku.concurrency.example2;

/**
 * 단일 스레드와 다르게 모든 스레드가 종료되어야 애플리케이션이 종료된다.
 *
 */
public class MultiThreadExample {

    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new ThreadStackExample.MyRunnable(i));
            thread.start();
        }

        System.out.println("메인 스레드 종료");

    }
    static class MyRunnable implements Runnable{

        private final int threadId;

        public MyRunnable(int threadId) {

            this.threadId = threadId;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": 스레드 실행 중...");
            firstMethod(threadId);
        }

        private void firstMethod(int threadId) {

            int localValue = threadId + 100;
            secondMethod(localValue);

        }

        private void secondMethod(int localValue) {
            String objectReference = threadId + ": Hello World";
            System.out.println(Thread.currentThread().getName() + " : 스레드 ID : " + threadId + ", Value:" + localValue);
        }
    }
}
