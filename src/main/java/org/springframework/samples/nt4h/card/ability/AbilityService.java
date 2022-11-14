package org.springframework.samples.nt4h.card.ability;

import lombok.AllArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AbilityService {

    private AbilityRepository abilityRepository;
    private AbilityInGameRepository abilityInGameRepository;

    @Transactional(readOnly = true)
    public Ability getAbilityById(Integer id) {
        return abilityRepository.findById(id).orElseThrow(() -> new NotFoundException("Ability not found"));
    }

    @Transactional(readOnly = true)
    public List<Ability> getAllAbilities() {
        return abilityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Ability getAbilityByName(String name) {
        return abilityRepository.findByName(name).orElseThrow(() -> new NotFoundException("Ability not found"));
    }

    @Transactional
    public void saveAbility(Ability ability) {
        abilityRepository.save(ability);
    }

    @Transactional
    public void deleteAbility(Ability ability) {
        abilityRepository.delete(ability);
    }

    @Transactional
    public void deleteAbilityById(Integer id) {
        abilityRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean abilityExists(int id) {
        return abilityRepository.existsById(id);
    }

    // AbilityInGame
    @Transactional(readOnly = true)
    public AbilityInGame getAbilityInGameById(Integer id) {
        return abilityInGameRepository.findById(id).orElseThrow(() -> new NotFoundException("AbilityInGame not found"));
    }

    @Transactional(readOnly = true)
    public List<AbilityInGame> getAllAbilityInGame() {
        return abilityInGameRepository.findAll();
    }

    @Transactional
    public void saveAbilityInGame(AbilityInGame abilityInGame) {
        abilityInGameRepository.save(abilityInGame);
    }

    @Transactional
    public void deleteAbilityInGame(AbilityInGame abilityInGame) {
        abilityInGameRepository.delete(abilityInGame);
    }

    @Transactional
    public void deleteAbilityInGameById(Integer id) {
        abilityInGameRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean abilityInGameExists(int id) {
        return abilityInGameRepository.existsById(id);
    }

}