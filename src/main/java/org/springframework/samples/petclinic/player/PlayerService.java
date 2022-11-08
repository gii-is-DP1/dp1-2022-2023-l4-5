package org.springframework.samples.petclinic.player;

import lombok.AllArgsConstructor;
import org.springframework.samples.petclinic.card.ability.Ability;
import org.springframework.samples.petclinic.card.ability.AbilityInGame;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Player findPlayerById(int id) { return playerRepository.findById(id).orElse(null);}

    public void savePlayer(Player player) {
        playerRepository.save(player);
    }

    public void deletePlayerById(int id) {
        playerRepository.deleteById(id);
    }

    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    public Player findPlayerByName(String name) { return playerRepository.findByName(name).orElse(null); }

    public AbilityInGame getCardInHand() { return (AbilityInGame) playerRepository.getCardInHand(); }

    public AbilityInGame getCardInDeck() { return (AbilityInGame) playerRepository.getCardInDeck(); }

    public AbilityInGame getCardInDiscard() { return (AbilityInGame) playerRepository.getCardInDiscard(); }

}
