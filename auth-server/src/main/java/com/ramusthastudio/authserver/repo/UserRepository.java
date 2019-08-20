package com.ramusthastudio.authserver.repo;

import com.ramusthastudio.authserver.domain.User;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
  User findByUsername(String aUsername);
  User findByEmail(String aEmail);

  @Query("select u from User u where u.username != ?1 and u.username != 'system'")
  List<User> findAllWithout(String aUsername);
}
