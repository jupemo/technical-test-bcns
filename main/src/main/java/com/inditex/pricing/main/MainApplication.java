package com.inditex.pricing.main;

import com.inditex.pricing.adaptor.database.config.DatabaseH2Configuration;
import com.inditex.pricing.application.config.ApplicationAutoConfiguration;
import com.inditex.pricing.application.port.input.GetCorrectPriceQuery;
import com.inditex.pricing.application.port.input.interaptor.GetCorrectPriceQueryService;
import com.inditex.pricing.application.port.output.LoadPricePort;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.inditex.pricing")
public class MainApplication {

  public static void main(String[] args) {
    SpringApplication.run(MainApplication.class, args);
  }

}
