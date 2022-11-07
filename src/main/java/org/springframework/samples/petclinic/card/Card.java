package org.springframework.samples.petclinic.card;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
@Setter
@Getter
@Table(name = "cards")
public class Card extends NamedEntity {

    @NotEmpty
    @URL
    private String backImage;

    @URL
    @NotEmpty
    private String frontImage;
}
