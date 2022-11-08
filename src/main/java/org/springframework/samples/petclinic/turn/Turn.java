package org.springframework.samples.petclinic.turn;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.effect.Phase;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Turn extends BaseEntity {
    private Integer gold;
    private Integer glory;
    private Boolean evasion;
    private Phase phase;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Card> usedCards;
}
