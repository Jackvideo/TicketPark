package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.tp_common","com.tp_movie"})
@EnableDiscoveryClient
public class TpMovieApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpMovieApplication.class, args);
    }

}
