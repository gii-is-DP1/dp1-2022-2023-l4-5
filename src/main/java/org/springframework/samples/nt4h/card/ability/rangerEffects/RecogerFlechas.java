package org.springframework.samples.nt4h.card.ability.rangerEffects;

import org.springframework.samples.nt4h.action.GainGold;

import org.springframework.samples.nt4h.action.RecoverCardDiscardToAbility;
import org.springframework.samples.nt4h.card.ability.AbilityCardType;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class RecogerFlechas {

    // Recuperas una carta de Disparo rapido de tu pila de Desgaste, barajas el Mazo de Habilidades y ganas 1 moneda.
    public void execute(Player activePlayer) {
        new GainGold(1, activePlayer).executeAction();
        new RecoverCardDiscardToAbility(activePlayer, AbilityCardType.RANGER).executeAction();
    }
}
