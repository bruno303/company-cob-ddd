package com.bso.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CompanyCobApplication {
    
    public static void main(String... args) {
        SpringApplication.run(CompanyCobApplication.class, args);
    }

    @Bean
    public CommandLineRunner test() {
        return args -> {
            
        };
    }
}
