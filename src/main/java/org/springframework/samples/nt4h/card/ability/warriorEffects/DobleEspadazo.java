package org.springframework.samples.nt4h.card.ability.warriorEffects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.stereotype.Component;
import org.springframework.samples.nt4h.action.actions.DiscardCards;

@Component
public class DobleEspadazo {

    public void execute(Player player, EnemyInGame enemy) {
        //new Attack(2, player, enemy).execute();
        new DiscardCards(1, player).executeAction();
    }
}
