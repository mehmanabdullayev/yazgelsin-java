package com.yazgelsin.ordering_system;

import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;
import com.rollbar.spring.webmvc.RollbarSpringConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration()
@EnableWebMvc
@ComponentScan({

// UPDATE TO YOUR PROJECT PACKAGE
    "com.example.app",
    "com.rollbar.spring"

})
public class RollbarConfig {

  /**
   * Register a Rollbar bean to configure App with Rollbar.
   */
  @Bean
  public Rollbar rollbar() {

    // Your ACCESS TOKEN is: 3128aa3167d64385bff7b35ae2563d77
    // Make sure to keep this secure    
    return new Rollbar(getRollbarConfigs("e7e2a730afbb49ffb8ecd77cead68d49"));
  }

  private Config getRollbarConfigs(String accessToken) {

    // Reference ConfigBuilder.java for all the properties you can set for Rollbar
    return RollbarSpringConfigBuilder.withAccessToken(accessToken)
            .environment("production")
            .build();
  }
}

