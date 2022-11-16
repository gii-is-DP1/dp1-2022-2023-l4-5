package org.springframework.samples.nt4h.card;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.samples.nt4h.model.NamedEntity;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Getter
@Setter
public class Card extends NamedEntity {

    //@NotNull
    @URL
    private String backImage;

    @URL
    //@NotNull
    private String frontImage;


    //@Min(0)
    @NotNull
    private Integer maxUses;

}
