package org.springframework.samples.nt4h.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.samples.nt4h.card.ability.exceptions.LessThan4AbilitiesException;
import org.springframework.samples.nt4h.card.hero.exceptions.MaxUsesExcededException;
import org.springframework.samples.nt4h.card.product.exceptions.NotInSaleException;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.exceptions.*;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.turn.exceptions.*;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This advice is necessary because MockMvc is not a real servlet environment, therefore it does not redirect error
 * responses to [ErrorController], which produces validation response. So we need to fake it in tests.
 * It's not ideal, but at least we can use classic MockMvc tests for testing error response + document it.
 */
@ControllerAdvice
public class ExceptionHandlerConfiguration
{
	private BasicErrorController errorController;
    // add any exceptions/validations/binding problems

    private final UserService userService;
    private static final String PAGE_GAMES = "redirect:/games";
    private static final String PAGE_GAME_LOBBY = "redirect:/games/current";
    private static final String PAGE_GAME_HERO_SELECT = "redirect:/games/heroSelect";
    private static final String PAGE_EVASION = "redirect:/evasion";
    private static final String PAGE_MARKET = "redirect:/market";
    private static final String PAGE_REESTABLISHMENT = "redirect:/reestablishment";
    private final String PAGE_HERO_ATTACK = "redirect:/heroAttack";

    private final String PAGE_START = "redirect:/start";

    public ExceptionHandlerConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public ExceptionHandlerConfiguration(BasicErrorController errorController, UserService userService) {
        this.errorController = errorController;
        this.userService = userService;
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException() {
        return "exception";
    }

    @ExceptionHandler(FullGameException.class)
    public String handleFullGameException(HttpSession session) {
        session.setAttribute("message", "The game is full.");
        session.setAttribute("messageType", "danger");
        return PAGE_GAMES;
    }

    @ExceptionHandler(HeroAlreadyChosenException.class)
    public String handleHeroAlreadyChosenException(HttpSession session) {
        session.setAttribute("message", "The hero has been chosen by other player.");
        session.setAttribute("messageType", "danger");
        return PAGE_GAME_HERO_SELECT;
    }

    @ExceptionHandler(PlayerInOtherGameException.class)
    public String handlePlayerInOtherGameException(HttpSession session) {
        session.setAttribute("message", "You are already in another game.");
        session.setAttribute("messageType", "danger");
        return PAGE_GAME_LOBBY;
    }

    @ExceptionHandler(RoleAlreadyChosenException.class)
    public String handleRoleAlreadyChosenException(HttpSession session) {
        session.setAttribute("message", "You already have that role.");
        session.setAttribute("messageType", "danger");
        return PAGE_GAME_HERO_SELECT;
    }

    @ExceptionHandler(PlayerIsReadyException.class)
    public String handlePlayerIsReadyException(HttpSession session) {
        session.setAttribute("message", "You are already ready.");
        session.setAttribute("messageType", "danger");
        return PAGE_GAME_LOBBY;
    }

    @ExceptionHandler(UserInAGameException.class)
    public String handleUserInAGameException(HttpSession session) {
        User loggedUser = userService.getLoggedUser();
        Game game = loggedUser.getGame();
        session.setAttribute("message", "User is in a game (" + game.getName() + ").");
        session.setAttribute("messageType", "danger");
        return PAGE_GAMES;
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
    public String handleNoMoneyException(HttpSession session) {
        session.setAttribute("message", "You don't have enough money.");
        session.setAttribute("messageType", "danger");
        return PAGE_MARKET;
    }

    @ExceptionHandler(NotInSaleException.class)
    public String handleNotInSaleException(HttpSession session) {
        session.setAttribute("message", "The card is not in sale.");
        session.setAttribute("messageType", "danger");
        return PAGE_MARKET;
    }

    @ExceptionHandler(NoCurrentPlayer.class)
    public String handleNoCurrentPlayer(HttpSession session, HttpServletRequest request) {
        session.setAttribute("message", "It's not your turn");
        session.setAttribute("messageType", "danger");
        return "redirect:" + request.getRequestURI();
    }

    @ExceptionHandler(WhenEvasionDiscardAtLeast2Exception.class)
    public String handleWhenEvasionDiscardAtLeast2Exception(HttpSession session) {
        session.setAttribute("message", "You must discard at least 2 cards.");
        session.setAttribute("messageType", "danger");
        return PAGE_EVASION;
    }

    @ExceptionHandler(WithOutPhaseException.class)
    public String handleWithOutPhaseException(HttpSession session) {
        session.setAttribute("message", "You must choose a phase: EVADE or ATTACK.");
        session.setAttribute("messageType", "danger");
        return PAGE_START;
    }

    @ExceptionHandler(WithOutAbilityException.class)
    public String handleWithOutAbilityException(HttpSession session, HttpServletRequest request) {
        session.setAttribute("message", "You must choose an ability.");
        session.setAttribute("messageType", "danger");
        return "redirect:" + request.getRequestURI();
    }

    @ExceptionHandler(WithOutProductException.class)
    public String handleWithOutProductException(HttpSession session) {
        session.setAttribute("message", "You must choose a product.");
        session.setAttribute("messageType", "danger");
        return PAGE_MARKET;
    }

    @ExceptionHandler(TooManyAbilitiesException.class)
    public String handleTooManyAbilitiesException(HttpSession session) {
        session.setAttribute("message", "You can't have more than 4 abilities.");
        session.setAttribute("messageType", "danger");
        return PAGE_REESTABLISHMENT;
    }

    @ExceptionHandler(LessThan4AbilitiesException.class)
    public String handleLessThan4AbilitiesException(HttpSession session) {
        session.setAttribute("message", "You must have at least 4 abilities.");
        session.setAttribute("messageType", "danger");
        return PAGE_REESTABLISHMENT;
    }

    @ExceptionHandler(CapacitiesRequiredException.class)
    public String handleCapacitiesRequiredException(HttpSession session) {
        session.setAttribute("message", "You don't have the requirements needed.");
        session.setAttribute("messageType", "danger");
        return PAGE_MARKET;
    }

    @ExceptionHandler(MaxUsesExcededException.class)
    public String handleMaxUsesExcededException(HttpSession session) {
        session.setAttribute("message", "You can't use this ability anymore.");
        session.setAttribute("messageType", "danger");
        return PAGE_HERO_ATTACK;
    }
}
