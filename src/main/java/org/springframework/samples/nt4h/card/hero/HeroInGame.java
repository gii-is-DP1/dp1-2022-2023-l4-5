package org.springframework.samples.nt4h.card.hero;

import lombok.*;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.player.Player;

import javax.persistence.*;
import javax.validation.constraints.Max;

@Entity
@Getter
@Setter
@Table(name = "heroes_in_game")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class HeroInGame extends BaseEntity {

    @Max(3)
    private Integer actualHealth;

    @Column(columnDefinition ="int default 0")
    private Integer effectUsed;

    @ManyToOne(cascade = CascadeType.ALL)
    private Hero hero;

    @ManyToOne(cascade = CascadeType.ALL)
    private Player player;

    public HeroInGame createHero(Hero hero, Player player) {
        return toBuilder().hero(hero).player(player).actualHealth(hero.getHealth()).build();
    }
}
