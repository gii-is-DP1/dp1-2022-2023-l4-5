package org.springframework.samples.nt4h.card.product;

import lombok.Getter;
import lombok.Setter;
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
public class ProductInGame extends NamedEntity {
    // TODO: decidir si necesita un mínimo.
    @Column(columnDefinition = "int default 0")
    @Min(0)
    private Integer timesUsed;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StateProduct stateProduct;

    // El producto.
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    // A que jugador pertenece.
    @ManyToOne(cascade = CascadeType.ALL)
    private Player player;

    // A que partida pertenece el producto.
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Game game;
}
