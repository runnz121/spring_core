package org.example.sample;



import static org.apache.juli.OneLineFormatter.*;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Sample {

    public static void main(String[] args) throws InterruptedException {
        Observable.just(100, 200, 300, 400, 500) // 1. 발행
            .doOnNext(data -> System.out.println(getThreadName() + " : " + "#doOnNext() : " + data))
            .subscribeOn(Schedulers.io()) // 메인쓰레드가 아닌 다른 쓰레드에서 동작 따라서 쓰레드 슬립 코드가 없으면 해당 쓰레드가 동작 하기 전에 종료됨
            // .observeOn(Schedulers.computation())
            .filter(number -> number > 300) // 2. 처리
            .subscribe(num -> System.out.println(getThreadName() + " : result : " + num)); // 3. 구독

        Thread.sleep(500);
    }

    public static String getThreadName(){
        return Thread.currentThread().getName();
    }
    /**
     * subscribeOn(스케쥴러 지정 필요)
     * 데이터 발행, 흐름을 결정 짓는 함수
     */
    // RxCachedThreadScheduler-1 : #doOnNext() : 100
    // RxCachedThreadScheduler-1 : #doOnNext() : 200
    // RxCachedThreadScheduler-1 : #doOnNext() : 300
    // RxCachedThreadScheduler-1 : #doOnNext() : 400
    // RxCachedThreadScheduler-1 : result : 400
    // RxCachedThreadScheduler-1 : #doOnNext() : 500
    // RxCachedThreadScheduler-1 : result : 500

    /**
     * observeOn(스케쥴러 지정 필요)
     * 발행된 데이터를 가공하고 구독하고 처리하는 스레드를 지정
     */
    // RxCachedThreadScheduler-1 : #doOnNext() : 100
    // RxCachedThreadScheduler-1 : #doOnNext() : 200
    // RxCachedThreadScheduler-1 : #doOnNext() : 300
    // RxCachedThreadScheduler-1 : #doOnNext() : 400
    // RxCachedThreadScheduler-1 : #doOnNext() : 500
    // RxComputationThreadPool-1 : result : 400
    // RxComputationThreadPool-1 : result : 500
}
