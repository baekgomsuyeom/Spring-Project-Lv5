package com.springproject.springprojectlv5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringProjectLv5Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringProjectLv5Application.class, args);
    }

}
