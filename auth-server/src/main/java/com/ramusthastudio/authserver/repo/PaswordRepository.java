package com.ramusthastudio.authserver.repo;

import com.ramusthastudio.authserver.domain.Password;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface PaswordRepository extends PagingAndSortingRepository<Password, String> {
}
