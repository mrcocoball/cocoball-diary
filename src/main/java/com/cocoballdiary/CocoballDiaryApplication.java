package com.cocoballdiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CocoballDiaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CocoballDiaryApplication.class, args);
    }

}
