package org.example.vendor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vendor.exception.FeignCustomException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class VendorFeignConfig {

    @Bean
    Logger.Level vendorFeignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Request.Options vendorFeignRequestOptions() {
        final long connectionTimeOut = 10;
        final long readTimeOut = 20;
        final boolean followRedirects = false;
        return new Request.Options(connectionTimeOut, TimeUnit.SECONDS, readTimeOut, TimeUnit.SECONDS, followRedirects);
    }

    @Bean
    public Retryer vendorFeignRetry() {
        return Retryer.NEVER_RETRY;
    }

    @Bean
    public RequestInterceptor vendorFeignRequestInterceptor() {
        return new VendorFeignRequestInterceptor();
    }

    @Bean
    public ErrorDecoder vendorFeignDecoder() {

        StringDecoder stringDecoder = new StringDecoder();
        ObjectMapper objectMapper = new ObjectMapper();

        return (methodKey, response) -> {
            String message = null;
            Map<String, Object> bodyMaps = null;
            if (response.body() != null) {
                try {
                    message = stringDecoder.decode(response, String.class).toString();
                    bodyMaps = objectMapper.readValue(message, Map.class);
                } catch (Exception e){
                    log.error("<VENDOR:BATCH:VENDOR_FEIGN:ERROR> methodKey: {}");
                }
            } else {
                log.error("<VENDOR:BATCH:VENDOR_FEIGN:ERROR> response.body is null. {}", methodKey);
            }
            return new FeignCustomException(response.status(), message, response.headers(), bodyMaps);
        };
    }
}
