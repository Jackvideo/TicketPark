package com.tp_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tp_user"})
public class TpUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpUserApplication.class, args);
    }

}
