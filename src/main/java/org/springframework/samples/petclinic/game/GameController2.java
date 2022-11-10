package org.springframework.samples.petclinic.game;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/games")
public class GameController2 {


    private static final String VIEWS_CREATE_GAME = "games/createGame";

    //Crear partida
    @GetMapping(value = "/new")
    public String initCreationForm(Map<String, Object> model) {
        Game  game = new Game();
        model.put("game", game);
        return VIEWS_CREATE_GAME;
    }

}
