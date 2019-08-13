package com.ramusthastudio.authserver.config;

import com.ramusthastudio.authserver.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.config.Customizer.withDefaults;

// read this https://www.baeldung.com/spring-security-method-security

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserDetailsService userDetailsService;

  public WebSecurityConfig(UserDetailsServiceImpl aUserDetailsService) {
    userDetailsService = aUserDetailsService;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  @Override
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder aAuth) throws Exception {
    aAuth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity aHttp) throws Exception {
    // @formatter:off
		aHttp
			.authorizeRequests(authorizeRequests ->
				authorizeRequests
					.antMatchers(HttpMethod.GET, "/oauth/userinfo").hasAuthority("SCOPE_profile")
					.anyRequest().authenticated()
			)
       .formLogin()
       .permitAll()
      .and()
			  .oauth2ResourceServer()
				  .jwt(withDefaults());
		// @formatter:on
  }
}
