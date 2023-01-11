package org.springframework.samples.nt4h.message;

import static org.mockito.Mockito.mock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityCardType;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.Deck;
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
import org.springframework.samples.nt4h.user.UserRepository;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;

@ContextConfiguration(classes = {MessageController.class})
@ExtendWith(SpringExtension.class)
class MessageControllerTest {
    @Autowired
    private MessageController messageController;

    @MockBean
    private MessageService messageService;

    @MockBean
    private UserService userService;

    @Test
    void testInitCreationForm() {

        MessageService messageService = new MessageService(mock(MessageRepository.class));
        MessageController messageController = new MessageController(messageService,
            new UserService(mock(UserRepository.class)));
        messageController.initCreationForm("janedoe", new ModelMap());
    }

    @Test
    @Disabled("TODO: Complete this test")
    void testSendMessage3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   See https://diff.blue/R013 to resolve this issue.

        MessageService messageService = new MessageService(mock(MessageRepository.class));
        MessageController messageController = new MessageController(messageService,
            new UserService(mock(UserRepository.class)));

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
        turn.setCurrentAbility(new AbilityInGame());
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
        game.setHasStages(true);
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

        Statistic statistic1 = new Statistic();
        statistic1.setDamageDealt(1);
        statistic1.setGlory(1);
        statistic1.setGold(1);
        statistic1.setId(1);
        statistic1.setNumOrcsKilled(10);
        statistic1.setNumPlayedGames(10);
        statistic1.setNumPlayers(10);
        statistic1.setNumWarLordKilled(10);
        statistic1.setNumWonGames(10);
        statistic1.setTimePlayed(1);

        Player player1 = new Player();
        player1.setAlive(true);
        player1.setBirthDate(LocalDate.ofEpochDay(1L));
        player1.setDamageProtect(1);
        player1.setDeck(deck);
        player1.setGame(game);
        player1.setHasEvasion(true);
        player1.setHeroes(new ArrayList<>());
        player1.setHost(true);
        player1.setId(1);
        player1.setName("Name");
        player1.setNextPhase(Phase.START);
        player1.setReady(true);
        player1.setSequence(1);
        player1.setStatistic(statistic1);
        player1.setTurns(new ArrayList<>());
        player1.setWounds(1);

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

        Player player2 = new Player();
        player2.setAlive(true);
        player2.setBirthDate(null);
        player2.setDamageProtect(1);
        player2.setDeck(new Deck());
        player2.setGame(new Game());
        player2.setHasEvasion(true);
        player2.setHeroes(new ArrayList<>());
        player2.setHost(true);
        player2.setId(1);
        player2.setName("Name");
        player2.setNextPhase(Phase.START);
        player2.setReady(true);
        player2.setSequence(1);
        player2.setStatistic(new Statistic());
        player2.setTurns(new ArrayList<>());
        player2.setWounds(1);

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
        abilityInGame.setPlayer(player2);
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

        Game game1 = new Game();
        game1.setAccessibility(Accessibility.PUBLIC);
        game1.setActualOrcs(new ArrayList<>());
        game1.setAlivePlayersInTurnOrder(new ArrayList<>());
        game1.setAllOrcsInGame(new ArrayList<>());
        game1.setCurrentPlayer(new Player());
        game1.setCurrentTurn(new Turn());
        game1.setFinishDate(null);
        game1.setHasStages(true);
        game1.setId(1);
        game1.setMaxPlayers(3);
        game1.setMode(Mode.UNI_CLASS);
        game1.setName("Name");
        game1.setPassiveOrcs(new ArrayList<>());
        game1.setPassword("iloveyou");
        game1.setPlayers(new ArrayList<>());
        game1.setSpectators(new ArrayList<>());
        game1.setStartDate(null);
        game1.setStatistic(new Statistic());

        Player player3 = new Player();
        player3.setAlive(true);
        player3.setBirthDate(null);
        player3.setDamageProtect(1);
        player3.setDeck(new Deck());
        player3.setGame(new Game());
        player3.setHasEvasion(true);
        player3.setHeroes(new ArrayList<>());
        player3.setHost(true);
        player3.setId(1);
        player3.setName("Name");
        player3.setNextPhase(Phase.START);
        player3.setReady(true);
        player3.setSequence(1);
        player3.setStatistic(new Statistic());
        player3.setTurns(new ArrayList<>());
        player3.setWounds(1);

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

