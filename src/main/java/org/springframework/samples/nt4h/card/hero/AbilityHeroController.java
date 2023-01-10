package org.springframework.samples.nt4h.card.hero;

import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.hero.exceptions.MaxUsesExcededException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Los héroes son:
 * - Magos:
 *   + Aranel
 *   + Taheral
 * - Exploradores:
 *   + Beleth-Il
 *   + Idril
 * - Ladrones:
 *   + Neddia
 *   + Feldon
 * - Caballeros.
 *   + Valèrys
 *   + Lisavette
 */
@Controller
@RequestMapping("abilities/hero")
public class AbilityHeroController {

    private final String PAGE_HERO_ATTACK = "redirect:/heroAttack";
    private final String PAGE_LOSE_CARD = "redirect:/loseCard";
    private final String VIEW_LOSE_CARD = "abilities/loseCard";
    private final String VIEW_CHOSE_ENEMY = "abilities/choseEnemy";
    private final String VIEW_COSE_CARD_FROM_MARKET = "abilities/choseCardFromMarket";
    private final String VIEW_CHOSE_CARD_FROM_DECK = "abilities/choseCardFromDeck";
    private final UserService userService;
    private final AbilityService abilityService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final GameService gameService;

    public AbilityHeroController(UserService userService, AbilityService abilityService, PlayerService playerService, TurnService turnService, GameService gameService) {
        this.userService = userService;
        this.abilityService = abilityService;
        this.playerService = playerService;
        this.turnService = turnService;
        this.gameService = gameService;
    }

    @ModelAttribute("loggedUser")
    public User getLoggedUser() {
        return userService.getLoggedUser();
    }

    @ModelAttribute("game")
    public Game getGame() {
        return getLoggedUser().getGame();
    }

    @ModelAttribute("currentPlayer")
    public Player getCurrentPlayer() {
        return getGame().getCurrentPlayer();
    }

    @ModelAttribute("logggedPlayer")
    public Player getLoggedPlayer() {
        return getLoggedUser().getPlayer();
    }

    /////////////////////////////////
    // Magos
    /////////////////////////////////

    // Aranel
    @GetMapping("/aranel")
    public String aranel(HttpSession session, ModelMap model) throws MaxUsesExcededException {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        Optional<HeroInGame> heroInGame = currentPlayer.getHeroes().stream().filter(h -> h.getHero().getName().equals("Aranel")).findFirst();
        if (!heroInGame.isPresent())
            return PAGE_HERO_ATTACK;
        HeroInGame aranel = heroInGame.get();
        if (aranel.getEffectUsed() > aranel.getHero().getMaxUses())
            throw new MaxUsesExcededException();
        aranel.setEffectUsed(aranel.getEffectUsed() + 1);
        playerService.savePlayer(currentPlayer);
        session.setAttribute("nextUrl", "abilities/exchangeCards");
        return VIEW_CHOSE_CARD_FROM_DECK;
    }

