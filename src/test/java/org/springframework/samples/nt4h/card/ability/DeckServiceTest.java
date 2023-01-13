package org.springframework.samples.nt4h.card.ability;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.nt4h.card.enemy.Enemy;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.card.product.Product;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.card.product.StateProduct;
import org.springframework.samples.nt4h.game.Accessibility;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.turn.Phase;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.turn.exceptions.TooManyAbilitiesException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DeckService.class})
@ExtendWith(SpringExtension.class)
class DeckServiceTest {
    @MockBean
    private AbilityService abilityService;

    @MockBean(name = "advise")
    private Advise advise;

    @MockBean
    private DeckRepository deckRepository;

    @Autowired
    private DeckService deckService;

    @MockBean
    private PlayerService playerService;

    private Player player;
    private Game game;
    private User user;
    private Turn turn;
    private AbilityInGame abilityInGame;
    private ProductInGame productInGame;
    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
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


        productInGame = new ProductInGame();
        productInGame.setGame(new Game());
        productInGame.setId(1);
        productInGame.setName("Name");
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

        user = new User();
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


    }

    @Test
    @Disabled
    public void testFromDeckToDiscard() {
        Player player = new Player();
        Deck deck = new Deck();
        deck.setInDeck(List.of(new AbilityInGame()));
        deckService.fromDeckToDiscard(player, deck);
        verify(abilityService, times(1)).saveAbilityInGame(deck.getInDeck().get(0));
        verify(deckRepository, times(1)).save(deck);
        verify(advise, times(1)).fromDeckToDiscard(deck.getInDeck().get(0));
    }

    @Test
    void testFromDiscardToDeck() {
        Deck deck = new Deck();
        deck.setId(1);
        ArrayList<AbilityInGame> abilityInGameList = new ArrayList<>();
        deck.setInDeck(abilityInGameList);
        deck.setInDiscard(new ArrayList<>());
        deck.setInHand(new ArrayList<>());
        deckService.fromDiscardToDeck(deck);
        assertEquals(1, deck.getId().intValue());
        assertEquals(abilityInGameList, deck.getInHand());
        List<AbilityInGame> inDiscard = deck.getInDiscard();
        assertEquals(abilityInGameList, inDiscard);
        assertEquals(inDiscard, deck.getInDeck());
    }

    @Test
    @Disabled
    public void testFromDeckToHand() {
        Player player = new Player();
        Deck deck = new Deck();
        deck.setInDeck(List.of(new AbilityInGame()));
        deckService.fromDeckToHand(player, deck);
        verify(abilityService, times(1)).saveAbilityInGame(deck.getInDeck().get(0));
        verify(deckRepository, times(1)).save(deck);
        verify(advise, times(1)).fromDeckToHand(deck.getInDeck().get(0));
    }


    @Test
    void testFromDiscardToHand() {
        Deck deck = new Deck();
        deck.setId(1);
        ArrayList<AbilityInGame> abilityInGameList = new ArrayList<>();
        deck.setInDeck(abilityInGameList);
        deck.setInDiscard(new ArrayList<>());
        deck.setInHand(new ArrayList<>());
        deckService.fromDiscardToHand(deck);
        assertEquals(1, deck.getId().intValue());
        assertEquals(abilityInGameList, deck.getInHand());
        List<AbilityInGame> inDiscard = deck.getInDiscard();
        assertEquals(abilityInGameList, inDiscard);
        assertEquals(inDiscard, deck.getInDeck());
    }


    @Test
    void testFromDiscardToHand2() {
        Deck deck = new Deck();
        deck.setId(1);
        ArrayList<AbilityInGame> abilityInGameList = new ArrayList<>();
        deck.setInDeck(abilityInGameList);
        deck.setInDiscard(new ArrayList<>());
        deck.setInHand(new ArrayList<>());
        deckService.fromDiscardToHand(deck, 1);
        assertEquals(1, deck.getId().intValue());
        assertEquals(abilityInGameList, deck.getInHand());
        List<AbilityInGame> inDiscard = deck.getInDiscard();
        assertEquals(abilityInGameList, inDiscard);
        assertEquals(inDiscard, deck.getInDeck());
    }


    @Test
    @Disabled
    void testMoveAllCardsFromDiscardToDeck() {
        when(deckRepository.save((Deck) any())).thenReturn(deck);
        doNothing().when(playerService).inflictWounds((Player) any(), anyInt());
        deckService.moveAllCardsFromDiscardToDeck(player, deck);
        verify(deckRepository).save((Deck) any());
        verify(playerService).inflictWounds((Player) any(), anyInt());
    }



    @Test
    void testCreateDeck() {
        Deck deck = new Deck();
        deck.setId(1);
        deck.setInDeck(new ArrayList<>());
        deck.setInDiscard(new ArrayList<>());
        deck.setInHand(new ArrayList<>());
        when(deckRepository.save((Deck) any())).thenReturn(deck);
        deckService.createDeck(player, new ArrayList<>(), 1);
        verify(deckRepository).save((Deck) any());
    }


    @Test
    public void testSaveDeck() {
        Deck deck = new Deck();
        deckService.saveDeck(deck);
        verify(deckRepository, times(1)).save(deck);
    }
    @Test
    @Disabled
    public void testSpecificCardFromDeckToDiscard() {
        Player player = new Player();
        Deck deck = new Deck();
        AbilityInGame inDeck = new AbilityInGame();
        deck.setInDeck(List.of(inDeck));
        deckService.specificCardFromDeckToDiscard(player, deck, inDeck);
        verify(abilityService, times(1)).saveAbilityInGame(inDeck);
        verify(deckRepository, times(1)).save(deck);
        verify(advise, times(1)).fromDeckToDiscard(inDeck);
    }
    @Test
    @Disabled
    public void testFromDeckToDiscardWithTimes() {
        Player player = new Player();
        Deck deck = new Deck();
        deck.setInDeck(List.of(new AbilityInGame(), new AbilityInGame()));
        deckService.fromDeckToDiscard(player, deck, 2);
        verify(abilityService, times(2)).saveAbilityInGame(deck.getInDeck().get(0));
        verify(deckRepository, times(2)).save(deck);
        verify(advise, times(2)).fromDeckToDiscard(deck.getInDeck().get(0));
    }
    @Test
    @Disabled
    public void testSpecificCardFromDiscardToDeck() {
        Deck deck = new Deck();
        AbilityInGame inDiscard = new AbilityInGame();
        deck.setInDiscard(List.of(inDiscard));
        deckService.specificCardFromDiscardToDeck(deck, inDiscard);
        verify(abilityService, times(1)).saveAbilityInGame(inDiscard);
        verify(deckRepository, times(1)).save(deck);
    }

    @Test
    @Disabled
    public void testSpecificCardFromDeckToHand() {
        Player player = new Player();
        Deck deck = new Deck();
        AbilityInGame inDeck = new AbilityInGame();
        deck.setInDeck(List.of(inDeck));
        deckService.specificCardFromDeckToHand(player, deck, inDeck);
        verify(abilityService, times(1)).saveAbilityInGame(inDeck);
        verify(deckRepository, times(1)).save(deck);
        verify(advise, times(1)).fromDeckToHand(inDeck);
    }


    @Test
    @Disabled
    public void testSpecificCardFromDiscardToHand() {
        Deck deck = new Deck();
        AbilityInGame inDiscard = new AbilityInGame();
        deck.setInDiscard(List.of(inDiscard));
        deckService.specificCardFromDiscardToHand(deck, inDiscard);
        verify(abilityService, times(1)).saveAbilityInGame(inDiscard);
        verify(deckRepository, times(1)).save(deck);
    }




}

