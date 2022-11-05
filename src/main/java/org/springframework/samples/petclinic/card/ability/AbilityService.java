package org.springframework.samples.petclinic.card.ability;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbilityService {

    private AbilityRepository abilityRepository;

    public Ability getAbilityById(Integer id){return abilityRepository.findById(id); }
    public List<Ability> getAllAbilityCards(){ return abilityRepository.findAllAbilityCard(); }
    public List<Ability> getAllAbilityCardsByName(String name){ return abilityRepository.findAllAbilityCardByName(name); }

}
