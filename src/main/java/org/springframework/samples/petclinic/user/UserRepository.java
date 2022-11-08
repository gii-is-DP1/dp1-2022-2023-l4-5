package org.springframework.samples.petclinic.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.player.Tier;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAll();

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.authority = :authority")
    List<User> findByAuthority(String authority);

    List<User> findByTier(Tier tier);

    @Query("SELECT u.friends FROM User u")
    List<User> findFriends();

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User findById(int id);

}
