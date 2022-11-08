package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.player.Player;

public class IncreaseDamageAllAbilities extends Effect {

    public Phase phase = Phase.HERO_ATTACK;

    @Override
    public boolean useEffect(Player player) {
        player.getInHand().forEach(ability -> ability.setAttack(ability.getAbility().getAttack() + 1));
        player.getInDeck().forEach(ability -> ability.setAttack(ability.getAbility().getAttack() + 1));
        return true;
    }
}
