package org.kuku.middleWare;

import java.util.function.Function;

public class MiddleWareTaskSample {

    private MiddleWareTask<String, String> greeter
        = MiddleWareTask.of(this::extract)
                    .andThen(this::crypto);

    public Object process(String record) {
        return greeter.run(record, name -> "hello " + name);
    }

    private String extract(String value, Function<String, String> next) {
        String[] token = value .split("=");
        String param  = token[0];
        String result = next.apply(token[1]);
        return param + "=" + result;
    }

    private String crypto(String value, Function<String, String> next) {
        String[] token = value .split("=");
        String param  = token[0];
        String result = next.apply(token[1]);
        return param + "=" + result;
    }

}
