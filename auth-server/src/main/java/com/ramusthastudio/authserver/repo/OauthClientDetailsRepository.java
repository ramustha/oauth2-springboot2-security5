package com.ramusthastudio.authserver.repo;

import com.ramusthastudio.authserver.domain.OauthClientDetails;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface OauthClientDetailsRepository extends PagingAndSortingRepository<OauthClientDetails, String> {
  OauthClientDetails findByClientId(String aClientId);
}
