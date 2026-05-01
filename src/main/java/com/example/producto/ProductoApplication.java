package com.example.producto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ProductoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductoApplication.class, args);
    }

}
