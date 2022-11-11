package org.springframework.samples.petclinic.game;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/games")
public class GameController {

    // Constantes.
    private static final String VIEW_GAME_CREATE = "games/createGame";
    private static final String VIEW_GAME_LIST = "games/gamesList";
    private static final String VIEW_GAME_LOBBY = "games/gameLobby";
    // Servicios
    private final GameService gameService;
    private final UserService userService;

    @Autowired
    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
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

    /// Unirse a una partida.
    @GetMapping("/{gameId}")
    public String joinGame(@PathVariable("gameId") int gameId, ModelMap model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails ud = null;
        if(principal instanceof UserDetails) {
            ud = ((UserDetails)principal);
        }
        User user = userService.getUserByUsername(ud.getUsername());
        Game game = gameService.getGameById(gameId).get();
        Player player = nuevoPlayer(user);
        List<Player> pl = game.getPlayers();
        pl.add(player);
        game.setPlayers(pl);
        model.put("selections", game.getPlayers());
        return VIEW_GAME_LOBBY;
    }

    // Crear una partida.
    @GetMapping(value = "/new")
    public String initCreationForm(ModelMap model) {
        model.put("game", new Game());
        return VIEW_GAME_CREATE;
    }

    // Clase auxiliar
    public Player nuevoPlayer(User user) {
        Player player = new Player();
        user.setPlayer(player);
        player.setId(user.getId());
        player.setName(user.getUsername());
        player.setGold(0);
        player.setGlory(0);
        player.setEvasion(Boolean.TRUE);
        player.setNumOrcsKilled(0);
        player.setNumWarLordKilled(0);
        player.setDamageDealed(0);
        player.setDamageDealedToNightLords(0);
        player.setSequence(1);
        //Set<HeroInGame> shg = Set.of();
        //player.setHeroes();
        //faltan algunos atributos
        return player;
    }
}
