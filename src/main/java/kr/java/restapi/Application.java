package kr.java.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // createdAt, updatedAt, createdBy, updatedBy ...
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
