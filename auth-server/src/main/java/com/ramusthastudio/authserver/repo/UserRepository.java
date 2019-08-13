package com.ramusthastudio.authserver.repo;

import com.ramusthastudio.authserver.domain.Users;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<Users, String> {
  Users findByUsername(String aUsername);
  Users findByEmail(String aEmail);
}
