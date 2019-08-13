package com.ramusthastudio.feed.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;

class InformativeAuthEntryPoint implements AuthenticationEntryPoint {
  private final BearerTokenAuthenticationEntryPoint delegate =
      new BearerTokenAuthenticationEntryPoint();

  private final ObjectMapper mapper = new ObjectMapper()
      .setSerializationInclusion(JsonInclude.Include.NON_NULL);

  @Override
  public void commence(HttpServletRequest aRequest, HttpServletResponse aResponse, AuthenticationException aReason) throws IOException, ServletException {
    delegate.commence(aRequest, aResponse, aReason);

    if (aReason.getCause() instanceof JwtValidationException) {
      JwtValidationException exception = (JwtValidationException) aReason.getCause();

      Collection<OAuth2Error> errors = exception.getErrors();
      mapper.writeValue(aResponse.getWriter(), errors);
    }
  }
}
