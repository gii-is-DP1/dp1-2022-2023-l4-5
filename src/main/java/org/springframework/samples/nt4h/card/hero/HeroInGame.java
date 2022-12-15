package org.springframework.samples.nt4h.card.hero;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import lombok.*;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.player.Player;

import javax.persistence.*;
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

    @Column(columnDefinition ="int default 0")
    private Integer effectUsed;

    @ManyToOne(cascade = CascadeType.ALL)
    private Hero hero;

    @ManyToOne(cascade = CascadeType.ALL)
    private Player player;

    public HeroInGame createHero(Hero hero, Player player) {
        return toBuilder().hero(hero).player(player).actualHealth(hero.getHealth()).build();
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
