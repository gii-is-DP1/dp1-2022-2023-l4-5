package org.springframework.samples.nt4h.card;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.samples.nt4h.model.NamedEntity;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
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

    @NotNull
    @Min(-1)
    private Integer maxUses;

}
