package kuku.batch.app.test;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;


@Configuration
public class SampleJob extends DefaultBatchConfiguration {

    @Bean
    public Job sampleJob1(JobRepository jobRepository, Step testStep) {
        return new JobBuilder("sampleJob1", jobRepository)
            .start(testStep)
            .build();
    }


    @Bean
    @JobScope
    public Step testStep(JobRepository jobRepository, Tasklet testTasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("testStep", jobRepository)
            .tasklet(testTasklet, platformTransactionManager).build();
    }

    @Bean
    @JobScope
    public Tasklet testTasklet(){
        return ((contribution, chunkContext) -> {
            System.out.println("테스트1");
            return RepeatStatus.FINISHED;
        });
    }
}
