package org.springframework.samples.nt4h.card.ability;

import org.junit.jupiter.api.Test;
import org.springframework.samples.nt4h.card.enemy.Enemy;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.card.product.Product;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.card.product.StateProduct;
import org.springframework.samples.nt4h.game.Accessibility;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.turn.Phase;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbilityInGameTest {
    @Test
    void testFromAbility() {
        Deck deck = new Deck();
        deck.setId(1);
        deck.setInDeck(new ArrayList<>());
        deck.setInDiscard(new ArrayList<>());
        deck.setInHand(new ArrayList<>());
        Player player = new Player();
        player.setAlive(true);
        player.setBirthDate(null);
        player.setDamageProtect(1);
        player.setDeck(new Deck());
        player.setGame(new Game());
        player.setHasEvasion(true);
        player.setHeroes(new ArrayList<>());
        player.setHost(true);
        player.setId(1);
        player.setName("Name");
        player.setNextPhase(Phase.START);
        player.setReady(true);
        player.setSequence(1);
        player.setStatistic(new Statistic());
        player.setTurns(new ArrayList<>());
        player.setWounds(1);
        Turn turn = new Turn();
        turn.setCurrentEnemy(new EnemyInGame());
        turn.setCurrentProduct(new ProductInGame());
        turn.setGame(new Game());
        turn.setId(1);
        turn.setPhase(Phase.START);
        turn.setPlayer(new Player());
        turn.setUsedAbilities(new ArrayList<>());
        turn.setUsedEnemies(new ArrayList<>());
        turn.setUsedProducts(new ArrayList<>());

        Statistic statistic = new Statistic();
        statistic.setDamageDealt(1);
        statistic.setGlory(1);
        statistic.setGold(1);
        statistic.setId(1);
        statistic.setNumOrcsKilled(10);
        statistic.setNumPlayedGames(10);
        statistic.setNumPlayers(10);
        statistic.setNumWarLordKilled(10);
        statistic.setNumWonGames(10);
        statistic.setTimePlayed(1);
        Game game = new Game();
        game.setAccessibility(Accessibility.PUBLIC);
        game.setActualOrcs(new ArrayList<>());
        game.setAlivePlayersInTurnOrder(new ArrayList<>());
        game.setAllOrcsInGame(new ArrayList<>());
        game.setCurrentPlayer(player);
        game.setCurrentTurn(turn);
        game.setFinishDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game.setId(1);
        game.setMaxPlayers(3);
        game.setMode(Mode.UNI_CLASS);
        game.setName("Name");
        game.setPassiveOrcs(new ArrayList<>());
        game.setPassword("iloveyou");
        game.setPlayers(new ArrayList<>());
        game.setSpectators(new ArrayList<>());
        game.setStartDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game.setStatistic(statistic);

        Ability ability = new Ability();
        ability.setAttack(1);
        ability.setBackImage("Back Image");
        ability.setFrontImage("Front Image");
        ability.setId(1);
        ability.setMaxUses(3);
        ability.setName("Name");
        ability.setPathName("Path Name");
        ability.setQuantity(1);
        ability.setRole(Role.WIZARD);


        ProductInGame productInGame = new ProductInGame();
        productInGame.setGame(new Game());
        productInGame.setId(1);
        productInGame.setName("Name");
        productInGame.setPlayer(new Player());
        productInGame.setProduct(new Product());
        productInGame.setStateProduct(StateProduct.IN_SALE);
        productInGame.setTimesUsed(1);

        AbilityInGame abilityInGame = new AbilityInGame();
        abilityInGame.setAbility(ability);
        abilityInGame.setAbilityCardType(AbilityCardType.WARRIOR);
        abilityInGame.setAttack(1);
        abilityInGame.setId(1);
        abilityInGame.setPlayer(player);
        abilityInGame.setProduct(true);
        abilityInGame.setProductInGame(productInGame);
        abilityInGame.setTimesUsed(1);


        Enemy enemy = new Enemy();
        enemy.setBackImage("Back Image");
        enemy.setFrontImage("Front Image");
        enemy.setHasCure(true);
        enemy.setHealth(1);
        enemy.setHiddenGlory(1);
        enemy.setHiddenGold(1);
        enemy.setId(1);
        enemy.setIsNightLord(true);
        enemy.setLessDamageWizard(true);
        enemy.setMaxUses(3);
        enemy.setName("Name");
        enemy.setNotHiddenGlory(1);
        enemy.setNotHiddenGold(1);

        EnemyInGame enemyInGame = new EnemyInGame();
        enemyInGame.setActualHealth(1);
        enemyInGame.setEnemy(enemy);
        enemyInGame.setId(1);
        enemyInGame.setNightLord(true);

        Product product = new Product();
        product.setAttack(1);
        product.setBackImage("Back Image");
        product.setCapacity(new ArrayList<>());
        product.setFrontImage("Front Image");
        product.setId(1);
        product.setMaxUses(3);
        product.setName("Name");
        product.setPathName("Path Name");
        product.setPrice(1);
        product.setQuantity(1);

        deck.setInDiscard(List.of(abilityInGame));
        deck.setInHand(List.of(abilityInGame, abilityInGame));
        deck.setInDeck(List.of(abilityInGame));
        player.setDeck(deck);
        game.setCurrentPlayer(player);
        turn.setCurrentAbility(abilityInGame);
        game.setCurrentTurn(turn);

        User user = new User();
        user.setAuthority("JaneDoe");
        user.setAvatar("Avatar");
        user.setBirthDate(LocalDate.ofEpochDay(1L));
        user.setDescription("The characteristics of someone or something");
        user.setEnable("Enable");
        user.setFriends(new ArrayList<>());
        user.setGame(game);
        user.setId(1);
        user.setIsConnected(true);
        user.setPassword("iloveyou");
        user.setPlayer(player);
        user.setReceivedMessages(new ArrayList<>());
        user.setSentMessages(new ArrayList<>());
        user.setStatistic(statistic);
        user.setTier(Tier.IRON);
        user.setUsername("janedoe");

        AbilityInGame actualFromAbilityResult = AbilityInGame.fromAbility(ability, player);
        assertSame(ability, actualFromAbilityResult.getAbility());
        assertNull(actualFromAbilityResult.getAbilityCardType());
        assertFalse(actualFromAbilityResult.isProduct());
        assertSame(player, actualFromAbilityResult.getPlayer());
        assertEquals(1, actualFromAbilityResult.getAttack().intValue());
        assertNull(actualFromAbilityResult.getProductInGame());
        assertEquals(0, actualFromAbilityResult.getTimesUsed().intValue());
    }

    @Test
    void testOnDeleteSetNull() {
        AbilityInGame abilityInGame = new AbilityInGame();
        abilityInGame.onDeleteSetNull();
        assertNull(abilityInGame.getAbility());
        assertNull(abilityInGame.getPlayer());
    }

    @Test
    void testOnDeleteSetNull2() {

        Deck deck = new Deck();
        deck.setId(1);
        deck.setInDeck(new ArrayList<>());
        deck.setInDiscard(new ArrayList<>());
        deck.setInHand(new ArrayList<>());
        Player player = new Player();
        player.setAlive(true);
        player.setBirthDate(null);
        player.setDamageProtect(1);
        player.setDeck(new Deck());
        player.setGame(new Game());
        player.setHasEvasion(true);
        player.setHeroes(new ArrayList<>());
        player.setHost(true);
        player.setId(1);
        player.setName("Name");
        player.setNextPhase(Phase.START);
        player.setReady(true);
        player.setSequence(1);
        player.setStatistic(new Statistic());
        player.setTurns(new ArrayList<>());
        player.setWounds(1);
        Turn turn = new Turn();
        turn.setCurrentEnemy(new EnemyInGame());
        turn.setCurrentProduct(new ProductInGame());
        turn.setGame(new Game());
        turn.setId(1);
        turn.setPhase(Phase.START);
        turn.setPlayer(new Player());
        turn.setUsedAbilities(new ArrayList<>());
        turn.setUsedEnemies(new ArrayList<>());
        turn.setUsedProducts(new ArrayList<>());

        Statistic statistic = new Statistic();
        statistic.setDamageDealt(1);
        statistic.setGlory(1);
        statistic.setGold(1);
        statistic.setId(1);
        statistic.setNumOrcsKilled(10);
        statistic.setNumPlayedGames(10);
        statistic.setNumPlayers(10);
        statistic.setNumWarLordKilled(10);
        statistic.setNumWonGames(10);
        statistic.setTimePlayed(1);
        Game game = new Game();
        game.setAccessibility(Accessibility.PUBLIC);
        game.setActualOrcs(new ArrayList<>());
        game.setAlivePlayersInTurnOrder(new ArrayList<>());
        game.setAllOrcsInGame(new ArrayList<>());
        game.setCurrentPlayer(player);
        game.setCurrentTurn(turn);
        game.setFinishDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game.setId(1);
        game.setMaxPlayers(3);
        game.setMode(Mode.UNI_CLASS);
        game.setName("Name");
        game.setPassiveOrcs(new ArrayList<>());
        game.setPassword("iloveyou");
        game.setPlayers(new ArrayList<>());
        game.setSpectators(new ArrayList<>());
        game.setStartDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game.setStatistic(statistic);

        Ability ability = new Ability();
        ability.setAttack(1);
        ability.setBackImage("Back Image");
        ability.setFrontImage("Front Image");
        ability.setId(1);
        ability.setMaxUses(3);
        ability.setName("Name");
        ability.setPathName("Path Name");
        ability.setQuantity(1);
        ability.setRole(Role.WIZARD);


        ProductInGame productInGame = new ProductInGame();
        productInGame.setGame(new Game());
        productInGame.setId(1);
        productInGame.setName("Name");
        productInGame.setPlayer(new Player());
        productInGame.setProduct(new Product());
        productInGame.setStateProduct(StateProduct.IN_SALE);
        productInGame.setTimesUsed(1);

        AbilityInGame abilityInGame = new AbilityInGame();
        abilityInGame.setAbility(ability);
        abilityInGame.setAbilityCardType(AbilityCardType.WARRIOR);
        abilityInGame.setAttack(1);
        abilityInGame.setId(1);
        abilityInGame.setPlayer(player);
        abilityInGame.setProduct(true);
        abilityInGame.setProductInGame(productInGame);
        abilityInGame.setTimesUsed(1);


        Enemy enemy = new Enemy();
        enemy.setBackImage("Back Image");
        enemy.setFrontImage("Front Image");
        enemy.setHasCure(true);
        enemy.setHealth(1);
        enemy.setHiddenGlory(1);
        enemy.setHiddenGold(1);
        enemy.setId(1);
        enemy.setIsNightLord(true);
        enemy.setLessDamageWizard(true);
        enemy.setMaxUses(3);
        enemy.setName("Name");
        enemy.setNotHiddenGlory(1);
        enemy.setNotHiddenGold(1);

        EnemyInGame enemyInGame = new EnemyInGame();
        enemyInGame.setActualHealth(1);
        enemyInGame.setEnemy(enemy);
        enemyInGame.setId(1);
        enemyInGame.setNightLord(true);

        Product product = new Product();
        product.setAttack(1);
        product.setBackImage("Back Image");
        product.setCapacity(new ArrayList<>());
        product.setFrontImage("Front Image");
        product.setId(1);
        product.setMaxUses(3);
        product.setName("Name");
        product.setPathName("Path Name");
        product.setPrice(1);
        product.setQuantity(1);

        deck.setInDiscard(List.of(abilityInGame));
        deck.setInHand(List.of(abilityInGame, abilityInGame));
        deck.setInDeck(List.of(abilityInGame));
        player.setDeck(deck);
        game.setCurrentPlayer(player);
        turn.setCurrentAbility(abilityInGame);
        game.setCurrentTurn(turn);

        User user = new User();
        user.setAuthority("JaneDoe");
        user.setAvatar("Avatar");
        user.setBirthDate(LocalDate.ofEpochDay(1L));
        user.setDescription("The characteristics of someone or something");
        user.setEnable("Enable");
        user.setFriends(new ArrayList<>());
        user.setGame(game);
        user.setId(1);
        user.setIsConnected(true);
        user.setPassword("iloveyou");
        user.setPlayer(player);
        user.setReceivedMessages(new ArrayList<>());
        user.setSentMessages(new ArrayList<>());
        user.setStatistic(statistic);
        user.setTier(Tier.IRON);
        user.setUsername("janedoe");
        abilityInGame.onDeleteSetNull();


        assertNull(abilityInGame.getAbility());
        assertNull(abilityInGame.getPlayer());
        ProductInGame productInGame1 = abilityInGame.getProductInGame();
        assertNull(productInGame1.getProduct());
        assertNull(productInGame1.getPlayer());
        assertNull(productInGame1.getGame());
    }
}

