package com.ramusthastudio.authserver;

import com.ramusthastudio.authserver.config.KeyStoreKeyFactory;
import java.security.KeyPair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class AuthServerApplication {
  @Value("classpath:/${keystore.path}")
  private Resource keystoreFile;
  @Value("${keystore.password}")
  private String keystorePassword;
  @Value("${keystore.alias}")
  private String keypairAlias;

  @Bean
  public KeyPair keyPair() {
    return new KeyStoreKeyFactory(keystoreFile, keystorePassword.toCharArray())
        .getKeyPair(keypairAlias);
  }

  public static void main(String[] args) {
    SpringApplication.run(AuthServerApplication.class, args);
  }
}
