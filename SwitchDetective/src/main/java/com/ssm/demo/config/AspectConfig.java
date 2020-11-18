package com.ssm.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.ssm.demo.aspect"})
@EnableAspectJAutoProxy //当启用自动生成代理时 被作为连接点的方法所在对象被引用时会自动生成代理类 而不是原来的类
public class AspectConfig {

}
