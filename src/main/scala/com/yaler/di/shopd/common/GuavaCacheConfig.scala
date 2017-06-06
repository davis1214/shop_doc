package com.yaler.di.shopd.common

import java.util.concurrent.TimeUnit

import com.google.common.cache.CacheBuilder
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.guava.GuavaCacheManager
import org.springframework.context.annotation.{Configuration, Bean}

/**
  * Created by Administrator on 17/6/5.
  */
@Configuration
@EnableCaching
class GuavaCacheConfig {

  @Bean
  def cacheManager(): CacheManager = {
    val cacheManager = new GuavaCacheManager();

    cacheManager.setCacheBuilder(
      CacheBuilder.newBuilder().
        expireAfterWrite(20, TimeUnit.MINUTES).
        maximumSize(1000));

    cacheManager;
  }


}
