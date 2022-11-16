package org.springframework.samples.nt4h.enemy;

/*
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EnemyServiceTest {
    @Autowired
    EnemyService ens;

    @BeforeAll
    void setUp() throws RoleAlreadyChosenException {
        EnemyInGame enemy = new EnemyInGame();
        enemy.setId(1);
        enemy.setActualHealth(4);
        ens.saveEnemyInGame(enemy);
    }
    @Test
    public void findByIdIGTrue() {
        EnemyInGame nuevo = ens.getEnemyInGameById(1);
        assertNotNull(nuevo);
        assertEquals(4, nuevo.getActualHealth());
    }

    @Test
    public void findByIdIGFalse() {
        assertThrows(NotFoundException.class,() -> ens.getEnemyInGameById(5));
    }

    @Test
    public void findAllTestIGTrue() {
        List<EnemyInGame> ls = ens.getAllEnemyInGame();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(1, ls.size());
    }

    @Test
    public void existsByIdTestIGTrue(){
        assertTrue(ens.enemyInGameExists(1));
    }

    @Test
    public void existByIdTestIGFalse(){
        assertFalse(ens.enemyInGameExists(50));
    }


    @Test
    public void shouldInsertIGEnemy(){
        EnemyInGame nuevo = new EnemyInGame();
        nuevo.setActualHealth(2);
        ens.saveEnemyInGame(nuevo);
        assertEquals(2, ens.getEnemyInGameById(2).getActualHealth());
    }
    @Test
    public void shouldUpdateIGEnemy(){
        EnemyInGame nuevo = ens.getEnemyInGameById(1);
        nuevo.setActualHealth(2);
        ens.saveEnemyInGame(nuevo);
        assertEquals(2, ens.getEnemyInGameById(1).getActualHealth());
    }

    @Test
    public void deleteEnemyIGTest(){
        ens.deleteEnemyInGameById(1);
        assertFalse(ens.enemyInGameExists(1));
    }
}
*/
