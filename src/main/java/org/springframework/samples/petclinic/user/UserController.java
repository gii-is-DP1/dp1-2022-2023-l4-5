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
package org.springframework.samples.petclinic.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    // Constantes.
    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "users/createUserForm";
    private static final String VIEWS_OWNER_LIST = "users/usersList";
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

    // Obtener todos los usuarios.
    @GetMapping
    public String getUsers(ModelMap model) {
        model.put("selections", userService.getAllUsers());
        return VIEWS_OWNER_LIST;
    }

    // Obtener información detallada de un usuario.
    @GetMapping("/{userId}")
    public String showOwner(@PathVariable("userId") int userId, ModelMap model) {
        model.addAttribute(this.userService.getUserById(userId));
        return PAGE_USER_DETAILS;
    }

    //Crear usuario
    @GetMapping(value = "/new")
    public String initCreationForm(ModelMap model) {
        model.put("user", new User());
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/new")
    public String processCreationForm(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            userService.saveUser(user);
            return PAGE_WELCOME;
        }
    }

    //Editar usuario
    @GetMapping(value = "/{userId}/edit")
    public String initUpdateUserForm(@PathVariable("userId") int userId, ModelMap model) {
        User user = this.userService.getUserById(userId);
        model.addAttribute(user);
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/{userId}/edit")
    public String processUpdateUserForm(@Valid User user, BindingResult result, @PathVariable("userId") int ownerId) {
        if (result.hasErrors()) {
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        } else {
            user.setId(ownerId);
            this.userService.saveUser(user);
            return PAGE_USER_DETAILS;
        }
    }
}