        ProductInGame productInGame1 = new ProductInGame();
        productInGame1.setGame(game1);
        productInGame1.setId(1);
        productInGame1.setName("Name");
        productInGame1.setPlayer(player3);
        productInGame1.setProduct(product);
        productInGame1.setStateProduct(StateProduct.IN_SALE);
        productInGame1.setTimesUsed(1);

        Player player4 = new Player();
        player4.setAlive(true);
        player4.setBirthDate(null);
        player4.setDamageProtect(1);
        player4.setDeck(new Deck());
        player4.setGame(new Game());
        player4.setHasEvasion(true);
        player4.setHeroes(new ArrayList<>());
        player4.setHost(true);
        player4.setId(1);
        player4.setName("Name");
        player4.setNextPhase(Phase.START);
        player4.setReady(true);
        player4.setSequence(1);
        player4.setStatistic(new Statistic());
        player4.setTurns(new ArrayList<>());
        player4.setWounds(1);

        Turn turn1 = new Turn();
        turn1.setCurrentAbility(new AbilityInGame());
        turn1.setCurrentEnemy(new EnemyInGame());
        turn1.setCurrentProduct(new ProductInGame());
        turn1.setGame(new Game());
        turn1.setId(1);
        turn1.setPhase(Phase.START);
        turn1.setPlayer(new Player());
        turn1.setUsedAbilities(new ArrayList<>());
        turn1.setUsedEnemies(new ArrayList<>());
        turn1.setUsedProducts(new ArrayList<>());

        Statistic statistic2 = new Statistic();
        statistic2.setDamageDealt(1);
        statistic2.setGlory(1);
        statistic2.setGold(1);
        statistic2.setId(1);
        statistic2.setNumOrcsKilled(10);
        statistic2.setNumPlayedGames(10);
        statistic2.setNumPlayers(10);
        statistic2.setNumWarLordKilled(10);
        statistic2.setNumWonGames(10);
        statistic2.setTimePlayed(1);

        Game game2 = new Game();
        game2.setAccessibility(Accessibility.PUBLIC);
        game2.setActualOrcs(new ArrayList<>());
        game2.setAlivePlayersInTurnOrder(new ArrayList<>());
        game2.setAllOrcsInGame(new ArrayList<>());
        game2.setCurrentPlayer(player4);
        game2.setCurrentTurn(turn1);
        game2.setFinishDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game2.setHasStages(true);
        game2.setId(1);
        game2.setMaxPlayers(3);
        game2.setMode(Mode.UNI_CLASS);
        game2.setName("Name");
        game2.setPassiveOrcs(new ArrayList<>());
        game2.setPassword("iloveyou");
        game2.setPlayers(new ArrayList<>());
        game2.setSpectators(new ArrayList<>());
        game2.setStartDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game2.setStatistic(statistic2);

        Deck deck1 = new Deck();
        deck1.setId(1);
        deck1.setInDeck(new ArrayList<>());
        deck1.setInDiscard(new ArrayList<>());
        deck1.setInHand(new ArrayList<>());

        Game game3 = new Game();
        game3.setAccessibility(Accessibility.PUBLIC);
        game3.setActualOrcs(new ArrayList<>());
        game3.setAlivePlayersInTurnOrder(new ArrayList<>());
        game3.setAllOrcsInGame(new ArrayList<>());
        game3.setCurrentPlayer(new Player());
        game3.setCurrentTurn(new Turn());
        game3.setFinishDate(null);
        game3.setHasStages(true);
        game3.setId(1);
        game3.setMaxPlayers(3);
        game3.setMode(Mode.UNI_CLASS);
        game3.setName("Name");
        game3.setPassiveOrcs(new ArrayList<>());
        game3.setPassword("iloveyou");
        game3.setPlayers(new ArrayList<>());
        game3.setSpectators(new ArrayList<>());
        game3.setStartDate(null);
        game3.setStatistic(new Statistic());

        Statistic statistic3 = new Statistic();
        statistic3.setDamageDealt(1);
        statistic3.setGlory(1);
        statistic3.setGold(1);
        statistic3.setId(1);
        statistic3.setNumOrcsKilled(10);
        statistic3.setNumPlayedGames(10);
        statistic3.setNumPlayers(10);
        statistic3.setNumWarLordKilled(10);
        statistic3.setNumWonGames(10);
        statistic3.setTimePlayed(1);

