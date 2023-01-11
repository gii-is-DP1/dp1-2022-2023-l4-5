
package org.springframework.samples.nt4h.game;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class GameTest {

    @Autowired
    private Validator validator;
    @Autowired
    private UserService userService;
    @Autowired
    private GameService gameService;
    @Autowired
    private HeroService heroService;

    private Game game;

    @BeforeEach
    public void setUp() throws FullGameException {
        User user = userService.getUserById(1);
        game = Game.createGame( "Test Game",   Mode.MULTI_CLASS, 2, "test123");
        Player.createPlayer(user, game, true);
        game.setFinishDate(LocalDateTime.of(2020, 1, 2, 0, 0));
        game.setStartDate(LocalDateTime.of(2020, 1, 1, 0, 0));
        game.setHasStages(true);
    }

    @Test
    public void testGameProperties() {
        assertThat(game.getName()).isEqualTo("Test Game");
        assertThat(game.getAccessibility()).isEqualTo(Accessibility.PRIVATE);
        assertThat(game.getMode()).isEqualTo(Mode.MULTI_CLASS);
        assertThat(game.getMaxPlayers()).isEqualTo(2);
        assertThat(game.getPassword()).isEqualTo("test123");
        assertThat(game.getStartDate()).isEqualTo(LocalDateTime.of(2020, 1, 1, 0, 0));
        assertThat(game.getFinishDate()).isEqualTo(LocalDateTime.of(2020, 1, 2, 0, 0));
        //assertThat(game.getPhase()).isEqualTo(Phase.START);
        assertThat(game.isHasStages()).isTrue();
    }

    @Test
    public void testGameConstraints() {
        // Test maxPlayers constraints
        game.setMaxPlayers(-1); // TODO: Decidir si puede ser negativo.
        assertThat(validator.validate(game)).isEmpty();
        game.setMaxPlayers(0);
        assertThat(validator.validate(game)).isEmpty();
        game.setMaxPlayers(5);
        assertThat(validator.validate(game)).isEmpty();
        game.setMaxPlayers(2);
        assertThat(validator.validate(game)).isEmpty();

        // Test password constraints
        game.setAccessibility(Accessibility.PUBLIC);
        game.setPassword(null);
        assertThat(validator.validate(game)).isEmpty();
        game.setPassword("password");
        assertThat(validator.validate(game)).isEmpty();

        // Test name constraints
        game.setName("");
        assertThat(validator.validate(game)).isNotEmpty();
        game.setName("A");
        assertThat(validator.validate(game)).isNotEmpty();
        game.setName("Game");
        assertThat(validator.validate(game)).isEmpty();

        // Test startDate constraints
        game.setStartDate(null);
        assertThat(validator.validate(game)).isEmpty();

        // Test finishDate constraints
        game.setStartDate(LocalDateTime.now());
        game.setFinishDate(null);
        assertThat(validator.validate(game)).isEmpty();

        // Test maxPlayers constraints
        game.setFinishDate(LocalDateTime.now());
        game.setMaxPlayers(0);
        assertThat(validator.validate(game)).isEmpty();

        // Test mode constraints
        game.setMaxPlayers(1);
        game.setMode(null);
        assertThat(validator.validate(game)).isNotEmpty();

        // Test phase constraints
        game.setMode(Mode.UNI_CLASS);
        //game.setPhase(null);
        assertThat(validator.validate(game)).isEmpty();

        // Test accessibility constraints
        //game.setPhase(Phase.START);
        game.setAccessibility(null);
        assertThat(validator.validate(game)).isEmpty();
    }

    @Test
    public void testGameLifecycle() {
        // Test saving a game
        gameService.saveGame(game);
        assertThat(game.getId()).isNotNull();

        // Test updating an existing game
        game.setName("Updated Game");
        gameService.saveGame(game);
        assertThat(game.getName()).isEqualTo("Updated Game");

        // Test deleting an existing game
        gameService.deleteGame(game);
        assertThatThrownBy(() -> gameService.getGameById(game.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void testGameQueries() {
        // Test finding all games
        gameService.saveGame(game);
        assertThat(gameService.getAllGames()).isNotEmpty();

        // Test finding a game by ID
        assertThatThrownBy(() -> gameService.getGameById(0)).isInstanceOf(NotFoundException.class);
        assertThat(gameService.getGameById(game.getId())).isEqualTo(game);
    }

    @Test
    public void testFullGameException() throws FullGameException {
        // Añade dos jugadores
        System.out.println("players" + game.getPlayers());
        game.addPlayer(new Player());

        // Verifica que se lanza una excepción al intentar añadir un tercer jugador
        assertThatThrownBy(() ->game.addPlayer(new Player())).isInstanceOf(FullGameException.class);
    }

    @Test
    public void testAddPlayerWithNewHero_duplicateHero() throws FullGameException, RoleAlreadyChosenException, HeroAlreadyChosenException {
        // Se crean dos jugadores y se añade al primero el héroe.
        Player player1 = game.getPlayers().get(0);
        Hero hero = heroService.getHeroById(1);
        HeroInGame heroInGame1 = HeroInGame.createHeroInGame(hero, player1);
        game.addPlayerWithNewHero(player1, heroInGame1);
        User user = userService.getUserById(2);
        System.out.println("players" + game.getPlayers());
        Player player2 = Player.createPlayer(user, game, false);
        player2.setHeroes(Lists.newArrayList());
        HeroInGame heroInGame2 = HeroInGame.createHeroInGame(hero, player2);

        // Se espera que se lance la excepción HeroAlreadyChosenException
        assertThatThrownBy(() -> game.addPlayerWithNewHero(player2, heroInGame2)).isInstanceOf(HeroAlreadyChosenException.class);
    }



}
