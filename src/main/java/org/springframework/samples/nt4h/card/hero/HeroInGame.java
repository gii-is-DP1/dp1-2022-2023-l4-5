package org.springframework.samples.nt4h.card.hero;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.player.Player;

import javax.persistence.*;
import javax.validation.constraints.Max;

@Entity
@Getter
@Setter
@Table(name = "heroes_in_game")
public class HeroInGame extends BaseEntity {

    //@NotNull
    @Max(3)
    private Integer actualHealth;

    @Column(columnDefinition ="int default 0")
    private Integer effectUsed;

    @ManyToOne(cascade = CascadeType.ALL)
    private Hero hero;

    @ManyToOne(cascade = CascadeType.ALL)
    private Player player;
}
