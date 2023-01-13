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

    // Hero
    @Transactional(readOnly = true, rollbackFor = NotFoundException.class)
    public Hero getHeroById(Integer id) {
        return heroRepository.findById(id).orElseThrow(() -> new NotFoundException("Hero not found"));
    }

    @Transactional(readOnly = true)
    public List<Hero> getAllHeroes() {
        return heroRepository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = NotFoundException.class)
    public Hero getHeroByName(String name) {
        return heroRepository.findByName(name).orElseThrow(() -> new NotFoundException("Hero not found"));
    }

}
