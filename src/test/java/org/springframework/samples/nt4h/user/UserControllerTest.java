package org.springframework.samples.nt4h.user;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityCardType;
import org.springframework.samples.nt4h.card.ability.AbilityEffect;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.ability.StateAbility;
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
import org.springframework.samples.nt4h.statistic.TipoEnt;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void testLoggedUser() {
        when(userService.getLoggedUser()).thenReturn(user);
        assertSame(user, userController.loggedUser());
        verify(userService).getLoggedUser();
    }

    @Test
    void testGetUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        when(userService.getAllUsers((Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/users");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("page", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(userController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(4))
            .andExpect(MockMvcResultMatchers.model().attributeExists("isNext", "loggedUser", "page", "users"))
            .andExpect(MockMvcResultMatchers.view().name("users/usersGameList"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("users/usersGameList"));
    }

    @Test
    void testInitCreationForm() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/new");
        MockMvcBuilders.standaloneSetup(userController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("users/createUserForm"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("users/createUserForm"));
    }

    @Test
    void testInitUpdateUserForm() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/edit");
        MockMvcBuilders.standaloneSetup(userController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("users/createUserForm"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("users/createUserForm"));
    }

    @Test
    void testProcessCreationForm() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/new")
            .param("username", "janedoe");
        MockMvcBuilders.standaloneSetup(userController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(2))
            .andExpect(MockMvcResultMatchers.model().attributeExists("loggedUser", "user"))
            .andExpect(MockMvcResultMatchers.view().name("users/createUserForm"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("users/createUserForm"));
    }

    @Test
    void testProcessDeleteUser() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        doNothing().when(userService).deleteUser((User) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/delete");
        MockMvcBuilders.standaloneSetup(userController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/welcome"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/welcome"));
    }

    @Test
    void testProcessUpdateUserForm() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/edit")
            .param("username", "janedoe");
        MockMvcBuilders.standaloneSetup(userController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(2))
            .andExpect(MockMvcResultMatchers.model().attributeExists("loggedUser", "user"))
            .andExpect(MockMvcResultMatchers.view().name("users/createUserForm"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("users/createUserForm"));
    }

    @Test
    void testShowOwner() throws Exception {
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/details");
        MockMvcBuilders.standaloneSetup(userController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(1))
            .andExpect(MockMvcResultMatchers.model().attributeExists("loggedUser"))
            .andExpect(MockMvcResultMatchers.view().name("users/userDetails"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("users/userDetails"));
    }
}
