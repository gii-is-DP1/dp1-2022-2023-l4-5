package org.springframework.samples.petclinic.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.player.Tier;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(String username);

    // @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
    // User findByUsernameAndPassword(String username, String password);

    @Query("SELECT u FROM User u WHERE u.authority = :authority")
    List<User> findByAuthority(String authority);

    List<User> findByTier(Tier tier);


}
