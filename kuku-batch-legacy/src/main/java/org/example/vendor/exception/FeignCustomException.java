package org.example.vendor.exception;

import lombok.Getter;

import java.util.Collection;
import java.util.Map;

@Getter
public class FeignCustomException extends RuntimeException {

    private final int status;
    private final String message;
    private final Map<String, Collection<String>> headers;
    private final Map<String, Object> body;

    public FeignCustomException(int status, String message, Map<String, Collection<String>> headers, Map<String, Object> body) {
        super(message);
        this.status = status;
        this.message = message;
        this.headers = headers;
        this.body = body;
    }

}
