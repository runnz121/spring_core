package org.kuku.middleWare;

import java.util.function.BiFunction;
import java.util.function.Function;

public class MiddleWareTask<T, R> {

    /*
        T 타입 파라미터와 next(제어함수) 를 받아 R 타입을 반환
     */
    private final BiFunction<T, Function<T, R>, R> middleWare;

    public MiddleWareTask(BiFunction<T, Function<T, R>, R> middleWare) {
        this.middleWare = middleWare;
    }

    public static <T, R> MiddleWareTask<T, R> of (BiFunction<T, Function<T, R>, R> middleWare) {
        return new MiddleWareTask<>(middleWare);
    }

    public static <T, R> MiddleWareTask<T, R> empty() {
        return new MiddleWareTask<>((value, next) -> next.apply(value));
    }

    public MiddleWareTask<T, R> andThen(MiddleWareTask<T, R> after) {
        return new MiddleWareTask<>((value, next) ->
            middleWare.apply(value, value2 -> after.middleWare.apply(value2, next))
        );
    }

    public MiddleWareTask<T, R> andThen(BiFunction<T, Function<T, R>, R> middleWare) {
        return andThen(MiddleWareTask.of(middleWare));
    }

    /*
        task를 수행하는 run() 메서드
     */
    public R run(T param, Function<T, R> task) {
        return middleWare.apply(param, task);
    }
}
