package com.daphnis.sbtdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class})
public class SbtdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbtdemoApplication.class, args);
	}
}
