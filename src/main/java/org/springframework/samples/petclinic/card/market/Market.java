package org.springframework.samples.petclinic.card.market;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Market extends NamedEntity {

    @Range(min = 0, max = 5)
    private Integer amount;
}
