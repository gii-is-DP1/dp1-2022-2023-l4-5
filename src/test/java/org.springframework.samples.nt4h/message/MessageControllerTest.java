package org.springframework.samples.nt4h.message;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @Mock
    private MessageService messageService;

    @Mock
    private UserService userService;

    @InjectMocks
    private MessageController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testSendMessage() throws Exception {
        User loggedUser = new User();
        loggedUser.setUsername("username");
        when(userService.getLoggedUser()).thenReturn(loggedUser);
        when(userService.getUserByUsername(any())).thenReturn(new User());

        mockMvc.perform(post("/messages/{username}", "receiver"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/messages/{username}"))
            .andExpect(model().attributeExists("chat"));

        verify(messageService).saveMessage(any());
    }

    @Test
    void testInitCreationForm() throws Exception {
        when(userService.getLoggedUser()).thenReturn(new User());
        when(messageService.getMessageByPair(any(), any())).thenReturn(new ArrayList<Message>());

        mockMvc.perform(get("/messages/{username}", "username"))
            .andExpect(status().isOk())
            .andExpect(view().name("messages/messagesList"))
            .andExpect(model().attributeExists("receiver"))
            .andExpect(model().attributeExists("chat"))
            .andExpect(model().attributeExists("messages"));
    }

    @Test
    void testSendMessageGame() throws Exception {
        User loggedUser = new User();
        loggedUser.setUsername("username");
        when(userService.getLoggedUser()).thenReturn(loggedUser);

        HttpSession session = new MockHttpSession();
        session.setAttribute("url", "someUrl");

        mockMvc.perform(post("/messages/game")
                .session(session)
                .param("content", "content"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:someUrl"));

        verify(messageService).saveMessage(any());
    }

    @Test
    void testSendInviteMessage() throws Exception {
        User loggedUser = new User();
        loggedUser.setUsername("username");
        when(userService.getLoggedUser()).thenReturn(loggedUser);
        when(userService.getUserByUsername(any())).thenReturn(new User());

        mockMvc.perform(get("/messages/{username}/invite", "receiver"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/messages/{username}"));

        verify(messageService).saveMessage(any());
    }
}
