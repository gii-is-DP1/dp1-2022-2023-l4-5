package org.springframework.samples.petclinic.game;


import lombok.AllArgsConstructor;
import org.springframework.samples.petclinic.effect.Phase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    @Transactional(readOnly = true)
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Game> getGameById(int id) {
        return gameRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Game> getGameByName(String name) {
        return gameRepository.findByName(name);
    }

    @Transactional
    public void saveGame(Game game) {
        if(game.getPhase() == null){
            game.setPhase(Phase.START);
        }
        if(game.getStartDate() == null){
            game.setStartDate(LocalDate.now());
        }
        gameRepository.save(game);
    }

    @Transactional
    public void deleteGame(Game game) {
        gameRepository.delete(game);
    }

    @Transactional
    public void deleteGameById(int id) {
        gameRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean gameExists(int id) {
        return gameRepository.existsById(id);
    }


}
