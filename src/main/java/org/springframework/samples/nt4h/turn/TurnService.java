package org.springframework.samples.nt4h.turn;


import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.InflictWounds;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.action.RemoveCardForEnemyAttack;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TurnService {
    private final TurnRepository turnRepository;
    private final PlayerService playerService;

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
    //Obetener el daño total de los enemigos en batalla

    public void attackEnemyToActualPlayer(Game game) {
        //Lo que hace el método :)
        //Cambiar mazo de habilidad
        //Recibir herida o no


        Integer damage = 0; //repito el bucle porque no se me he encariñado de el
        if (game.getActualOrcs().size() != 0) {  // Si hay enemigos en el campo si no pues no recibe daño el heroe
            for (int i = 0; i <= game.getActualOrcs().size(); i++) {  //calculamos el daño total inflijido
                damage = damage + game.getActualOrcs().get(i).getActualHealth();
            }
            if (game.currentPlayer.getInDeck().size() <= damage) { //si el daño es mayor o igual a la cantidad de cartass quue tengo pues recibo la herida
                Action DamageWithWounds = new InflictWounds(game.currentPlayer);  //Recibe herida
                DamageWithWounds.executeAction();
                if(game.currentPlayer.getWounds()==game.currentPlayer.getHeroes().get(0).getActualHealth()){
                    game.getPlayers().remove(game.currentPlayer);  // de momento sales de la partida, mas adelante cambia a vista espectador
                    playerService.getOutGame(game.currentPlayer, game);
                }
            } else {
                Action DamageWithOUTWounds = new RemoveCardForEnemyAttack(game.currentPlayer, damage); //no recibe herida
                DamageWithOUTWounds.executeAction();
            }
        }
        playerService.savePlayer(game.currentPlayer);
    }

}
