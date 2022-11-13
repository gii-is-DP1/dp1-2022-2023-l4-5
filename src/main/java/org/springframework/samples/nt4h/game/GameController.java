package org.springframework.samples.nt4h.game;


import com.github.cliftonlabs.json_simple.JsonObject;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/games")
public class GameController {

    // Constantes.
    private static final String VIEW_GAME_CREATE = "games/createGame";
    private static final String VIEW_GAME_LIST = "games/gamesList";
    private static final String VIEW_GAME_LOBBY = "games/gameLobby";
    private static final String PAGE_GAME_LOBBY = "redirect:/games/{gameId}";
    // Servicios
    private final GameService gameService;
    private final UserService userService;
    private final HeroService heroService;

    private final PlayerService playerService;

    @Autowired
    public GameController(GameService gameService, UserService userService, HeroService heroService, PlayerService playerService) {
        this.gameService = gameService;
        this.userService = userService;
        this.heroService = heroService;
        this.playerService= playerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("mode")
    public List<Mode> getMode() {
        return Lists.newArrayList(Mode.UNI_CLASS, Mode.MULTI_CLASS);
    }

    @ModelAttribute("accessibility")
    public List<Accessibility> getAccessibility() {
        return Lists.newArrayList(Accessibility.PUBLIC, Accessibility.PRIVATE);
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
        Game game = gameService.getGameById(gameId);
        model.put("selections", game.getPlayers());
        model.put("p", new Player());
        return VIEW_GAME_LOBBY;
    }
    @PostMapping(value = "/{gameId}")
    public String processCreationPlayerReady(@Valid Player player, @PathVariable Integer gameId, BindingResult result) {
        if (result.hasErrors()) {
            return PAGE_GAME_LOBBY;
        } else {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails ud = null;
            if (principal instanceof UserDetails) {
                ud = ((UserDetails) principal);
            }
            User user = userService.getUserByUsername(ud.getUsername());
            Game game = gameService.getGameById(gameId);
            player.setName(ud.getUsername());
            if (game.getPlayers().stream().map(Player::getName).noneMatch(name -> name.equals(player.getName()))) {
                game.addPlayer(player);
                player.setGame(game);
                playerService.savePlayer(player);
                gameService.saveGame(game);
            } else {
                // TODO: Lanzar una excepción para indicar que el jugador ya se ha unido a la partida.
            }

            return PAGE_GAME_LOBBY;
        }
    }

    // Crear una partida.
    @GetMapping(value = "/new")
    public String initCreationForm(ModelMap model) {
        model.put("game", new Game());
        return VIEW_GAME_CREATE;
    }


    @PostMapping(value = "/new")
    public String processCreationForm(@Valid Game game, BindingResult result) {
        System.out.println(result.getAllErrors());
        if (result.hasErrors()) {
            return VIEW_GAME_CREATE;
        } else {
            gameService.saveGame(game);
            System.out.println(game);
            return PAGE_GAME_LOBBY.replace("{gameId}", game.getId().toString());
        }
    }

    // Acrualizar los jugadores en el lobby.
    @GetMapping("/update/{gameId}")
    public ResponseEntity<String> updateMessages(@PathVariable Integer gameId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("messages",
            gameService.getGameById(gameId)
                .getPlayers().stream().map(Player::getName)
                .collect(Collectors.toList()));
        return new ResponseEntity<>(jsonObject.toJson(), HttpStatus.OK);
    }


    // Clase auxiliar
    public Player newPlayer(User user, Player player) {
        user.setPlayer(player);
        player.setName(user.getUsername());
        player.setSequence(1);
        player.setReady(Boolean.FALSE);
        return player;
    }
}
