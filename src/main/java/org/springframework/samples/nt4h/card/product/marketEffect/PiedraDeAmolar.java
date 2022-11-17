package org.springframework.samples.nt4h.card.product.marketEffect;

import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PiedraDeAmolar {
    public void execute(Player player){
        Game game = player.getGame();
        List<EnemyInGame> listEnemiesFighting = game.getActualOrcs();
        for(EnemyInGame enemy : listEnemiesFighting){
            enemy.getPermanentEffectCardsUsed().add(AbilityEffectEnum.PIEDRA_DE_AMOLAR);
        }
    }
}
