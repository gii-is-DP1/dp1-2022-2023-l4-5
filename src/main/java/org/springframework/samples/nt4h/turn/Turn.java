package org.springframework.samples.nt4h.turn;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.product.ProductInGame;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.player.Player;

import javax.persistence.*;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Turn extends BaseEntity implements Jsonable {

    @Enumerated
    private Phase phase;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<AbilityInGame> usedAbilities;

    @ManyToOne(cascade = CascadeType.ALL)
    private AbilityInGame currentAbility;

    @ManyToOne(cascade = CascadeType.ALL)
    private EnemyInGame currentEnemy;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<EnemyInGame> usedEnemies;

    @ManyToOne(cascade = CascadeType.ALL)
    private ProductInGame currentProduct;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<ProductInGame> usedProducts;

    @ManyToOne(cascade = CascadeType.ALL)
    private Game game;

    @ManyToOne(cascade = CascadeType.ALL)
    private Player player;

    public void addProduct(ProductInGame product) {
        if (usedProducts == null) {
            usedProducts = Lists.newArrayList();
        }
        usedProducts.add(product);
    }

    public void addEnemy(EnemyInGame attackedEnemy) {
        if(usedEnemies == null) {
            usedEnemies = Lists.newArrayList(attackedEnemy);
        } else {
            usedEnemies.add(attackedEnemy);
        }
    }


    public void addAbility(AbilityInGame usedAbility) {
        if(usedAbilities == null) {
            usedAbilities = Lists.newArrayList(usedAbility);
        } else {
            usedAbilities.add(usedAbility);
        }
    }

    public void OnDeleteSetNull() {
        usedAbilities = null;
        currentAbility = null;
        currentEnemy = null;
        usedEnemies = null;
        game = null;
        player = null;
    }

    @Override
    public String toJson() {
        JsonObject json = new JsonObject();
        json.put("phase", getPhase().toString());
        return json.toJson();
    }

    @Override
    public void toJson(Writer writer) throws IOException {
        try {
            writer.write(toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

