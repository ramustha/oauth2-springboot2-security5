package com.ramusthastudio.authserver.service;

import com.ramusthastudio.authserver.domain.Users;
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
    Users users = userRepository.findByUsername(aUsername);

    if (users == null) {
      throw new BadCredentialsException(aUsername + " no found.");
    }

    new AccountStatusUserDetailsChecker().check(users);
    return users;
  }
}
