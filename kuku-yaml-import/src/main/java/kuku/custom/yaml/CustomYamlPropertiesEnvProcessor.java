package kuku.custom.yaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Profiles;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class CustomYamlPropertiesEnvProcessor implements EnvironmentPostProcessor, Ordered {

    private static final int DEFAULT_ORDER = Ordered.LOWEST_PRECEDENCE;
    private static final String SPRING_PROFILES = "spring.profiles";
    private static final String RESOURCE_PATH = "classpath*:config/common-*.yml";
    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        ResourceLoader resourceLoader = Optional
            .ofNullable(application.getResourceLoader())
            .orElseGet(DefaultResourceLoader::new);

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(resourceLoader);
        Resource[] resources = new Resource[]{};

        try {
            resources = resourcePatternResolver.getResources(RESOURCE_PATH);
        } catch (IOException ex) {

        }
        for (Resource resource : resources) {
            yamlLoader(resource, environment)
                .forEach(propertySource -> {
                    environment.getPropertySources().addLast(propertySource);
                });
        }
    }

    private List<PropertySource<?>> yamlLoader(Resource path, ConfigurableEnvironment environment) {

        if (!path.exists()) {
            throw new IllegalArgumentException("path not found");
        }

        try {
            List<PropertySource<?>> defaultPropertySource = new ArrayList<>();
            List<PropertySource<?>> propertySourceList = this.loader
                .load(path.getFilename(), path)
                .stream()
                .filter(propertySource -> {

                    Binder binder = new Binder(
                        ConfigurationPropertySources.from(propertySource),
                        new PropertySourcesPlaceholdersResolver(environment));

                    String[] profiles = binder
                        .bind(SPRING_PROFILES, Bindable.of(String[].class))
                        .orElse(new String[0]);

                    if (profiles.length == 0) {
                        defaultPropertySource.add(propertySource);
                        return false;
                    }

                    return environment.acceptsProfiles(Profiles.of(profiles));

                }).collect(Collectors.toList());

            propertySourceList = sortProfilePriority(environment, propertySourceList);
            propertySourceList.addAll(defaultPropertySource);

            return propertySourceList;

        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to load yaml");
        }
    }

    private List<PropertySource<?>> sortProfilePriority(ConfigurableEnvironment environment,
                                                       List<PropertySource<?>> propertySourceList) {

        String[] activeProfiles = environment.getActiveProfiles();

        return IntStream.range(0, activeProfiles.length)
            .map(idx -> activeProfiles.length - idx - 1)
            .mapToObj(idx -> activeProfiles[idx])
            .filter(Objects::nonNull)
            .map(profile -> propertySourceList.stream()
                .filter(
                    propertySource ->
                        matchProfiles(profile, propertySource))
                .findAny()
                .orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private boolean matchProfiles(String profile, PropertySource<?> propertySource) {

        Object property = propertySource.getProperty(SPRING_PROFILES);
        if (Objects.isNull(property)) {
            return ((Map<String, OriginTrackedValue>) propertySource.getSource())
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains(SPRING_PROFILES))
                .map(stringOriginTrackedValueEntry -> stringOriginTrackedValueEntry.getValue().toString())
                .anyMatch(profile::equals);
        }

        return Arrays.stream(((String) property).split(","))
            .map(String::trim)
            .anyMatch(s -> s.equals(profile));
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }
}
