package org.springframework.samples.nt4h.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/friends")
public class FriendController {

    private static final String VIEW_USER_LIST = "users/usersList";
    private final UserService userService;

    @Autowired
    public FriendController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("selections")
    public Set<User> getFriends() {
        // return userService.currentUser().getFriends()
        //    .stream().map(f -> new Pair<>(f, f.getPlayer() != null ? f.getPlayer().getGame() : null)).collect(Collectors.toList());
        return userService.currentUser().getFriends();
    }

    /*
    @ModelAttribute("games")
    public List<Game> getGames() {
        return getFriends().stream().map(User::getPlayer).map(p -> p != null ? p.getGame() : null).collect(Collectors.toList());
    }
     */

    @RequestMapping
    public ModelAndView listFriends() {
        return new ModelAndView(VIEW_USER_LIST);
    }

    @GetMapping("/add/{friendId}")
    public ModelAndView addFriend(@PathVariable("friendId") int friendId) {
        userService.addFriend(friendId);
        return listFriends();
    }

    @GetMapping("/remove/{friendId}")
    public ModelAndView removeFriend(@PathVariable("friendId") int friendId) {
        userService.removeFriend(friendId);
        return listFriends();
    }
}