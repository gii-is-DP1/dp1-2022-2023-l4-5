package org.springframework.samples.nt4h.game;


import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.effect.Phase;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public Game getGameById(int id) {
        return gameRepository.findById(id).orElseThrow(() -> new NotFoundException("Game not found"));
    }

    @Transactional(readOnly = true)
    public Optional<Game> getGameByName(String name) {
        return gameRepository.findByName(name);
    }

    //TODO: actualizar la Date_finish cuadno acabe la partida
    @Transactional
    public void saveGame(Game game) {
        if (game.getPhase() == null) {
            game.setPhase(Phase.START);
        }
        if (game.getStartDate() == null) {
            game.setStartDate(LocalDateTime.now());
        }
        if (game.getPassword() == "") {
            if (game.getAccessibility() == null) {
                game.setAccessibility(Accessibility.PUBLIC);
            }
        } else {
            game.setAccessibility(Accessibility.PRIVATE);
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
