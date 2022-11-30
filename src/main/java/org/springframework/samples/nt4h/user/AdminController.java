package org.springframework.samples.nt4h.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {

    // Constantes.
    private static final String VIEW_USER_UPDATE_FORM = "admins/updateUserForm";
    private static final String VIEW_USER_LIST = "admins/usersAdminList";
    private static final String VIEW_USER_DETAILS = "users/userDetails";
    private static final String PAGE_WELCOME = "redirect:/welcome";
    private static final String PAGE_USER_DETAILS = "redirect:/users/{userId}";
    // Servicios.
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("selections")
    public List<User> getFriends() {
        return userService.getAllUsers();
    }

    @ModelAttribute("user")
    public User getUser() {
        User loggedUser = userService.getLoggedUser();
        return loggedUser != null ? loggedUser : new User();
    }

    // Obtener todos los usuarios.
    @GetMapping("/usersAdminList")
    public String getUsers() {
        return VIEW_USER_LIST;
    }

    @GetMapping("/details")
    public String showOwner() {
        return VIEW_USER_DETAILS;
    }


    //Editar usuario
    @GetMapping(value = "/edit")
    public String initUpdateUserForm() {return VIEW_USER_UPDATE_FORM;
    }

    @PostMapping(value = "/edit")
    public String processUpdateUserForm(@Valid User user, BindingResult result) {
        User oldUser = this.userService.getLoggedUser();
        if (result.hasErrors()) return VIEW_USER_UPDATE_FORM;
        else {
            User newUser = user.toBuilder().enable(oldUser.getEnable()).tier(oldUser.getTier()).build();
            newUser.setId(oldUser.getId());
            userService.saveUser(newUser);
            return PAGE_USER_DETAILS.replace("{userId}", String.valueOf(user.getId()));
        }
    }

    @GetMapping(value = "/delete")
    public String processDeleteUser() {
        User loggedUser = userService.getLoggedUser();
        SecurityContextHolder.clearContext();
        this.userService.deleteUser(loggedUser);
        return PAGE_WELCOME;
    }
}
