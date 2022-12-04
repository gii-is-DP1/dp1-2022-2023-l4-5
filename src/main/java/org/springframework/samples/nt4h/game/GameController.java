package org.springframework.samples.nt4h.game;


import com.github.cliftonlabs.json_simple.JsonObject;
import com.google.common.collect.Lists;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.turn.Advise;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// TODO: Cambiar nombre y enlaces.
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
    // Servicios
    private final GameService gameService;

    private final HeroService heroService;
    private final UserService userService;
    private final Advise advise = new Advise();


    @Autowired
    public GameController(GameService gameService, HeroService heroService, UserService userService) {
        this.gameService = gameService;
        this.heroService = heroService;
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
        return new HeroInGame(); // TODO: comprobar si necesita valores por defecto.
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
        Game loggedGame = getUser().getGame();
        return loggedGame != null ? loggedGame : new Game();
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
        String message = advise.getMessage();
        advise.resetMessage();
        return message;
    }

    @ModelAttribute("messageType")
    public String getMessageType() {
        String messageType = advise.getMessageType();
        advise.setMessageType("");
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
            return advise.sendError("Ya estás en una partida y esa es  " + loggedUser.getGame() + ".", PAGE_GAMES);
        if (oldGame == null) userService.addUserToGame(loggedUser, newGame);
        model.put("numHeroes", gameService.getGameById(gameId).isUniClass()); // El jugador todavía no se ha unido, CUIODADO.
        return VIEW_GAME_LOBBY;
    }

    // TODO: SI es privada deberá de solicitar la contraseña.
    // Crear un jugador y vincularlo con la partida.
    @PostMapping(value = "/{gameId}")
    public String processCreationPlayerReady(@Valid Player player, @PathVariable Integer gameId) {
        // Obtenemos los datos.
        User loggedUser = getUser();
        Game game = getGame();
        if (loggedUser.getPlayer() != null)
            return PAGE_GAME_HERO_SELECT.replace("{gameId}", gameId.toString()).replace("{playerId}", loggedUser.getPlayer().getId().toString());
        // Creamos el jugador si el usuario no se había unido a la partida.
        Player newPlayer;
        try {
            newPlayer = gameService.addPlayerToGame(player, game, loggedUser);
        } catch (FullGameException e) {
            return advise.sendError("Game is full", PAGE_GAMES);
        }
        return PAGE_GAME_HERO_SELECT.replace("{gameId}", gameId.toString()).replace("{playerId}", newPlayer.getId().toString());
    }

    //Elegir here
    @GetMapping(value = "/heroSelect")
    public String initHeroSelectForm() {
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
        HeroInGame updatedHero = heroInGame.createHero(hero, player);
        // Esta línea va dedicada a Pedro, por su gran colaboración a la búsqueda de errores.
        if (Boolean.TRUE.equals(player.getReady()))
            return PAGE_GAME_LOBBY.replace("{gameId}", game.getId().toString());
        // Vinculamos el héroe al jugador.
        try {
            gameService.addHeroToPlayer(player, updatedHero, game);
        } catch (HeroAlreadyChosenException e) {
            return advise.sendError("Hero already chosen.", PAGE_GAME_HERO_SELECT);
        } catch (FullGameException e) {
            return advise.sendError("Game is full.", PAGE_GAMES);
        } catch (RoleAlreadyChosenException e) {
            return advise.sendError("Role already chosen", PAGE_GAME_HERO_SELECT);
        }
        return PAGE_GAME_LOBBY.replace("{gameId}", game.getId().toString());

    }

    // Llamamos al formulario para crear la partida.
    @GetMapping(value = "/new")
    public String initCreationForm() {
        // Comprobamos si está en otras partidas.
        Game oldGame = getGame();
        if (!oldGame.isNew())
            return advise.sendError("Ya estás en una partida y esa es  " + oldGame + ".", PAGE_GAMES);
        return VIEW_GAME_CREATE;
    }

    // Comprobamos si la partida es correcta y la almacenamos.
    @PostMapping(value = "/new")
    public String processCreationForm(@Valid Game game, BindingResult result) throws FullGameException {
        User user = userService.getLoggedUser();
        if (result.hasErrors()) return VIEW_GAME_CREATE;
        gameService.createGame(user, game); // TODO: Comprobar si la id se guarda.
        return PAGE_GAME_LOBBY.replace("{gameId}", game.getId().toString());
    }

    // Actualizar los jugadores en el lobby.
    @GetMapping("/update/{gameId}")
    public ResponseEntity<String> updateMessages(@PathVariable Integer gameId) {
        JsonObject jsonObject = new JsonObject();
        Game game = gameService.getGameById(gameId);
        LocalDateTime now = LocalDateTime.now();
        jsonObject.put("messages", createMessages(game));
        long difference = ChronoUnit.SECONDS.between(game.getStartDate(), now);
        jsonObject.put("timer", 20 - difference);
        return new ResponseEntity<>(jsonObject.toJson(), HttpStatus.OK);
    }

    @GetMapping("/selectOrder/")
    public String orderPlayers() {
        // TODO: Mostrar un formulario, ¿quizás para elegir las dos cartas?
        return "I DON'T KNOW";
    }

    // Tiene que recibir las cartas de habilidad que desea utilizar el jugador, por tanto, se va a modificar entero.
    @PostMapping("/selectOrder")
    public String processOrderPlayers() {
        List<Player> players = getPlayers();
        Game game = getGame();
        gameService.orderPlayer(players, game);

        return VIEW_GAME_ORDER;
    }
}
