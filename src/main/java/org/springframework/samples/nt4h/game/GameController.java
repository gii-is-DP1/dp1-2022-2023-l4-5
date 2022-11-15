package org.springframework.samples.nt4h.game;


import com.github.cliftonlabs.json_simple.JsonObject;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.model.NamedEntity;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
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
    private static final String VIEW_GAME_HERO_SELECT = "games/heroSelect";
    private static final String PAGE_GAME_HERO_SELECT = "redirect:/games/{gameId}/{playerId}";

    // Servicios
    private final GameService gameService;

    private final HeroService heroService;

    private final PlayerService playerService;
    private final UserService userService;

    @Autowired
    public GameController(GameService gameService, HeroService heroService, PlayerService playerService, UserService userService) {
        this.gameService = gameService;
        this.heroService = heroService;
        this.playerService= playerService;
        this.userService = userService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("mode")
    public List<Mode> getMode() {
        return Lists.newArrayList(Mode.UNI_CLASS, Mode.MULTI_CLASS);
    }

    @ModelAttribute("heroes")
    public List<Hero> getHeroes() {
        return heroService.getAllHeros();
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
        User user = userService.currentUser();
        if (game.getPlayers().stream().anyMatch(player -> player.getName().equals(user.getUsername()))) {
            Player player = game.getPlayers().stream().filter(p -> p.getName().equals(user.getUsername())).findFirst().get();
            model.put("p", player);
        } else {
            model.put("p", new Player());
        }
        model.put("selections", game.getPlayers());

        model.put("numHeroes", game.getMode() == Mode.UNI_CLASS ? 1 : 2);
        return VIEW_GAME_LOBBY;
    }
    @PostMapping(value = "/{gameId}")
    public String processCreationPlayerReady(@Valid Player player, @PathVariable Integer gameId, BindingResult result) throws RoleAlreadyChosenException, HeroAlreadyChosenException {
        if (result.hasErrors()) {
            return PAGE_GAME_LOBBY;
        }
        User user = userService.currentUser();
        Game game = gameService.getGameById(gameId);
        player.setName(user.getUsername());
        if (game.getPlayers().stream().map(Player::getName).noneMatch(name -> name.equals(player.getName()))) {
            game.addPlayer(player);
            player.setGame(game);
            playerService.savePlayer(player);
            gameService.saveGame(game);
            System.out.println("Player " + player.getName() + " added to game " + game.getId());

        } else {
            player.setId(playerService.getPlayerByName(player.getName()).getId());
            // TODO: Lanzar una excepción para indicar que el jugador ya se ha unido a la partida.
        }
        return PAGE_GAME_HERO_SELECT.replace("{gameId}", gameId.toString()).replace("{playerId}", player.getId().toString());
    }

    //Elegir heroe
    @GetMapping(value = "/{gameId}/{playerId}")
    public String initHeroSelectForm(@PathVariable Integer gameId, @PathVariable Integer playerId, ModelMap model) {
        System.out.println("initHeroSelectForm");
        model.put("game", gameService.getGameById(gameId));
        model.put("player", playerService.getPlayerById(playerId));
        model.put("hero", new HeroInGame());
        return VIEW_GAME_HERO_SELECT;
    }

    @PostMapping(value = "/{gameId}/{playerId}")
    public String processHeroSelectForm(HeroInGame heroInGame, @PathVariable Integer gameId, @PathVariable Integer playerId, BindingResult result) {
        if (result.hasErrors()) {
            return PAGE_GAME_HERO_SELECT;
        }
        Hero hero = heroService.getHeroById(heroInGame.getHero().getId());
        Game game = gameService.getGameById(gameId);
        Player player = playerService.getPlayerById(playerId);
        heroInGame.setActualHealth(hero.getHealth());
        try {
            heroInGame.setPlayer(player);
            player.addHero(heroInGame);
            if (player.getHeroes() != null && (player.getHeroes().size() == game.getMode().getNumHeroes()))
                player.setReady(true);
            playerService.savePlayer(player);
            gameService.saveGame(game);
        } catch (RoleAlreadyChosenException e) {
            System.out.println("RoleAlreadyChosenException");
            result.rejectValue("hero", "role already chosen");
            return PAGE_GAME_HERO_SELECT;
        } catch (HeroAlreadyChosenException e) {
            System.out.println("HeroAlreadyChosenException");
            result.rejectValue("hero", "hero already chosen");
            return PAGE_GAME_HERO_SELECT;
        }

        System.out.println("----------------------");
        System.out.println(player.getHeroes().stream().map(HeroInGame::getHero).map(NamedEntity::getName).collect(Collectors.toList()));
        System.out.println(game.getMode().getNumHeroes());
        System.out.println(player.getHeroes().size() == game.getMode().getNumHeroes());
        System.out.println(player.getHeroes() != null);
        System.out.println(player.getReady());
        System.out.println("----------------------");

        return PAGE_GAME_LOBBY.replace("{gameId}", gameId.toString());

    }

    // Crear una partida.
    @GetMapping(value = "/new")
    public String initCreationForm(ModelMap model) {
        model.put("game", new Game());
        return VIEW_GAME_CREATE;
    }


    @PostMapping(value = "/new")
    public String processCreationForm(@Valid Game game, BindingResult result) throws HeroAlreadyChosenException {
        if (result.hasErrors()) {
            return VIEW_GAME_CREATE;
        } else {
            gameService.saveGame(game);
            return PAGE_GAME_LOBBY.replace("{gameId}", game.getId().toString());
        }
    }

    // Acrualizar los jugadores en el lobby.
    @GetMapping("/update/{gameId}")
    public ResponseEntity<String> updateMessages(@PathVariable Integer gameId) {
        JsonObject jsonObject = new JsonObject();
        System.out.println(gameService.getGameById(gameId).getPlayers().stream().flatMap(player -> player.getHeroes().stream()).map(HeroInGame::getHero).map(NamedEntity::getName).collect(Collectors.toList()));
        jsonObject.put("messages",
            gameService.getGameById(gameId)
                .getPlayers().stream().map(player -> player.getName() + " { " +
                    player.getHeroes().stream().map(hero -> hero.getHero().getName()).reduce((s, s2) -> s + ", " + s2)
                        .orElse("No hero selected") + " }" + " " + (player.getReady() ? "Ready": "Not ready"))
                .collect(Collectors.toList()));
        return new ResponseEntity<>(jsonObject.toJson(), HttpStatus.OK);
    }
}
