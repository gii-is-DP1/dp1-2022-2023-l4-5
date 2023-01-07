package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.player.Player;

@AllArgsConstructor
public class RemoveCardForEnemyAttack implements Action {

    private Player player;
    private Integer damage;


    @Override
    public void executeAction() {

        for (int j = 0; j < damage && j < player.getDeck().getInDeck().size(); j++) {  //retiramos las cartas segun el daño recibido
            AbilityInGame ability = player.getDeck().getInDeck().get(j);
            player.getDeck().getInDeck().remove(j);
            player.getDeck().getInDiscard().add(ability);
        }
    }
}

