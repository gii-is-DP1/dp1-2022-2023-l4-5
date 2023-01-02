package org.springframework.samples.nt4h.statistic;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StatisticServiceTest {
    @Autowired
    StatisticService statisticService;
    @BeforeAll
    void ini() {
        Statistic statistic= new Statistic();
        statistic.setDamageDealt(0);
        statistic.setGlory(0);
        statistic.setNumOrcsKilled(0);
        statistic.setNumWarLordKilled(0);
        statistic.setGold(0);
        statistic.setDamageDealtToNightLords(0);
        statistic.setNumWonGames(0);
        statistic.setNumPlayedGames(0);
        statisticService.saveStatistic(statistic);
    }
    @Test
    public void findByIdTrue(){
        Statistic stat = statisticService.getStatisticById(1);
        assertNotNull(stat);
        assertEquals(0, stat.getDamageDealtToNightLords());
    }
    @Test
    public void findByIdFalse(){
        Statistic stat = statisticService.getStatisticById(1);
        assertNotNull(stat);
        assertNotEquals(1, stat.getDamageDealtToNightLords());
    }
    @Test
    public void findAll(){
        List<Statistic>ls= statisticService.getAllStatistics();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(1,ls.size());
    }
    @Test
    public void  shouldInsertStatistic(){
        Statistic statistic= new Statistic();
        statistic.setDamageDealt(0);
        statistic.setGlory(0);
        statistic.setNumOrcsKilled(0);
        statistic.setNumWarLordKilled(0);
        statistic.setGold(0);
        statistic.setDamageDealtToNightLords(0);
        statistic.setNumWonGames(0);
        statistic.setNumPlayedGames(0);
        statisticService.saveStatistic(statistic);
        assertEquals(statistic,statisticService.getStatisticById(2));
    }
    @Test
    public void shouldUpdateStatistic(){
        Statistic stat = statisticService.getStatisticById(1);
        Integer oldGold= stat.getGold();
        Integer newGold= oldGold+1;
        stat.setGold(newGold);
        statisticService.saveStatistic(stat);
        assertEquals(newGold,statisticService.getStatisticById(1).getGold());
    }
    @Test
    public void deleteStatisticTest(){
        statisticService.deleteStatisticById(1);
        assertFalse(statisticService.statisticExists(1));
    }

}
