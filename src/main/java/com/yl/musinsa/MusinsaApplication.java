package com.yl.musinsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MusinsaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusinsaApplication.class, args);
    }

}
