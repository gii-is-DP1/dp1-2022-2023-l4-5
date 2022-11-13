package org.springframework.samples.nt4h.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    // Constantes.
    private static final String VIEW_USER_CREATE_OR_UPDATE_FORM = "users/createUserForm";
    private static final String PAGE_USER_DETAILS = "redirect:/users/{userId}";
    private static final String VIEW_USER_LIST = "users/usersList";
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

    //Editar usuario siendo admin.
    @GetMapping(value = "/{userId}/edit")
    public String initUpdateUserForm(@PathVariable("userId") int userId, ModelMap model) {
        User user = this.userService.getUserById(userId);
        model.addAttribute(user);
        return VIEW_USER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/{userId}/edit")
    public String processUpdateUserForm(@Valid User user, BindingResult result, @PathVariable("userId") int userId) {
        if (result.hasErrors()) {
            return VIEW_USER_CREATE_OR_UPDATE_FORM;
        } else {
            user.setId(userId);
            this.userService.saveUser(user);
            return PAGE_USER_DETAILS;
        }
    }

    @DeleteMapping(value = "/{userId}/delete")
    public String processDeleteUser(@PathVariable int userId) {
        userService.deleteUserById(userId);
        return VIEW_USER_LIST;
    }

}
