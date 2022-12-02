package org.springframework.samples.nt4h.achievement;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.samples.nt4h.model.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "achievements")
public class Achievement extends NamedEntity {

    @NotNull
    @Size(min = 3, max = 255)
    private String description;

    @NotNull
    @Min(value = 0)
    private Integer threshold;

    @URL
    private String image;
}
