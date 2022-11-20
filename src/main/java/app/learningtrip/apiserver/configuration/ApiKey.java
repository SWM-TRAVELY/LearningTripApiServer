package app.learningtrip.apiserver.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApiKey {

    @Value("${google-map-api-key}")
    private String GoogleMapApiKey;
}
