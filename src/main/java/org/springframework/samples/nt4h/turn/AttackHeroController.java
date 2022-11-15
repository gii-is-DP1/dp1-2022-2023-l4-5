package org.springframework.samples.nt4h.turn;
/*


import org.hibernate.sql.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.card.enemy.EnemyService;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.lang.reflect.Array;

@Controller
@RequestMapping("/phases/attackHero/{playerId}")
public class AttackHeroController {

    // Constantes.
    private static final String VIEW_PHASES_ATTACK = "/phases/attackHero/cardAboutEnemy";

    // Servicios
    private final GameService gameService;
    private final AbilityService abilityService;
    private final EnemyService enemyService;
    private final PlayerService playerService;


    @Autowired
    public AttackHeroController(GameService gameService, AbilityService abilityService, EnemyService enemyService, PlayerService playerService) {
        this.gameService = gameService;
        this.abilityService = abilityService;
        this.enemyService = enemyService;
        this.playerService= playerService;

    }

/*

obeter una lista de los enemiggos

  public ArrayEnemy parse(String text) {

       Array<Enemy> ls2;
       Array<String> ls= text.split(",")
        for(int i; ls<=i;i++){
           ls2.add (emeny.getid(ls[i]);
        }

        return ls2;
    }



    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/{abilityId}/{enemyId}/{playerId}")
    public String getViewPhasesAttack(ModelMap model, @PathVariable int abilityId, @PathVariable ArrayEnemy enemyId, @PathVariable ArrayPlayer playerId ) {

        //Inicia turno de ataque

        //Selecciono una carta
        Ability ability = abilityService.getAbilityById(abilityId);

        //Selecciono un enemigo

        //Confirmo ataque

        //Fin de turno
        return VIEW_PHASES_ATTACK;
    }

    }
*/
