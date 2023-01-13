package org.springframework.samples.nt4h.game;



import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.exceptions.*;
import org.springframework.samples.nt4h.message.Advise;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GameController {

    // Constantes.
    private static final String VIEW_GAME_CREATE = "games/createGame";
    private static final String VIEW_GAME_LIST = "games/gamesList";
    private static final String VIEW_GAME_LOBBY = "games/gameLobby";
    private static final String PAGE_GAME_TO_LOBBY = "redirect:/games/{gameId}";
    private static final String VIEW_GAME_HERO_SELECT = "games/heroSelect";
    private static final String PAGE_GAMES = "redirect:/games";
    private static final String VIEW_GAME_ORDER = "games/selectOrder";
    private static final String VIEW_GAME_PREORDER = "games/preSelectOrder";
    private static final String GAMES_IN_PROGRES= "admins/gameInProgres";
    private static final String PAGE_CURRENT_GAME = "redirect:/games/current";

    // Servicios
    private final GameService gameService;
    private final HeroService heroService;
    private final UserService userService;
    private final PlayerService playerService;
    private final Advise advise;


    @Autowired
    public GameController(GameService gameService, HeroService heroService, UserService userService, PlayerService playerService, Advise advise) {
        this.gameService = gameService;
        this.heroService = heroService;
        this.userService = userService;
        this.playerService = playerService;
        this.advise = advise;
    }

    @GetMapping("/gameInProgres")
    private String getGamesInProgres() {
        return GAMES_IN_PROGRES;
    }

    @InitBinder
    private void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("mode")
    private List<Mode> getMode() {
        return Lists.newArrayList(Mode.UNI_CLASS, Mode.MULTI_CLASS);
    }

    @ModelAttribute("heroes")
    private List<Hero> getHeroes() {
        return heroService.getAllHeroes();
    }

    @ModelAttribute("accessibility")
    private List<Accessibility> getAccessibility() {
        return Lists.newArrayList(Accessibility.PUBLIC, Accessibility.PRIVATE);
    }

    @ModelAttribute("newHero")
    private HeroInGame getHero() {
        return new HeroInGame();
    }


    @ModelAttribute("loggedPlayer")
    private Player getPlayer() {
        User loggedUser = getLoggedUser();
        return loggedUser.getPlayer() != null ? loggedUser.getPlayer() : Player.builder().statistic(Statistic.createStatistic()).build();
    }

    @ModelAttribute("players")
    private List<Player> getPlayers() {
        Game game = getGame();
        return game != null ? game.getPlayers() : null;
    }

    @ModelAttribute("game")
    private Game getGame() {
        Game loggedGame = getLoggedUser().getGame();
        return loggedGame != null ? loggedGame : new Game();
    }

    @ModelAttribute("games")
    private List<Game> getGames() {
        return gameService.getAllGames();
    }

    @ModelAttribute("loggedUser")
    private User getLoggedUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("chat")
    private Message getChat() {
        return new Message();
    }

    // Obtener todas las partidas.
    @GetMapping
    public String showGames(@RequestParam(defaultValue = "0") int page, ModelMap model, HttpSession session) {
        page = page < 0 ? 0 : page;
        Pageable pageable = PageRequest.of(page, 5);
        List<Game> games = gameService.getAllGames();
        Page<Game> gamePage = gameService.getAllGames(pageable);
        if (!games.isEmpty() && gamePage.isEmpty()) {
            page = games.size() / 5;
            pageable = PageRequest.of(page, 5);
            gamePage = gameService.getAllGames(pageable);
        }
        advise.getMessage(session, model);
        model.put("isNext", gamePage.hasNext());
        model.put("games", gamePage.getContent());
        model.put("page", page);
        return VIEW_GAME_LIST;
    }

    // Parida actual
    @GetMapping("/current")
    public String showCurrentGame(HttpSession session, HttpServletRequest request) {
        Game game = getGame();
        advise.keepUrl(session, request);
        return game == null ? PAGE_GAMES : VIEW_GAME_LOBBY;
    }

    // Visualizar una partida.
    @GetMapping("/view/{gameId}")
    public String showGame(@PathVariable("gameId") int gameId, ModelMap model) throws UserInAGameException {
        Game game = gameService.getGameById(gameId);
        model.put("game", game);
        gameService.addSpectatorToGame(game, getLoggedUser());
        return VIEW_GAME_LOBBY;
    }

    /// Unirse a una partida.
    @GetMapping("/{gameId}")
    public String joinGame(@PathVariable("gameId") int gameId, @RequestParam(defaultValue = "null") String password, ModelMap model, HttpSession session, HttpServletRequest request) throws IncorrectPasswordException, UserHasAlreadyAPlayerException, UserInAGameException, FullGameException {
        Game newGame = gameService.getGameById(gameId);
        User loggedUser = getLoggedUser();
        if (!newGame.getPlayers().contains(loggedUser.getPlayer()))
            gameService.addPlayerToGame(newGame, loggedUser, password);
        advise.keepUrl(session, request);
        advise.getMessage(session, model);
        model.put("numHeroes", newGame.isUniClass());
        return PAGE_CURRENT_GAME;
    }

    //Elegir here
    @GetMapping(value = "/heroSelect")
    public String initHeroSelectForm(HttpSession session, ModelMap model, HttpServletRequest request) {
        // Los datos para el formulario.
        User loggedUser = getLoggedUser();
        Game game = getGame();
        advise.getMessage(session, model);
        advise.keepUrl(session, request);
        advise.chooseHero();
        return VIEW_GAME_HERO_SELECT;
    }

    // Analizamos la elección del héroe.
    @PostMapping(value = "/heroSelect")
    public String processHeroSelectForm(HeroInGame heroInGame) throws RoleAlreadyChosenException, HeroAlreadyChosenException, PlayerIsReadyException {
        Player loggedPlayer = getPlayer();
        Game game = getGame();
        gameService.addHeroToPlayer(loggedPlayer, heroInGame, game);
        return PAGE_CURRENT_GAME;
    }

    // Llamamos al formulario para crear la partida.
    @GetMapping(value = "/new")
    public String initCreationForm() throws UserInAGameException {
        if (!getGame().isNew())
            throw new UserInAGameException();
        return VIEW_GAME_CREATE;
    }

    // Comprobamos si la partida es correcta y la almacenamos.
    @PostMapping(value = "/new")
    public String processCreationForm(@Valid Game game, BindingResult result) throws FullGameException {
        if (result.hasErrors()) return VIEW_GAME_CREATE;
        User loggedUser = userService.getLoggedUser();
        gameService.createGame(loggedUser, game);
        return PAGE_CURRENT_GAME;
    }

    @GetMapping("/selectOrder")
    public String orderPlayers(HttpSession session, HttpServletRequest request) {
        User loggedUser = getLoggedUser();
        Game game = getGame();
        advise.keepUrl(session, request);
        return VIEW_GAME_PREORDER;
    }

    // Tiene que recibir las cartas de habilidad que desea utilizar el jugador, por tanto, se va a modificar entero.
    @PostMapping("/selectOrder")
    public String processOrderPlayers(HttpSession session, HttpServletRequest request) {
        List<Player> players = getPlayers();
        Game game = getGame();
        if (players.stream().anyMatch(player -> player.getSequence() == -1))
            gameService.orderPlayer(players, game);
        advise.keepUrl(session, request);
        return VIEW_GAME_ORDER;
    }

    @GetMapping("deletePlayer/{playerId}")
    public String deletePlayer(@PathVariable("playerId") int playerId) {
        Game game = getGame();
        if(playerService.getPlayerById(playerId).getHost()) {
            if(game.getCurrentPlayer()==null) {
                gameService.deleteGameById(game.getId());
            }
            playerService.deletePlayerById(playerId);
            userService.removeUserFromGame(userService.getLoggedUser());
            return PAGE_GAMES;
        }else{
            userService.removeUserFromGame(userService.getUserByUsername(playerService.getPlayerById(playerId).getName()));
            playerService.deletePlayerById(playerId);
            return PAGE_GAME_TO_LOBBY.replace("{gameId}", game.getId().toString());
        }
    }

    @GetMapping("deleteGame/{gameId}")
    public String deleteGame(@PathVariable("gameId") int gameId) {
        gameService.deleteGameById(gameId);
        return PAGE_GAMES;
    }
}
