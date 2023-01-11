package org.springframework.samples.nt4h.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admins")
public class AdminController {

    // Constantes.
    private static final String VIEW_USER_CREATE_OR_UPDATE_FORM = "admins/updateUserForm";

    private static final String VIEW_USER_DETAILS = "users/userDetails";

    private static final String PAGE_USER_LIST = "redirect:/users";
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
}
