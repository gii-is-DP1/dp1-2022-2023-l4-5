package org.springframework.samples.petclinic.card.hero;

import lombok.AllArgsConstructor;
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
    public Optional<Hero> getHeroById(Integer id) {
        return heroRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Hero> getAllHeros() {

        return heroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Hero> getHeroByName(String name) {
        return heroRepository.findByName(name);
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

    // HeroInGame
    @Transactional(readOnly = true)
    public Optional<HeroInGame> getHeroInGameById(Integer id) {
        return heroInGameRepository.findById(id);
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
}
