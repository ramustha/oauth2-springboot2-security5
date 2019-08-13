package com.ramusthastudio.authserver.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.ramusthastudio.authserver.service.UserDetailsServiceImpl;
import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Configuration
@Import({
    AuthorizationServerConfig.JwkSetEndpoint.class,
    AuthorizationServerConfig.JwkSetEndpointConfiguration.class,
})
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  private final DataSource dataSource;
  private final UserDetailsService userDetailsService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final KeyPair keyPair;

  public AuthorizationServerConfig(
      DataSource aDataSource,
      UserDetailsServiceImpl aUserDetailsService,
      AuthenticationManager aAuthenticationManager,
      PasswordEncoder aPasswordEncoder,
      KeyPair aKeyPair) {
    dataSource = aDataSource;
    userDetailsService = aUserDetailsService;
    authenticationManager = aAuthenticationManager;
    passwordEncoder = aPasswordEncoder;
    keyPair = aKeyPair;
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new SubjectAttributeUserTokenConverter();
    converter.setKeyPair(keyPair);
    return converter;
  }

  @Bean
  public TokenStore tokenStore() {
    return new JdbcTokenStore(dataSource);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer aSecurity) throws Exception {
    // this configuration for expose public key on oauth/token_key
    aSecurity.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer aEndpoints) throws Exception {
    aEndpoints
        .authenticationManager(authenticationManager)
        .accessTokenConverter(accessTokenConverter())
        .tokenStore(tokenStore());
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer aClients) throws Exception {
    aClients.jdbc(dataSource).passwordEncoder(passwordEncoder);
  }

  /**
   * Legacy Authorization Server (spring-security-oauth2) does not support any
   * <a href target="_blank" href="https://tools.ietf.org/html/rfc7517#section-5">JWK Set</a> endpoint.
   **/
  @FrameworkEndpoint
  static class JwkSetEndpoint {
    KeyPair keyPair;

    public JwkSetEndpoint(KeyPair aKeyPair) {
      keyPair = aKeyPair;
    }

    @GetMapping("/.well-known/jwks.json")
    @ResponseBody
    public Map<String, Object> getKey(Principal aPrincipal) {
      RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
      RSAKey key = new RSAKey.Builder(publicKey).build();
      return new JWKSet(key).toJSONObject();
    }
  }

  @Import(AuthorizationServerEndpointsConfiguration.class)
  @Configuration
  static class JwkSetEndpointConfiguration extends AuthorizationServerSecurityConfiguration {
    @Override
    protected void configure(HttpSecurity aHttp) throws Exception {
      super.configure(aHttp);
      aHttp
          .requestMatchers()
          .mvcMatchers("/.well-known/jwks.json")
          .and()
          .authorizeRequests()
          .mvcMatchers("/.well-known/jwks.json").permitAll();
    }
  }
}
