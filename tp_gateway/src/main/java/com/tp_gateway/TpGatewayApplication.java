package com.tp_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {
        "com.tp_gateway"
})
@EnableDiscoveryClient
public class TpGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpGatewayApplication.class, args);
    }

}
