package org.springframework.samples.nt4h.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {

    // Constantes.
    private static final String VIEW_USER_CREATE_OR_UPDATE_FORM = "admins/updateUserForm";

    private static final String VIEW_USER_DETAILS = "users/userDetails";
    private static final String VIEW_ALLGAMES = "admins/finishedGamesList";

    private static final String VIEW_USER_STATISTICS = "users/userStatistics";
    private static final String PAGE_USER_LIST = "redirect:/users";


    // Servicios.
    private final UserService userService;
    private final GameService gameService;
    private final Advise advise;

    @Autowired
    public AdminController(UserService userService, GameService gameService, Advise advise) {
        this.userService = userService;
        this.gameService = gameService;
        this.advise = advise;
    }


    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("selections")
    public List<User> getFriends() {
        return userService.getAllUsers();
    }

    @ModelAttribute("loggedUser")
    public User getUser() {
        User loggedUser = userService.getLoggedUser();
        return loggedUser != null ? loggedUser : new User();
    }

    // Obtener todos los usuarios.
    @GetMapping("/usersAdminList")
    public String getUsers() {
        return PAGE_USER_LIST;
    }

    @GetMapping("/details")
    public String showOwner() {
        return VIEW_USER_DETAILS;
    }

    //Editar usuario
    @GetMapping(value = "{userId}/edit")
    public String initUpdateUserForm(@PathVariable Integer userId, ModelMap model) {
        model.put("user", userService.getUserById(userId));
        return VIEW_USER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "{userId}/edit")
    public String processUpdateUserForm(@Valid User user, BindingResult result, @PathVariable Integer userId) {
        User oldUser = this.userService.getUserById(userId);
        if (result.hasErrors()) return VIEW_USER_CREATE_OR_UPDATE_FORM;
        else {
            User newUser = user.toBuilder().enable(oldUser.getEnable()).tier(oldUser.getTier()).authority(oldUser.getAuthority()).build();
            newUser.setId(oldUser.getId());
            userService.saveUser(newUser);
            return PAGE_USER_LIST;
        }
    }

    @GetMapping(value = "{userId}/delete")
    public String processDeleteUser(@PathVariable  Integer userId) {
        userService.deleteUserById(userId);
        return PAGE_USER_LIST;
    }

    @GetMapping("/games")
    public String showGames(@RequestParam(defaultValue = "0") int page, ModelMap model, HttpSession session) {
        page = page < 0 ? 0 : page;
        Pageable pageable = PageRequest.of(page, 5);
        List<Game> games = gameService.getAllGames();
        Page<Game> gamePage = gameService.getAllGames(pageable);
        if (!games.isEmpty() && gamePage.isEmpty()) {
            page = games.size() / 5;
            pageable = PageRequest.of(page, 5);
            gamePage = gameService.getAllGames(pageable);
        }
        advise.getMessage(session, model);
        model.put("isNext", gamePage.hasNext());
        model.put("games", gamePage.getContent());
        model.put("page", page);
        return "admins/games";
    }

    @GetMapping("/finished")
    public String showAllGames(@RequestParam(defaultValue = "0") int page, ModelMap model, HttpSession session) {
        page = page < 0 ? 0 : page;
        Pageable pageable = PageRequest.of(page, 5);
        List<Game> games = gameService.getAllGames();
        Page<Game> gamePage = gameService.getAllGames(pageable);
        if (!games.isEmpty() && gamePage.isEmpty()) {
            page = games.size() / 5;
            pageable = PageRequest.of(page, 5);
            gamePage = gameService.getAllGames(pageable);
        }
        advise.getMessage(session, model);
        model.put("isNext", gamePage.hasNext());
        model.put("games", gamePage.getContent());
        model.put("page", page);
        return VIEW_ALLGAMES;
    }
    @GetMapping("/statistics/{userId}")
    public String showUserStatistics(@PathVariable("userId") int userId, ModelMap model) {
        Statistic userStatistic = userService.getUserById(userId).getStatistic();
        model.put("statistic", userStatistic);
        return VIEW_USER_STATISTICS;
    }


}
