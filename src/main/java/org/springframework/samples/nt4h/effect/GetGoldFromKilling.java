package org.springframework.samples.nt4h.effect;

import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;

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
