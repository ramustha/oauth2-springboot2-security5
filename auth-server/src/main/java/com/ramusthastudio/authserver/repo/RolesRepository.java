package com.ramusthastudio.authserver.repo;

import com.ramusthastudio.authserver.domain.Roles;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface RolesRepository extends PagingAndSortingRepository<Roles, String> {
  @Query("select r from Roles r where r.name not like 'ROLE_ADMIN'")
  // @Query("select r from Roles r")
  List<Roles> findByNameNotLikeAdmin();
}
