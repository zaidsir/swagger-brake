package io.redskap.swagger.brake.maven;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class MavenConfiguration {
    @Bean
    public HttpClient httpClient() {
        return HttpClientBuilder.create().build();
    }
}
