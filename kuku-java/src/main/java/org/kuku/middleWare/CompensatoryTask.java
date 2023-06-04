package org.kuku.middleWare;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

// Try catch  벗어나기!
public class CompensatoryTask<T> {

    private final MiddleWareTask<T, T> task;

    public CompensatoryTask(MiddleWareTask<T, T> task) {
        this.task = task;
    }

    public static <T> CompensatoryTask<T> of(
        UnaryOperator<T> action,
        BiFunction<T, RuntimeException, RuntimeException> compensation
    ) {
        return new CompensatoryTask<>(
            MiddleWareTask.of(
                (value, next) -> {
                    final var result = action.apply(value);
                    try {
                        return next.apply(result);
                    } catch (RuntimeException re) {
                        throw compensation.apply(value, re);
                    }
                }
            )
        );
    }

    public static <T> CompensatoryTask<T> empty() {
        return new CompensatoryTask<>(MiddleWareTask.empty());
    }

    public CompensatoryTask<T> andThen(
        UnaryOperator<T> action,
        BiFunction<T, RuntimeException, RuntimeException> compensation
    ) {
        return andThen(CompensatoryTask.of(action, compensation));
    }

    public CompensatoryTask<T> andThen(CompensatoryTask<T> compensatoryTask) {
        return new CompensatoryTask<>(task.andThen(compensatoryTask.task));
    }

    public T run(T init) {
        return task.run(init, it -> null);
    }
}
