package org.springframework.samples.nt4h.player;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.HeroService;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.stereotype.Service;

@DataJpaTest(
    includeFilters = {@Filter({Service.class})}
)
@TestInstance(Lifecycle.PER_CLASS)
public class PlayerTest {
    @Autowired
    protected HeroService heroService;

    public PlayerTest() {
    }

    @Test
    public void shouldRoleAlreadyChosenException() throws FullGameException, RoleAlreadyChosenException, HeroAlreadyChosenException {
        Player player1 = new Player();
        HeroInGame h = new HeroInGame();
        HeroInGame h1 = new HeroInGame();
        h.setHero(this.heroService.getHeroByName("Aranel"));
        h1.setHero(this.heroService.getHeroByName("Taheral"));
        player1.addHero(h);
        Assertions.assertThrows(RoleAlreadyChosenException.class, () -> {
            player1.addHero(h1);
        });
    }
}
