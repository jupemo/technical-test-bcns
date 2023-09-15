package com.inditex.pricing.adaptor.database.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("com.inditex.pricing.adaptor.database")
@EntityScan("com.inditex.pricing.adaptor.database")
@EnableJpaRepositories(basePackages = "com.inditex.pricing.adaptor.database")
public class DatabaseH2Configuration {}
