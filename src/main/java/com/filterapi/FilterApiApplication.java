package com.filterapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FilterApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(FilterApiApplication.class, args);
  }
}
