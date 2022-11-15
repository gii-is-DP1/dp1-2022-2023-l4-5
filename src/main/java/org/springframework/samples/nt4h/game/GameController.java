package org.springframework.samples.nt4h.game;


import com.github.cliftonlabs.json_simple.JsonObject;
import com.google.common.collect.Lists;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private static final String VIEW_GAME_ORDER = "games/selectOrder";


    // Servicios
    private final GameService gameService;
    private final UserService userService;
    private final HeroService heroService;

    private final PlayerService playerService;

    @Autowired
    public GameController(GameService gameService, HeroService heroService, PlayerService playerService, UserService userService) {
        this.gameService = gameService;
        this.heroService = heroService;
        this.playerService= playerService;
        this.userService= userService;
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
                player.setBirthDate(user.getBirthDate());
                game.addPlayer(player);
                player.setGame(game);
                playerService.savePlayer(player);
                gameService.saveGame(game);
            } else {
                player.setId(playerService.getPlayerByName(player.getName()).getId());
                // TODO: Lanzar una excepción para indicar que el jugador ya se ha unido a la partida.
            }
            return PAGE_GAME_HERO_SELECT.replace("{gameId}", gameId.toString()).replace("{playerId}", player.getId().toString() );
        }
    }
    @PostMapping(value = "/{gameId}/{playerId}")
    public String processHeroSelectForm(HeroInGame heroInGame, @PathVariable Integer gameId, @PathVariable Integer playerId, BindingResult result) {
        Hero hero = heroService.getHeroById(heroInGame.getHero().getId());
        if (result.hasErrors()) {
            return PAGE_GAME_HERO_SELECT;
        } else {
            Game game = gameService.getGameById(gameId);
            Player player = playerService.getPlayerById(playerId);
            heroInGame.setActualHealth(hero.getHealth());
            heroInGame.setPlayer(player);
            player.addHero(heroInGame);
            playerService.savePlayer(player);
            gameService.saveGame(game);
            return PAGE_GAME_LOBBY.replace("{gameId}", gameId.toString());
        }
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

    //Indicar turno de los players
    @GetMapping("/{gameId}/selectOrder")
    public String orderRule(@PathVariable Integer gameId) {
        Game game = gameService.getGameById(gameId);
        List<Player> players = game.getPlayers();
        List<Triplet<Integer, Player, Integer>> datos = new ArrayList<>();
        for (var i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            List<AbilityInGame> abilities = player.getInDeck();
            datos.add(new Triplet<>(i, player, abilities.get(0).getAttack() + abilities.get(1).getAttack()));
        }
        datos.sort((o1, o2) -> o2.getValue2().compareTo(o1.getValue2()));
        if (Objects.equals(datos.get(0).getValue2(), datos.get(1).getValue2()) &&
            datos.get(0).getValue1().getBirthDate().after(datos.get(1).getValue1().getBirthDate())) {
            var first = datos.get(0);
            var second = datos.get(1);
            datos.set(0, second);
            datos.set(1, first);
        }
        datos.forEach(triplet -> triplet.getValue1().setSequence(triplet.getValue0() + 1));
        return VIEW_GAME_ORDER;
    }
}
