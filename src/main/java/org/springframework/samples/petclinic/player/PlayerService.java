package org.springframework.samples.petclinic.player;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public Player getPlayerById(int id) {
        return playerRepository.findById(id).orElse(null);
    }

    @Transactional
    public void savePlayer(Player player) {
        playerRepository.save(player);
    }

    @Transactional
    public void deletePlayerById(int id) {
        playerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Player getPlayerByName(String name) {
        return playerRepository.findByName(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public boolean exists(int id) {
        return playerRepository.existsById(id);
    }

}