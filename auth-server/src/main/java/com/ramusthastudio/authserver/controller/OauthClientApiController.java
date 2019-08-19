package com.ramusthastudio.authserver.controller;

import com.ramusthastudio.authserver.domain.OauthClientDetails;
import com.ramusthastudio.authserver.domain.User;
import com.ramusthastudio.authserver.publicapi.OauthClientDetailsPublic;
import com.ramusthastudio.authserver.repo.OauthClientDetailsRepository;
import com.ramusthastudio.authserver.repo.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OauthClientApiController {
  private final UserRepository userRepository;
  private final OauthClientDetailsRepository oauthClientDetailsRepository;
  private final PasswordEncoder passwordEncoder;

  public OauthClientApiController(
      UserRepository aUserRepository,
      OauthClientDetailsRepository aOauthClientDetailsRepository,
      PasswordEncoder aPasswordEncoder) {
    userRepository = aUserRepository;
    oauthClientDetailsRepository = aOauthClientDetailsRepository;
    passwordEncoder = aPasswordEncoder;
  }

  @GetMapping("/clientapi")
  public String clientApi(Model aModel, UsernamePasswordAuthenticationToken aAuthentication) {
    User user = (User) aAuthentication.getPrincipal();

    List<OauthClientDetailsPublic> oauthClientDetailsList = new ArrayList<>();
    user.getOauthClientDetails().forEach(aData -> oauthClientDetailsList.add(new OauthClientDetailsPublic(aData)));

    aModel.addAttribute("oauthClientDetailsList", oauthClientDetailsList);
    return "clientapi";
  }

  @GetMapping("/createclientapi")
  public String createClientApiForm(Model aModel, UsernamePasswordAuthenticationToken aAuthentication) {
    User user = (User) aAuthentication.getPrincipal();

    String generatedSecret = RandomStringUtils.randomAlphabetic(36);
    // String encodeSecret = passwordEncoder.encode(generatedSecret);

    OauthClientDetails newClientApi = new OauthClientDetails();
    newClientApi.setClientId(RandomStringUtils.randomAlphabetic(36) + "_" + user.getUsername());
    newClientApi.setClientSecret(generatedSecret.replace("{noop}", ""));
    newClientApi.setScope("profile,source_read,synd_read");

    aModel.addAttribute("newClientApi", newClientApi);
    return "createclientapi";
  }

  @PostMapping("/createclientapi")
  public String createClientApiSubmit(Model aModel, UsernamePasswordAuthenticationToken aAuthentication,
      @ModelAttribute("newClientApi") OauthClientDetails aNewClientApi) {

    aNewClientApi.setClientSecret("{noop}" + aNewClientApi.getClientSecret());
    aNewClientApi.setResourceIds("syndResource");
    aNewClientApi.setAuthorizedGrantTypes("password,authorization_code,refresh_token,implicit");
    aNewClientApi.setAccessTokenValidity(900);
    aNewClientApi.setRefreshTokenValidity(3600);
    aNewClientApi.setAdditionalInformation("{}");

    OauthClientDetails savedOauthClientDetail = oauthClientDetailsRepository.save(aNewClientApi);
    if (savedOauthClientDetail != null) {
      User user = (User) aAuthentication.getPrincipal();
      List<OauthClientDetails> oauthClientDetails = user.getOauthClientDetails();
      oauthClientDetails.add(aNewClientApi);

      User savedUser = userRepository.save(user);
      if (savedUser != null) {
        aModel.addAttribute("createClientSuccess", "Successfully registered " + aNewClientApi.getClientId() + ".");
        return "redirect:clientapi";
      }
    }

    aModel.addAttribute("createClientFailed", "Failed registered.");
    return "redirect:createclientapi";
  }

  @GetMapping("/updateclientapi")
  public String updateClientApiForm(Model aModel, @RequestParam("id") String aId, UsernamePasswordAuthenticationToken aAuthentication) {
    Optional<OauthClientDetails> byId = oauthClientDetailsRepository.findById(aId);
    if (byId.isPresent()) {
      OauthClientDetails oauthClientDetails = byId.get();
      aModel.addAttribute("clientApi", new OauthClientDetailsPublic(oauthClientDetails));
    } else {
      aModel.addAttribute("updateClientFailed", "Client " + aId + " not found.");
    }
    return "updateclientapi";
  }

  @PostMapping("/updateclientapi")
  public String updateClientApiSubmit(Model aModel, UsernamePasswordAuthenticationToken aAuthentication,
      @ModelAttribute("clientApi") OauthClientDetails aNewClientApi) {
    User user = (User) aAuthentication.getPrincipal();
    List<OauthClientDetails> oauthClientDetails = user.getOauthClientDetails();

    OauthClientDetails currentClient = oauthClientDetailsRepository.findByClientId(aNewClientApi.getClientId());
    int currentIndex = oauthClientDetails.indexOf(currentClient);
    currentClient.setWebServerRedirectUri(aNewClientApi.getWebServerRedirectUri());

    OauthClientDetails savedOauthClientDetail = oauthClientDetailsRepository.save(currentClient);
    if (savedOauthClientDetail != null) {
      oauthClientDetails.set(currentIndex, savedOauthClientDetail);

      User savedUser = userRepository.save(user);
      if (savedUser != null) {
        aModel.addAttribute("createClientSuccess", "Successfully update " + aNewClientApi.getClientId() + ".");
        return "redirect:clientapi";
      }
    }

    aModel.addAttribute("createClientFailed", "Failed update.");
    return "redirect:updateclientapi";
  }
}
