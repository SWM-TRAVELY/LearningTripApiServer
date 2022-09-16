package app.learningtrip.apiserver.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource(value = "classpath:secret.properties")
public class SecretPropertiesConfiguration {
    @Value("spring.datasource.url")
    private String url;

    @Value("spring.datasource.username")
    private String username;

    @Value("spring.datasource.password")
    private String password;

    @Value("spring.datasource.driver-class-name")
    private String driverClassName;
}

