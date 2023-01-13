package org.springframework.samples.nt4h.user;

import java.time.LocalDate;
import java.util.ArrayList;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.samples.nt4h.game.GameRepository;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.MessageRepository;
import org.springframework.samples.nt4h.message.MessageService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AdminController.class})
@ExtendWith(SpringExtension.class)
class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @MockBean
    private UserService userService;

    @MockBean
    private Advise advise;

    @MockBean
    private GameService gameService;

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
    void testGetUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admins/usersAdminList");
        MockMvcBuilders.standaloneSetup(adminController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(2))
            .andExpect(MockMvcResultMatchers.model().attributeExists("loggedUser", "selections"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/users"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/users"));
    }

    @Test
    void testInitUpdateUserForm() throws Exception {
        User user1 = new User();
        user1.setBirthDate(LocalDate.ofEpochDay(1L));
        user1.setDescription("The characteristics of someone or something");
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");

        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        when(userService.getLoggedUser()).thenReturn(user);
        when(userService.getUserById(anyInt())).thenReturn(user1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admins/{userId}/edit", 123);
        MockMvcBuilders.standaloneSetup(adminController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(3))
            .andExpect(MockMvcResultMatchers.model().attributeExists("loggedUser", "selections", "user"))
            .andExpect(MockMvcResultMatchers.view().name("admins/updateUserForm"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("admins/updateUserForm"));
    }

    @Test
    void testProcessDeleteUser() throws Exception {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        when(userService.getLoggedUser()).thenReturn(user);
        doNothing().when(userService).deleteUserById(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admins/{userId}/delete", 123);
        MockMvcBuilders.standaloneSetup(adminController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(2))
            .andExpect(MockMvcResultMatchers.model().attributeExists("loggedUser", "selections"))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/users"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/users"));
    }

    @Test
    void testProcessUpdateUserForm() throws Exception {
        User user1 = new User();
        user1.setBirthDate(LocalDate.ofEpochDay(1L));
        user1.setDescription("The characteristics of someone or something");
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");

        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        when(userService.getLoggedUser()).thenReturn(user);
        when(userService.getUserById(anyInt())).thenReturn(user1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admins/{userId}/edit", 123)
            .param("username", "janedoe");
        MockMvcBuilders.standaloneSetup(adminController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(3))
            .andExpect(MockMvcResultMatchers.model().attributeExists("loggedUser", "selections", "user"))
            .andExpect(MockMvcResultMatchers.view().name("admins/updateUserForm"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("admins/updateUserForm"));
    }

    @Test
    void testShowOwner() throws Exception {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        when(userService.getLoggedUser()).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admins/details");
        MockMvcBuilders.standaloneSetup(adminController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(2))
            .andExpect(MockMvcResultMatchers.model().attributeExists("loggedUser", "selections"))
            .andExpect(MockMvcResultMatchers.view().name("users/userDetails"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("users/userDetails"));
    }

    @Test
    void testShowUserStatistics() throws Exception {
        User user1 = new User();
        user1.setBirthDate(LocalDate.ofEpochDay(1L));
        user1.setDescription("The characteristics of someone or something");
        user1.setPassword("iloveyou");
        user1.setUsername("janedoe");

        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        when(userService.getLoggedUser()).thenReturn(user);
        when(userService.getUserById(anyInt())).thenReturn(user1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admins/statistics/{userId}", 123);
        MockMvcBuilders.standaloneSetup(adminController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(3))
            .andExpect(MockMvcResultMatchers.model().attributeExists("loggedUser", "selections"))
            .andExpect(MockMvcResultMatchers.view().name("users/userStatistics"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("users/userStatistics"));
    }

    @Test
    @Disabled
    void testShowGames() {
        GameRepository gameRepository = mock(GameRepository.class);
        when(gameRepository.findAll()).thenReturn(new ArrayList<>());
        when(gameRepository.findAll((Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        UserService userService1 = new UserService(mock(UserRepository.class));
        AdminController adminController = new AdminController(userService1, gameService,
            new Advise(new MessageService(mock(MessageRepository.class)), new UserService(mock(UserRepository.class))));
        ModelMap model = new ModelMap();
        assertEquals("admins/games", adminController.showGames(1, model, new MockHttpSession()));
        verify(gameRepository).findAll();
        verify(gameRepository).findAll((Pageable) any());
    }
}