        Player player5 = new Player();
        player5.setAlive(true);
        player5.setBirthDate(LocalDate.ofEpochDay(1L));
        player5.setDamageProtect(1);
        player5.setDeck(deck1);
        player5.setGame(game3);
        player5.setHasEvasion(true);
        player5.setHeroes(new ArrayList<>());
        player5.setHost(true);
        player5.setId(1);
        player5.setName("Name");
        player5.setNextPhase(Phase.START);
        player5.setReady(true);
        player5.setSequence(1);
        player5.setStatistic(statistic3);
        player5.setTurns(new ArrayList<>());
        player5.setWounds(1);

        Turn turn2 = new Turn();
        turn2.setCurrentAbility(abilityInGame);
        turn2.setCurrentEnemy(enemyInGame);
        turn2.setCurrentProduct(productInGame1);
        turn2.setGame(game2);
        turn2.setId(1);
        turn2.setPhase(Phase.START);
        turn2.setPlayer(player5);
        turn2.setUsedAbilities(new ArrayList<>());
        turn2.setUsedEnemies(new ArrayList<>());
        turn2.setUsedProducts(new ArrayList<>());

        Statistic statistic4 = new Statistic();
        statistic4.setDamageDealt(1);
        statistic4.setGlory(1);
        statistic4.setGold(1);
        statistic4.setId(1);
        statistic4.setNumOrcsKilled(10);
        statistic4.setNumPlayedGames(10);
        statistic4.setNumPlayers(10);
        statistic4.setNumWarLordKilled(10);
        statistic4.setNumWonGames(10);
        statistic4.setTimePlayed(1);

        Game game4 = new Game();
        game4.setAccessibility(Accessibility.PUBLIC);
        game4.setActualOrcs(new ArrayList<>());
        game4.setAlivePlayersInTurnOrder(new ArrayList<>());
        game4.setAllOrcsInGame(new ArrayList<>());
        game4.setCurrentPlayer(player1);
        game4.setCurrentTurn(turn2);
        game4.setFinishDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game4.setHasStages(true);
        game4.setId(1);
        game4.setMaxPlayers(3);
        game4.setMode(Mode.UNI_CLASS);
        game4.setName("Name");
        game4.setPassiveOrcs(new ArrayList<>());
        game4.setPassword("iloveyou");
        game4.setPlayers(new ArrayList<>());
        game4.setSpectators(new ArrayList<>());
        game4.setStartDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game4.setStatistic(statistic4);

        Deck deck2 = new Deck();
        deck2.setId(1);
        deck2.setInDeck(new ArrayList<>());
        deck2.setInDiscard(new ArrayList<>());
        deck2.setInHand(new ArrayList<>());

        Game game5 = new Game();
        game5.setAccessibility(Accessibility.PUBLIC);
        game5.setActualOrcs(new ArrayList<>());
        game5.setAlivePlayersInTurnOrder(new ArrayList<>());
        game5.setAllOrcsInGame(new ArrayList<>());
        game5.setCurrentPlayer(new Player());
        game5.setCurrentTurn(new Turn());
        game5.setFinishDate(null);
        game5.setHasStages(true);
        game5.setId(1);
        game5.setMaxPlayers(3);
        game5.setMode(Mode.UNI_CLASS);
        game5.setName("Name");
        game5.setPassiveOrcs(new ArrayList<>());
        game5.setPassword("iloveyou");
        game5.setPlayers(new ArrayList<>());
        game5.setSpectators(new ArrayList<>());
        game5.setStartDate(null);
        game5.setStatistic(new Statistic());

        Statistic statistic5 = new Statistic();
        statistic5.setDamageDealt(1);
        statistic5.setGlory(1);
        statistic5.setGold(1);
        statistic5.setId(1);
        statistic5.setNumOrcsKilled(10);
        statistic5.setNumPlayedGames(10);
        statistic5.setNumPlayers(10);
        statistic5.setNumWarLordKilled(10);
        statistic5.setNumWonGames(10);
        statistic5.setTimePlayed(1);

        Player player6 = new Player();
        player6.setAlive(true);
        player6.setBirthDate(LocalDate.ofEpochDay(1L));
        player6.setDamageProtect(1);
        player6.setDeck(deck2);
        player6.setGame(game5);
        player6.setHasEvasion(true);
        player6.setHeroes(new ArrayList<>());
        player6.setHost(true);
        player6.setId(1);
        player6.setName("Name");
        player6.setNextPhase(Phase.START);
        player6.setReady(true);
        player6.setSequence(1);
        player6.setStatistic(statistic5);
        player6.setTurns(new ArrayList<>());
        player6.setWounds(1);

