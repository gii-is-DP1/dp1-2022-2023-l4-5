package org.springframework.samples.petclinic.card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Optional<Card> getHeroeById(Integer id){return cardRepository.findById(id); }
    public List<Card> getAllHeroeCards(){ return cardRepository.findAllCards(); }
    public Card getAllHeroeCardsByName(String name){ return cardRepository.findCardByName(name); }

}

