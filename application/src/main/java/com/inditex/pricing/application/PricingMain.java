package com.inditex.pricing.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.inditex.pricing")
public class PricingMain {
  public static void main(String[] args) {
    SpringApplication.run(PricingMain.class, args);
  }
}
