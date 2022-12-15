package org.springframework.samples.nt4h.card.enemy;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.action.InflictWounds;
import org.springframework.samples.nt4h.action.RemoveCardForEnemyAttack;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EnemyService {
    private final EnemyInGameRepository enemyInGameRepository;
    private final EnemyRepository enemyRepository;
    private final PlayerService playerService;

    // EnemyInGame
    @Transactional(readOnly = true)
    public EnemyInGame getEnemyInGameById(int id) {
        return enemyInGameRepository.findById(id).orElseThrow(() -> new NotFoundException("EnemyInGame not found"));
    }

    @Transactional(readOnly = true)
    public List<EnemyInGame> getAllEnemyInGame() {
        return enemyInGameRepository.findAll();
    }

    @Transactional
    public void saveEnemyInGame(EnemyInGame enemyInGame) {
        enemyInGameRepository.save(enemyInGame);
    }

    @Transactional
    public void deleteEnemyInGame(EnemyInGame enemyInGame) {
        enemyInGameRepository.delete(enemyInGame);
    }

    @Transactional
    public void deleteEnemyInGameById(int id) {
        enemyInGameRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean enemyInGameExists(int id) {
        return enemyInGameRepository.existsById(id);
    }


    public List<Enemy> getAllEnemies() { return enemyRepository.findAll(); }

    public Enemy getNightLord() {
        Integer randomNumber = (int) (Math.random() * 3);
        List<Enemy> allNightLords = getAllEnemyByIsNightLord(true);
        return allNightLords.get(randomNumber);
    }

    @Transactional(readOnly = true)
    public List<Enemy> getAllEnemyByIsNightLord(Boolean isNightLord) {
        return enemyRepository.findAll().stream().filter(enemy -> enemy.getIsNightLord() == isNightLord).collect(Collectors.toList());
    }

    public List<EnemyInGame> addOrcsToGame() {
        List<EnemyInGame> orcs = getAllEnemyByIsNightLord(false).stream()
            .map(enemy -> EnemyInGame.createEnemy(false, enemy)).collect(Collectors.toList());
        orcs.forEach(this::saveEnemyInGame);
        return orcs;
    }

    public EnemyInGame addNightLordToGame() {
        Enemy nightLord = getNightLord();
        EnemyInGame nightLordInGame = EnemyInGame.createEnemy(true, nightLord);
        saveEnemyInGame(nightLordInGame);
        return nightLordInGame;
    }
    //Obetener el daño total de los enemigos en batalla

    public Integer attackEnemyToActualPlayer(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        if (game.getActualOrcs().isEmpty()) return 0;
        // Si hay enemigos en el campo si no pues no recibe daño el heroe
        int damage = game.getActualOrcs().stream().mapToInt(EnemyInGame::getActualHealth).sum();
        if (currentPlayer.getInDeck().size() <= damage) { //si el daño es mayor o igual a la cantidad de cartass quue tengo pues recibo la herida
            new InflictWounds(currentPlayer).executeAction();  //Recibe herida
            if (Objects.equals(currentPlayer.getWounds(), currentPlayer.getHealth())) {
                game.getPlayers().remove(currentPlayer);  // de momento sales de la partida, mas adelante cambia a vista espectador
                playerService.getOutGame(currentPlayer, game);
            }
        } else {
            new RemoveCardForEnemyAttack(currentPlayer, damage).executeAction(); //no recibe herida
        }
        return damage;
    }

}
