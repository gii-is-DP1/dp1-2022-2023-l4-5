package org.springframework.samples.petclinic.card.hero;

import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.petclinic.card.market.Market;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeroeRepository {

    Heroe findById(Integer id);
    List<Heroe> findAllHeroCard();
    @Query("SELECT h FROM Heroe h WHERE h.name=:name")
    List<Heroe> findAllHeroCardByName(String name);
}
