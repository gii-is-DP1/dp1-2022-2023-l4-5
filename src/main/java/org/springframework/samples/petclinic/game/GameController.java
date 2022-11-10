package org.springframework.samples.petclinic.game;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameService gameService;

    @GetMapping()
    public String getGames(Map<String,Object> model) {
        List<Game> results = gameService.getAll();
        model.put("selections", results);
        return "games/gamesList";
    }
    @GetMapping("/{gameId}")
    public ModelAndView showOwner(@PathVariable("gameId") int gameId) {
        ModelAndView mav = new ModelAndView("games/gameDetails");
        mav.addObject(this.gameService.getById(gameId));
        return mav;
    }

}
