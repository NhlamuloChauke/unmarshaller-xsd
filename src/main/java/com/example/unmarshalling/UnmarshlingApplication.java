package com.example.unmarshalling;

import com.example.unmarshalling.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication(scanBasePackages = "com.example.unmarshalling")
@EnableConfigurationProperties(AppProperties.class)
public class UnmarshlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnmarshlingApplication.class, args);
	}

}
