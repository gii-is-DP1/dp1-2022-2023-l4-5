package org.springframework.samples.petclinic.card.ability;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.product.ProductInGame;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "abilities_in_game")
public class AbilityInGame extends BaseEntity {
    @Min(0)
    @Column(columnDefinition = "int default 0")
    private Integer timesUsed;

    @Min(0)
    @NotNull
    private Integer attack;

    @NotNull
    private boolean isProduct;

    @ManyToOne
    private Ability ability;

    @NotNull
    @ManyToOne
    private Player player;

    @ManyToOne
    private ProductInGame productInGame;
}
