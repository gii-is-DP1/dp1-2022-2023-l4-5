package org.springframework.samples.nt4h.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.nt4h.message.MessageService;
import org.springframework.ui.ModelMap;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class FriendControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private MessageService messageService;

    private static final String VIEW_FRIEND_LIST = "users/friendList";
    private static final String PAGE_FRIEND_LIST = "redirect:/friends";

    @InjectMocks
    private FriendController friendController;

    @Test
    void testListFriends() {
        // given
        List<User> friends = Arrays.asList(new User(), new User());
        Pageable pageable = PageRequest.of(0, 3);
        Page<User> friendsPage = new PageImpl<>(friends);
        given(userService.getFriends()).willReturn(friends);
        given(userService.getFriendsPaged(pageable)).willReturn(friendsPage);
        ModelMap model = new ModelMap();
        // when
        String viewName = friendController.listFriends(0, model);
        // then
        assertEquals(VIEW_FRIEND_LIST, viewName);
        assertTrue(model.containsAttribute("friendsList"));
        assertTrue(model.containsAttribute("isNext"));
        assertEquals(0, model.get("page"));
    }

    @Test
    void testAddFriend() {
        // when
        String redirectUrl = friendController.addFriend(1);
        // then
        verify(userService, times(1)).addFriend(1);
        assertEquals(PAGE_FRIEND_LIST, redirectUrl);
    }

    @Test
    void testRemoveFriend() {
        // when
        String redirectUrl = friendController.removeFriend(1);
        // then
        verify(userService, times(1)).removeFriend(1);
        assertEquals(PAGE_FRIEND_LIST, redirectUrl);
    }
}
