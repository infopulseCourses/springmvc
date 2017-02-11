package com.courses.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by vedmant on 2/4/17.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.courses.mvc.*")
public class AppConfig extends WebMvcConfigurerAdapter {}
