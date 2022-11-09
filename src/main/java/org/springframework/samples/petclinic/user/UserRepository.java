package org.springframework.samples.petclinic.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.player.Tier;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAll();

    User findByUsername(String username);

    List<User> findByAuthority(String authority);

    List<User> findByTier(Tier tier);

    User findById(int id);

}
