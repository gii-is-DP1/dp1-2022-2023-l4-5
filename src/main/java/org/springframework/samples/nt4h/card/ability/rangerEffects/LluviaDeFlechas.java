package org.springframework.samples.nt4h.card.ability.rangerEffects;

import org.springframework.samples.nt4h.action.Attack;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LluviaDeFlechas {

    // 2 de daño, dañas a 2 enemigos y al heroe con menos Heridas(en caso de empate se elige)
    public void execute(Player activePlayer) {
        Game game = activePlayer.getGame();
        List<EnemyInGame> enemiesFighting = game.getOrcs();
        List<EnemyInGame> targetList = new ArrayList<>();
        for(EnemyInGame enemy : enemiesFighting){
            targetList.add(enemy);
        }
        for (EnemyInGame target : targetList) {
            new Attack(2, activePlayer, target).executeAction();
        }
        List<Player> playersAlive = game.getAlivePlayersInTurnOrder();
        Integer minNumWounds = 2;
        List<Player> healthyMembers = new ArrayList<>();
        for(Player player:playersAlive){
            if(player.getWounds().equals(minNumWounds)){
                healthyMembers.add(player);
            } else if(player.getWounds()<minNumWounds){
                minNumWounds = player.getWounds();
                healthyMembers.clear();
                healthyMembers.add(player);
            }
        }

        for(Player player:healthyMembers){
            new DiscardCards(2, player).executeAction();
        }
    }
}
