package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.user.UserService;

import java.util.List;

@AllArgsConstructor
public class RemoveCardForEnemyAttack implements Action {

    private Player player;
    private Integer damage;


    @Override
    public void executeAction() {

        for (int j = 0; j < damage && j < player.getInDeck().size(); j++) {  //retiramos las cartas segun el daño recibido
            AbilityInGame ability = player.getInDeck().get(j);
            player.getInDeck().remove(j);
            player.getInDiscard().add(ability);
        }
    }
}

