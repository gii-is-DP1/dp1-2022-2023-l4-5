package org.springframework.samples.nt4h.card.ability;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AbilityService {

    private AbilityRepository abilityRepository;
    private AbilityInGameRepository abilityInGameRepository;

    @Transactional(readOnly = true)
    public List<Ability> getAllAbilities() {
        return abilityRepository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = NotFoundException.class)
    public Ability getAbilityByName(String name) {
        return abilityRepository.findByName(name).orElseThrow(() -> new NotFoundException("Ability not found"));
    }

    // AbilityInGame
    @Transactional(readOnly = true, rollbackFor = NotFoundException.class)
    public AbilityInGame getAbilityInGameById(Integer id) {
        return abilityInGameRepository.findById(id).orElseThrow(() -> new NotFoundException("AbilityInGame not found"));
    }

    @Transactional(readOnly = true)
    public List<Ability> getAbilitiesByRole(Role role) {
        return abilityRepository.findAllByIds(role.getAbilities());
    }

    @Transactional
    public void saveAbilityInGame(AbilityInGame abilityInGame) {
        abilityInGameRepository.save(abilityInGame);
    }


    @Transactional
    public void deleteAbilityInGame(AbilityInGame abilityInGame) {
        abilityInGame.onDeleteSetNull();
        abilityInGameRepository.save(abilityInGame);
        abilityInGameRepository.delete(abilityInGame);
    }

}
