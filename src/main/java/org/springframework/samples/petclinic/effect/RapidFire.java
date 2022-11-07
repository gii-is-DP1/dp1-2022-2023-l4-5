package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.card.enemy.EnemyInGame;
import org.springframework.samples.petclinic.player.Player;

public class RapidFire extends Effect {

    private final Integer quantity = 1;

    @Override
    public boolean useEffect(Player player, EnemyInGame enemyInGame) {

        var enemy = enemyInGame.getEnemy();
        var abilityInGame = player.getInDeck().get(0);
        var ability = abilityInGame.getAbility();
        if (ability.getName().equals("Disparo Rápido")) {
            // TODO: utiliza la carta, si el enemygo está muerto ataca a otro aleatorio.
        } else {
            player.getInDeck().remove(abilityInGame);
            player.getInDeck().add(abilityInGame);
        }
        player.getInDiscard().add(abilityInGame);
        return true;
    }
}
