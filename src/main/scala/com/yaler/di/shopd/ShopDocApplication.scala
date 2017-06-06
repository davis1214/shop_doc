package com.yaler.di.shopd

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.io.ClassPathResource

@SpringBootApplication
class ShopDocApplication {

  @Bean def createPropertySourcesPlaceholderConfigurer: PropertySourcesPlaceholderConfigurer = {
    val propertyPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer
    propertyPlaceholderConfigurer.setLocation(new ClassPathResource("config.properties"))
    propertyPlaceholderConfigurer.setFileEncoding("UTF-8")
    propertyPlaceholderConfigurer
  }


}

object ShopDocApplication {
  def main(args: Array[String]) {
    SpringApplication.run(classOf[ShopDocApplication])
  }
}