        AbilityInGame abilityInGame1 = new AbilityInGame();
        abilityInGame1.setAbility(new Ability());
        abilityInGame1.setAbilityCardType(AbilityCardType.WARRIOR);
        abilityInGame1.setAttack(1);
        abilityInGame1.setId(1);
        abilityInGame1.setPlayer(new Player());
        abilityInGame1.setProduct(true);
        abilityInGame1.setProductInGame(new ProductInGame());
        abilityInGame1.setTimesUsed(1);

        EnemyInGame enemyInGame1 = new EnemyInGame();
        enemyInGame1.setActualHealth(1);
        enemyInGame1.setEnemy(new Enemy());
        enemyInGame1.setId(1);
        enemyInGame1.setNightLord(true);

        ProductInGame productInGame2 = new ProductInGame();
        productInGame2.setGame(new Game());
        productInGame2.setId(1);
        productInGame2.setName("Name");
        productInGame2.setPlayer(new Player());
        productInGame2.setProduct(new Product());
        productInGame2.setStateProduct(StateProduct.IN_SALE);
        productInGame2.setTimesUsed(1);

        Game game6 = new Game();
        game6.setAccessibility(Accessibility.PUBLIC);
        game6.setActualOrcs(new ArrayList<>());
        game6.setAlivePlayersInTurnOrder(new ArrayList<>());
        game6.setAllOrcsInGame(new ArrayList<>());
        game6.setCurrentPlayer(new Player());
        game6.setCurrentTurn(new Turn());
        game6.setFinishDate(null);
        game6.setHasStages(true);
        game6.setId(1);
        game6.setMaxPlayers(3);
        game6.setMode(Mode.UNI_CLASS);
        game6.setName("Name");
        game6.setPassiveOrcs(new ArrayList<>());
        game6.setPassword("iloveyou");
        game6.setPlayers(new ArrayList<>());
        game6.setSpectators(new ArrayList<>());
        game6.setStartDate(null);
        game6.setStatistic(new Statistic());

        Player player7 = new Player();
        player7.setAlive(true);
        player7.setBirthDate(null);
        player7.setDamageProtect(1);
        player7.setDeck(new Deck());
        player7.setGame(new Game());
        player7.setHasEvasion(true);
        player7.setHeroes(new ArrayList<>());
        player7.setHost(true);
        player7.setId(1);
        player7.setName("Name");
        player7.setNextPhase(Phase.START);
        player7.setReady(true);
        player7.setSequence(1);
        player7.setStatistic(new Statistic());
        player7.setTurns(new ArrayList<>());
        player7.setWounds(1);

        Turn turn3 = new Turn();
        turn3.setCurrentAbility(abilityInGame1);
        turn3.setCurrentEnemy(enemyInGame1);
        turn3.setCurrentProduct(productInGame2);
        turn3.setGame(game6);
        turn3.setId(1);
        turn3.setPhase(Phase.START);
        turn3.setPlayer(player7);
        turn3.setUsedAbilities(new ArrayList<>());
        turn3.setUsedEnemies(new ArrayList<>());
        turn3.setUsedProducts(new ArrayList<>());

        Statistic statistic6 = new Statistic();
        statistic6.setDamageDealt(1);
        statistic6.setGlory(1);
        statistic6.setGold(1);
        statistic6.setId(1);
        statistic6.setNumOrcsKilled(10);
        statistic6.setNumPlayedGames(10);
        statistic6.setNumPlayers(10);
        statistic6.setNumWarLordKilled(10);
        statistic6.setNumWonGames(10);
        statistic6.setTimePlayed(1);

        Game game7 = new Game();
        game7.setAccessibility(Accessibility.PUBLIC);
        game7.setActualOrcs(new ArrayList<>());
        game7.setAlivePlayersInTurnOrder(new ArrayList<>());
        game7.setAllOrcsInGame(new ArrayList<>());
        game7.setCurrentPlayer(player6);
        game7.setCurrentTurn(turn3);
        game7.setFinishDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game7.setHasStages(true);
        game7.setId(1);
        game7.setMaxPlayers(3);
        game7.setMode(Mode.UNI_CLASS);
        game7.setName("Name");
        game7.setPassiveOrcs(new ArrayList<>());
        game7.setPassword("iloveyou");
        game7.setPlayers(new ArrayList<>());
        game7.setSpectators(new ArrayList<>());
        game7.setStartDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game7.setStatistic(statistic6);

