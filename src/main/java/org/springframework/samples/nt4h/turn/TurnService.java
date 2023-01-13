package org.springframework.samples.nt4h.turn;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TurnService {
    private final TurnRepository turnRepository;

    @Transactional
    public void saveTurn(Turn turn) throws DataAccessException {
        turnRepository.save(turn);
    }

    @Transactional
    public void createAllTurnForAPlayer(Player player) {
        for (Phase phase : Phase.values()) {
            Turn turn = Turn.builder().player(player).game(player.getGame()).phase(phase)
                .usedEnemies(Lists.newArrayList()).usedAbilities(Lists.newArrayList()).build();
            player.getTurns().add(turn);
            saveTurn(turn);
        }
    }

    @Transactional(readOnly = true)
    public List<Turn> getAllTurns() {
        return turnRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Turn getTurnsByPhaseAndPlayerId(Phase phase, int playerId) {
        return getAllTurns().stream().filter(turn -> turn.getPhase().equals(phase) && turn.getPlayer().getId() == playerId)
            .findFirst().orElseThrow(() -> new NotFoundException("Turn not found"));
    }

    // Dependiendo de la fase.
    @Transactional
    public void chooseAttackOrEvasion(Player player, Phase phase, Game game) {
        Turn turn = getTurnsByPhaseAndPlayerId(phase, player.getId());
        game.setCurrentTurn(turn);
        System.out.println("Turno actual: " + game.getCurrentTurn().getPhase());
        System.out.println(phase == Phase.EVADE);
        if ((phase == Phase.EVADE) && player.getHasEvasion()) {
            player.setHasEvasion(false);
            player.setNextPhase(Phase.EVADE);
        } else
            player.setNextPhase(Phase.HERO_ATTACK);
        saveTurn(turn);
    }
}
