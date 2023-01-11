package org.springframework.samples.nt4h.enemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.samples.nt4h.card.enemy.*;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.PlayerService;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class EnemyServiceTest {
    @Mock
    private EnemyInGameRepository enemyInGameRepository;

    @Mock
    private EnemyRepository enemyRepository;

    @Mock
    private PlayerService playerService;

    private EnemyService enemyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
      //  enemyService = new EnemyService(enemyInGameRepository, enemyRepository, playerService);
    }

    @Test
    void testGetEnemyInGameById() {
        EnemyInGame enemyInGame = new EnemyInGame();
        when(enemyInGameRepository.findById(anyInt())).thenReturn(Optional.of(enemyInGame));

        EnemyInGame result = enemyService.getEnemyInGameById(1);
        assertEquals(enemyInGame, result);
    }

    @Test
    void testGetEnemyInGameByIdNotFound() {
        when(enemyInGameRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> enemyService.getEnemyInGameById(1));
    }

    @Test
    void testGetAllEnemyInGame() {
        List<EnemyInGame> enemyInGameList = new ArrayList<>();
        when(enemyInGameRepository.findAll()).thenReturn(enemyInGameList);

        List<EnemyInGame> result = enemyService.getAllEnemyInGame();
        assertEquals(enemyInGameList, result);
    }
    @Test
    void testDeleteEnemyInGame() {
        EnemyInGame enemyInGame = new EnemyInGame();
        enemyService.deleteEnemyInGame(enemyInGame);
        // Verify that onDeleteSetNull() method is called on the enemyInGame object
        verify(enemyInGame).onDeleteSetNull();
        // Verify that the save and delete methods are called on the enemyInGameRepository
        verify(enemyInGameRepository).save(enemyInGame);
        verify(enemyInGameRepository).delete(enemyInGame);
    }

    @Test
    void testDeleteEnemyInGameById() {
        EnemyInGame enemyInGame = new EnemyInGame();
        when(enemyInGameRepository.findById(anyInt())).thenReturn(Optional.of(enemyInGame));

        enemyService.deleteEnemyInGameById(1);
        // Verify that onDeleteSetNull() method is called on the enemyInGame object
        verify(enemyInGame).onDeleteSetNull();
        // Verify that the save and delete methods are called on the enemyInGameRepository
        verify(enemyInGameRepository).save(enemyInGame);
        verify(enemyInGameRepository).delete(enemyInGame);
    }

    @Test
    void testDeleteEnemyInGameByIdNotFound() {
        when(enemyInGameRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> enemyService.deleteEnemyInGameById(1));
    }

    @Test
    void testEnemyInGameExists() {
        when(enemyInGameRepository.existsById(anyInt())).thenReturn(true);
        assertTrue(enemyService.enemyInGameExists(1));

        when(enemyInGameRepository.existsById(anyInt())).thenReturn(false);
        assertFalse(enemyService.enemyInGameExists(1));
    }

    @Test
    void testGetAllEnemies() {
        List<Enemy> enemyList = new ArrayList<>();
        when(enemyRepository.findAll()).thenReturn(enemyList);

        List<Enemy> result = enemyService.getAllEnemies();
        assertEquals(enemyList, result);
    }

    @Test
    void testGetNightLord() {
        List<Enemy> allNightLords = new ArrayList<>();
        allNightLords.add(new Enemy());
        allNightLords.add(new Enemy());
        allNightLords.add(new Enemy());

        when(enemyRepository.findAllNightLords()).thenReturn(allNightLords);

        Enemy result = enemyService.getNightLord();
        assertTrue(allNightLords.contains(result));
    }
    @Test
    void testAddNightLordToGame() {
        Enemy nightLord = new Enemy();
        when(enemyRepository.findAllNightLords()).thenReturn(List.of(nightLord));

        EnemyInGame nightLordInGame = enemyService.addNightLordToGame();
        assertEquals(nightLord, nightLordInGame.getEnemy());
        verify(enemyInGameRepository).save(nightLordInGame);
    }

   /* @Test
    void testAttackEnemyToActualPlayer() {
        // Set up the objects for the test
        Player player = new Player();
        Game game = new Game();
        game.setCurrentPlayer(player);
        EnemyInGame enemyInGame = new EnemyInGame();
        enemyInGame.setEnemy(new Enemy());
        enemyInGame.setActualHealth(2);
        int damage = 2;
        game.setActualOrcs(List.of(enemyInGame));
        InflictWounds wounds= new InflictWounds(player);
        RemoveCardForEnemyAttack removeCard = new RemoveCardForEnemyAttack(player,damage);

        // Set up the mocks
        doNothing().when(wounds).executeAction();
        doNothing().when(removeCard).executeAction();

        // Run the test
        Integer damageCalculated= enemyService.attackEnemyToActualPlayer(game);

        // Verify that the inflictWounds method is called
        verify(wounds).executeAction();
        // Verify that the addCardForEnemyAttack method is called
        verify(removeCard).executeAction();

        assertEquals(2,damageCalculated);
    }*/

}
