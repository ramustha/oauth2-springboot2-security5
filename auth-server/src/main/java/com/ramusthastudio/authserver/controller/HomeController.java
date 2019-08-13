package com.ramusthastudio.authserver.controller;

import java.security.Principal;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  @GetMapping("/")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public Principal index(Principal aPrincipal) {
    return aPrincipal;
  }

  @GetMapping("/authentication")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public Authentication getAuthentication(Authentication aAuthentication) {
    return aAuthentication;
  }

  @GetMapping("/api/news/all")
  @PreAuthorize("hasAuthority('SYND_READ')")
  public String allNews() {
    return "User @PreAuthorize(\"hasAuthority('SYND_READ')\")";
  }

  @GetMapping("/api/news/delete")
  @PostAuthorize("hasAuthority('SYND_DELETE')")
  public String deleteNews() {
    return "User @PostAuthorize(\"hasAuthority('SYND_DELETE')\")";
  }
}
