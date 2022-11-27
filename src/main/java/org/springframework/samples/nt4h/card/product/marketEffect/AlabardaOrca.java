package org.springframework.samples.nt4h.card.product.marketEffect;

import org.springframework.samples.nt4h.action.Attack;
import org.springframework.samples.nt4h.capacity.Capacity;
import org.springframework.samples.nt4h.capacity.StateCapacity;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlabardaOrca {
    public void execute(Player player, EnemyInGame enemy) {
        int damageModifier = 0;
        List<StateCapacity> capacities = List.of(StateCapacity.MELEE);
        List<Capacity> ls = player.getHeroes().stream()
            .flatMap(h -> h.getHero().getCapacities().stream()).collect(Collectors.toList())
            .stream()
            .filter(p -> capacities.contains(p.getStateCapacity())).filter(Capacity::getLessDamage)
            .collect(Collectors.toList());
        if(!(ls.isEmpty())){
            damageModifier = -1;
        }
        new Attack(4 + damageModifier, player, enemy).executeAction();
    }


}
