package com.yaler.di.shopd.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by Administrator on 2016/10/13.
 */
@Configuration
public class PropertiesConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer createPropertySourcesPlaceholderConfigurer() {
        ClassPathResource resource = new ClassPathResource("config.properties");
        PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocation(resource);
        propertyPlaceholderConfigurer.setFileEncoding("UTF-8");
        return propertyPlaceholderConfigurer;
    }
}