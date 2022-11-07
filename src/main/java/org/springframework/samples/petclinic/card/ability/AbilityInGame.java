package org.springframework.samples.petclinic.card.ability;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.product.Product;
import org.springframework.samples.petclinic.card.product.ProductInGame;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "abilities_in_game")
public class AbilityInGame extends BaseEntity {
    @Column(columnDefinition = "int default 0")
    private Integer timesUsed;

    private Integer attack;

    @ManyToOne
    private Ability ability;

    @ManyToOne
    private Player player;

    @ManyToOne
    private ProductInGame productInGame;
}
