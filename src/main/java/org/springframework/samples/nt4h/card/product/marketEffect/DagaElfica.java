package org.springframework.samples.nt4h.card.product.marketEffect;

import org.springframework.samples.nt4h.action.DropCardOnPileAbility;
import org.springframework.samples.nt4h.action.Attack;
import org.springframework.samples.nt4h.capacity.StateCapacity;
import org.springframework.samples.nt4h.card.ability.AbilityCardType;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DagaElfica {
    public void execute(Player player, EnemyInGame enemy) {
        new Attack(2, player, enemy).executeAction();

        Boolean hasExpertise= player.getHeroes().stream()
            .flatMap(h -> h.getHero().getCapacities().stream()).collect(Collectors.toList())
            .stream()
            .anyMatch(p -> p.getStateCapacity().equals(StateCapacity.EXPERTISE));

        if (Boolean.TRUE.equals(hasExpertise))
            new DropCardOnPileAbility(player, AbilityCardType.MARKET).executeAction();
    }
}
