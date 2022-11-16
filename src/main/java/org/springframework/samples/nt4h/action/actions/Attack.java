package org.springframework.samples.nt4h.action.actions;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Action;
import org.springframework.samples.nt4h.card.ability.AbilityEffectEnum;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.product.MarketEffectEnum;
import org.springframework.samples.nt4h.player.Player;

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
        Boolean whetstoneCondition = targetedEnemy.getPlayedCardsOnMeInTurn().contains(MarketEffectEnum.PIEDRA_DE_AMOLAR);
        Boolean corrosiveArrow = targetedEnemy.getPlayedCardsOnMeInTurn().contains(AbilityEffectEnum.FLECHA_CORROSIVA);
        if (whetstoneCondition || corrosiveArrow) {
            damage++;
        }

        List<EnemyType> warlords = List.of(EnemyType.GURDRUG, EnemyType.ROGHKILLER, EnemyType.SHRIEKKNIFER);
        EnemyType targetedEnemyType = targetedEnemy.getEnemy().getEnemyType();

        if (warlords.contains(targetedEnemyType)) {
            new GainGlory(1, activePlayer).executeAction();
            if(targetedEnemyType.equals(warlords.get(0))){
                new DiscardCards(1, activePlayer).executeAction();
            }
        }

        List<EnemyType> enemiesFightingTypesList = new ArrayList<>();
        for(EnemyInGame enemy: activePlayer.getGame().getOrcs()){
            enemiesFightingTypesList.add(enemy.getEnemy().getEnemyType());
        }

        if (enemiesFightingTypesList.contains(EnemyType.SHRIEKKNIFER) && damage==1){
            new GainGlory(1, activePlayer).executeAction();
        }

        targetedEnemy.setActualHealth(currentHealth - damage);
        if (Boolean.TRUE.equals(targetedEnemy.isDead())) {
            targetedEnemy.getGame().getEnemiesFighting().remove(targetedEnemy);
            activePlayer.setNumOrcsKilled(playerKillCount + 1);

            if (targetedEnemy.getPlayedCardsOnMeInTurn().contains(AbilityEffectEnum.TRAMPA)) {
                Integer enemyDefeatedGlory = targetedEnemy.getEnemy().getBaseGlory();
                new GainGlory(enemyDefeatedGlory, activePlayer).executeAction();

            } else {

                Integer enemyDefeatedGlory = targetedEnemy.getEnemy().getBaseGlory() + targetedEnemy.getEnemy().getExtraGlory();
                Integer enemyDefeatedGold = targetedEnemy.getEnemy().getGold();
                new GainGlory(enemyDefeatedGlory, activePlayer).executeAction();
                new GainGold(enemyDefeatedGold, activePlayer).executeAction();
            }
        }
    }
}
