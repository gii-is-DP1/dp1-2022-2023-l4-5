package org.springframework.samples.nt4h.effect;

import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.Enemy;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.ArrayList;
import java.util.List;

public class SecondStrike extends Effect {

    private static final List<Enemy> enemies = new ArrayList<>();

    private static final List<Ability> abilities = new ArrayList<>();

    @Override
    public boolean useEffect(Player player, EnemyInGame enemyInGame, AbilityInGame abilityInGame) {
        var enemy = enemyInGame.isNightLord() ? enemyInGame.getNightLord() : enemyInGame.getOrc();
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
