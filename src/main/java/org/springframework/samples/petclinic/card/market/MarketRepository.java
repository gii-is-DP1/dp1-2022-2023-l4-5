package org.springframework.samples.petclinic.card.market;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarketRepository extends CrudRepository<Market, Integer> {

    Optional<Market> findById(Integer id);
    List<Market> findAllMarketCard();

    @Query("SELECT m FROM Market m WHERE m.name=:name")
    List<Market> findAllMarketCardByName(String name);





}
