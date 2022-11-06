package org.springframework.samples.petclinic.card.ability;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class AbilityInGame extends BaseEntity {

    // private Deck deck;
    private Integer timesUsed;

    @ManyToOne
    private Ability ability;

    @ManyToOne
    private Player player;


}
