package org.springframework.samples.nt4h.game;


import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.game.exceptions.PlayerInOtherGameException;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    //TODO: actualizar la Date_finish cuadno acabe la partida
    @Transactional(rollbackFor = {HeroAlreadyChosenException.class, FullGameException.class})
    public void saveGame(Game game) throws HeroAlreadyChosenException, FullGameException, PlayerInOtherGameException {
        System.out.println("----");
        /*
        if ()))
            throw new PlayerInOtherGameException();
       */
        if (game.getPlayers().size() > game.getMaxPlayers())
            throw new FullGameException();
        if (game.getPlayers().stream().flatMap(player -> player.getHeroes() != null ? player.getHeroes().stream().map(HeroInGame::getHero) : null)
            .filter(Objects::nonNull).collect(Collectors.groupingBy(Hero::getName)).values().stream().anyMatch(l -> l.size() > 1))
            throw new HeroAlreadyChosenException();
        if (game.getPhase() == null) game.setPhase(Phase.START);
        if (game.getStartDate() == null) game.setStartDate(LocalDateTime.now());
        if (game.getPassword().isEmpty()) game.setAccessibility(Accessibility.PUBLIC);
        else game.setAccessibility(Accessibility.PRIVATE);
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
    public Turn findTurnById(int turnId) {
        return gameRepository.findByTurn(turnId);
    }

}
