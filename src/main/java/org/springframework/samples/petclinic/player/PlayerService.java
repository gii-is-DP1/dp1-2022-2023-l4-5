package org.springframework.samples.petclinic.player;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    public Player findPlayerById(int id) {
        // TODO: Añadir comprobación por si jugador no existe
        return playerRepository.findById(id).orElse(null);
    }

    public void savePlayer(Player player) {
        playerRepository.save(player);
    }

    public void deletePlayerById(int id) {
        playerRepository.deleteById(id);
    }

    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    public Player findPlayerByName(String name) {
        // TODO: Añadir comprobación por si jugador no existe
        return playerRepository.findByName(name).orElse(null);
    }
}
