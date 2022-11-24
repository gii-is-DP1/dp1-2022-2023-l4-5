package org.springframework.samples.nt4h.action;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.orc.Orc;

import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.action.actions.DiscardCards;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class Attack implements Action {

    private Integer damage;
    private Player activePlayer;
    private EnemyInGame targetedEnemy;


    @Override
    public void executeAction() {
        Integer currentHealth = targetedEnemy.getActualHealth();
        Integer playerKillCount = activePlayer.getNumOrcsKilled();
        Boolean whetstoneCondition = targetedEnemy.getPermanentEffectCardsUsed().contains(AbilityEffectEnum.PIEDRA_DE_AMOLAR);
        Boolean corrosiveArrow = targetedEnemy.getPermanentEffectCardsUsed().contains(AbilityEffectEnum.FLECHA_CORROSIVA);
        if (whetstoneCondition || corrosiveArrow) {
            damage++;
        }

        List<String> warlords = List.of("Gurdrug", "Roghkiller", "Shriekknifer");
        String targetedEnemyType = targetedEnemy.getEnemy().getName();

        if (warlords.contains(targetedEnemyType)) {
            new GainGlory(1, activePlayer).executeAction();
            if (targetedEnemyType.equals(warlords.get(0))) {
                new DiscardCards(1, activePlayer).executeAction();
            }
        }

        List<String> enemiesFightingTypesList = new ArrayList<>();
        for (EnemyInGame enemy : activePlayer.getGame().getAllOrcsInGame()) {
            enemiesFightingTypesList.add(enemy.getEnemy().getName());
        }

        if (enemiesFightingTypesList.contains("Shriekknifer") && damage == 1) {
            new GainGlory(1, activePlayer).executeAction();
        }

        targetedEnemy.setActualHealth(currentHealth - damage);
        if (Boolean.TRUE.equals(targetedEnemy.isDead())) {
            activePlayer.getGame().getAllOrcsInGame().remove(targetedEnemy);
            activePlayer.setNumOrcsKilled(playerKillCount + 1);

            if (targetedEnemy.getPermanentEffectCardsUsed().contains(AbilityEffectEnum.TRAMPA)) {
                Integer enemyDefeatedGlory = ((Orc) targetedEnemy.getEnemy()).getGlory();
                new GainGlory(enemyDefeatedGlory, activePlayer).executeAction();

            } else {

                Integer enemyDefeatedGlory = ((Orc) targetedEnemy.getEnemy()).getGlory() /*+ targetedEnemy.getEnemy().getExtraGlory()*/;
                Integer enemyDefeatedGold = ((Orc) targetedEnemy.getEnemy()).getGold();
                new GainGlory(enemyDefeatedGlory, activePlayer).executeAction();
                new GainGold(enemyDefeatedGold, activePlayer).executeAction();
            }
        }
    }
}
