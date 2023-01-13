package org.springframework.samples.nt4h.statistic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class StatisticTest {
    @Autowired
    private StatisticService statisticService;
    @Autowired
    private Validator validator;
    private Statistic statistic;

    @BeforeEach
    void setUp() {
        statistic = new Statistic();
        statistic.setGold(10);
        statistic.setGlory(20);
        statistic.setNumOrcsKilled(30);
        statistic.setNumWarLordKilled(40);
        statistic.setNumWonGames(50);
        statistic.setNumPlayedGames(60);
        statistic.setDamageDealt(70);
    }

    @Test
    public void testStatisticProperties() {
        assertThat(statistic.getGold()).isEqualTo(10);
        assertThat(statistic.getGlory()).isEqualTo(20);
        assertThat(statistic.getNumOrcsKilled()).isEqualTo(30);
        assertThat(statistic.getNumWarLordKilled()).isEqualTo(40);
        assertThat(statistic.getNumWonGames()).isEqualTo(50);
        assertThat(statistic.getNumPlayedGames()).isEqualTo(60);
        assertThat(statistic.getDamageDealt()).isEqualTo(70);
    }

    @Test
    public void testStatisticConstraints() {
        // Test gold constraint
        statistic.setGold(-1);
        assertThat(validator.validate(statistic)).isNotEmpty();
        statistic.setGold(100);
        assertThat(validator.validate(statistic)).isEmpty();

        // Test glory constraint
        statistic.setGlory(-1);
        assertThat(validator.validate(statistic)).isNotEmpty();
        statistic.setGlory(100);
        assertThat(validator.validate(statistic)).isEmpty();

        // Test numOrcsKilled constraint
        statistic.setNumOrcsKilled(-1);
        assertThat(validator.validate(statistic)).isNotEmpty();
        statistic.setNumOrcsKilled(100);
        assertThat(validator.validate(statistic)).isEmpty();

        // Test numWarLordKilled constraint
        statistic.setNumWarLordKilled(-1);
        assertThat(validator.validate(statistic)).isNotEmpty();
        statistic.setNumWarLordKilled(100);
        assertThat(validator.validate(statistic)).isEmpty();

        // Test numWonGames constraint
        statistic.setNumWonGames(-1);
        assertThat(validator.validate(statistic)).isNotEmpty();
        statistic.setNumWonGames(100);
        assertThat(validator.validate(statistic)).isEmpty();

        // Test numPlayedGames constraint
        statistic.setNumPlayedGames(-1);
        assertThat(validator.validate(statistic)).isNotEmpty();
        statistic.setNumPlayedGames(100);
        assertThat(validator.validate(statistic)).isEmpty();

        // Test damageDealt constraint
        statistic.setDamageDealt(-1);
        assertThat(validator.validate(statistic)).isNotEmpty();
        statistic.setDamageDealt(100);
        assertThat(validator.validate(statistic)).isEmpty();
    }

    @Test
    public void testStatisticLifecycle() {
        // Test saving a statistic
        statisticService.saveStatistic(statistic);
        assertThat(statistic.getId()).isNotNull();

        // Test updating an existing statistic
        statistic.setGold(100);
        statisticService.saveStatistic(statistic);

        // Test deleting an existing statistic
        statisticService.deleteStatistic(statistic);
        assertThatThrownBy(() -> statisticService.getStatisticById(statistic.getId())).isInstanceOf(NotFoundException.class);
    }

    @Test
    public void testStatisticQueries() {
        // Test finding all statistics
        statisticService.saveStatistic(statistic);
        assertThat(statisticService.getAllStatistics()).isNotEmpty();

        // Test finding a statistic by id
        statisticService.saveStatistic(statistic);
        assertThat(statisticService.getStatisticById(statistic.getId())).isEqualTo(statistic);
    }
}
