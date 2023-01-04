package org.springframework.samples.nt4h.card.enemy;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.InflictWounds;
import org.springframework.samples.nt4h.action.RemoveCardForEnemyAttack;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
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

    @Transactional(rollbackFor = Exception.class)
    public void deleteEnemyInGame(EnemyInGame enemyInGame) {
        enemyInGame.onDeleteSetNull();
        enemyInGameRepository.save(enemyInGame);
        enemyInGameRepository.delete(enemyInGame);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteEnemyInGameById(int id) {
        EnemyInGame enemyInGame = getEnemyInGameById(id);
        deleteEnemyInGame(enemyInGame);
    }

    @Transactional(readOnly = true)
    public boolean enemyInGameExists(int id) {
        return enemyInGameRepository.existsById(id);
    }


    public List<Enemy> getAllEnemies() { return enemyRepository.findAll(); }

    @Transactional
    public Enemy getNightLord() {
        int randomNumber = (int) (Math.random() * 3);
        List<Enemy> allNightLords = getAllNightLords();
        return allNightLords.get(randomNumber);
    }

    @Transactional(readOnly = true)
    public List<Enemy> getAllNightLords() {
        return enemyRepository.findAllNightLords();
    }

    @Transactional(readOnly = true)
    public List<Enemy> getAllNotNightLords() {
        return enemyRepository.findAllNotNightLords();
    }

    @Transactional
    public List<EnemyInGame> addOrcsToGame() { // TODO: dependiendo del juego deberán de ser un número u otro.
        List<EnemyInGame> orcs = getAllNotNightLords().stream()
            .map(enemy -> EnemyInGame.createEnemy(false, enemy)).collect(Collectors.toList());
        orcs.forEach(this::saveEnemyInGame);
        return orcs;
    }

    @Transactional
    public EnemyInGame addNightLordToGame() {
        Enemy nightLord = getNightLord();
        EnemyInGame nightLordInGame = EnemyInGame.createEnemy(true, nightLord);
        saveEnemyInGame(nightLordInGame);
        return nightLordInGame;
    }
    //Obetener el daño total de los enemigos en batalla
    // TODO: comprobar.

    public Integer attackEnemyToActualPlayer(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        if (game.getActualOrcs().isEmpty()) return 0;
        int numCardsInDeck = currentPlayer.getDeck().getInDeck().size();
        int damage = game.getActualOrcs().stream().mapToInt(EnemyInGame::getActualHealth).sum();
        if (numCardsInDeck <= damage) { //si el daño es mayor o igual a la cantidad de cartass quue tengo pues recibo la herida
            new InflictWounds(currentPlayer).executeAction();  //Recibe herida
            if (Objects.equals(currentPlayer.getWounds(), currentPlayer.getHealth())) {
                game.getPlayers().remove(currentPlayer);  // de momento sales de la partida, mas adelante cambia a vista espectador
                playerService.deletePlayer(currentPlayer);
            }
        } else {
            new RemoveCardForEnemyAttack(currentPlayer, damage).executeAction(); //no recibe herida
        }
        return damage;
    }

    @Transactional
    public Enemy getEnemyById(int id) {
        return enemyRepository.findById(id).orElseThrow(() -> new NotFoundException("Enemy not found"));
    }

}
