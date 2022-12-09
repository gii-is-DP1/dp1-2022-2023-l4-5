package org.springframework.samples.nt4h.card.product;

import lombok.*;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.model.NamedEntity;
import org.springframework.samples.nt4h.player.Player;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "products_in_game")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductInGame extends NamedEntity {

    @Min(0)
    private Integer timesUsed;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StateProduct stateProduct;

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    private Player player;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Game game;
}
