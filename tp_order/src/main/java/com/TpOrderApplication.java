package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.ticketpark.tp_common","com.ticketpark.tp_order"})
public class TpOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpOrderApplication.class, args);
    }

}
