package com.ramusthastudio.feed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;

@SpringBootApplication
public class ClientFeedApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClientFeedApplication.class, args);
  }

  @Bean
  public StringHttpMessageConverter stringHttpMessageConverter(HttpProperties httpProperties) {
    System.out.println(httpProperties);
    StringHttpMessageConverter converter = new StringHttpMessageConverter(httpProperties.getEncoding().getCharset());
    converter.setWriteAcceptCharset(false);
    return converter;
  }
}
