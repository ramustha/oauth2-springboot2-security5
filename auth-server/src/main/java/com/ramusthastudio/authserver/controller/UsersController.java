package com.ramusthastudio.authserver.controller;

import com.ramusthastudio.authserver.domain.Roles;
import com.ramusthastudio.authserver.domain.User;
import com.ramusthastudio.authserver.repo.RolesRepository;
import com.ramusthastudio.authserver.repo.UserRepository;
import java.security.Principal;
import java.util.Optional;
import org.apache.commons.compress.utils.Lists;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class UsersController {
  private final UserRepository userRepository;
  private final RolesRepository rolesRepository;

  public UsersController(UserRepository aUserRepository, RolesRepository aRolesRepository) {
    userRepository = aUserRepository;
    rolesRepository = aRolesRepository;
  }

  @GetMapping("/user")
  public String index(Model aModel, Principal aPrincipal) {
    aModel.addAttribute("users", userRepository.findAllWithout(aPrincipal.getName()));
    return "user";
  }

  @GetMapping("/user/update")
  public String updateUser(Model aModel, @RequestParam("id") String aId) {
    Optional<User> byId = userRepository.findById(aId);
    if (byId.isPresent()) {
      aModel.addAttribute("user", byId.get());
      aModel.addAttribute("allRoles", rolesRepository.findAllWithoutSystem());
    } else {
      aModel.addAttribute("updateUserFailed", "User " + aId + " not found.");
    }
    return "updateuser";
  }

  @PostMapping("/user/update")
  public String updateUser(Model aModel, @ModelAttribute("user") User aUser) {
    Optional<User> byId = userRepository.findById(aUser.getId());
    if (byId.isPresent()) {
      User user = byId.get();
      user.setEnabled(aUser.isEnabled());
      user.setAccountNonExpired(aUser.isAccountNonExpired());
      user.setCredentialsNonExpired(aUser.isCredentialsNonExpired());
      user.setAccountNonLocked(aUser.isAccountNonLocked());

      Iterable<Roles> roles = rolesRepository.saveAll(aUser.getRoles());
      user.setRoles(Lists.newArrayList(roles.iterator()));

      User save = userRepository.save(user);
      if (save != null) {
        aModel.addAttribute("updateUserSuccess", "Successfully update " + aUser.getId() + ".");
        return "redirect:/user";
      }
    }

    aModel.addAttribute("updateUserFailed", "User " + aUser.getId() + " not found.");
    return "redirect:/user/update";
  }
}
