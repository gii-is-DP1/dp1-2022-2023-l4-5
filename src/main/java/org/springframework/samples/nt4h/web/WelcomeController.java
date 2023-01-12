package org.springframework.samples.nt4h.web;

import org.springframework.samples.nt4h.model.Person;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class WelcomeController {

    private final UserService userService;

    public WelcomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Map<String, Object> model) {
        List<Person> people = new ArrayList<>();
        Person p1 = new Person();
        p1.setFirstName("Pedro Jesús ");
        p1.setLastName("Ruiz Aguilar");
        people.add(p1);
        Person p2 = new Person();
        p2.setFirstName("Ismael ");
        p2.setLastName("Ruiz Jurado");
          people.add(p2);
          Person p3 = new Person();
          p3.setFirstName("Laura ");
          p3.setLastName("Roldán Merat");
          people.add(p3);
          Person p4 = new Person();
          p4.setFirstName("Alejandro ");
          p4.setLastName("Santiago Félix");
          people.add(p4);
          Person p5 = new Person();
          p5.setFirstName("Iván ");
          p5.setLastName("Sánchez San José");
          people.add(p5);
        Person p6 = new Person();
        p6.setFirstName("Álvaro ");
        p6.setLastName("Hidalgo Rodríguez");
        people.add(p6);

        model.put("people", people);
        model.put("title", "No Time For Heroes");

        return "welcome";
    }

    @GetMapping("/checkin")
    public String checkin() {
        User user = userService.getLoggedUser();
        user.setIsConnected(true);
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/checkout")
    public String checkout() {
        User user = userService.getLoggedUser();
        if (user.getId() != null) {
            user.setIsConnected(false);
            userService.saveUser(user);
        }

        return "redirect:/logout";
    }

}
