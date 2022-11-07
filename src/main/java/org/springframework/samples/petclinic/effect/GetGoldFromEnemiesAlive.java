package org.springframework.samples.petclinic.effect;

import org.springframework.samples.petclinic.card.enemy.EnemyInGame;
import org.springframework.samples.petclinic.player.Player;

import java.util.Arrays;

public class GetGoldFromEnemiesAlive extends Effect {

    public Phase phase = Phase.HERO_ATTACK;

    @Override
    public boolean useEffect(Player player, EnemyInGame... enemiesInGame) {
        player.setGold((int) (player.getGold() + Arrays.stream(enemiesInGame)
            .map(EnemyInGame::getEnemy)
            .filter(enemy -> enemy.getHealth() > 0).count()));
        return true;
    }
}
