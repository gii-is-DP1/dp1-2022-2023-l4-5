package org.springframework.samples.nt4h.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.nt4h.player.Tier;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAll();

    Optional<User> findByUsername(String username);

    List<User> findByAuthority(Authority authority);

    List<User> findByTier(Tier tier);

    Optional<User> findById(int id);

}
