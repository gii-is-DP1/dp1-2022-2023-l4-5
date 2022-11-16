package org.springframework.samples.nt4h.player;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final AbilityService abilityService;

    @Transactional(readOnly = true)
    public Player getPlayerById(int id) {
        return playerRepository.findById(id).orElseThrow(() -> new NotFoundException("Player not found"));
    }

    @Transactional
    public void savePlayer(Player player) {
        if (player.getGold() == null) player.setGold(0);
        if (player.getGlory() == null) player.setGlory(0);
        if (player.getHasEvasion() == null) player.setHasEvasion(true);
        if (player.getNumOrcsKilled() == null) player.setNumOrcsKilled(0);
        if (player.getNumWarLordKilled() == null) player.setNumWarLordKilled(0);
        if (player.getDamageDealed() == null) player.setDamageDealed(0);
        if (player.getDamageDealedToNightLords() == null) player.setDamageDealedToNightLords(0);
        if (player.getReady() == null) player.setReady(false);
        playerRepository.save(player);
    }

    @Transactional(rollbackFor = RoleAlreadyChosenException.class)
    public void savePlayer(Player player, Mode mode) throws RoleAlreadyChosenException {
        if (player.getHeroes() != null && player.getHeroes().stream().map(HeroInGame::getHero)
            .collect(Collectors.groupingBy(Hero::getRole)).values().stream().anyMatch(l -> l.size() > 1)) {
            throw new RoleAlreadyChosenException();
        }
        addDeckFromRole(player, mode);
        savePlayer(player);
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
        // if (player.getId() == null)
        // abilityService.deleteAllAbilityInGameByPlayer(player);
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
            AbilityInGame abilityInGame = new AbilityInGame();
            abilityInGame.setPlayer(player);
            abilityInGame.setAttack(ability.getAttack());
            abilityInGame.setTimesUsed(abilityInGame.getTimesUsed());
            abilityInGame.setProduct(true);
            abilityInGame.setAbility(ability);
            abilityService.saveAbilityInGame(abilityInGame);
            player.addAbilityInDeck(abilityInGame);
        }
    }
}
