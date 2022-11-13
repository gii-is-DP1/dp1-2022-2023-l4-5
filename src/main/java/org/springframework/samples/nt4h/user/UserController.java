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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    // Constantes.
    private static final String VIEW_USER_CREATE_OR_UPDATE_FORM = "users/createUserForm";
    private static final String VIEW_USER_LIST = "users/usersList";
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
        return VIEW_USER_LIST;
    }

    @GetMapping("/{userId}")
    public ModelAndView showOwner(@PathVariable("userId") int userId) {
        ModelAndView mav = new ModelAndView("users/userDetails");
        mav.addObject(this.userService.getUserById(userId));
        return mav;
    }

    // Crear usuario
    @GetMapping(value = "/new")
    public String initCreationForm(ModelMap model) {
        model.put("user", new User());
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
    public String initUpdateUserForm(ModelMap model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails ud = null;
        if (principal instanceof UserDetails) {
            ud = ((UserDetails) principal);
        }
        User user = this.userService.getUserByUsername(ud.getUsername());
        model.addAttribute(user);
        return VIEW_USER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/edit")
    public String processUpdateUserForm(@Valid User user, BindingResult result) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails ud = null;
        if (principal instanceof UserDetails) {
            ud = ((UserDetails) principal);
        }
        User oldUser = this.userService.getUserByUsername(ud.getUsername());
        if (result.hasErrors()) {
            return VIEW_USER_CREATE_OR_UPDATE_FORM;
        } else {
            System.out.println("User: " + user);
            user.setId(oldUser.getId());
            user.setEnable(oldUser.getEnable());
            user.setTier(oldUser.getTier());
            this.userService.saveUser(user);
            return PAGE_USER_DETAILS.replace("{userId}", String.valueOf(user.getId()));
        }
    }

    @GetMapping(value = "/delete")
    public String processDeleteUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails ud = null;
        if (principal instanceof UserDetails) {
            ud = ((UserDetails) principal);
        }
        User oldUser = this.userService.getUserByUsername(ud.getUsername());
        userService.deleteUser(oldUser);
        // TODO: deslogearse
        return PAGE_WELCOME;
    }

}
