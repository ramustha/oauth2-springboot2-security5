package com.ramusthastudio.feed.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Controller
public class NewsController {
  private final WebClient webClient;
  private final String uri;

  public NewsController(WebClient aWebClient, @Value("${resource-uri}") String aUri) {
    webClient = aWebClient;
    uri = aUri;
  }

  @GetMapping("/news")
  public String apiNews(Model aModel, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient aAuthorizedClient) {
    String attribute = webClient
        .get()
        .uri(uri + "/api/news/all")
        .attributes(oauth2AuthorizedClient(aAuthorizedClient))
        .retrieve()
        .bodyToMono(String.class)
        .block();
    aModel.addAttribute("news", attribute);
    return "news";
  }

  @GetMapping("/sources")
  public String apiSources(Model aModel, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient aAuthorizedClient) {
    String attribute = webClient
        .get()
        .uri(uri + "/api/source/all")
        .attributes(oauth2AuthorizedClient(aAuthorizedClient))
        .retrieve()
        .bodyToMono(String.class)
        .block();
    aModel.addAttribute("sources", attribute);
    return "sources";
  }
}
