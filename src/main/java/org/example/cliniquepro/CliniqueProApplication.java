package org.example.cliniquepro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CliniqueProApplication {

    public static void main(String[] args) {
        SpringApplication.run(CliniqueProApplication.class, args);
    }

}
