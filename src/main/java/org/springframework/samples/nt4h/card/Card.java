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

    @URL
    private String backImage;

    @URL
    private String frontImage;

    @NotNull
    @Min(-1)
    public Integer maxUses;

}
