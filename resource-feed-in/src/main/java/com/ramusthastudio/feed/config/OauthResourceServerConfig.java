package com.ramusthastudio.feed.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class OauthResourceServerConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
		http
			.authorizeRequests(authorizeRequests ->
				authorizeRequests
					.antMatchers(HttpMethod.GET, "/news/**").hasAuthority("SCOPE_SYND_READ")
					.antMatchers(HttpMethod.GET, "/source/**").hasAuthority("SCOPE_SOURCE_READ")
					.anyRequest().authenticated()
			)
			.oauth2ResourceServer()
				.jwt()
					.jwtAuthenticationConverter(new JwtAuthenticationConverter());

		// @formatter:on
  }
}
