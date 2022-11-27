package org.springframework.samples.nt4h.web;

import org.springframework.samples.nt4h.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class WelcomeController {


	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {
          List<Person> people = new ArrayList<>();
          Person p1 = new Person();
          p1.setFirstName("Pedro ");
          p1.setLastName("Ruiz Aguilar");
          people.add(p1);
          Person p2 = new Person();
          p2.setFirstName("Ismael ");
          p2.setLastName("Ruiz Jurado");
          people.add(p2);
          Person p3 = new Person();
          p3.setFirstName("Laura ");
          p3.setLastName("Roldan Merat");
          people.add(p3);
          Person p4 = new Person();
          p4.setFirstName("Alejandro ");
          p4.setLastName("Santiago Felix");
          people.add(p4);
          Person p5 = new Person();
          p5.setFirstName("Ivan ");
          p5.setLastName("Sanchez San jose");
          people.add(p5);
          model.put("people", people);
          model.put("title", "No Time For Heroes");
          return "welcome";
      }
}
