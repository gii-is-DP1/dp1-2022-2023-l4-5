package org.springframework.samples.petclinic.card;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.samples.petclinic.model.NamedEntity;

import org.springframework.samples.petclinic.turn.Turn;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class Card extends NamedEntity {

    @NotEmpty
    @URL
    private String backImage;

    @URL
    @NotEmpty
    private String frontImage;

    @Min(0)
    // @NotNull
    private Integer maxUses;

}
