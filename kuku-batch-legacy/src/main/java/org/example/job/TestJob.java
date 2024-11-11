package org.example.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.example.job.TestJob.JOB_NAME;


@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(name = "spring.batch.job.names", havingValue = JOB_NAME)
public class TestJob {

    public static final String JOB_NAME = "gorillaProductJob1";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job gorillaProductJob1() {
        return jobBuilderFactory.get("gorillaProductJob1")
//                .listener(new JobListener())
                .start(testStep2())
                .build();
    }

    @Bean
    @JobScope
    public Step testStep2() {
        return stepBuilderFactory.get("testStep2")
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    System.out.println("Hello, world!");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

//    @Bean
//    @StepScope
//    public Tasklet sampleTasklet1() {
//        return new SampleTasklet();
//    }
}
