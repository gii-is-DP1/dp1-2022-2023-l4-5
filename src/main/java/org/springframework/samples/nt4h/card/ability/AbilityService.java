package org.springframework.samples.nt4h.card.ability;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.RemoveCardFromHandToDiscard;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.EnoughCardsException;
import org.springframework.samples.nt4h.turn.exceptions.NoMoneyException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AbilityService {

    private AbilityRepository abilityRepository;
    private AbilityInGameRepository abilityInGameRepository;

    @Transactional(readOnly = true)
    public Ability getAbilityById(Integer abilityId) {
        return abilityRepository.findById(abilityId).orElseThrow(() -> new NotFoundException("Ability not found"));
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
    public void deleteAbilityById(Integer abilityId) {
        abilityRepository.deleteById(abilityId);
    }

    @Transactional(readOnly = true)
    public boolean abilityExists(int abilityId) {
        return abilityRepository.existsById(abilityId);
    }

    // AbilityInGame
    @Transactional(readOnly = true)
    public AbilityInGame getAbilityInGameById(Integer gameId) {
        return abilityInGameRepository.findById(gameId).orElseThrow(() -> new NotFoundException("AbilityInGame not found"));
    }

    @Transactional(readOnly = true)
    public List<AbilityInGame> getAllAbilityInGame() {
        return abilityInGameRepository.findAll();
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
    public void saveAllAbilityInGame(List<AbilityInGame> abilityInGame) {
        abilityInGameRepository.saveAll(abilityInGame);
    }

    @Transactional
    public void deleteAbilityInGame(AbilityInGame abilityInGame) {
        abilityInGameRepository.delete(abilityInGame);
    }

    @Transactional
    public void deleteAbilityInGameById(Integer gameId) {
        abilityInGameRepository.deleteById(gameId);
    }

    @Transactional
    public void deleteAllAbilityInGameByPlayer(Player player) {
        abilityInGameRepository.deleteAllByPlayer(player);
    }

    @Transactional
    public void deleteAllAbilityInGame(List<AbilityInGame> abilityInGame) {
        abilityInGameRepository.deleteAll(abilityInGame);
    }

    @Transactional(readOnly = true)
    public boolean abilityInGameExists(int gameId) {
        return abilityInGameRepository.existsById(gameId);
    }

    @Transactional
    public List<AbilityInGame> getAbilityCardsByPlayer(int playerId) {
        return abilityInGameRepository.findAbilitiesByPlayer(playerId);
    }

}
