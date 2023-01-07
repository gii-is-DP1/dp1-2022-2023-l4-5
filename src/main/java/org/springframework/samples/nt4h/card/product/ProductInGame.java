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

    public static ProductInGame createProduct(Product product, Player player, Game game) {
        ProductInGame productInGame = ProductInGame.builder()
                .timesUsed(0)
                .stateProduct(StateProduct.IN_SALE)
                .product(product)
                .player(player)
                .game(game)
                .build();
        product.setName(product.getName());
    return productInGame;
    }

    public void onDeleteSetNull() {
        product = null;
        player = null;
        game = null;
    }
}
