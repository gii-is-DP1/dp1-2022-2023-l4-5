package org.springframework.samples.nt4h.user;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.samples.nt4h.message.MessageService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;

@ContextConfiguration(classes = {FriendController.class})
@ExtendWith(SpringExtension.class)
class FriendControllerTest {
    @Autowired
    private FriendController friendController;

    @MockBean
    private MessageService messageService;

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
    void testAddFriend() throws Exception {
        doNothing().when(userService).addFriend(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/friends/add/{friendId}", 3);
        MockMvcBuilders.standaloneSetup(friendController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/friends"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/friends"));
    }

    @Test
    void testAddFriend2() throws Exception {
        doNothing().when(userService).addFriend(anyInt());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/friends/add/{friendId}", 3);
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(friendController)
            .build()
            .perform(getResult)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/friends"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/friends"));
    }

    @Test
    void testRemoveFriend() throws Exception {
        doNothing().when(userService).removeFriend(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/friends/remove/{friendId}", 3);
        MockMvcBuilders.standaloneSetup(friendController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/friends"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/friends"));
    }

    @Test
    void testRemoveFriend2() throws Exception {
        doNothing().when(userService).removeFriend(anyInt());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/friends/remove/{friendId}", 3);
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(friendController)
            .build()
            .perform(getResult)
            .andExpect(MockMvcResultMatchers.status().isFound())
            .andExpect(MockMvcResultMatchers.model().size(0))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/friends"))
            .andExpect(MockMvcResultMatchers.redirectedUrl("/friends"));
    }

    @Test
    void testListFriends() throws Exception {
        when(userService.getFriends()).thenReturn(new ArrayList<>());
        when(userService.getFriendsPaged((Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/friends");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("page", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(friendController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(3))
            .andExpect(MockMvcResultMatchers.model().attributeExists("friendsList", "isNext", "page"))
            .andExpect(MockMvcResultMatchers.view().name("users/friendList"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("users/friendList"));
    }

    @Test
    void testListFriends2() throws Exception {
        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.getFriends()).thenReturn(userList);
        when(userService.getFriendsPaged((Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/friends");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("page", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(friendController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(3))
            .andExpect(MockMvcResultMatchers.model().attributeExists("friendsList", "isNext", "page"))
            .andExpect(MockMvcResultMatchers.view().name("users/friendList"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("users/friendList"));
    }

    @Test
    void testListFriends3() throws Exception {
        when(userService.getFriends()).thenReturn(new ArrayList<>());
        when(userService.getFriendsPaged((Pageable) any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/friends");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("page", String.valueOf(-1));
        MockMvcBuilders.standaloneSetup(friendController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().size(3))
            .andExpect(MockMvcResultMatchers.model().attributeExists("friendsList", "isNext", "page"))
            .andExpect(MockMvcResultMatchers.view().name("users/friendList"))
            .andExpect(MockMvcResultMatchers.forwardedUrl("users/friendList"));
    }
}