        Deck deck3 = new Deck();
        deck3.setId(1);
        deck3.setInDeck(new ArrayList<>());
        deck3.setInDiscard(new ArrayList<>());
        deck3.setInHand(new ArrayList<>());

        Player player8 = new Player();
        player8.setAlive(true);
        player8.setBirthDate(null);
        player8.setDamageProtect(1);
        player8.setDeck(new Deck());
        player8.setGame(new Game());
        player8.setHasEvasion(true);
        player8.setHeroes(new ArrayList<>());
        player8.setHost(true);
        player8.setId(1);
        player8.setName("Name");
        player8.setNextPhase(Phase.START);
        player8.setReady(true);
        player8.setSequence(1);
        player8.setStatistic(new Statistic());
        player8.setTurns(new ArrayList<>());
        player8.setWounds(1);

        Turn turn4 = new Turn();
        turn4.setCurrentAbility(new AbilityInGame());
        turn4.setCurrentEnemy(new EnemyInGame());
        turn4.setCurrentProduct(new ProductInGame());
        turn4.setGame(new Game());
        turn4.setId(1);
        turn4.setPhase(Phase.START);
        turn4.setPlayer(new Player());
        turn4.setUsedAbilities(new ArrayList<>());
        turn4.setUsedEnemies(new ArrayList<>());
        turn4.setUsedProducts(new ArrayList<>());

        Statistic statistic7 = new Statistic();
        statistic7.setDamageDealt(1);
        statistic7.setGlory(1);
        statistic7.setGold(1);
        statistic7.setId(1);
        statistic7.setNumOrcsKilled(10);
        statistic7.setNumPlayedGames(10);
        statistic7.setNumPlayers(10);
        statistic7.setNumWarLordKilled(10);
        statistic7.setNumWonGames(10);
        statistic7.setTimePlayed(1);

        Game game8 = new Game();
        game8.setAccessibility(Accessibility.PUBLIC);
        game8.setActualOrcs(new ArrayList<>());
        game8.setAlivePlayersInTurnOrder(new ArrayList<>());
        game8.setAllOrcsInGame(new ArrayList<>());
        game8.setCurrentPlayer(player8);
        game8.setCurrentTurn(turn4);
        game8.setFinishDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game8.setHasStages(true);
        game8.setId(1);
        game8.setMaxPlayers(3);
        game8.setMode(Mode.UNI_CLASS);
        game8.setName("Name");
        game8.setPassiveOrcs(new ArrayList<>());
        game8.setPassword("iloveyou");
        game8.setPlayers(new ArrayList<>());
        game8.setSpectators(new ArrayList<>());
        game8.setStartDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game8.setStatistic(statistic7);

        Statistic statistic8 = new Statistic();
        statistic8.setDamageDealt(1);
        statistic8.setGlory(1);
        statistic8.setGold(1);
        statistic8.setId(1);
        statistic8.setNumOrcsKilled(10);
        statistic8.setNumPlayedGames(10);
        statistic8.setNumPlayers(10);
        statistic8.setNumWarLordKilled(10);
        statistic8.setNumWonGames(10);
        statistic8.setTimePlayed(1);

        Player player9 = new Player();
        player9.setAlive(true);
        player9.setBirthDate(LocalDate.ofEpochDay(1L));
        player9.setDamageProtect(1);
        player9.setDeck(deck3);
        player9.setGame(game8);
        player9.setHasEvasion(true);
        player9.setHeroes(new ArrayList<>());
        player9.setHost(true);
        player9.setId(1);
        player9.setName("Name");
        player9.setNextPhase(Phase.START);
        player9.setReady(true);
        player9.setSequence(1);
        player9.setStatistic(statistic8);
        player9.setTurns(new ArrayList<>());
        player9.setWounds(1);

        Statistic statistic9 = new Statistic();
        statistic9.setDamageDealt(1);
        statistic9.setGlory(1);
        statistic9.setGold(1);
        statistic9.setId(1);
        statistic9.setNumOrcsKilled(10);
        statistic9.setNumPlayedGames(10);
        statistic9.setNumPlayers(10);
        statistic9.setNumWarLordKilled(10);
        statistic9.setNumWonGames(10);
        statistic9.setTimePlayed(1);

