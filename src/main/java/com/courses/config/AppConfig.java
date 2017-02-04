package com.courses.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Stepan
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.courses.*" })
public class AppConfig extends WebMvcConfigurerAdapter {

}
