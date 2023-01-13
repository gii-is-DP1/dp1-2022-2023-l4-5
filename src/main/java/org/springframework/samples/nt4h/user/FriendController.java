package org.springframework.samples.nt4h.user;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.nt4h.message.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/friends")
public class FriendController {

    private static final String VIEW_FRIEND_LIST = "users/friendList";
    private static final String PAGE_FRIEND_LIST = "redirect:/friends";
    private final UserService userService;
    private final MessageService messageService;
    private final Integer PAGE_SIZE = 5;


    @Autowired
    public FriendController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @RequestMapping
    public String listFriends(@RequestParam(defaultValue = "0") int page, ModelMap model) {
        page = page < 0 ? 0 : page;
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        List<User> friends = userService.getFriends();
        Page<User> friendsPage = userService.getFriendsPaged(pageable);
        if (!friends.isEmpty() && friendsPage.isEmpty()) {
            page = friends.size() / PAGE_SIZE;
            pageable = PageRequest.of(page, PAGE_SIZE);
            friendsPage = userService.getFriendsPaged(pageable);
        }
        model.put("isNext", friendsPage.hasNext());
        List<Pair<User, Integer>> friendsWithMessages = friendsPage.getContent().stream()
            .map(friend -> new Pair<>(friend, messageService.getUnreadMessages(friend.getUsername(), userService.getLoggedUser().getUsername())))
            .collect(Collectors.toList());
        model.addAttribute("friendsList", friendsWithMessages);
        model.put("page", page);
        return VIEW_FRIEND_LIST;
    }

    @GetMapping("/add/{friendId}")
    public String addFriend(@PathVariable("friendId") int friendId) {
        userService.addFriend(friendId);
        return PAGE_FRIEND_LIST;
    }

    @GetMapping("/remove/{friendId}")
    public String removeFriend(@PathVariable("friendId") int friendId) {
        userService.removeFriend(friendId);
        return PAGE_FRIEND_LIST;
    }
}
