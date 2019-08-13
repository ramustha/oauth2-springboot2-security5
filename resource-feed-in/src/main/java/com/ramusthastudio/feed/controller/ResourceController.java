package com.ramusthastudio.feed.controller;

import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

  @GetMapping("/")
  public Jwt index(@AuthenticationPrincipal Jwt aJwt) {
    return aJwt;
  }

  @GetMapping("/principal")
  public Principal getPrincipal(Principal aPrincipal) {
    return aPrincipal;
  }

  @GetMapping("/authentication")
  public Authentication getAuthentication(Authentication aAuthentication) {
    return aAuthentication;
  }

  @GetMapping("/news/all")
  public String allNews() {
    return "User @PreAuthorize(\"hasAuthority('SCOPE_SYND_READ')\")";
  }

  @GetMapping("/source/all")
  public String allSource() {
    return "User @PreAuthorize(\"hasAuthority('SCOPE_SOURCE_READ')\")";
  }
}
