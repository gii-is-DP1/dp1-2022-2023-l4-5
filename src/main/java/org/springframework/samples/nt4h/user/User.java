package org.springframework.samples.nt4h.user;


import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.nt4h.message.Message;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.Tier;
import org.springframework.samples.nt4h.statistic.Statistic;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

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

    // TODO: Decidir tamaño.
    @NotBlank
    private String description;

    @Enumerated
    private Authority authority;

    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<User> friends;

    @OneToOne(cascade = CascadeType.ALL)
    private Statistic statistic;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private List<Message> sendedMessages; // TODO: Solucionar de una mejor forma.

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver")
    private List<Message> receivedMessages; // TODO: Solucionar de una mejor forma.

    @OneToOne(cascade = CascadeType.ALL)
    private Player player;

    void addFriend(User user) {
        if (friends == null)
            friends = Sets.newHashSet();
        friends.add(user);
    }

    public void removeFriend(User user) {
        friends.remove(user);
    }
}
