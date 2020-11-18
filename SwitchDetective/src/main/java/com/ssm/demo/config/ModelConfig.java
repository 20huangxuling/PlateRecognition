package com.ssm.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.ssm.demo.model"})
@Import({ServiceConfig.class,AspectConfig.class})
public class ModelConfig {
}
