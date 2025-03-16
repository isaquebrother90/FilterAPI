package com.filterapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Classe principal da aplicação Filter API. Esta classe é responsável por inicializar e executar a
 * aplicação Spring Boot.
 *
 * @since 1.0
 */
@EnableFeignClients
@SpringBootApplication
public class FilterApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(FilterApiApplication.class, args);
  }
}
