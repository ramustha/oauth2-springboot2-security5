package com.ramusthastudio.authserver.service;

import com.ramusthastudio.authserver.domain.User;
import com.ramusthastudio.authserver.repo.UserRepository;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository aUserRepository) {
    userRepository = aUserRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String aUsername) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(aUsername);

    if (user == null) {
      throw new BadCredentialsException(aUsername + " no found.");
    }

    new AccountStatusUserDetailsChecker().check(user);
    return user;
  }
}
