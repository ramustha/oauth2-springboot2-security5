package com.ramusthastudio.authserver.publicapi;

import com.ramusthastudio.authserver.domain.OauthClientDetails;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class OauthClientDetailsPublic extends OauthClientDetails {
  private final String id;
  private final String clientId;
  private final String clientSecret;
  private final String resourceIds;
  private final String scope;
  private final String authorizedGrantTypes;
  private final String webServerRedirectUri;
  private final String authorities;
  private final LocalDateTime accessTokenValidityDate;
  private final LocalDateTime refreshTokenValidityDate;
  private final String additionalInformation;
  private final boolean autoapprove;

  public OauthClientDetailsPublic(OauthClientDetails aOauthClientDetails) {
    id = aOauthClientDetails.getId();
    clientId = aOauthClientDetails.getClientId();
    String currentClientSecret = aOauthClientDetails.getClientSecret();
    clientSecret = currentClientSecret.substring(currentClientSecret.indexOf('}') + 1); // remove {noop}
    resourceIds = aOauthClientDetails.getResourceIds();
    scope = aOauthClientDetails.getScope();
    authorizedGrantTypes = aOauthClientDetails.getAuthorizedGrantTypes();
    webServerRedirectUri = aOauthClientDetails.getWebServerRedirectUri();
    authorities = aOauthClientDetails.getAuthorities();
    accessTokenValidityDate = LocalDateTime.now().plusSeconds(aOauthClientDetails.getAccessTokenValidity());
    refreshTokenValidityDate = LocalDateTime.now().plusSeconds(aOauthClientDetails.getRefreshTokenValidity());
    additionalInformation = aOauthClientDetails.getAdditionalInformation();
    autoapprove = aOauthClientDetails.isAutoapprove();
  }
}
