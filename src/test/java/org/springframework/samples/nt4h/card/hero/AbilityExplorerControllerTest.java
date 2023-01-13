package org.springframework.samples.nt4h.card.hero;

import static java.lang.Boolean.TRUE;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.nt4h.card.ability.*;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.product.Product;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.card.product.StateProduct;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.message.CacheManager;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.statistic.StatisticService;
import org.springframework.samples.nt4h.turn.Phase;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AbilityExplorerController.class})
@ExtendWith(SpringExtension.class)
class AbilityExplorerControllerTest {
    @Autowired
    private AbilityExplorerController abilityExplorerController;

    @MockBean
    private CacheManager cacheManager;

    @MockBean
    private DeckService deckService;

    @MockBean
    private GameService gameService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private StatisticService statisticService;

    @MockBean
    private UserService userService;

    private User user;
    private Game game;
    private Player player;
    private Turn turn;

    @BeforeEach
    void setUp() throws FullGameException {
        Statistic statistic = new Statistic();
        statistic.setDamageDealt(0);
        statistic.setGold(0);
        statistic.setGlory(0);
        statistic.setNumWonGames(0);
        statistic.setNumPlayedGames(0);
        statistic.setNumOrcsKilled(0);
        statistic.setNumWarLordKilled(0);
        user = User.builder()
            .username("user")
            .password("pass")
            .enable("true")
            .avatar("http://example.com/avatar")
            .tier(Tier.BRONZE)
            .description("Description")
            .authority("USER")
            .birthDate(LocalDate.of(2000, 1, 1))
            .friends(Lists.newArrayList())
            .statistic(statistic)
            .sentMessages(Lists.newArrayList())
            .receivedMessages(Lists.newArrayList())
            .player(new Player())
            .build();
        game = Game.createGame( "Test Game",   Mode.MULTI_CLASS, 2, "test123");
        game.setFinishDate(LocalDateTime.of(2020, 1, 2, 0, 0));
        game.setStartDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        player = Player.createPlayer(user, game, true);
        game.setCurrentPlayer(player);
        Deck deck = new Deck();
        Ability ability = new Ability();
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
        AbilityInGame abilityInGame = new AbilityInGame();
        abilityInGame.setAbility(ability);
        abilityInGame.setAbilityCardType(AbilityCardType.WARRIOR);
        abilityInGame.setAttack(1);
        abilityInGame.setId(1);
        abilityInGame.setPlayer(player);
        abilityInGame.setProduct(true);
        abilityInGame.setProductInGame(productInGame);
        abilityInGame.setTimesUsed(1);
        deck.setInDiscard(List.of(abilityInGame));
        deck.setInHand(List.of(abilityInGame));
        deck.setInDeck(List.of(abilityInGame));
        player.setDeck(deck);
        game.setCurrentPlayer(player);
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
        turn.setCurrentAbility(abilityInGame);
        game.setCurrentTurn(turn);
        EnemyInGame enemyInGame = new EnemyInGame();
        enemyInGame.setActualHealth(4);
        enemyInGame.setNightLord(TRUE);
        game.setActualOrcs(List.of(enemyInGame));
        game.setAllOrcsInGame(List.of(enemyInGame,enemyInGame));
    }

    @Test
    void testArrowRain() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/abilities/arrowRain",
            123);
        MockMvcBuilders.standaloneSetup(abilityExplorerController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(8))
            .andExpect(MockMvcResultMatchers.model().attributeExists("currentPlayer", "game", "loggedPlayer", "loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("abilities/choseEnemy"));
    }

    @Test
    void testCollectArrows() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/abilities/collectArrows", 123);
        MockMvcBuilders.standaloneSetup(abilityExplorerController)
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
    void testFellowWolf() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/abilities/fellowWolf", 123);
        MockMvcBuilders.standaloneSetup(abilityExplorerController)
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
    void testPreciseShot() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/abilities/preciseShot", 123);
        MockMvcBuilders.standaloneSetup(abilityExplorerController)
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
    void testRapidFire() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
            .get("/abilities/rapidFire", 123);
        MockMvcBuilders.standaloneSetup(abilityExplorerController)
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
    @Disabled
    void testSurvival() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/abilities/survival",
            123);
        MockMvcBuilders.standaloneSetup(abilityExplorerController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(5))
            .andExpect(
                MockMvcResultMatchers.model().attributeExists("currentPlayer", "game", "loggedPlayer", "loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("abilities/choseEnemy"));
    }

    @Test
    void testTarget() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/abilities/target",
            123);
        MockMvcBuilders.standaloneSetup(abilityExplorerController)
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
