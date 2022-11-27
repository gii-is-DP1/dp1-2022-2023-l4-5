package org.springframework.samples.nt4h.statistic;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.achievement.Achievement;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StatisticService {

    private final StatisticRepository statisticRepository;

    @Transactional(readOnly = true)
    public Statistic getStatisticById(int id) {
        return statisticRepository.findById(id).orElseThrow(() -> new NotFoundException("Statistic not found"));
    }

    @Transactional(readOnly = true)
    public List<Statistic> getAllStatistics() {
        return statisticRepository.findAll();
    }

    @Transactional
    public void saveStatistic(Statistic statistic) {
        statisticRepository.save(statistic);
    }

    @Transactional
    public void deleteStatistic(Statistic statistic) {
        statisticRepository.delete(statistic);
    }

    @Transactional
    public void deleteStatisticById(int id) {
        statisticRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean statisticExists(int id) {
        return statisticRepository.existsById(id);
    }

}
