package org.example.sample.batch.job.api;

import lombok.RequiredArgsConstructor;
import org.example.sample.batch.listener.JobListener;
import org.example.sample.batch.tasklet.ApiEndTasklet;
import org.example.sample.batch.tasklet.ApiStartTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class ApiConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ApiStartTasklet apiStartTasklet;
    private final ApiEndTasklet apiEndTasklet;
    private final Step jobStep;

    @Bean
    public Job apiJob() {
        return jobBuilderFactory.get("apiJob")
                .listener(new JobListener())
                .start(apiStep1())
                .next(jobStep)
                .next(apiStep2())
                .build();
    }

    @Bean
    public Step apiStep1() {
        return stepBuilderFactory.get("apiStep1")
                .tasklet(apiStartTasklet)
                .build();
    }

    @Bean
    public Step apiStep2() {
        return stepBuilderFactory.get("apiStep2")
                .tasklet(apiEndTasklet)
                .build();
    }
}
