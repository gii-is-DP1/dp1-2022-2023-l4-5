package org.springframework.samples.nt4h.card.ability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameController;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.turn.TurnService;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FASE_ATAQUE_ENEMIGO_CONTROLLER {
    private final UserService userService;
    private final PlayerService playerService;
    private final TurnService turnService;
    private final GameController gameController;
    private final AbilityService abilityService;
    private final EnemyService enemyService;
    private final Player player;
    private final Hero hero;


    public final String VIEW_REESTABLISHMENT = "turns/reestablishmentPhase"; //Hay que cambiarlo

    private String message;
    private String messageType;

    @Autowired
    public FASE_ATAQUE_ENEMIGO_CONTROLLER(UserService userService, PlayerService playerService, TurnService turnService, GameController gameController, AbilityService abilityService, EnemyService enemyService, Player player, Hero hero) {
        this.playerService = playerService;
        this.userService = userService;
        this.turnService = turnService;
        this.gameController = gameController;
        this.abilityService = abilityService;
        this.enemyService = enemyService;

    }

    @ModelAttribute("game")
    public Game getGame() {
        return userService.getLoggedUser().getGame();
    }

    @ModelAttribute("player")
    public Player getPlayer() {
        return getGame().getCurrentPlayer();
    }

    //Creo que me  sobra porque puedo pillar el daño total de todos los enemigos directamente
    @ModelAttribute("enemiesInBattle")
    public List<EnemyInGame> getEnemiesInBattle() {
        return getGame().getActualOrcs();
    }

    @ModelAttribute("enemiesInBattle")
    public List<EnemyInGame> getAllEnemies() {
        return getGame().getAllOrcsInGame();
    }

    //creo que me sobra porque no me hace falta saber cuantas cartas tengo en la mano
    @ModelAttribute("handCards")
    public List<AbilityInGame> getHandDeckByPlayer() {
        return getPlayer().getInHand();
    }


    //las cartas que me quitan se van al mazo de descarte???
    @ModelAttribute("discardCards")
    public List<AbilityInGame> getDiscardDeckByPlayer() {
        return getPlayer().getInDiscard();
    }

    //ESTE ES IMPORTANTE????
    @ModelAttribute("abilityCards")
    public List<AbilityInGame> getAbilityDeckByPlayer() {
        return getPlayer().getInDeck();
    }




    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //Cambiar la vista a la de fase de ataque de enemigo

    //MÃ©todos auxiliares
    public String sendError(String message, String redirect) {
        this.message = message;
        messageType = "danger";
        return redirect;
    }
    private void resetMessage() {
        this.message = "";
        this.messageType = "";
    }

    public void takeNewCard() throws EnoughCardsException, NoMoneyException {
        Player player = getPlayer();
        for (int i = 0; i < 3; i++) {
            if (getHandDeckByPlayer().size() < 3) {
                Action takeNewCard = new TakeCardFromAbilityPile(player);
                takeNewCard.executeAction();
            } else
                throw new EnoughCardsException();
        }
    }

    //ese método entiendo que es  necesario
    private void removeAbilityCards(Integer cardId) throws EnoughCardsException, NoMoneyException {
        Player player = getPlayer();
        while (getHandDeckByPlayer().size() > 3) {
            if (getHandDeckByPlayer().size() > 3) {
                Action removeToDiscard = new RemoveCardFromHandToDiscard(player, cardId);
                removeToDiscard.executeAction();
            } else
                throw new EnoughCardsException();
        }
    }


    //creo que me sobra
    private void addNewEnemiesToBattle() throws EnoughEnemiesException {
        List<EnemyInGame> enemies = getEnemiesInBattle();
        List<EnemyInGame> allOrcs = getAllEnemies();
        if (enemies.size() == 1 || enemies.size() == 2) {
            enemies.add(allOrcs.get(1));
            allOrcs.remove(1);
        } else if (enemies.size() == 0) {
            enemies = getAllEnemies().stream().limit(3).collect(Collectors.toList());
            allOrcs.removeAll(enemies);
        } else
            throw new EnoughEnemiesException();
    }




        @GetMapping("")
        public String reestablishment() {
            return VIEW_REESTABLISHMENT;
        }
    public String ataqueEnemigo(){
        getDamageEnemy();
        getAbilityDeckByPlayer();
        getDiscardDeckByPlayer();
        player.getWounds();
        hero.getHealth();
        getDiscardDeckByPlayer();
        return reestablishment(); //CAMBIAR VISTA
    }

    //necesito:
    // la camtidad de cartasd el mazo de habilidad,
    // el daño de todos los orcos del campo,
    // las cartas restates se van al mazo de descarte (suponogo)
    // las heridas el heroe
    // la vida del heroe
}
