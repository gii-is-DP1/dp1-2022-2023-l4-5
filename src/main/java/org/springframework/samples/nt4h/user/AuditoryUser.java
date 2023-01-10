package org.springframework.samples.nt4h.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Audited
@Entity
@Getter
@Setter
public class AuditoryUser extends BaseEntity{

    @LastModifiedBy
    private String newCreator;
    @LastModifiedDate
    private LocalDateTime modDate;

    @OneToOne
    @NotAudited
    User user;

    @OneToOne
    @NotAudited
    User userMod;

  //  String username;


}
