package org.springframework.samples.nt4h.card.ability.rangerEffects;

import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

@Component
public class RecogerFlechas {

    // Recuperas una carta de Disparo rapido de tu pila de Desgaste, barajas el Mazo de Habilidades y ganas 1 moneda.
    public void execute(Player activePlayer) {
        new GainGold(1, activePlayer).executeAction();
        new RecoverCardFromDiscard(activePlayer, AbilityEffectEnum.DISPARO_RAPIDO).executeAction();
    }
}
