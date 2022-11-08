package org.springframework.samples.petclinic.achievement;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "achievements")
public class Achievement extends NamedEntity {

    @NotEmpty
    @Size(min = 3, max = 255)
    private String description;

    @NotNull
    @Min(value = 0)
    private Integer threshold;

    @NotEmpty
    @URL
    private String image;
}
