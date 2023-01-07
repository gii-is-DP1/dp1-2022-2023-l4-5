package org.springframework.samples.nt4h.card.ability;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.AbilityService;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.Accessibility;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.GameService;
import org.springframework.samples.nt4h.game.Mode;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbilityServiceTest {
    @Autowired
    PlayerService playerService;
    @Autowired
    UserService userService;
    @Autowired
    GameService gameService;
    @Autowired
    AbilityService abilityService;
    Player player;
    private Ability ability;
    private final int idAbility = 1;
    private int idAbilityInGame;
    Integer attack = 5;

    @BeforeEach
    void setUp() throws FullGameException {
        User user = userService.getUserById(1);
        Game game = Game.createGame( "Prueba",   Mode.UNI_CLASS, 2, "");
        player = Player.createPlayer(user, game, true);
        game.setFinishDate(LocalDateTime.of(2020, 1, 2, 0, 0));
        game.setHasStages(true);
        ability = abilityService.getAbilityById(idAbility);
        AbilityInGame abilityInGame = AbilityInGame.fromAbility(ability, player);
        player.getDeck().getInDeck().add(abilityInGame);
        gameService.saveGame(game);
        idAbilityInGame = abilityInGame.getId();
        /*
        Game game = Game.createGame("Prueba", Accessibility.PUBLIC, Mode.UNI_CLASS, 4, null);
        User user = userService.getUserById(1);
        user.createPlayer(game);
        player = user.getPlayer();
        ability = abilityService.getAbilityById(idAbility);
        AbilityInGame abilityInGame = AbilityInGame.fromAbility(ability, player);
        player.getDeck().getInDeck().add(abilityInGame);
        game.addPlayer(player);
        gameService.saveGame(game);
        idAbilityInGame = abilityInGame.getId();
        */
    }

    @AfterEach
    void tearDown() {
        if (player.getGame() != null)
            gameService.deleteGameById(player.getGame().getId());


    }


    @Test
    public void testFindByName() {
        assertEquals(ability.getName(), abilityService.getAbilityByName(ability.getName()).getName());
    }

    @Test
    public void testFindByNotFoundName() {
        assertThrows(NotFoundException.class, () -> abilityService.getAbilityByName("No existe"));
    }

    @Test
    public void testFindByCorrectId() {
        assertEquals(idAbility, abilityService.getAbilityById(idAbility).getId());
    }

    @Test
    public void findByIdFalse() {
        Ability nuevo = abilityService.getAbilityById(idAbility);
        assertNotEquals(1, nuevo.getAttack());
    }

    @Test
    public void findAllTestTrue() {
        assertEquals(33, abilityService.getAllAbilities().size());
    }

    @Test
    public void testExistsByIdTest() {
        assertTrue(abilityService.abilityExists(idAbility));
        assertFalse(abilityService.abilityExists(-1));
    }


//In Game----------------------------------------------------------------

    @Test
    public void testFindById() {
        assertEquals(idAbilityInGame, abilityService.getAbilityInGameById(idAbilityInGame).getId());
    }

    @Test
    public void testFindByIncorrectId() {
        assertThrows(NotFoundException.class, () -> abilityService.getAbilityInGameById(-1));
    }

    @Test
    public void findAllTestIGTrue() {
        assertEquals(1, abilityService.getAllAbilityInGame().size());
    }

    @Test
    public void existsByIdTestIGTrue() {
        assertTrue(abilityService.abilityInGameExists(idAbilityInGame));
    }

    @Test
    public void existByIdTestIGFalse() {
        assertFalse(abilityService.abilityInGameExists(50));
    }


    @Test
    public void testSave() {

        AbilityInGame newAbilityInGame = AbilityInGame.fromAbility(ability, player);
        newAbilityInGame.setAttack(attack);
        abilityService.saveAbilityInGame(newAbilityInGame);
        assertEquals(attack, abilityService.getAbilityInGameById(idAbilityInGame + 1).getAttack());
    }

    @Test
    public void testUpdate() {
        AbilityInGame oldAbilityInGame = abilityService.getAbilityInGameById(idAbilityInGame);
        AbilityInGame newAbilityInGame = oldAbilityInGame.toBuilder()
            .attack(attack)
            .build();
        newAbilityInGame.setId(idAbilityInGame);
        abilityService.saveAbilityInGame(newAbilityInGame);
        System.out.println("old: " + oldAbilityInGame.getAttack());
        System.out.println("new: " + newAbilityInGame.getAttack());
        assertEquals(attack, abilityService.getAbilityInGameById(idAbilityInGame).getAttack());
    }

    @Test
    public void testDeleteAbility() {
        playerService.deletePlayerById(player.getId());
        assertThrows(NotFoundException.class, () -> abilityService.getAbilityInGameById(idAbilityInGame));
        // assertFalse(abilityService.abilityInGameExists(idAbilityInGame));
    }
}
