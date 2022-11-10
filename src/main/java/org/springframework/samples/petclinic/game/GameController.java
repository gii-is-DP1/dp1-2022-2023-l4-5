package org.springframework.samples.petclinic.game;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GameController {

    // Constantes.
    private static final String VIEW_GAME_CREATE = "games/createGame";
    private static final String VIEW_GAME_LIST = "games/gamesList";
    private static final String VIEW_GAME_DETAILS = "games/gameDetails";
    // Servicios
    private final GameService gameService;



    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    // Obtener todas las partidas.
    @GetMapping
    public String getGames(ModelMap model) {
        model.put("selections", gameService.getAllGames());
        return VIEW_GAME_LIST;
    }

    // Obtener detalles de una partida.
    @GetMapping("/{gameId}")
    public String showGame(@PathVariable("gameId") int gameId, ModelMap model) {
        model.addAttribute(this.gameService.getGameById(gameId));
        return VIEW_GAME_DETAILS;
    }

    // Crear una partida.
    @GetMapping(value = "/new")
    public String initCreationForm(ModelMap model) {

        //Hacer lo mismo con: maxPlayer y con phase, accssesibilit, y los denas atributos
        List<Mode> ls = new ArrayList<Mode>();
        ls.add(Mode.MULTI_CLASS);
        ls.add(Mode.UNI_CLASS);
        List<Accessibility> ls2 = new ArrayList<Accessibility>();
        ls2.add(Accessibility.PRIVATE);
        ls2.add(Accessibility.PUBLIC);
        model.put("mode", ls);
        model.put("accesibility", ls2);
        model.put("game", new Game());
        return VIEW_GAME_CREATE;
    }


    @PostMapping(value = "/new")
    public String processCreationForm(@Valid Game game, BindingResult result) {
        if (result.hasErrors()) {
            return VIEW_GAME_CREATE;
        } else {
            gameService.saveGame(game);
            return VIEW_GAME_DETAILS;
        }
    }
}
