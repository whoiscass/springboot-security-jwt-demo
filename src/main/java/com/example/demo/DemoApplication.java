package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import lombok.extern.slf4j.Slf4j;

import com.example.demo.config.security.SecurityConfigProperties;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({
    SecurityConfigProperties.class
})
public class DemoApplication {

	public static void main(String[] args) {
        try {
            SpringApplication.run(DemoApplication.class, args);
        }
        catch(Error e) {
            log.error("FATAL: Application failed to start: ", e.getMessage(), e);
        }
	}
}
