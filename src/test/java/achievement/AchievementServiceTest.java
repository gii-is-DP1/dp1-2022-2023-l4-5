package achievement;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.nt4h.achievement.Achievement;
import org.springframework.samples.nt4h.achievement.AchievementRepository;
import org.springframework.samples.nt4h.achievement.AchievementService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AchievementServiceTest {
    @Autowired
    AchievementService acs;

    @Test
    public void findByNameTrue(){
        Achievement logro = acs.getAchievementByName("Touch Some Grass");
        assertNotNull(logro);
        assertEquals(20, logro.getThreshold());
    }
    @Test
    public void findByNameFalse(){
        Achievement logro = acs.getAchievementByName("Primeros pasos");
        assertNotNull(logro);
        assertEquals(20,logro.getThreshold());
    }
    @Test
    public void findByIdTrue(){
        Achievement logro = acs.getAchievementById(1);
        assertNotNull(logro);
        assertEquals(1, logro.getThreshold());
    }
    @Test
    public void findByIdFalse(){
        Achievement logro = acs.getAchievementById(5);
        assertNotNull(logro);
        assertEquals(1,logro.getThreshold());
    }

    @Test
    public void findAllTestTrue(){
        List<Achievement> ls = acs.getAllAchievements();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(15, ls.size());
    }

    @Test
    public void findAllTestFalse(){
        List<Achievement> ls = acs.getAllAchievements();
        assertNotNull(ls);
        assertTrue(ls.isEmpty());
    }
}
