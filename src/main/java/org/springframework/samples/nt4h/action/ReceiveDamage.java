package org.springframework.samples.nt4h.action;


public class ReceiveDamage implements Action {

    private EnemyInGame enemy;

    private Player playerTo;

    @Override
    public void executeAction() {

        Integer damage = enemy.getActualHealth();
        EnemyModifierType enemyModifier = enemy.getId().getEnemyModifierType();
        CharacterTypeEnum characterClass = playerTo.getCharacterTypeEnum();
        Game game = playerTo.getGame();
        List<EnemyType> listEnemiesFighting = new ArrayList<>();
        for(EnemyIngame enemy : game.getEnemiesFighting()){
            listEnemiesFighting.add(enemy.getEnemy().getEnemyType());
        }

        if(listEnemiesFighting.contains(EnemyType.ROGHKILLER)){
            damage++;
        }

        if (Boolean.TRUE.equals(enemyFrom.getRestrained()))
            damage = 0;

        if (enemyModifier != null && enemyModifier.equals(EnemyModifierType.MAGIC_ATTACKER_1)
            && characterClass.equals(CharacterTypeEnum.WIZARD)) {
            damage -= 1;
        } else if (enemyModifier != null && enemyModifier.equals(EnemyModifierType.MAGIC_ATTACKER_2)
            && characterClass.equals(CharacterTypeEnum.WIZARD)) {
            damage -= 2;
        }

        for (int i = 0; i < damage; i++) {
            Integer guard = playerTo.getGuard();
            if (guard > 0) {
                playerTo.setGuard(guard - 1);
                break;
            }

            // "Top card" is the last card in the abilityPile list
            Integer topCardIndex = playerTo.getAbilityPile().size() - 1;
            AbilityCardIngame topCard = playerTo.getAbilityPile().get(topCardIndex);

            playerTo.getAbilityPile().remove(topCard);
            playerTo.getDiscardPile().add(topCard);

            if (playerTo.getAbilityPile().isEmpty()) {
                new GiveWoundCommand(playerTo).execute();
            }
        }
    }

}
