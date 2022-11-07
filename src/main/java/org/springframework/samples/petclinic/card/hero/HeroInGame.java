package org.springframework.samples.petclinic.card.hero;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "heroes_in_game")
public class HeroInGame extends BaseEntity {

    @NotNull
    private Integer actualHealth;
    @Column(columnDefinition = "default 0")
    private Integer timesUsed;

    @ManyToOne
    private Hero hero;

    @ManyToOne
    private Player player;
}
