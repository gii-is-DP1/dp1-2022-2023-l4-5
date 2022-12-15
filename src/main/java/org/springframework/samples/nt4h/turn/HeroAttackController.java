package org.springframework.samples.nt4h.turn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
    @RequestMapping("/heroAttack")
    public class HeroAttackController {

        public final String NEXT_TURN = "redirect:/turns";
        public final String VIEW_ATTACK_ACTION = "turns/heroAttackAction";

        private final UserService userService;
        private final TurnService turnService;
        private final PlayerService playerService;
        private final GameService gameService;

        // @ModelAttribute("enemiesInBattle")
        // public List<EnemyInGame> getEnemiesInBattle() {
        //     return game.getEnemiesInBattle();
        // }


        @Autowired
        public HeroAttackController(UserService userService, TurnService turnService, PlayerService playerService, GameService gameService) {
            this.userService = userService;
            this.turnService = turnService;
            this.playerService = playerService;
            this.gameService = gameService;
        }

        @ModelAttribute("user")
        public User getUser() {
            return userService.getLoggedUser();
        }

        @ModelAttribute("player")
        public Player getPlayer() {
            return userService.getLoggedUser().getPlayer();
        }

        @ModelAttribute("game")
        public Game getGame() {
            return getPlayer().getGame();
        }

        @GetMapping()
        public String showHeroAttackBoard() {
            return VIEW_ATTACK_ACTION;
        }

        @ModelAttribute("newTurn")
        public Turn getNewTurn() {
            return new Turn();
        }

        @PostMapping()
        public String modifyCardAttributes(Turn turn) {
            Player player = getPlayer();
            Game game = getGame();

            if(player == getGame().getCurrentPlayer()) {
                AbilityInGame usedAbility = turn.getCurrentAbility();
                EnemyInGame attackedEnemy = turn.getCurrentEnemy();
                Integer enemyInitialHealth = attackedEnemy.getActualHealth();
                attackedEnemy.setActualHealth(enemyInitialHealth - usedAbility.getAttack());
                player.getInHand().remove(usedAbility);
                player.addAbilityInDiscard(usedAbility);
                playerService.savePlayer(player);
                if(attackedEnemy.getActualHealth() <= 0) {
                    player.setGold(player.getGold() + attackedEnemy.getEnemy().getGold());
                    game.getActualOrcs().remove(attackedEnemy);
                    playerService.savePlayer(player);
                    gameService.saveGame(game);
                }
                Turn createdTurn = turnService.getTurnsByPhaseAndPlayerId(Phase.HERO_ATTACK, player.getId());
                createdTurn.addEnemy(attackedEnemy);
                createdTurn.addAbility(usedAbility);
                turnService.saveTurn(createdTurn);
            }
            return showHeroAttackBoard();
        }

        @GetMapping("/next")
        public String next() {
            Player player = getPlayer();
            Game game = getGame();
            if(player == getGame().getCurrentPlayer()) {
                game.setCurrentTurn(turnService.getTurnsByPhaseAndPlayerId(Phase.ENEMY_ATTACK, player.getId()));
                gameService.saveGame(game);
            }
            return NEXT_TURN;
        }

    }
