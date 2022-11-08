package org.springframework.samples.petclinic.card.hero;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeroRepository extends CrudRepository<Hero, Integer> {
    @Query("SELECT h FROM Hero h WHERE h.id = :id")
    Optional<Hero> findById(Integer id);

    List<Hero> findAll();

    @Query("SELECT h FROM Hero h WHERE h.name = :name")
    Optional<Hero> findByName(String name);

    @Query("SELECT h FROM Hero h WHERE h.role = :role")
    List<Hero> findByRole(Role role);

    @Query("SELECT h FROM Hero h WHERE h.health = :health")
    List<Hero> findByHealth(Integer health);

}
