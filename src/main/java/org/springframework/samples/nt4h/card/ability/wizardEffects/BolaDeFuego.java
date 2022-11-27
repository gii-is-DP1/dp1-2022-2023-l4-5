package org.springframework.samples.nt4h.card.ability.wizardEffects;

import org.springframework.samples.nt4h.action.Attack;
import org.springframework.samples.nt4h.action.DiscardCards;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BolaDeFuego {
    public void execute(Player player) {
        Game game = player.getGame();
        List<EnemyInGame> activeEnemies = game.getActualOrcs();
        List<EnemyInGame> enemies = new ArrayList<>();
        enemies.addAll(activeEnemies);
        for (EnemyInGame enemy : enemies) {
            new Attack(2, player, enemy).executeAction();
        }
        List<Player> players = game.getPlayers();
        List<Player> otherPlayers = new ArrayList<>();
        for (Player p : players) {
            if (p != player) {
                otherPlayers.add(p);
            }
        }
        for (Player player1 : otherPlayers) {
            new DiscardCards(1, player1).executeAction();
        }
    }
}