    // Taheral
    @GetMapping("/taheral")
    public String taheral(HttpSession session, ModelMap model) throws MaxUsesExcededException {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        Optional<HeroInGame> heroInGame = currentPlayer.getHeroes().stream().filter(h -> h.getHero().getName().equals("Aranel")).findFirst();
        if (!heroInGame.isPresent())
            return PAGE_HERO_ATTACK;
        HeroInGame taheral = heroInGame.get();
        if (taheral.getEffectUsed() > taheral.getHero().getMaxUses())
            throw new MaxUsesExcededException();
        // TODO
        taheral.setEffectUsed(taheral.getEffectUsed() + 1);
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    /////////////////////////////////
    // Exploradores
    /////////////////////////////////

    // Beleth-Il
    @GetMapping("/beleth")
    public String beleth(HttpSession session, ModelMap model) throws MaxUsesExcededException {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        Optional<HeroInGame> heroInGame = currentPlayer.getHeroes().stream().filter(h -> h.getHero().getName().equals("Aranel")).findFirst();
        if (!heroInGame.isPresent())
            return PAGE_HERO_ATTACK;
        HeroInGame beleth = heroInGame.get();
        if (beleth.getEffectUsed() > beleth.getHero().getMaxUses())
            throw new MaxUsesExcededException();
        // TODO
        beleth.setEffectUsed(beleth.getEffectUsed() + 1);
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    // Idril
    @GetMapping("/idril")
    public String idril() throws MaxUsesExcededException {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        Optional<HeroInGame> heroInGame = currentPlayer.getHeroes().stream().filter(h -> h.getHero().getName().equals("Aranel")).findFirst();
        if (!heroInGame.isPresent())
            return PAGE_HERO_ATTACK;
        HeroInGame idril = heroInGame.get();
        if (idril.getEffectUsed() > idril.getHero().getMaxUses())
            throw new MaxUsesExcededException();
        // TODO
        idril.setEffectUsed(idril.getEffectUsed() + 1);
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    /////////////////////////////////
    // Ladrones
    /////////////////////////////////

    // Neddia
    @GetMapping("/neddia")
    private String neddia(HttpSession session) throws MaxUsesExcededException {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        Optional<HeroInGame> heroInGame = currentPlayer.getHeroes().stream().filter(h -> h.getHero().getName().equals("Aranel")).findFirst();
        if (!heroInGame.isPresent())
            return PAGE_HERO_ATTACK;
        HeroInGame neddia = heroInGame.get();
        if (neddia.getEffectUsed() > neddia.getHero().getMaxUses())
            throw new MaxUsesExcededException();
        // TODO
        neddia.setEffectUsed(neddia.getEffectUsed() + 1);
        playerService.savePlayer(currentPlayer);
        session.setAttribute("nextUrl", "abilities/exchangeMarket");
        return VIEW_CHOSE_CARD_FROM_DECK;
    }

    // Feldon
    @GetMapping("/feldon")
    private String feldon() throws MaxUsesExcededException {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        Optional<HeroInGame> heroInGame = currentPlayer.getHeroes().stream().filter(h -> h.getHero().getName().equals("Aranel")).findFirst();
        if (!heroInGame.isPresent())
            return PAGE_HERO_ATTACK;
        HeroInGame feldon = heroInGame.get();
        if (feldon.getEffectUsed() > feldon.getHero().getMaxUses())
            throw new MaxUsesExcededException();
        // TODO
        feldon.setEffectUsed(feldon.getEffectUsed() + 1);
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    /////////////////////////////////
    // Guerreros
    /////////////////////////////////

    // Lisavette
    @GetMapping("/lisavette")
    private String lisavetter() throws MaxUsesExcededException {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        Optional<HeroInGame> heroInGame = currentPlayer.getHeroes().stream().filter(h -> h.getHero().getName().equals("Aranel")).findFirst();
        if (!heroInGame.isPresent())
            return PAGE_HERO_ATTACK;
        HeroInGame lisavette = heroInGame.get();
        if (lisavette.getEffectUsed() > lisavette.getHero().getMaxUses())
            throw new MaxUsesExcededException();
        // TODO
        lisavette.setEffectUsed(lisavette.getEffectUsed() + 1);
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }

    // Valerys
    @GetMapping("/valerys")
    private String valerys() throws MaxUsesExcededException {
        Player currentPlayer = getCurrentPlayer();
        Player loggedPlayer = getLoggedPlayer();
        if (currentPlayer != loggedPlayer)
            return PAGE_HERO_ATTACK;
        Optional<HeroInGame> heroInGame = currentPlayer.getHeroes().stream().filter(h -> h.getHero().getName().equals("Aranel")).findFirst();
        if (!heroInGame.isPresent())
            return PAGE_HERO_ATTACK;
        HeroInGame feldon = heroInGame.get();
        if (feldon.getEffectUsed() > feldon.getHero().getMaxUses())
            throw new MaxUsesExcededException();
        // TODO

        feldon.setEffectUsed(feldon.getEffectUsed() + 1);
        playerService.savePlayer(currentPlayer);
        return PAGE_HERO_ATTACK;
    }
}
