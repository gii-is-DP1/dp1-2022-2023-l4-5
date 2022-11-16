package org.springframework.samples.nt4h.card.ability.rangerEffects;

import org.springframework.samples.nt4h.action.Attack;
import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;

public class DisparoRapido {

    // 1 de daño, robas una carta, si es Disparo Rapido se usa, si no, se pone en el fondo del mazo de habilidad
    public void execute(Player activePlayer, EnemyInGame targetedEnemy) {
        new Attack(1, activePlayer, targetedEnemy).executeAction();
        new DrawCards(1, activePlayer).executeAction();
        Integer posicion = activePlayer.getInHand().size() - 1;
        AbilityInGame cartaRobada = activePlayer.getInHand().get(posicion);
        if (cartaRobada.getAbilityEffectEnum().equals(AbilityEffectEnum.DISPARO_RAPIDO)) {

        } else {
            new HandToAbilityDeck(activePlayer, cartaRobada.getAbilityEffectEnum()).executeAction();
        }
    }
}
