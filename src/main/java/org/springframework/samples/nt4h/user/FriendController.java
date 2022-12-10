package org.springframework.samples.nt4h.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/friends")
public class FriendController {

    private static final String VIEW_FRIEND_LIST = "users/friendList";
    private static final String PAGE_FRIEND_LIST = "redirect:/friends";
    private final UserService userService;

    @Autowired
    public FriendController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping
    public String listFriends(@RequestParam(defaultValue = "0") int page, ModelMap model) {
        page = page < 0 ? 0 : page;
        Pageable pageable = PageRequest.of(page, 3);
        List<User> friends = userService.getFriends();
        Page<User> friendsPage = userService.getFriendsPaged(pageable);
        if (!friends.isEmpty() && friendsPage.isEmpty()) {
            page = friends.size() / 5;
            pageable = PageRequest.of(page, 3);
            friendsPage = userService.getFriendsPaged(pageable);
        }
        model.put("isNext", friendsPage.hasNext());
        model.addAttribute("friendsList", friendsPage.getContent());
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
