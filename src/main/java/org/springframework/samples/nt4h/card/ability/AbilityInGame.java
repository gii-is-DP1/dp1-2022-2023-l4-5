package org.springframework.samples.nt4h.card.ability;

import lombok.*;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.player.Player;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "abilities_in_game")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AbilityInGame extends BaseEntity {


    @Enumerated
    private StateAbility stateAbility;

    @Min(0)
    private Integer timesUsed;

    @Min(0)
    private Integer attack;

    @NotNull
    private boolean isProduct;

    @ManyToOne
    private Ability ability;

    @ManyToOne
    private Player player;

    @ManyToOne
    private ProductInGame productInGame;

    @Enumerated(EnumType.STRING)
    private AbilityCardType abilityCardType;

    public static AbilityInGame fromAbility(Ability ability, Player player) {
        return AbilityInGame.builder()
                .timesUsed(0)
                .attack(ability.getAttack())
                .isProduct(false)
                .ability(ability)
                .player(player)
                .build();
    }

    public static AbilityInGame fromProduct(ProductInGame productInGame, Player player) {
        return AbilityInGame.builder()
                .timesUsed(0)
                .attack(productInGame.getProduct().getAttack())
                .isProduct(true)
                .productInGame(productInGame)
                .player(player)
                .build();
    }
    public void onDeleteSetNull() {
        ability = null;
        player = null;
        if (productInGame != null) {
            productInGame.onDeleteSetNull();
        }
    }
}
