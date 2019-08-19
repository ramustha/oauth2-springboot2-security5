package com.ramusthastudio.authserver.controller;

import java.security.Principal;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {

  // this provide for oauth client
  @GetMapping("/oauth/userinfo")
  public Object oauthUserinfo(Principal aPrincipal) {
    if (aPrincipal instanceof JwtAuthenticationToken) {
      JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) aPrincipal;
      return jwtAuthenticationToken.getTokenAttributes();
    }
    return aPrincipal;
  }

  // TESTING ONLY

  @GetMapping("/news/all")
  @PreAuthorize("hasAuthority('synd_read')")
  public String allNews() {
    return "User @PreAuthorize(\"hasAuthority('SYND_READ')\")";
  }

  @GetMapping("/source/all")
  @PostAuthorize("hasAuthority('source_read')")
  public String allSources() {
    return "User @PostAuthorize(\"hasAuthority('SOURCE_READ')\")";
  }

  @GetMapping("/news/delete")
  @PreAuthorize("hasAuthority('synd_delete')")
  public String deleteAllNews() {
    return "User @PreAuthorize(\"hasAuthority('SYND_DELETE')\")";
  }

  @GetMapping("/source/delete")
  @PostAuthorize("hasAuthority('source_delete')")
  public String deleteAllSources() {
    return "User @PostAuthorize(\"hasAuthority('SOURCE_DELETE')\")";
  }

}
