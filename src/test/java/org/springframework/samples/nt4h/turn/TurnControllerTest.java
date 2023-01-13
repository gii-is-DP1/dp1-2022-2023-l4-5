package org.springframework.samples.nt4h.turn;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {TurnController.class})
@ExtendWith(SpringExtension.class)
class TurnControllerTest {
    @Autowired
    private TurnController turnController;

    @MockBean
    private UserService userService;

    private User user;
    private Player player;
    private Game game;
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
        turn = new Turn();
        turn.setPhase(Phase.EVADE);
        game.setCurrentTurn(turn);
        game.setCurrentPlayer(player);
    }

    @Test
    void testEnterInGame() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/turns");
        MockMvcBuilders.standaloneSetup(turnController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(5))
            .andExpect(MockMvcResultMatchers.model().attributeExists("game", "loggedPlayer", "player", "turn", "user"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/evasion"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/evasion"));
    }
}
