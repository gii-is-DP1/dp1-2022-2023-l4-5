package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.card.enemy.Enemy;
import org.springframework.samples.petclinic.player.Player;

public class RapidFire extends Effect {

    private final Integer quantity = 1;

    @Override
    public boolean useEffect(Player player, Enemy enemy) {

        for (int i = 0; i < quantity; i++) {
            var card = player.getInDeck().get(0);
            if (card.getAbility().getName().equals("Disparo Rápido")) {
                // TODO: utiliza la carta, si el enemygo está muerto ataca a otro aleatorio.
            } else {
                player.getInDeck().remove(card);
                player.getInDeck().add(card);
            }
            player.getInDiscard().add(card);
        }
        return true;
    }
}
