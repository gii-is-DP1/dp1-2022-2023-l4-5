package org.springframework.samples.nt4h.message;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.user.User;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@AllArgsConstructor
public class Advise {

    @Autowired
    private final MessageService messageService;

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

    public void createGame(User loggedUser, Game game) {
        messageService.createNotification(game, "Game created by " + loggedUser.getUsername() + "!.");
    }

    public void chooseHero(User loggedUser, Game game) {
        messageService.createNotification(game, loggedUser.getUsername() + " is choosing a hero.");
    }

    public void choosenHero(Player loggedPlayer, HeroInGame heroInGame, Game game) {
        messageService.createNotification(game, loggedPlayer.getName() + " has choosen " + heroInGame.getHero().getName() + ".");
    }

    public void getOutPlayer(Player player, Game game) {
        messageService.createNotification(game, player.getName() + " has left the game.");
    }

    public void playerJoinGame(Player newPlayer, Game game) {
        messageService.createNotification(game, newPlayer.getName() + " has joined the game.");
    }

    public void playersOrdered(Game game) {
        messageService.createNotification(game, "Players have been ordered.");
    }

    public void spectatorJoinGame(User user, Game game) {
        messageService.createNotification(game, user.getUsername() + " has joined the game as spectator.");
    }

    public void playerIsAttacked(Integer attack, Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        messageService.createNotification(game, currentPlayer.getName() + " is attacked by " + attack + " points.");
    }

    public void passPhase(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        messageService.createNotification(game, currentPlayer.getName() + " has passed to phase " + game.getCurrentTurn().getPhase() + ".");
    }

    public void discardCard(AbilityInGame currentAbility, Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        messageService.createNotification(game, currentPlayer.getName() + " has discarded " + currentAbility.getAbility().getName() + ".");
    }

    public void heroAttack(AbilityInGame usedAbility, EnemyInGame attackedEnemy, Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        messageService.createNotification(game, currentPlayer.getName() + " has used " + usedAbility.getAbility().getName() + " on " + attackedEnemy.getEnemy().getName() + ".");
    }

    public void buyProduct(ProductInGame productInGame, Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        messageService.createNotification(game, currentPlayer.getName() + " has bought " + productInGame.getProduct().getName() + ".");
    }

    public void addEnemies(List<EnemyInGame> added, Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        StringBuilder enemies = new StringBuilder();
        for (EnemyInGame enemy : added) {
            enemies.append(enemy.getEnemy().getName()).append(", ");
        }
        messageService.createNotification(game, currentPlayer.getName() + " has added " + enemies + "to the game.");
    }

    public void discardAbilityInHand(AbilityInGame ability, Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        messageService.createNotification(game, currentPlayer.getName() + " has discarded " + ability.getAbility().getName() + " from hand.");
    }

    public void changePlayer(Player loggedPlayer, Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        messageService.createNotification(game,"The turn is now for " + currentPlayer.getName() + "and before was " + loggedPlayer.getName() + ".");
    }

    public void choose(Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        messageService.createNotification(game, currentPlayer.getName() + " is choosing between two options, evade or attack.");
    }

    public void addAbilityInHand(List<AbilityInGame> takeNewCard, Game game) {
        Player currentPlayer = game.getCurrentPlayer();
        StringBuilder abilities = new StringBuilder();
        for (AbilityInGame ability : takeNewCard) {
            abilities.append(ability.getAbility().getName()).append(", ");
        }
        messageService.createNotification(game, currentPlayer.getName() + " has taken " + abilities + "from the deck.");
    }
}
