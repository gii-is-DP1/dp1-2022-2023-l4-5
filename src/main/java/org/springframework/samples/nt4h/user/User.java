package org.springframework.samples.nt4h.user;


import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.google.common.collect.Lists;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.samples.nt4h.statistic.Statistic;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements Jsonable {

    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 20)
    private String username;

    @NotNull
    @Size(min = 1, max = 20)
    private String password;

    private String enable;

    @URL
    private String avatar;

    @Enumerated
    private Tier tier;

    @NotBlank
    @Size(min = 0, max = 100)
    private String description;

    private String authority;

    @ManyToOne(cascade = CascadeType.ALL)
    private Game game;

    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> friends;

    @OneToOne(cascade = CascadeType.ALL)
    private Statistic statistic;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private List<Message> sentMessages; // TODO: Solucionar de una mejor forma.

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver")
    private List<Message> receivedMessages; // TODO: Solucionar de una mejor forma.

    @OneToOne(cascade = CascadeType.ALL)
    private Player player;

    public void defaultUser() {
        this.authority = Authority.USER.toString();
        this.enable = "true";
        this.tier = Tier.BRONZE;
        this.friends = Lists.newArrayList();
        this.statistic = Statistic.createStatistic();
    }

    public void onDeleteSetNull() {
        game = null;
        friends = null;
        sentMessages.forEach(Message::onDeleteSetNull);
        receivedMessages.forEach(Message::onDeleteSetNull);
    }

    @Override
    public String toJson() {
        JsonObject json = new JsonObject();
        json.put("player", player);
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
