package org.springframework.samples.nt4h.game;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.user.User;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Audited
@Entity
@Getter
@Setter
public class AuditoryGame extends BaseEntity {

    @OneToOne
        @NotAudited
    Game game;
    @OneToOne
        @NotAudited
    User user;
}
