package com.ramusthastudio.authserver.config;

import java.util.LinkedHashMap;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Legacy Authorization Server does not support a custom name for the user parameter, so we'll need
 * to extend the default. By default, it uses the attribute {@code user_name}, though it would be
 * better to adhere to the {@code sub} property defined in the
 * <a target="_blank" href="https://tools.ietf.org/html/rfc7519">JWT Specification</a>.
 */
public class CustomAttributeUserTokenConverter extends JwtAccessTokenConverter {
  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken aAccessToken, OAuth2Authentication aAuthentication) {
    LinkedHashMap<String, Object> info = new LinkedHashMap<>(aAccessToken.getAdditionalInformation());
    info.put("sub", aAuthentication.getName());

    DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(aAccessToken);
    accessToken.setAdditionalInformation(info);

    return super.enhance(accessToken, aAuthentication);
  }
}
