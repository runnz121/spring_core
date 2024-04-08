package org.example;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableBatchProcessing
@EnableFeignClients
public class BatchLegacy {
    public static void main(String[] args) {
        SpringApplication.run(BatchLegacy.class, args);
    }
}