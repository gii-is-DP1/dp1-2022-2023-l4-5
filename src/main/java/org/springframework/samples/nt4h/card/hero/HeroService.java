package org.springframework.samples.nt4h.card.hero;

import lombok.AllArgsConstructor;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public void saveHero(Hero hero) {
        heroRepository.save(hero);
    }

    @Transactional
    public void deleteHero(Hero hero) {
        heroRepository.delete(hero);
    }

    @Transactional
    public void deleteHeroById(Integer id) {
        heroRepository.deleteById(id);
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
        heroInGameRepository.delete(heroInGame);
    }

    @Transactional
    public void deleteHeroInGameById(Integer id) {
        heroInGameRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean heroInGameExists(int id) {
        return heroInGameRepository.existsById(id);
    }
}
