package org.springframework.samples.nt4h.turn;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.nt4h.action.Phase;
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
            saveTurn(turn);
            player.addTurn(turn);
        }
    }

    @Transactional(readOnly = true)
    public Turn getTurnByID(int id) {
        return turnRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Turn> getAllTurns() {
        return turnRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Turn> getTurnsByPhase(Phase phase) {
        return turnRepository.findByPhase(phase);
    }

    @Transactional
    public void deleteTurn(Turn turn) {
        turnRepository.delete(turn);
    }

    @Transactional
    public void deleteTurnById(int id) {
        turnRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean turnExists(int id) {
        return turnRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public Turn getTurnsByPhaseAndPlayerId(Phase phase, int playerId) {
        return getAllTurns().stream().filter(turn -> turn.getPhase().equals(phase) && turn.getPlayer().getId() == playerId)
            .findFirst().orElseThrow(() -> new NotFoundException("Turn not found"));
    }

}
