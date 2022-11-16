package org.springframework.samples.nt4h.player;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        System.out.println("Saving player: " + player);
        if (player.getId() != null) {

            Optional<Player> oldPlayer = playerRepository.findById(player.getId());
            Role newRole = player.getHeroes().stream().map(h -> h.getHero().getRole())
                .filter(r -> oldPlayer.get().getHeroes().stream().noneMatch(oh -> oh.getHero().getRole().equals(r))).findFirst().orElse(null);
            System.out.println(player.getInDeck());
            System.out.println("newRole: " + newRole);
            System.out.println("oldPlayer: " + oldPlayer.get().getHeroes().stream().map(h -> h.getHero().getName()).collect(Collectors.toList()));
            System.out.println("player: " + player.getHeroes().stream().map(h -> h.getHero().getName()).collect(Collectors.toList()));
            if (newRole == null && oldPlayer.get().getHeroes().size() < player.getHeroes().size())
                throw new RoleAlreadyChosenException();
            else if (newRole != null) addDeckFromRole(player, newRole);
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

    @Transactional
    public void addDeckFromRole(Player player, Role... roles) {
        abilityRepository.deleteAllAbilityInGameByPlayer(player);
        for (Role role : roles) {
            for (Integer abilityId : role.getAbilities()) {
                Ability ability = abilityRepository.getAbilityById(abilityId);
                for (int i = 0; i < ability.getQuantity(); i++) {
                    // System.out.println("Adding " + ability.getName() + " to " + player.getName());
                    // System.out.println(ability.getQuantity());
                    AbilityInGame abilityInGame = new AbilityInGame();
                    abilityInGame.setPlayer(player);
                    abilityInGame.setAttack(ability.getAttack());
                    abilityInGame.setTimesUsed(abilityInGame.getTimesUsed());
                    abilityInGame.setProduct(true);
                    abilityInGame.setAbility(ability);
                    abilityRepository.saveAbilityInGame(abilityInGame);
                    player.addAbilityInDeck(abilityInGame);
                    // System.out.println(player.getInDeck().stream().map(AbilityInGame::getAttack).collect(Collectors.toList()));
                }
            }
        }


        if (player.getHeroes().size() == 2) {
            List<AbilityInGame> abilities = player.shuffleDeck();
            System.out.println("---");
            System.out.println(abilities);
            System.out.println(abilities.subList(16, abilities.size()));
            abilityRepository.deleteAbilityInGame();
            for (AbilityInGame destroy : abilities.subList(16, abilities.size())) {
                // System.out.println("Destroying " + destroy.getAbility().getName());
                abilityRepository.deleteAbilityInGame(destroy);
            }
            // System.out.println("Habilidades finales " + abilities);
            System.out.println(abilities.subList(0, 16));
            player.setInDeck(abilities.subList(0, 16));
            System.out.println("---");
        }
    }
}
