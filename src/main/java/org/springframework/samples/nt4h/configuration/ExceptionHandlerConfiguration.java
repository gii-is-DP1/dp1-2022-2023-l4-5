package org.springframework.samples.nt4h.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.exceptions.*;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.turn.exceptions.EnoughCardsException;
import org.springframework.samples.nt4h.turn.exceptions.EnoughEnemiesException;
import org.springframework.samples.nt4h.turn.exceptions.NoCurrentPlayer;
import org.springframework.samples.nt4h.turn.exceptions.NoMoneyException;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * This advice is necessary because MockMvc is not a real servlet environment, therefore it does not redirect error
 * responses to [ErrorController], which produces validation response. So we need to fake it in tests.
 * It's not ideal, but at least we can use classic MockMvc tests for testing error response + document it.
 */
@ControllerAdvice
public class ExceptionHandlerConfiguration
{
	@Autowired
	private BasicErrorController errorController;
    // add any exceptions/validations/binding problems

    @Autowired
    private UserService userService;
    private static final String PAGE_GAMES = "redirect:/games";
    private static final String PAGE_GAME_LOBBY = "redirect:/games/current";
    private static final String PAGE_GAME_HERO_SELECT = "redirect:/games/heroSelect";

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException() {
        return "exception";
    }

    @ExceptionHandler(FullGameException.class)
    public ModelAndView handleFullGameException() {
        ModelAndView mav = new ModelAndView(PAGE_GAMES);
        mav.addObject("message", "Game is full.");
        mav.addObject("messageType", "error");
        return mav;
    }

    @ExceptionHandler(HeroAlreadyChosenException.class)
    public ModelAndView handleHeroAlreadyChosenException() {
        ModelAndView mav = new ModelAndView(PAGE_GAME_HERO_SELECT);
        mav.addObject("message", "Hero already chosen.");
        mav.addObject("messageType", "error");
        return mav;
    }

    @ExceptionHandler(PlayerInOtherGameException.class)
    public String handlePlayerInOtherGameException() {
        return "exception";
    }

    @ExceptionHandler(RoleAlreadyChosenException.class)
    public ModelAndView handleRoleAlreadyChosenException() {
        ModelAndView mav = new ModelAndView(PAGE_GAME_HERO_SELECT);
        mav.addObject("message", "Role already chosen.");
        mav.addObject("messageType", "error");
        return mav;
    }

    @ExceptionHandler(PlayerIsReadyException.class)
    public ModelAndView handlePlayerIsReadyException() {
        ModelAndView mav = new ModelAndView(PAGE_GAME_LOBBY);
        mav.addObject("message", "Player is ready.");
        mav.addObject("messageType", "error");
        return mav;
    }

    @ExceptionHandler(UserInAGameException.class)
    public ModelAndView handleUserInAGameException() {
        User loggedUser = userService.getLoggedUser();
        Game game = loggedUser.getGame();
        ModelAndView mav = new ModelAndView(PAGE_GAMES);
        mav.addObject("message", "User is in a game (" + game.getName() + ").");
        mav.addObject("messageType", "error");
        return mav;
    }

    // TODO: decidir si esto debería de ser una excepción o no.
    @ExceptionHandler(UserHasAlreadyAPlayerException.class)
    public String handleUserHasAlreadyAPlayerException() {
        return PAGE_GAME_HERO_SELECT;
    }

    @ExceptionHandler(EnoughCardsException.class)
    public String handleEnoughCardsException() {
        return "exception";
    }

    @ExceptionHandler(EnoughEnemiesException.class)
    public String handleEnoughEnemiesException() {
        return "exception";
    }

    @ExceptionHandler(NoMoneyException.class)
    public String handleNoMoneyException() {
        return "exception";
    }

    @ExceptionHandler(NoCurrentPlayer.class)
    public String handleNoCurrentPlayer() {
        return "exception";
    }
}
