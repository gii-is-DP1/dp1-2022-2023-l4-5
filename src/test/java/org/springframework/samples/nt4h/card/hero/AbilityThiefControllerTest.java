package org.springframework.samples.nt4h.card.hero;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityCardType;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.ability.DeckService;
import org.springframework.samples.nt4h.card.enemy.Enemy;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.card.product.Product;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.card.product.StateProduct;
import org.springframework.samples.nt4h.game.Accessibility;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.statistic.StatisticService;
import org.springframework.samples.nt4h.turn.Phase;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AbilityThiefController.class})
@ExtendWith(SpringExtension.class)
class AbilityThiefControllerTest {
    @MockBean
    private AbilityService abilityService;

    @Autowired
    private AbilityThiefController abilityThiefController;

    @MockBean
    private CacheManager cacheManager;

    @MockBean
    private DeckService deckService;

    @MockBean
    private EnemyService enemyService;

    @MockBean
    private GameService gameService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private StatisticService statisticService;

    @MockBean
    private TurnService turnService;

    @MockBean
    private UserService userService;

    private Player player;
    private Game game;
    private User user;
    private Turn turn;
    private AbilityInGame abilityInGame;
    private Ability ability;
    private EnemyInGame enemyInGame;

    @BeforeEach
    void setUp() {
        Deck deck = new Deck();
        deck.setId(1);
        deck.setInDeck(new ArrayList<>());
        deck.setInDiscard(new ArrayList<>());
        deck.setInHand(new ArrayList<>());
        player = new Player();
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
        turn = new Turn();
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
        game = new Game();
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
        game.setName("Juego Test");
        game.setPassiveOrcs(new ArrayList<>());
        game.setPassword("malo");
        game.setPlayers(new ArrayList<>());
        game.setSpectators(new ArrayList<>());
        game.setStartDate(LocalDateTime.of(1, 1, 1, 1, 1));
        game.setStatistic(statistic);

        ability = new Ability();
        ability.setAttack(1);
        ability.setBackImage("Back Image");
        ability.setFrontImage("Front Image");
        ability.setId(1);
        ability.setMaxUses(3);
        ability.setName("Abilidad");
        ability.setPathName("Path Name");
        ability.setQuantity(1);
        ability.setRole(Role.WIZARD);


        ProductInGame productInGame = new ProductInGame();
        productInGame.setGame(new Game());
        productInGame.setId(1);
        productInGame.setName("Daga");
        productInGame.setPlayer(new Player());
        productInGame.setProduct(new Product());
        productInGame.setStateProduct(StateProduct.IN_SALE);
        productInGame.setTimesUsed(1);

        abilityInGame = new AbilityInGame();
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
        enemy.setName("Orco");
        enemy.setNotHiddenGlory(1);
        enemy.setNotHiddenGold(1);

        enemyInGame = new EnemyInGame();
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
        deck.setInHand(List.of(abilityInGame));
        deck.setInDeck(List.of(abilityInGame));
        player.setStatistic(statistic);
        player.setDeck(deck);
        game.setCurrentPlayer(player);
        turn.setCurrentAbility(abilityInGame);
        game.setCurrentTurn(turn);

        user = new User();
        user.setAuthority("DOKTOL");
        user.setAvatar("Avatar");
        user.setBirthDate(LocalDate.ofEpochDay(1L));
        user.setDescription("Wink");
        user.setEnable("Enable");
        user.setFriends(new ArrayList<>());
        user.setGame(game);
        user.setId(1);
        user.setIsConnected(true);
        user.setPassword("rocker");
        user.setPlayer(player);
        user.setReceivedMessages(new ArrayList<>());
        user.setSentMessages(new ArrayList<>());
        user.setStatistic(statistic);
        user.setTier(Tier.IRON);
        user.setUsername("The Rock");
    }

