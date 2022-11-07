package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.card.ability.Ability;
import org.springframework.samples.petclinic.card.ability.AbilityInGame;
import org.springframework.samples.petclinic.card.enemy.Enemy;
import org.springframework.samples.petclinic.card.enemy.EnemyInGame;
import org.springframework.samples.petclinic.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SecondStrike extends Effect {

    private static final List<Enemy> enemies = new ArrayList<>();

    private static final List<Ability> abilities = new ArrayList<>();

    @Override
    public boolean useEffect(Player player, EnemyInGame enemyInGame, AbilityInGame abilityInGame) {
        var enemy = enemyInGame.getEnemy();
        var ability = abilityInGame.getAbility();
        if (enemies.contains(enemy) && abilities.contains(ability)) {
            abilityInGame.setAttack(ability.getAttack() + 1);
        } else {
            abilityInGame.setAttack(ability.getAttack() + 1);
            enemies.add(enemy);
            abilities.add(ability);
        }

        return true;
    }
}
