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
    }

    @Test
    public void testPlayerProperties() {
        assertThat(player.getName()).isEqualTo("alesanfe");
        assertThat(player.getBirthDate()).isEqualTo(LocalDate.of(1999, 2, 1));
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
        player.setName("");
        assertThat(validator.validate(player)).isNotEmpty();
        player.setName(" ");
        assertThat(validator.validate(player)).isNotEmpty();
        player.setName("Test Hero");
        assertThat(validator.validate(player)).isEmpty();
    }

    @Test
    public void testPlayerLifecycles() {
        playerService.savePlayer(player);
        assertThat(player.getId()).isNotNull();

        player.setName("player2");
        playerService.savePlayer(player);
        assertThat(player.getName()).isEqualTo("player2");

        playerService.deletePlayer(player);
        assertThatThrownBy(() -> playerService.getPlayerById(player.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void testAddHero_roleAlreadyChosenException() throws RoleAlreadyChosenException {
        HeroInGame h1 = HeroInGame.createHeroInGame(heroService.getHeroById(1), player);
        HeroInGame h2 = HeroInGame.createHeroInGame(heroService.getHeroById(2), player);
        player.addHero(h1);

        assertThatThrownBy(() -> player.addHero(h2)).isInstanceOf(RoleAlreadyChosenException.class);
    }
}
