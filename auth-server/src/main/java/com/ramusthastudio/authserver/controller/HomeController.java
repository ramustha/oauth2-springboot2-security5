package com.ramusthastudio.authserver.controller;

import com.ramusthastudio.authserver.domain.Password;
import com.ramusthastudio.authserver.domain.User;
import com.ramusthastudio.authserver.repo.PaswordRepository;
import com.ramusthastudio.authserver.repo.PermissionsRepository;
import com.ramusthastudio.authserver.repo.RolesRepository;
import com.ramusthastudio.authserver.repo.UserRepository;
import java.security.Principal;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final PaswordRepository paswordRepository;
  private final RolesRepository rolesRepository;
  private final PermissionsRepository permissionsRepository;

  public HomeController(PasswordEncoder aPasswordEncoder,
      UserRepository aUserRepository,
      PaswordRepository aPaswordRepository,
      RolesRepository aRolesRepository,
      PermissionsRepository aPermissionsRepository) {
    passwordEncoder = aPasswordEncoder;
    userRepository = aUserRepository;
    paswordRepository = aPaswordRepository;
    rolesRepository = aRolesRepository;
    permissionsRepository = aPermissionsRepository;
  }

  @GetMapping("/")
  public String index(Model aModel, Principal aPrincipal) {
    if (aPrincipal instanceof UsernamePasswordAuthenticationToken) {
      UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) aPrincipal;
      User user = (User) authenticationToken.getPrincipal();

      aModel.addAttribute("user", user);
    }
    return "index";
  }

  @GetMapping("/register")
  public String register(Model aModel) {
    aModel.addAttribute("newUser", new User());
    return "register";
  }

  @PostMapping("/register")
  public String register(Model aModel, @ModelAttribute("newUser") User aNewUser) {
    String encodePassword = passwordEncoder.encode(aNewUser.getCurrentPassword());
    Password userPassword = paswordRepository.save(Password.builder().currentPassword(encodePassword).build());

    User newUser = User.builder()
        .username(aNewUser.getUsername())
        .email(aNewUser.getEmail())
        .enabled(true)
        .isAccountNonExpired(true)
        .isCredentialsNonExpired(true)
        .isAccountNonLocked(true)
        .roles(Collections.singletonList(rolesRepository.findByName("ROLE_USER")))
        .password(userPassword)
        .build();

    User user = userRepository.save(newUser);
    if (user != null) {
      aModel.addAttribute("registerSuccess", "Successfully register " + user.getUsername() + ".");
      return "redirect:login";
    }

    aModel.addAttribute("registerFailed", "Failed register " + aNewUser.getUsername() + ".");
    return "redirect:register";
  }
}
