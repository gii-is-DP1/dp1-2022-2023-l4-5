package org.springframework.samples.petclinic.statistic;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoricService {

    private final HistoricRepository historicRepository;

    public Historic findHistoricByName(Integer integer) {
        return historicRepository.findByName(integer).orElse(null);
    }

    public Historic findHistoricById(int id) {
        return historicRepository.findById(id).orElse(null);
    }

    public Iterable<Historic> findAllHistorics() {
        return historicRepository.findAll();
    }

    public void saveHistoric(Historic historic) {
        historicRepository.save(historic);
    }

    // TODO: Actualizar historic.

    public void deleteHistoric(Historic historic) {
        historicRepository.delete(historic);
    }

    public void deleteHistoricById(int id) {
        historicRepository.deleteById(id);
    }
}
