package org.springframework.samples.nt4h.effect;

import org.springframework.samples.nt4h.player.Player;

public class IncreaseDamageAllAbilities extends Effect {

    public Phase phase = Phase.HERO_ATTACK;

    @Override
    public boolean useEffect(Player player) {
        player.getInHand().forEach(ability -> ability.setAttack(ability.getAbility().getAttack() + 1));
        player.getInDeck().forEach(ability -> ability.setAttack(ability.getAbility().getAttack() + 1));
        return true;
    }
}
