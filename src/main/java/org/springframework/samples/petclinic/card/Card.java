package org.springframework.samples.petclinic.card;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Setter
@Getter
@Table(name = "cards")
public class Card extends NamedEntity {

    @URL
    @NotEmpty
    @Column(name = "frontImage")
    private String frontImage;

    @NotEmpty
    @URL
    @Column(name = "backImage")
    private String backImage;

    @NotNull
    @Min(value = 0)
    @Column(name = "timesUsed")
    private Integer timesUsed;

    @NotNull
    @Min(value = 0)
    private Integer aux;
}
