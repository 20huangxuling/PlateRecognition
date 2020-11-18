package com.ssm.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.ssm.demo.service.impl"})
public class ServiceConfig {
}
