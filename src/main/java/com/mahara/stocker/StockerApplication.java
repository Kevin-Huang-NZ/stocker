package com.mahara.stocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.mahara.stocker"})
public class StockerApplication {

  public static void main(String[] args) {
    SpringApplication.run(StockerApplication.class, args);
  }
}
