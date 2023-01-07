package org.springframework.samples.nt4h.card.hero;

import lombok.AllArgsConstructor;
import org.springframework.samples.nt4h.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class HeroService {
    private HeroRepository heroRepository;
    private HeroInGameRepository heroInGameRepository;

    // Hero
    @Transactional(readOnly = true)
    public Hero getHeroById(Integer id) {
        return heroRepository.findById(id).orElseThrow(() -> new NotFoundException("Hero not found"));
    }



    @Transactional(readOnly = true)
    public List<Hero> getAllHeros() {
        return heroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Hero getHeroByName(String name) {
        return heroRepository.findByName(name).orElseThrow(() -> new NotFoundException("Hero not found"));
    }

    @Transactional(readOnly = true)
    public boolean heroExists(int id) {
        return heroRepository.existsById(id);
    }

    // HeroInGame
    @Transactional(readOnly = true)
    public HeroInGame getHeroInGameById(Integer id) {
        return heroInGameRepository.findById(id).orElseThrow(() -> new NotFoundException("HeroInGame not found"));
    }

    @Transactional(readOnly = true)
    public List<HeroInGame> getAllHeroInGame() {
        return heroInGameRepository.findAll();
    }

    @Transactional
    public void saveHeroInGame(HeroInGame heroInGame) {
        heroInGameRepository.save(heroInGame);
    }

    @Transactional
    public void deleteHeroInGame(HeroInGame heroInGame) {
        heroInGame.onDeleteSetNull();
        heroInGameRepository.save(heroInGame);
        heroInGameRepository.delete(heroInGame);
    }

    @Transactional
    public void deleteHeroInGameById(Integer id) {
        HeroInGame heroInGame = getHeroInGameById(id);
        deleteHeroInGame(heroInGame);
    }

    @Transactional(readOnly = true)
    public boolean heroInGameExists(int id) {
        return heroInGameRepository.existsById(id);
    }
}
