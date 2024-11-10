package org.example.gorilla.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

public class JobNameCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context,
                           AnnotatedTypeMetadata metadata) {
        final String currentJobName = context.getEnvironment().getProperty("job.name");
        return Objects.equals("gorillaProductJob", currentJobName);
    }
}
