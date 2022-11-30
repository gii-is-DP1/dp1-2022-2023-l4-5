package org.springframework.samples.nt4h.player;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final AbilityService abilityService;
    private final TurnService turnService;

    @Transactional(readOnly = true)
    public Player getPlayerById(int id) {
        return playerRepository.findById(id).orElseThrow(() -> new NotFoundException("Player not found"));
    }

    @Transactional
    public void savePlayer(Player player) {
        playerRepository.save(player);
        turnService.createAllTurnForAPlayer(player);
    }

    @Transactional
    public void deletePlayerById(int id) {
        playerRepository.deleteById(id);
    }

    @Transactional
    public void deletePlayer(Player player) {
        playerRepository.delete(player);
    }

    @Transactional(readOnly = true)
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Player getPlayerByName(String name) {
        return playerRepository.findByName(name).orElseThrow(() -> new NotFoundException("Player not found"));
    }

    @Transactional(readOnly = true)
    public boolean playerExists(int id) {
        return playerRepository.existsById(id);
    }

    @Transactional
    public void addDeckFromRole(Player player, Mode mode) {
        Role[] roles = player.getHeroes().stream().map(h -> h.getHero().getRole()).distinct().toArray(Role[]::new);
        if (roles.length == 1 && mode == Mode.UNI_CLASS)
            createDeck(player, roles[0], roles[0].getAbilities().size() - 1);
        else if (roles.length == 2 && mode == Mode.MULTI_CLASS) {
            createDeck(player, roles[0], 8);
            createDeck(player, roles[1], 7);
        }
    }

    private void createDeck(Player player, Role role, Integer limit) {
        List<Ability> abilities = abilityService.getAbilitiesByRole(role);
        List<Ability> totalAbilities = Lists.newArrayList();
        for (var ability : abilities)
            for (int i = 0; i < ability.getQuantity(); i++)
                totalAbilities.add(ability);
        Collections.shuffle(totalAbilities);
        for (int i = 0; i < limit; i++) {
            Ability ability = totalAbilities.get(i);
            AbilityInGame abilityInGame = AbilityInGame.builder()
                .player(player).ability(ability).timesUsed(0).attack(ability.getAttack()).isProduct(false).build();
            abilityService.saveAbilityInGame(abilityInGame);
            player.addAbilityInDeck(abilityInGame);
        }
    }

}
