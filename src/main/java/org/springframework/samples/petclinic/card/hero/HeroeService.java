package org.springframework.samples.petclinic.card.hero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.card.ability.Ability;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeroeService {

    @Autowired
    private HeroeRepository heroeRepository;

    public Heroe getHeroeById(Integer id){return heroeRepository.findById(id); }
    public List<Heroe> getAllHeroeCards(){ return heroeRepository.findAllHeroCard(); }
    public List<Heroe> getAllHeroeCardsByName(String name){ return heroeRepository.findAllHeroCardByName(name); }

}
