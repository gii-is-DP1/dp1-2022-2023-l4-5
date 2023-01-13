package org.springframework.samples.nt4h.player;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TurnService turnService;
    private final UserRepository userRepository;
    private final Advise advise;


    @Transactional(readOnly = true, rollbackFor = NotFoundException.class)
    public Player getPlayerById(int id) {
        return playerRepository.findById(id).orElseThrow(() -> new NotFoundException("Player not found"));
    }

    @Transactional
    public void createTurns(Player player) {
        turnService.createAllTurnForAPlayer(player);
        savePlayer(player);
    }

    @Transactional
    public void savePlayer(Player player) {
        playerRepository.save(player);
    }


    @Transactional
    public void deletePlayerById(int id) {
        Player player = getPlayerById(id);
        playerRepository.findUserByPlayer(player).ifPresent(user -> {
            user.setPlayer(null);
            userRepository.save(user);
        });
        deletePlayer(player);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePlayer(Player player) {
        player.onDeleteSetNull();
        playerRepository.save(player);
        playerRepository.delete(player);
    }


    @Transactional(rollbackFor = Exception.class)
    public void decreaseWounds(Player player, int wounds) {
        if (wounds > 0) {
            player.setWounds(player.getWounds() - wounds);
            savePlayer(player);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void inflictWounds(Player player, int i) {
        player.setWounds(player.getWounds() + i);
        advise.getOneWound();
        if (player.getHealth() <= 0) {
            advise.playerIsDead();
            player.setAlive(false);
        }

        savePlayer(player);

    }


}
