package org.springframework.samples.petclinic.card.hero;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeroRepository extends CrudRepository<Hero, Integer> {

    Optional<Hero> findById(Integer id);

    List<Hero> findAll();

    Optional<Hero> findByName(String name);

    List<Hero> findByRole(Role role);

    List<Hero> findByHealth(Integer health);

}
