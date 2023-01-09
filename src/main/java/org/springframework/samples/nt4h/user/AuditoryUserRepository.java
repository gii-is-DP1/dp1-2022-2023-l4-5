package org.springframework.samples.nt4h.user;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AuditoryUserRepository extends CrudRepository<AuditoryUser, Integer> {

    List<AuditoryUser> findAll();
  //  Optional<AuditoryUser> findByUsername(String username);

}

