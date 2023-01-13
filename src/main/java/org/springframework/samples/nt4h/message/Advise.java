package org.springframework.samples.nt4h.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.achievement.Achievement;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component("advise")
public class Advise {

    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public Advise(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    private Game getGame() {
        return userService.getLoggedUser().getGame();
    }

    private Player getLoggedPlayer() {
        return userService.getLoggedUser().getPlayer();
    }

    private User getUser() {
        return userService.getLoggedUser();
    }

    private Player getCurrentPlayer() {
        return getGame().getCurrentPlayer();
    }

    public void getMessage(HttpSession session, ModelMap model) {
        Object message = session.getAttribute("message");
        Object messageType = session.getAttribute("messageType");
        if (message != null) {
            model.addAttribute("message", message);
            model.addAttribute("messageType", messageType);
            session.removeAttribute("message");
            session.removeAttribute("messageType");
        }
    }

    public void keepUrl(HttpSession session, HttpServletRequest request) {
        session.setAttribute("url", request.getRequestURI());
    }

    public void createGame(Game game) {
        messageService.createNotification(getGame(), "Game created by " + getUser().getUsername() + "!.");
    }

    public void chooseHero() {
        messageService.createNotification(getGame(), getUser().getUsername() + " is choosing a hero.");
    }

    public void chosenHero(HeroInGame heroInGame) {
        messageService.createNotification(getGame(), getLoggedPlayer().getName() + " has choosen " + heroInGame.getHero().getName() + ".");
    }

    public void getOutPlayer() {
        messageService.createNotification(getGame(), getLoggedPlayer().getName() + " has left the game.");
    }

    public void playerJoinGame() {
        messageService.createNotification(getGame(), getLoggedPlayer().getName() + " has joined the game.");
    }

    public void playersOrdered(Game game) {
        messageService.createNotification(game, "Players have been ordered.");
    }

    public void spectatorJoinGame() {
        messageService.createNotification(getGame(), getUser().getUsername() + " has joined the game as spectator.");
    }

    public void playerIsAttacked(Integer attack) {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " is attacked by " + attack + " points.");
    }

    public void passPhase(Game game) {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has passed to phase " + game.getCurrentTurn().getPhase() + ".");
    }

    public void discardCard(AbilityInGame currentAbility) {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has discarded " + currentAbility.getAbility().getName() + ".");
    }

    public void heroAttack(AbilityInGame usedAbility, EnemyInGame attackedEnemy) {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has used " + usedAbility.getAbility().getName() + " on " + attackedEnemy.getEnemy().getName() + ".");
    }

    public void buyProduct(ProductInGame productInGame) {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has bought " + productInGame.getProduct().getName() + ".");
    }

    public void addEnemies(List<EnemyInGame> added, Game game) {
        StringBuilder enemies = new StringBuilder();
        for (EnemyInGame enemy : added) {
            enemies.append(enemy.getEnemy().getName()).append(", ");
        }
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has added " + enemies + "to the game.");
    }

    public void discardAbilityInHand(AbilityInGame ability, Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has discarded " + ability.getAbility().getName() + " from hand.");
    }

    public void changePlayer(Player loggedPlayer, Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        messageService.createNotification(game,"The turn is now for " + currentPlayer.getName() + "and before was " + loggedPlayer.getName() + ".");
    }

    public void choose(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " is choosing between two options, evade or attack.");
    }

    public void addAbilityInHand(List<AbilityInGame> takeNewCard, Game game) {
        StringBuilder abilities = new StringBuilder();
        for(AbilityInGame ability : takeNewCard) {
            System.out.println("Ability: " + ability.getAbility().getName());
            abilities.append(ability.getAbility().getName()).append(", ");
        }
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has taken " + abilities + "from the deck.");
    }

    public void fromDeckToDiscard(AbilityInGame inDeck) {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has discarded " + inDeck.getAbility().getName() + " from deck.");
    }

    public void fromDiscardToDeck(AbilityInGame inDiscard) {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has taken " + inDiscard.getAbility().getName() + " from discard.");
    }

    public void fromDeckToHand(AbilityInGame inDeck) {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has taken " + inDeck.getAbility().getName() + " from deck.");
    }

    public void getOneWound() {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has taken one wound.");
    }

    public void playerIsDead() {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " is dead.");
    }

    public void addGold(Integer gold) {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has taken " + gold + " gold.");
    }

    public void addAchievement(Achievement achievement) {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has taken " + achievement.getName() + " achievement.");
    }

    public void addGlory(Integer glory) {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has taken " + glory + " glory.");
    }

    public void loseGold(Integer gold) {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has lost " + gold + " gold.");
    }

    public void loseGlory(Integer glory) {
        messageService.createNotification(getGame(), getCurrentPlayer().getName() + " has lost " + glory + " glory.");
    }
}
