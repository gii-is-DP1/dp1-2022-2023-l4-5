package org.springframework.samples.petclinic.turn;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.effect.Phase;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.player.Player;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Turn {
    private Integer gold;
    private Integer glory;
    private Boolean evasion;
    private Phase phase;

    @OneToOne
    private Player player;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Card> usedCards;

    @ManyToOne
    private Game game;
}
