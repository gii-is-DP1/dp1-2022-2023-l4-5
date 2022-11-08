package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.card.ability.Ability;
import org.springframework.samples.petclinic.card.ability.AbilityInGame;
import org.springframework.samples.petclinic.card.enemy.EnemyInGame;
import org.springframework.samples.petclinic.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GetGoldFromKilling extends Effect {

    private static final List<Ability> abilities = new ArrayList<>();
    public static Integer gold = 1;
    public Phase phase = Phase.HERO_ATTACK;

    @Override
    public boolean useEffect(Player player, EnemyInGame enemyInGame, AbilityInGame abilityInGame) {
        var ability = abilityInGame.getAbility();
        var enemy = enemyInGame.isNightLord() ? enemyInGame.getNightLord() : enemyInGame.getOrc();
        if (enemy.getHealth() - ability.getAttack() <= 0 && !abilities.contains(ability)) {
            abilities.add(ability);
            player.setGold(player.getGold() + gold);
        }
        return true;
    }
}
