package com.ramusthastudio.feed.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

  @GetMapping("/api/news/all")
  public String allNews() {
    return "User @PreAuthorize(\"hasAuthority('SCOPE_SYND_READ')\")";
  }

  @GetMapping("/api/source/all")
  public String allSource() {
    return "User @PreAuthorize(\"hasAuthority('SCOPE_SOURCE_READ')\")";
  }
}
