package org.springframework.samples.nt4h.player;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final AbilityService abilityRepository;

    @Transactional(readOnly = true)
    public Player getPlayerById(int id) {
        return playerRepository.findById(id).orElseThrow(() -> new NotFoundException("Player not found"));
    }

    @Transactional(rollbackFor = RoleAlreadyChosenException.class)
    public void savePlayer(Player player) throws RoleAlreadyChosenException {
        if (player.getHeroes() != null && player.getHeroes().stream().map(HeroInGame::getHero).collect(Collectors.groupingBy(Hero::getRole)).values().stream().anyMatch(l -> l.size() > 1)) {
            throw new RoleAlreadyChosenException();
        }
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

    @Transactional(rollbackFor = RoleAlreadyChosenException.class)
    public void addDeckFromRoles(Player player, Role role) {
        for (Integer abilityId : role.getAbilities()) {
            Ability ability = abilityRepository.getAbilityById(abilityId);
            for (int i = 0; i < ability.getQuantity(); i++) {
                System.out.println("Adding " + ability.getName() + " to " + player.getName());
                System.out.println(ability.getQuantity());
                AbilityInGame abilityInGame = new AbilityInGame();
                abilityInGame.setPlayer(player);
                abilityInGame.setAttack(ability.getAttack());
                abilityInGame.setTimesUsed(abilityInGame.getTimesUsed());
                abilityInGame.setProduct(true);
                abilityInGame.setAbility(ability);
                abilityRepository.saveAbilityInGame(abilityInGame);
                player.addAbilityInDeck(abilityInGame);
                System.out.println(player.getInDeck().stream().map(AbilityInGame::getId).collect(Collectors.toList()));
            }

        }

        if (player.getHeroes().size() == 2) {
            List<AbilityInGame> abilities = player.shuffleDeck();
            for (AbilityInGame destroy : abilities.subList(16, abilities.size()))
                abilityRepository.deleteAbilityInGame(destroy);
            System.out.println(abilities);
            player.setInDeck(abilities.subList(0, 16));
        }
    }
}