        User user = new User();
        user.setAuthority("JaneDoe");
        user.setAvatar("Avatar");
        user.setBirthDate(LocalDate.ofEpochDay(1L));
        user.setDescription("The characteristics of someone or something");
        user.setEnable("Enable");
        user.setFriends(new ArrayList<>());
        user.setGame(game7);
        user.setId(1);
        user.setIsConnected(true);
        user.setPassword("iloveyou");
        user.setPlayer(player9);
        user.setReceivedMessages(new ArrayList<>());
        user.setSentMessages(new ArrayList<>());
        user.setStatistic(statistic9);
        user.setTier(Tier.IRON);
        user.setUsername("janedoe");

        Deck deck4 = new Deck();
        deck4.setId(1);
        deck4.setInDeck(new ArrayList<>());
        deck4.setInDiscard(new ArrayList<>());
        deck4.setInHand(new ArrayList<>());

        Game game9 = new Game();
        game9.setAccessibility(Accessibility.PUBLIC);
        game9.setActualOrcs(new ArrayList<>());
        game9.setAlivePlayersInTurnOrder(new ArrayList<>());
        game9.setAllOrcsInGame(new ArrayList<>());
        game9.setCurrentPlayer(new Player());
        game9.setCurrentTurn(new Turn());
        game9.setFinishDate(null);
        game9.setHasStages(true);
        game9.setId(1);
        game9.setMaxPlayers(3);
        game9.setMode(Mode.UNI_CLASS);
        game9.setName("Name");
        game9.setPassiveOrcs(new ArrayList<>());
        game9.setPassword("iloveyou");
        game9.setPlayers(new ArrayList<>());
        game9.setSpectators(new ArrayList<>());
        game9.setStartDate(null);
        game9.setStatistic(new Statistic());

        Statistic statistic10 = new Statistic();
        statistic10.setDamageDealt(1);
        statistic10.setGlory(1);
        statistic10.setGold(1);
        statistic10.setId(1);
        statistic10.setNumOrcsKilled(10);
        statistic10.setNumPlayedGames(10);
        statistic10.setNumPlayers(10);
        statistic10.setNumWarLordKilled(10);
        statistic10.setNumWonGames(10);
        statistic10.setTimePlayed(1);

        Player player10 = new Player();
        player10.setAlive(true);
        player10.setBirthDate(LocalDate.ofEpochDay(1L));
        player10.setDamageProtect(1);
        player10.setDeck(deck4);
        player10.setGame(game9);
        player10.setHasEvasion(true);
        player10.setHeroes(new ArrayList<>());
        player10.setHost(true);
        player10.setId(1);
        player10.setName("Name");
        player10.setNextPhase(Phase.START);
        player10.setReady(true);
        player10.setSequence(1);
        player10.setStatistic(statistic10);
        player10.setTurns(new ArrayList<>());
        player10.setWounds(1);

        AbilityInGame abilityInGame2 = new AbilityInGame();
        abilityInGame2.setAbility(new Ability());
        abilityInGame2.setAbilityCardType(AbilityCardType.WARRIOR);
        abilityInGame2.setAttack(1);
        abilityInGame2.setId(1);
        abilityInGame2.setPlayer(new Player());
        abilityInGame2.setProduct(true);
        abilityInGame2.setProductInGame(new ProductInGame());
        abilityInGame2.setTimesUsed(1);

        EnemyInGame enemyInGame2 = new EnemyInGame();
        enemyInGame2.setActualHealth(1);
        enemyInGame2.setEnemy(new Enemy());
        enemyInGame2.setId(1);
        enemyInGame2.setNightLord(true);

        ProductInGame productInGame3 = new ProductInGame();
        productInGame3.setGame(new Game());
        productInGame3.setId(1);
        productInGame3.setName("Name");
        productInGame3.setPlayer(new Player());
        productInGame3.setProduct(new Product());
        productInGame3.setStateProduct(StateProduct.IN_SALE);
        productInGame3.setTimesUsed(1);

        Game game10 = new Game();
        game10.setAccessibility(Accessibility.PUBLIC);
        game10.setActualOrcs(new ArrayList<>());
        game10.setAlivePlayersInTurnOrder(new ArrayList<>());
        game10.setAllOrcsInGame(new ArrayList<>());
        game10.setCurrentPlayer(new Player());
        game10.setCurrentTurn(new Turn());
        game10.setFinishDate(null);
        game10.setHasStages(true);
        game10.setId(1);
        game10.setMaxPlayers(3);
        game10.setMode(Mode.UNI_CLASS);
        game10.setName("Name");
        game10.setPassiveOrcs(new ArrayList<>());
        game10.setPassword("iloveyou");
        game10.setPlayers(new ArrayList<>());
        game10.setSpectators(new ArrayList<>());
        game10.setStartDate(null);
        game10.setStatistic(new Statistic());

