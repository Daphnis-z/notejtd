package com.daphnis.sbtdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class})
@EnableAsync
public class SbtdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbtdemoApplication.class, args);
	}
}
