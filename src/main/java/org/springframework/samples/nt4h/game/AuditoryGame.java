package org.springframework.samples.nt4h.game;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.samples.nt4h.model.BaseEntity;
import org.springframework.samples.nt4h.user.User;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Audited
@Entity
@Getter
@Setter
public class AuditoryGame extends BaseEntity {

    @CreatedBy
    private String creator;
    @CreatedDate
    private LocalDateTime createDate;

    @OneToOne
        @NotAudited
    private Game game;
    @OneToOne
    @NotAudited
    private User user;
}
