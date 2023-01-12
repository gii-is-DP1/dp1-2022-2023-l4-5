package org.springframework.samples.nt4h.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.turn.Phase;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PlayerTest {
    @Autowired
    private HeroService heroService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;
    private Player player;

    @BeforeEach
    public void setUp() throws FullGameException {
        // Se crea un objeto Player con valores válidos para sus propiedades
        User user = userService.getUserById(1);
        Game game = Game.createGame( "Test Game",   Mode.MULTI_CLASS, 2, "test123");
        player = Player.createPlayer(user, game, true);
        game.setStartDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        game.setFinishDate(LocalDateTime.of(2020, 1, 2, 0, 0));
        //game.setPhase(Phase.START);
        /*
        player = Player.createPlayer(userService.getUserById(1), Game.createGame("Test Game", Mode.MULTI_CLASS, 2, "test123"), true);
        player.setName("player1");
        player.setBirthDate(LocalDate.of(1990, 1, 1));
        player.setHasEvasion(true);
        player.setSequence(1);
        player.setNextPhase(Phase.EVADE);
        player.setReady(true);
        player.setHost(true);
        player.setWounds(0);
        player.setDamageProtect(0);
        player.setHeroes(Lists.newArrayList());
        User user = userService.getUserById(1);
        Game game = new Game();
        Deck deck = new Deck();
        deck.setInDeck(Lists.newArrayList());
        deck.setInHand(Lists.newArrayList());
        deck.setInDiscard(Lists.newArrayList());
        player.setDeck(deck);
        player.setHeroes(Lists.newArrayList());
        game = Game.createGame("Test Game",  Mode.MULTI_CLASS, 2, "test123");
        game.setPlayers(Lists.newArrayList(player));
        game.setStartDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        game.setFinishDate(LocalDateTime.of(2020, 1, 2, 0, 0));
        game.setPhase(Phase.START);
        game.setHasStages(true);
        System.out.println("Game: " + game);
        player.setGame(game);
        System.out.println("Player: " + player.getGame());
         */
    }

    @Test
    public void testPlayerProperties() {
        // Se comprueba que el objeto Player se ha creado correctamente
        assertThat(player.getName()).isEqualTo("alesanfe");
        assertThat(player.getBirthDate()).isEqualTo(LocalDate.of(2002, 2, 1));
        assertThat(player.getHasEvasion()).isEqualTo(true);
        assertThat(player.getSequence()).isEqualTo(-1);
        assertThat(player.getNextPhase()).isEqualTo(Phase.START);
        assertThat(player.getReady()).isEqualTo(false);
        assertThat(player.getHost()).isEqualTo(true);
        assertThat(player.getWounds()).isEqualTo(0);
        assertThat(player.getDamageProtect()).isEqualTo(0);
    }

    @Test
    public void testPlayerConstraints() {
        // Test name constraint
        player.setName("");
        assertThat(validator.validate(player)).isNotEmpty();
        player.setName(" ");
        assertThat(validator.validate(player)).isNotEmpty();
        player.setName("Test Hero");
        assertThat(validator.validate(player)).isEmpty();
    }

    @Test
    public void testPlayerLifecycles() {
        // Test saving a new player
        playerService.savePlayer(player);
        assertThat(player.getId()).isNotNull();

        // Test updating an existing player
        player.setName("player2");
        playerService.savePlayer(player);
        assertThat(player.getName()).isEqualTo("player2");

        // Test deleting an player
        playerService.deletePlayer(player);
        assertThatThrownBy(() -> playerService.getPlayerById(player.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void testPlayerQueries() {
        // Test finding all players
        playerService.savePlayer(player);
        assertThat(playerService.getAllPlayers()).isNotEmpty();

        // Test finding a player by id
        assertThat(playerService.getPlayerById(player.getId())).isNotNull();

        // Test finding a player by name
        assertThat(playerService.getPlayerByName(player.getName())).isNotNull();
    }

    @Test
    public void testAddHero_roleAlreadyChosenException() throws RoleAlreadyChosenException {
        // Creating hero
        HeroInGame h1 = HeroInGame.createHeroInGame(heroService.getHeroById(1), player);
        HeroInGame h2 = HeroInGame.createHeroInGame(heroService.getHeroById(2), player);
        player.addHero(h1);

        // Test adding a hero to a player
        assertThatThrownBy(() -> player.addHero(h2)).isInstanceOf(RoleAlreadyChosenException.class);
    }
}
