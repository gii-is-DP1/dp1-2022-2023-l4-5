package org.springframework.samples.nt4h.turn;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayer;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TurnService {
    private final TurnRepository turnRepository;
    private final GameService gameService;

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
        }
    }

    @Transactional
    public List<Turn> getWaysTurns(Player player) {
        return Lists.newArrayList(
            getTurnsByPhaseAndPlayerId(Phase.MARKET, player.getId()),
            getTurnsByPhaseAndPlayerId(Phase.HERO_ATTACK, player.getId()));
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

    // Dependiendo de la fase.
    @Transactional
    public void chooseAttackOrEvasion(Player player, Player loggedPlayer, Phase phase, Game game) throws NoCurrentPlayer {
        Turn turn = getTurnsByPhaseAndPlayerId(phase, player.getId());
        if (loggedPlayer != player)
            throw new NoCurrentPlayer();
        game.setCurrentTurn(turn);
        if (turn.getPhase() == Phase.EVADE) {
            player.setHasEvasion(false);
            player.setNextPhase(Phase.MARKET);
            // TODO: Se deben de descartar dos cartas.
        } else
            player.setNextPhase(Phase.HERO_ATTACK);
        saveTurn(turn); // TODO: Comprobar si se actualiza el jugador y la partida.
    }

    @Transactional
    public void setNextPlayerAndNextTurn(Game game, Player loggedPlayer, Player player) throws NoCurrentPlayer {
        if (loggedPlayer != player)
            throw new NoCurrentPlayer();
        Integer totalPlayers = game.getPlayers().size();
        Integer nextSequence = (game.getCurrentPlayer().getSequence()+1) % totalPlayers;
        Player nextPlayer = game.getPlayers().stream().filter(p -> p.getSequence() == nextSequence).findFirst().get();
        game.setCurrentPlayer(nextPlayer);
        game.setCurrentTurn(getTurnsByPhaseAndPlayerId(Phase.EVADE, nextPlayer.getId()));
        gameService.saveGame(game);
    }

}
