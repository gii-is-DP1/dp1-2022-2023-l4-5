package org.springframework.samples.nt4h.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private static final String VIEW_USER_LIST = "admins/usersAdminList";
    private static final String VIEW_USER_DETAILS = "users/userDetails";

    private static final String PAGE_USER_DETAILS = "redirect:/users/{userId}";
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
    @GetMapping(value = "{userId}/edit")
    public String initUpdateUserForm(Integer userId, ModelMap model) {
        model.put("user", userService.getUserById(userId));
        return VIEW_USER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "{userId}/edit")
    public String processUpdateUserForm(@Valid User newUser, BindingResult result) {
        User oldUser = this.userService.getLoggedUser();
        if (result.hasErrors()) return VIEW_USER_CREATE_OR_UPDATE_FORM;
        else {
            BeanUtils.copyProperties(newUser, oldUser, "id", "password", "enable", "tier");
            userService.saveUser(newUser);
            return PAGE_USER_DETAILS.replace("{userId}", String.valueOf(newUser.getId()));
        }
    }




    @GetMapping(value = "{userId}/delete")
    public String processDeleteUser(@PathVariable  Integer userId) {
        userService.deleteUserById(userId);
        return PAGE_USER_LIST;
    }
}
