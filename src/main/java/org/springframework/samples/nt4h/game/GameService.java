package org.springframework.samples.nt4h.game;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.user.User;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Game getGameByName(String name) {
        return gameRepository.findByName(name).orElseThrow(() -> new NotFoundException("Game not found"));
    }

    //TODO: actualizar la Date_finish cuando acabe la partida
    @Transactional
    public void saveGame(Game game) {
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

    @Transactional
    public Optional<Player> getPlayerByUserInGame(User user, Game game) {
        return game.getPlayers().stream()
            .filter(p -> p.getName().equals(user.getUsername()))
            .findFirst();
    }

    @Transactional
    public Boolean isUserInOtherGame(int gameId, User user) {
        Boolean response = null;
        for (int i = 0; gameRepository.findDistinctById(gameId).size() > i; i++) {
            List<Player> players = gameRepository.findPlayersByGame(i);
            response = players.stream().anyMatch(p -> p.userHasSameNameAsPlayer(user));
        }
        return response;
    }

    @Transactional
    public Optional<Game> getUserInOtherGame(Integer gameId, User user) {
        return getAllGames().stream()
            .filter(x -> isUserInOtherGame(gameId, user))
            .findFirst();
    }

}
