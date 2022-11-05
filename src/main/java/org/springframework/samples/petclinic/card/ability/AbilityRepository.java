package org.springframework.samples.petclinic.card.ability;

import org.springframework.data.jpa.repository.Query;
import org.springframework.samples.petclinic.card.market.Market;
import org.springframework.samples.petclinic.card.market.MarketRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AbilityRepository {

    Ability findById(Integer id);
    List<Ability> findAllAbilityCard();
    @Query("SELECT a FROM Ability a WHERE a.name=:name")
    List<Ability> findAllAbilityCardByName(String name);
}
