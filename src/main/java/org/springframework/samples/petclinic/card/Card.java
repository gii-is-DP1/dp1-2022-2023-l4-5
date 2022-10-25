package org.springframework.samples.petclinic.card;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Setter
@Getter
public class Card extends NamedEntity {
    @NotEmpty
    @URL
    private String backImage;

    @URL
    @NotEmpty
    private String frontImage;

    @NotNull
    @Min(value = 0)
    private Integer timesUsed;
}
