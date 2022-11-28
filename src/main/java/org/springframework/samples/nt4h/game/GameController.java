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
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    private static final String PAGE_GAME_HERO_SELECT = "redirect:/games/heroSelect";
    private static final String PAGE_GAMES = "redirect:/games";
    private static final String VIEW_GAME_ORDER = "games/selectOrder";
    private String message = "";
    private String messageType = "";

    // Servicios
    private final GameService gameService;

    private final HeroService heroService;

    private final PlayerService playerService;
    private final UserService userService;


    @Autowired
    public GameController(GameService gameService, HeroService heroService, PlayerService playerService, UserService userService) {
        this.gameService = gameService;
        this.heroService = heroService;
        this.playerService = playerService;
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

    @ModelAttribute("hero")
    public HeroInGame getHero() {
        return new HeroInGame();
    }

    private static List<String> createMessages(Game game) {
        return game.getPlayers().stream().map(player -> player.getName() + " { " +
                player.getHeroes().stream().map(hero -> hero.getHero().getName()).sorted().reduce((s, s2) -> s + ", " + s2)
                    .orElse("No hero selected") + " }" + " " + (Boolean.TRUE.equals(player.getReady()) ? "Ready" : "Not ready"))
            .collect(Collectors.toList());
    }

    @ModelAttribute("player")
    public Player getPlayer() {
        User loggedUser = getUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().glory(0).gold(0).build();
    }

    @ModelAttribute("players")
    public List<Player> getPlayers() {
        Game game = getGame();
        return game != null ? game.getPlayers() : null;
    }

    @ModelAttribute("game")
    public Game getGame() {
        User loggedUser = getUser();
        return loggedUser != null ? loggedUser.getGame() : new Game();
    }

    @ModelAttribute("games")
    public List<Game> getGames() {
        return gameService.getAllGames();
    }

    @ModelAttribute("user")
    public User getUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("message")
    public String getMessage() {
        return message;
    }

    @ModelAttribute("messageType")
    public String getMessageType() {
        return messageType;
    }

    // Obtener todas las partidas.
    @GetMapping
    public String showGames() {
        return VIEW_GAME_LIST;
    }

    /// Unirse a una partida.
    @GetMapping("/{gameId}")
    public String joinGame(@PathVariable("gameId") int gameId, ModelMap model) {
        User loggedUser = getUser();
        Game newGame = gameService.getGameById(gameId);
        Game oldGame = loggedUser.getGame();
        if (oldGame != null && oldGame != newGame)
            return sendError("Ya estás en una partida y esa es  " + loggedUser.getGame() + ".", PAGE_GAMES);
        if (oldGame == null) {
            loggedUser.setGame(gameService.getGameById(gameId));
            userService.saveUser(loggedUser);
        }
        model.put("numHeroes", gameService.getGameById(gameId).isUniClass()); // El jugador todavía no se ha unido, CUIODADO.
        resetMessage();
        return VIEW_GAME_LOBBY;
    }

    // Crear un jugador y vincularlo con la partida.
    @PostMapping(value = "/{gameId}")
    public String processCreationPlayerReady(@Valid Player player, @PathVariable Integer gameId) {
        // Obtenemos los datos.
        User loggedUser = getUser();
        Game game = getGame();
        if (loggedUser.getPlayer() != null)
            return PAGE_GAME_HERO_SELECT.replace("{gameId}", gameId.toString()).replace("{playerId}", loggedUser.getPlayer().getId().toString());
        // Creamos el jugador si el usuario no se había unido a la partida.
        Player newPlayer = player.toBuilder().host(false).birthDate(loggedUser.getBirthDate()).game(game).build();
        newPlayer.setName(loggedUser.getUsername());
        try {
            game.addPlayer(newPlayer);
        } catch (FullGameException e) {
            return sendError("Game is full", PAGE_GAMES);
        }
        playerService.savePlayer(newPlayer);
        loggedUser.setPlayer(newPlayer);
        userService.saveUser(loggedUser);
        resetMessage();
        return PAGE_GAME_HERO_SELECT.replace("{gameId}", gameId.toString()).replace("{playerId}", newPlayer.getId().toString());
    }

    //Elegir here
    @GetMapping(value = "/heroSelect")
    public String initHeroSelectForm() {
        resetMessage();
        // Los datos para el formulario.
        return VIEW_GAME_HERO_SELECT;
    }

    // Analizamos la elección del héroe.
    @PostMapping(value = "/heroSelect")
    public String processHeroSelectForm(HeroInGame heroInGame) {
        // Obtenemos los datos.
        Hero hero = heroService.getHeroById(heroInGame.getHero().getId());
        Game game = getGame();
        Player player = getPlayer();
        // Esta línea va dedicada a Pedro, por su gran colaboración a la búsqueda de errores.
        if (Boolean.TRUE.equals(player.getReady()))
            return PAGE_GAME_LOBBY.replace("{gameId}", game.getId().toString());
        // Vinculamos el héroe al jugador.
        HeroInGame updatedHero = heroInGame.toBuilder().hero(hero).player(player).actualHealth(hero.getHealth()).build();
        playerService.addDeckFromRole(player, game.getMode());
        player.setReady(player.getHeroes().size() == game.getMode().getNumHeroes());
        // Si el héroe ya ha sido elegido o ya tenía uno de ese rol, se le impedirá elegirlo.
        try {
            game.addPlayerWithNewHero(player, updatedHero);
        } catch (HeroAlreadyChosenException e) {
            return sendError("Hero already chosen.", PAGE_GAME_HERO_SELECT);
        } catch (FullGameException e) {
            return sendError("Game is full.", PAGE_GAMES);
        } catch (RoleAlreadyChosenException e) {
            return sendError("Role already chosen", PAGE_GAME_HERO_SELECT);
        }
        heroService.saveHeroInGame(updatedHero);
        playerService.savePlayer(player);
        gameService.saveGame(game);
        resetMessage();
        return PAGE_GAME_LOBBY.replace("{gameId}", game.getId().toString());

    }

    // Llamamos al formulario para crear la partida.
    @GetMapping(value = "/new")
    public String initCreationForm() {
        // Comprobamos si está en otras partidas.
        Optional<Game> otherGame = gameService.getUserInOtherGame(null, userService.getLoggedUser());
        if (otherGame.isPresent())
            return sendError("Ya estás en una partida y esa es  " + otherGame.get() + ".", PAGE_GAMES);
        resetMessage();
        return VIEW_GAME_CREATE;
    }

    // Comprobamos si la partida es correcta y la almacenamos.
    @PostMapping(value = "/new")
    public String processCreationForm(@Valid Game game, BindingResult result) throws FullGameException {
        User user = userService.getLoggedUser();
        if (result.hasErrors()) return VIEW_GAME_CREATE;
        Player newPlayer = Player.builder().host(true).glory(0).gold(0).ready(false).build();
        newPlayer.setName(user.getUsername());
        game.addPlayer(newPlayer);
        gameService.saveGame(game);
        resetMessage();
        return PAGE_GAME_LOBBY.replace("{gameId}", game.getId().toString());
    }

    // Actualizar los jugadores en el lobby.
    @GetMapping("/update/{gameId}")
    public ResponseEntity<String> updateMessages(@PathVariable Integer gameId) {
        resetMessage();
        JsonObject jsonObject = new JsonObject();
        Game game = gameService.getGameById(gameId);
        LocalTime time = LocalTime.now();
        jsonObject.put("messages", createMessages(game));
        // TODO: Arreglar
        jsonObject.put("timer", 80 - ((time.getMinute() * 60 + time.getSecond()) - (game.getStartDate().getMinute() * 60 + game.getStartDate().getSecond())));
        return new ResponseEntity<>(jsonObject.toJson(), HttpStatus.OK);
    }

    @GetMapping("/selectOrder/")
    public String orderPlayers() {
        resetMessage();
        List<Player> players = getPlayers();
        List<Triplet<Integer, Player, Integer>> datos = Lists.newArrayList();
        for (var i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            List<AbilityInGame> abilities = player.getInDeck();
            datos.add(new Triplet<>(i, player, abilities.get(0).getAttack() + abilities.get(1).getAttack()));
        }
        datos.sort((o1, o2) -> o2.getValue2().compareTo(o1.getValue2()));
        if (Objects.equals(datos.get(0).getValue2(), datos.get(1).getValue2()) &&
            datos.get(0).getValue1().getBirthDate().isAfter(datos.get(1).getValue1().getBirthDate())) {
            var first = datos.get(0);
            var second = datos.get(1);
            datos.set(0, second);
            datos.set(1, first);
        }
        // Hace falta un post para almacenar.
        datos.forEach(triplet -> triplet.getValue1().setSequence(triplet.getValue0() + 1));
        resetMessage();
        return VIEW_GAME_ORDER;
    }

    public String sendError(String message, String redirect) {
        this.message = message;
        messageType = "danger";
        return redirect;
    }

    private void resetMessage() {
        this.message = "";
        this.messageType = "";
    }
}
