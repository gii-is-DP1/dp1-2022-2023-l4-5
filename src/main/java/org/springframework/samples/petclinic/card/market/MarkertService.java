package org.springframework.samples.petclinic.card.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.hero.Heroe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarkertService {

    @Autowired
    private MarketRepository marketRepository;


    public Optional<Market> getMarketById(Integer id){return marketRepository.findById(id); }
    public List<Market> getAllMarketCards(){ return marketRepository.findAllMarketCard(); }
    public List<Market> getAllMarketCardsByName(String name){ return marketRepository.findAllMarketCardByName(name); }

}
