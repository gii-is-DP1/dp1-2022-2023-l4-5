package org.springframework.samples.petclinic.statistic;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticService {

    private final StatisticRepository statisticRepository;

    public Statistic getStatisticById(int id) {
        return statisticRepository.findById(id).orElse(null);
    }

    public Iterable<Statistic> getAllStatistics() {
        return statisticRepository.findAll();
    }

    public void saveStatistic(Statistic statistic) {
        statisticRepository.save(statistic);
    }

    // TODO: Actualizar statistic.

    public void deleteStatistic(Statistic statistic) {
        statisticRepository.delete(statistic);
    }

    public void deleteStatisticById(int id) {
        statisticRepository.deleteById(id);
    }
}
