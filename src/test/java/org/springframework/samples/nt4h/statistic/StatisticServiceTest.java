package org.springframework.samples.nt4h.statistic;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class StatisticServiceTest {
    @Autowired
    StatisticService statisticService;

    void ini() {
        Statistic statistic= new Statistic();
        statistic.setAllDamgeDealed(0);
        statistic.setAllGlory(0);
        statistic.setNumOrcsKilled(0);
        statistic.setNumWarLordKilled(0);
        statistic.setAllGold(0);
        statistic.setAllDamageDealedToNightLords(0);
        statistic.setNumWinnedGames(0);
        statistic.setNumPlayedGames(0);
        statisticService.saveStatistic(statistic);
    }
    @Test
    public void findByIdTrue(){
        ini();
        Statistic stat = statisticService.getStatisticById(1);
        assertNotNull(stat);
        assertEquals(0, stat.getAllDamageDealedToNightLords());
    }
    @Test
    public void findByIdFalse(){
        ini();
        Statistic stat = statisticService.getStatisticById(1);
        assertNotNull(stat);
        assertNotEquals(1, stat.getAllDamageDealedToNightLords());
    }
    @Test
    public void findAll(){
        ini();
        List<Statistic>ls= statisticService.getAllStatistics();
        assertNotNull(ls);
        assertFalse(ls.isEmpty());
        assertEquals(1,ls.size());
    }
    @Test
    public void  shouldInsertStatistic(){
        Statistic statistic= new Statistic();
        statistic.setAllDamgeDealed(0);
        statistic.setAllGlory(0);
        statistic.setNumOrcsKilled(0);
        statistic.setNumWarLordKilled(0);
        statistic.setAllGold(0);
        statistic.setAllDamageDealedToNightLords(0);
        statistic.setNumWinnedGames(0);
        statistic.setNumPlayedGames(0);
        statisticService.saveStatistic(statistic);
        assertEquals(statistic,statisticService.getStatisticById(1));
    }
    @Test
    public void shouldUpdateStatistic(){
        ini();
        Statistic stat = statisticService.getStatisticById(1);
        Integer oldGold= stat.getAllGold();
        Integer newGold= oldGold+1;
        stat.setAllGold(newGold);
        statisticService.saveStatistic(stat);
        assertEquals(newGold,statisticService.getStatisticById(1).getAllGold());
    }

}