        Player player11 = new Player();
        player11.setAlive(true);
        player11.setBirthDate(null);
        player11.setDamageProtect(1);
        player11.setDeck(new Deck());
        player11.setGame(new Game());
        player11.setHasEvasion(true);
        player11.setHeroes(new ArrayList<>());
        player11.setHost(true);
        player11.setId(1);
        player11.setName("Name");
        player11.setNextPhase(Phase.START);
        player11.setReady(true);
        player11.setSequence(1);
        player11.setStatistic(new Statistic());
        player11.setTurns(new ArrayList<>());
        player11.setWounds(1);

        Turn turn5 = new Turn();
        turn5.setCurrentAbility(abilityInGame2);
        turn5.setCurrentEnemy(enemyInGame2);
        turn5.setCurrentProduct(productInGame3);
        turn5.setGame(game10);
        turn5.setId(1);
        turn5.setPhase(Phase.START);
        turn5.setPlayer(player11);
        turn5.setUsedAbilities(new ArrayList<>());
        turn5.setUsedEnemies(new ArrayList<>());
        turn5.setUsedProducts(new ArrayList<>());

        Statistic statistic11 = new Statistic();
        statistic11.setDamageDealt(1);
        statistic11.setGlory(1);
        statistic11.setGold(1);
        statistic11.setId(1);
        statistic11.setNumOrcsKilled(10);
        statistic11.setNumPlayedGames(10);
        statistic11.setNumPlayers(10);
        statistic11.setNumWarLordKilled(10);
        statistic11.setNumWonGames(10);
        statistic11.setTimePlayed(1);

        Game game11 = new Game();
        game11.setAccessibility(Accessibility.PUBLIC);
        game11.setActualOrcs(new ArrayList<>());
        game11.setAlivePlayersInTurnOrder(new ArrayList<>());
        game11.setAllOrcsInGame(new ArrayList<>());
        game11.setCurrentPlayer(player10);
        game11.setCurrentTurn(turn5);
        game11.setFinishDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game11.setHasStages(true);
        game11.setId(1);
        game11.setMaxPlayers(3);
        game11.setMode(Mode.UNI_CLASS);
        game11.setName("Name");
        game11.setPassiveOrcs(new ArrayList<>());
        game11.setPassword("iloveyou");
        game11.setPlayers(new ArrayList<>());
        game11.setSpectators(new ArrayList<>());
        game11.setStartDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game11.setStatistic(statistic11);

        Deck deck5 = new Deck();
        deck5.setId(1);
        deck5.setInDeck(new ArrayList<>());
        deck5.setInDiscard(new ArrayList<>());
        deck5.setInHand(new ArrayList<>());

        Player player12 = new Player();
        player12.setAlive(true);
        player12.setBirthDate(null);
        player12.setDamageProtect(1);
        player12.setDeck(new Deck());
        player12.setGame(new Game());
        player12.setHasEvasion(true);
        player12.setHeroes(new ArrayList<>());
        player12.setHost(true);
        player12.setId(1);
        player12.setName("Name");
        player12.setNextPhase(Phase.START);
        player12.setReady(true);
        player12.setSequence(1);
        player12.setStatistic(new Statistic());
        player12.setTurns(new ArrayList<>());
        player12.setWounds(1);

        Turn turn6 = new Turn();
        turn6.setCurrentAbility(new AbilityInGame());
        turn6.setCurrentEnemy(new EnemyInGame());
        turn6.setCurrentProduct(new ProductInGame());
        turn6.setGame(new Game());
        turn6.setId(1);
        turn6.setPhase(Phase.START);
        turn6.setPlayer(new Player());
        turn6.setUsedAbilities(new ArrayList<>());
        turn6.setUsedEnemies(new ArrayList<>());
        turn6.setUsedProducts(new ArrayList<>());

        Statistic statistic12 = new Statistic();
        statistic12.setDamageDealt(1);
        statistic12.setGlory(1);
        statistic12.setGold(1);
        statistic12.setId(1);
        statistic12.setNumOrcsKilled(10);
        statistic12.setNumPlayedGames(10);
        statistic12.setNumPlayers(10);
        statistic12.setNumWarLordKilled(10);
        statistic12.setNumWonGames(10);
        statistic12.setTimePlayed(1);

