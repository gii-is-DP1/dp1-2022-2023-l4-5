/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.nt4h.user;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    // Constantes.
    private static final String VIEW_USER_CREATE_OR_UPDATE_FORM = "users/createUserForm";
    private static final String VIEW_USER_LIST = "users/usersGameList";
    private static final String VIEW_USER_DETAILS = "users/userDetails";
    private static final String PAGE_WELCOME = "redirect:/welcome";
    private static final String PAGE_USER_DETAILS = "redirect:/users/{userId}";
    // Servicios.
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
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
        return userService.getLoggedUser();
    }

    // Obtener todos los usuarios.
    @GetMapping
    public String getUsers(@RequestParam(defaultValue = "0") int page, ModelMap model) {
        Pageable pageable = PageRequest.of(page, 1);
        System.out.println("Page: " + page);
        model.addAttribute("users", userService.getAllUsers(pageable));
        return VIEW_USER_LIST;
    }

    @GetMapping("/details")
    public String showOwner() {
        return VIEW_USER_DETAILS;
    }

    // Crear usuario
    @GetMapping(value = "/new")
    public String initCreationForm() {
        return VIEW_USER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/new")
    public String processCreationForm(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return VIEW_USER_CREATE_OR_UPDATE_FORM;
        } else {
            userService.saveUser(user);
            return PAGE_WELCOME;
        }
    }


    //Editar usuario
    @GetMapping(value = "/edit")
    public String initUpdateUserForm() {
        return VIEW_USER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/edit")
    public String processUpdateUserForm(@Valid User newUser, BindingResult result) {
        User oldUser = this.userService.getLoggedUser();
        if (result.hasErrors()) return VIEW_USER_CREATE_OR_UPDATE_FORM;
        else {
            BeanUtils.copyProperties(newUser, oldUser, "id", "password", "enable", "tier");
            userService.saveUser(newUser);
            return PAGE_USER_DETAILS.replace("{userId}", String.valueOf(newUser.getId()));
        }
    }

    @GetMapping(value = "/delete")
    public String processDeleteUser() {
        User loggedUser = userService.getLoggedUser();
        SecurityContextHolder.clearContext();
        this.userService.deleteUser(loggedUser);
        return PAGE_WELCOME;
    }

    // TODO: Utilizar un jsp que impida que se pueda modificar los datos del USUArio
    // @GetMapping(value="/{userId})
}
