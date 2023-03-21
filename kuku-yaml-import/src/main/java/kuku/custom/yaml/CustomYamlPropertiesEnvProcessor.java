package kuku.custom.yaml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class CustomYamlPropertiesEnvProcessor implements EnvironmentPostProcessor, Ordered {

    private static final String SPRING_PROFILES = "spring.profiles";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {



    }

    @Override
    public int getOrder() {
        return 0;
    }
}
