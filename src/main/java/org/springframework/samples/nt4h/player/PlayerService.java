package org.springframework.samples.nt4h.player;

import lombok.AllArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public Player getPlayerById(int id) {
        return playerRepository.findById(id).orElseThrow(() -> new NotFoundException("Player not found"));
    }

    @Transactional
    public void savePlayer(Player player) {
        if (player.getGold() == null) {
            player.setGold(0);
        }
        if (player.getGlory() == null) {
            player.setGlory(0);
        }
        if (player.getEvasion() == null) {
            player.setEvasion(true);
        }
        if (player.getNumOrcsKilled() == null) {
            player.setNumOrcsKilled(0);
        }
        if (player.getNumWarLordKilled() == null) {
            player.setNumWarLordKilled(0);
        }
        if (player.getDamageDealed() == null) {
            player.setDamageDealed(0);
        }
        if (player.getDamageDealedToNightLords() == null) {
            player.setDamageDealedToNightLords(0);
        }
        if(player.getReady()==null){
            player.setReady(false);
        }
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
        return playerRepository.findByName(name).orElseThrow(() -> new NotFoundException("Player not found"));
    }

    @Transactional(readOnly = true)
    public boolean playerExists(int id) {
        return playerRepository.existsById(id);
    }


}
