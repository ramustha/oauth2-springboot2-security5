package com.ramusthastudio.feed.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.BearerTokenError;

class AudienceValidator implements OAuth2TokenValidator<Jwt> {
  private static final BearerTokenError MISSING_AUDIENCE =
      new BearerTokenError("invalid_token", HttpStatus.UNAUTHORIZED,
          "The Audience required is missing", null);

  @Override
  public OAuth2TokenValidatorResult validate(Jwt aToken) {
    if (aToken.getAudience().contains("syndResource")) {
      return OAuth2TokenValidatorResult.success();
    }

    return OAuth2TokenValidatorResult.failure(MISSING_AUDIENCE);
  }
}
