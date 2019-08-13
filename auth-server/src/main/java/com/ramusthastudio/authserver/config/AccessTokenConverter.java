package com.ramusthastudio.authserver.config;

import com.ramusthastudio.authserver.domain.Users;
import java.util.LinkedHashMap;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class AccessTokenConverter extends JwtAccessTokenConverter {
  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken aAccessToken, OAuth2Authentication aAuthentication) {
    Users user = (Users) aAuthentication.getPrincipal();

    LinkedHashMap<String, Object> info = new LinkedHashMap<>(aAccessToken.getAdditionalInformation());
    info.put("email", user.getEmail());

    DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(aAccessToken);
    accessToken.setAdditionalInformation(info);

    return super.enhance(accessToken, aAuthentication);
  }
}
