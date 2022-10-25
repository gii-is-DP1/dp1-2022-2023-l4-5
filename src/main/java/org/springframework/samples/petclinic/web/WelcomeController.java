package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.samples.petclinic.model.Person;


@Controller
public class WelcomeController {


	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {
          List<Person> persons= new ArrayList<Person>();
          Person p= new Person();
          p.setFirstName("Pedro ");
          p.setLastName("Ruiz Aguilar");
          persons.add(p);
          p.setFirstName("Ismael ");
          p.setLastName("Ruiz Jurado");
          persons.add(p);
          p.setFirstName("Laura ");
          p.setLastName("Roldan Merat");
          persons.add(p);
          p.setFirstName("Alejandro ");
          p.setLastName("Ni idea");
          persons.add(p);
          p.setFirstName("Ivan ");
          p.setLastName("Sanchez San jose");
          persons.add(p);
          model.put("persons",persons);
          model.put("title","My project");
          model.put("group","Teachers");

	    return "welcome";
	  }
}