    @Test
    void testDeceive() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/abilities/deceive");
        MockMvcBuilders.standaloneSetup(abilityThiefController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(4))
            .andExpect(MockMvcResultMatchers.model().attributeExists("currentPlayer", "game", "loggedPlayer", "loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/heroAttack/makeDamage"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/heroAttack/makeDamage"));
    }

    @Test
    void testInTheShadows() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/abilities/inTheShadows");
        MockMvcBuilders.standaloneSetup(abilityThiefController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(4))
            .andExpect(
                MockMvcResultMatchers.model().attributeExists("currentPlayer", "game", "loggedPlayer", "loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/heroAttack/makeDamage"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/heroAttack/makeDamage"));
    }

    @Test
    void testLoot1() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/abilities/loot1");
        MockMvcBuilders.standaloneSetup(abilityThiefController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(4))
            .andExpect(
                MockMvcResultMatchers.model().attributeExists("currentPlayer", "game", "loggedPlayer", "loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/heroAttack/makeDamage"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/heroAttack/makeDamage"));
    }

    @Test
    void testLoot2() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/abilities/loot2");
        MockMvcBuilders.standaloneSetup(abilityThiefController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(4))
            .andExpect(
                MockMvcResultMatchers.model().attributeExists("currentPlayer", "game", "loggedPlayer", "loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/heroAttack/makeDamage"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/heroAttack/makeDamage"));
    }

    @Test
    void testPreciseBow() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/abilities/preciseBow");
        MockMvcBuilders.standaloneSetup(abilityThiefController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(4))
            .andExpect(
                MockMvcResultMatchers.model().attributeExists("currentPlayer", "game", "loggedPlayer", "loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/heroAttack/makeDamage"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/heroAttack/makeDamage"));
    }

    @Test
    void testStealPockets() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/abilities/stealPockets");
        MockMvcBuilders.standaloneSetup(abilityThiefController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(4))
            .andExpect(
                MockMvcResultMatchers.model().attributeExists("currentPlayer", "game", "loggedPlayer", "loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/heroAttack/makeDamage"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/heroAttack/makeDamage"));
    }

    @Test
    void testStealthAttack() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        when(userService.getLoggedUser()).thenReturn(user);
        when(cacheManager.getAttackedEnemy(any())).thenReturn(enemyInGame);
        when(cacheManager.getAttack(any())).thenReturn(4);
        when(cacheManager.isFirstStealthAttack(any())).thenReturn(true);
        when(abilityService.getAbilityByName(anyString())).thenReturn(ability);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/abilities/stealthAttack");
        MockMvcBuilders.standaloneSetup(abilityThiefController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(4))
            .andExpect(
                MockMvcResultMatchers.model().attributeExists("currentPlayer", "game", "loggedPlayer", "loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/heroAttack/makeDamage"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/heroAttack/makeDamage"));
    }

    @Test
    void testToTheHearth() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        when(cacheManager.getAttackedEnemy(any())).thenReturn(enemyInGame);
        when(cacheManager.getAttack(any())).thenReturn(4);
        when(cacheManager.isFirstToTheHeart(any())).thenReturn(true);
        when(abilityService.getAbilityByName(anyString())).thenReturn(ability);
        doNothing().when(cacheManager).setFirstToTheHeart(any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/abilities/toTheHearth");
        MockMvcBuilders.standaloneSetup(abilityThiefController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(4))
            .andExpect(
                MockMvcResultMatchers.model().attributeExists("currentPlayer", "game", "loggedPlayer", "loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/heroAttack/makeDamage"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/heroAttack/makeDamage"));
    }
    @Test
    void testTrap() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/abilities/trap");
        MockMvcBuilders.standaloneSetup(abilityThiefController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(4))
            .andExpect(
                MockMvcResultMatchers.model().attributeExists("currentPlayer", "game", "loggedPlayer", "loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/heroAttack/makeDamage"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/heroAttack/makeDamage"));
    }
}