        Game game12 = new Game();
        game12.setAccessibility(Accessibility.PUBLIC);
        game12.setActualOrcs(new ArrayList<>());
        game12.setAlivePlayersInTurnOrder(new ArrayList<>());
        game12.setAllOrcsInGame(new ArrayList<>());
        game12.setCurrentPlayer(player12);
        game12.setCurrentTurn(turn6);
        game12.setFinishDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game12.setHasStages(true);
        game12.setId(1);
        game12.setMaxPlayers(3);
        game12.setMode(Mode.UNI_CLASS);
        game12.setName("Name");
        game12.setPassiveOrcs(new ArrayList<>());
        game12.setPassword("iloveyou");
        game12.setPlayers(new ArrayList<>());
        game12.setSpectators(new ArrayList<>());
        game12.setStartDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game12.setStatistic(statistic12);

        Statistic statistic13 = new Statistic();
        statistic13.setDamageDealt(1);
        statistic13.setGlory(1);
        statistic13.setGold(1);
        statistic13.setId(1);
        statistic13.setNumOrcsKilled(10);
        statistic13.setNumPlayedGames(10);
        statistic13.setNumPlayers(10);
        statistic13.setNumWarLordKilled(10);
        statistic13.setNumWonGames(10);
        statistic13.setTimePlayed(1);

        Player player13 = new Player();
        player13.setAlive(true);
        player13.setBirthDate(LocalDate.ofEpochDay(1L));
        player13.setDamageProtect(1);
        player13.setDeck(deck5);
        player13.setGame(game12);
        player13.setHasEvasion(true);
        player13.setHeroes(new ArrayList<>());
        player13.setHost(true);
        player13.setId(1);
        player13.setName("Name");
        player13.setNextPhase(Phase.START);
        player13.setReady(true);
        player13.setSequence(1);
        player13.setStatistic(statistic13);
        player13.setTurns(new ArrayList<>());
        player13.setWounds(1);

        Statistic statistic14 = new Statistic();
        statistic14.setDamageDealt(1);
        statistic14.setGlory(1);
        statistic14.setGold(1);
        statistic14.setId(1);
        statistic14.setNumOrcsKilled(10);
        statistic14.setNumPlayedGames(10);
        statistic14.setNumPlayers(10);
        statistic14.setNumWarLordKilled(10);
        statistic14.setNumWonGames(10);
        statistic14.setTimePlayed(1);

        User user1 = new User();
        user1.setAuthority("JaneDoe");
        user1.setAvatar("Avatar");
        user1.setBirthDate(LocalDate.ofEpochDay(1L));
        user1.setDescription("The characteristics of someone or something");
        user1.setEnable("Enable");
        user1.setFriends(new ArrayList<>());
        user1.setGame(game11);
        user1.setId(1);
        user1.setIsConnected(true);
        user1.setPassword("iloveyou");
        user1.setPlayer(player13);
        user1.setReceivedMessages(new ArrayList<>());
        user1.setSentMessages(new ArrayList<>());
        user1.setStatistic(statistic14);
        user1.setTier(Tier.IRON);
        user1.setUsername("janedoe");

        Message message = new Message();
        message.setContent("Not all who wander are lost");
        message.setGame(game4);
        message.setId(1);
        message.setRead(true);
        message.setReceiver(user);
        message.setSender(user1);
        message.setTime(LocalDateTime.of(1, 1, 1, 1, 1));
        message.setType(MessageType.CHAT);
        messageController.sendMessage(message, new MockHttpSession());
    }

    /**
     * Method under test: {@link MessageController#sendInviteMessage(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testSendInviteMessage() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //   See https://diff.blue/R013 to resolve this issue.

        MessageService messageService = new MessageService(mock(MessageRepository.class));
        (new MessageController(messageService, new UserService(mock(UserRepository.class)))).sendInviteMessage("janedoe");
    }

    /**
     * Method under test: {@link MessageController#sendMessage(Message, String)}
     */
    @Test
    void testSendMessage() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/messages/{username}", "janedoe");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messageController)
            .build()
            .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link MessageController#sendMessage(Message, String)}
     */
    @Test
    void testSendMessage2() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/messages/{username}", "janedoe");
        postResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(messageController)
            .build()
            .perform(postResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

