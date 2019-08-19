package com.ramusthastudio.authserver.controller;

import com.ramusthastudio.authserver.repo.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {
  private final UserRepository userRepository;

  public UsersController(UserRepository aUserRepository) {
    userRepository = aUserRepository;
  }

  @GetMapping("/users")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
  public String index(Model aModel) {
    aModel.addAttribute("users", userRepository.findAll());
    return "users";
  }
}
