package org.springframework.samples.nt4h.card.hero;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import lombok.*;
import org.springframework.samples.nt4h.capacity.Capacity;
import org.springframework.samples.nt4h.card.Card;
import org.springframework.samples.nt4h.card.ability.Ability;

import javax.persistence.*;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "heroes")
public class  Hero extends Card implements Jsonable {

    private Integer health;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Ability> abilities;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Capacity> capacities;

    @Override
    public String toJson() {
        JsonObject json = new JsonObject();
        json.put("name", name);
        return json.toJson();
    }

    @Override
    public void toJson(Writer writer) {
        try {
            writer.write(toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
