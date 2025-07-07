package kuku.externalAPI.infrastructure.controller;

import kuku.externalAPI.application.RetryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/retry")
@RequiredArgsConstructor
public class RetryController {

    private final RetryService retryService;

    @GetMapping
    public void retry() {

    }
}
