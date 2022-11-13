package org.springframework.samples.nt4h.effect;

import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.player.Player;

import java.util.Arrays;

public class GetGoldFromEnemiesAlive extends Effect {

    public Phase phase = Phase.HERO_ATTACK;

    @Override
    public boolean useEffect(Player player, EnemyInGame... enemiesInGame) {
        player.setGold((int) (player.getGold() + Arrays.stream(enemiesInGame)
            .filter(enemy -> enemy.getActualHealth() > 0).count()));
        return true;
    }
}
