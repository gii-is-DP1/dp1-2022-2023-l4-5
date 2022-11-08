package org.springframework.samples.petclinic.turn;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TurnService {
    private final TurnRepository turnRepository;

    @Autowired
    public TurnService(TurnRepository turnRepository) {
        this.turnRepository = turnRepository;
    }

    @Transactional
    public void saveTurn(Turn turn) throws DataAccessException {turnRepository.save(turn);}

    @Transactional
    public Turn getTurnbyID(int id){ return turnRepository.findById(id).get(); }

    @Transactional
    public List<Turn> getTurns(){return turnRepository.findAll();}

}
