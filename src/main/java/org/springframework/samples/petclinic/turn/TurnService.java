package org.springframework.samples.petclinic.turn;


import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.effect.Phase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TurnService {
    private final TurnRepository turnRepository;

    @Transactional
    public void saveTurn(Turn turn) throws DataAccessException {
        turnRepository.save(turn);
    }

    @Transactional(readOnly = true)
    public Turn getTurnByID(int id) {
        return turnRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Turn> getAllTurns() {
        return turnRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Turn> getTurnsByPhase(Phase phase) {
        return turnRepository.findByPhase(phase);
    }

    @Transactional
    public void deleteTurn(Turn turn) {
        turnRepository.delete(turn);
    }

    @Transactional
    public void deleteTurnById(int id) {
        turnRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean exists(int id) {
        return turnRepository.findById(id).orElse(null) != null;
    }

}
