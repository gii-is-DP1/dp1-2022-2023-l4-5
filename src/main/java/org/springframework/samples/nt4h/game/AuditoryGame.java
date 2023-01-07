package org.springframework.samples.nt4h.game;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.samples.nt4h.model.BaseEntity;

import javax.persistence.Entity;

@Setter
@Getter
@Entity
@Audited

public class AuditoryGame extends BaseEntity {
}
