package org.springframework.samples.petclinic.card.product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.game.Game;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.player.Player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "products_in_game")
public class ProductInGame extends NamedEntity {
    // TODO: decidir si necesita un mínimo.
    @Column(columnDefinition = "int default 0")
    private Integer timesUsed;

    // El producto.
    @ManyToOne
    private Product product;

    // A que jugador pertenece.
    @ManyToOne
    private Player player;

    // A que partida pertenece el producto.
    @ManyToOne
    private Game game;
}
