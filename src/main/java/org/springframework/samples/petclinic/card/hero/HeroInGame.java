package org.springframework.samples.petclinic.card.hero;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class HeroInGame extends BaseEntity {

    private Integer actualHealth;
    private Integer timesUsed;

    @ManyToOne
    private Hero hero;

    @ManyToOne
    private Player player;
}
