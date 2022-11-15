package org.springframework.samples.nt4h.card.hero;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.player.Player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

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

    @ManyToOne
    private Hero hero;

    @ManyToOne
    private Player player;
}
