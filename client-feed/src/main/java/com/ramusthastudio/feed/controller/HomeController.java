package com.ramusthastudio.feed.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Controller
public class HomeController {
  private final WebClient webClient;
  private final String uri;

  public HomeController(WebClient aWebClient, @Value("${resource-uri}") String aUri) {
    webClient = aWebClient;
    uri = aUri;
  }

  @GetMapping("/")
  public String index(Model aModel, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient aAuthorizedClient) {
    aModel.addAttribute("userName", aAuthorizedClient.getPrincipalName());
    aModel.addAttribute("clientName", aAuthorizedClient.getClientRegistration().getClientName());
    return "index";
  }

  @GetMapping("/userinfo")
  public String userinfo(Model aModel, @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient aAuthorizedClient) {
    String userInfoEndpointUri = aAuthorizedClient.getClientRegistration()
        .getProviderDetails().getUserInfoEndpoint().getUri();

    Map userAttributes = webClient
        .get()
        .uri(userInfoEndpointUri)
        .attributes(oauth2AuthorizedClient(aAuthorizedClient))
        .retrieve()
        .bodyToMono(Map.class)
        .block();
    aModel.addAttribute("userAttributes", userAttributes);
    return "userinfo";
  }
}
