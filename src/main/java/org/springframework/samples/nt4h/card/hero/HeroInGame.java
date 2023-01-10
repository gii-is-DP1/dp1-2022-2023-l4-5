package org.springframework.samples.nt4h.card.hero;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import lombok.*;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.player.Player;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import java.io.IOException;
import java.io.Writer;

@Entity
@Getter
@Setter
@Table(name = "heroes_in_game")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class HeroInGame extends BaseEntity implements Jsonable {

    @Max(3)
    private Integer actualHealth; // TODO: quitar en algún momento.

    private Integer effectUsed;

    @ManyToOne(cascade = CascadeType.ALL)
    private Hero hero;

    @ManyToOne(cascade = CascadeType.ALL)
    private Player player;

    public static HeroInGame createHeroInGame(Hero hero, Player player) {
        return HeroInGame.builder().hero(hero).player(player).actualHealth(hero.getHealth())
            .effectUsed(hero.maxUses == -1 ? -1: 0).build();
    }

    public void onDeleteSetNull() {
        this.hero = null;
        this.player = null;
    }

    @Override
    public String toJson() {
        JsonObject json = new JsonObject();
        json.put("hero", hero);
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
