package org.springframework.samples.nt4h.achievement;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.samples.nt4h.model.NamedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

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
    private double threshold;

    @URL
    private String image;

    public String getActualDescription(){
        return description.replace("<THRESHOLD>",String.valueOf(threshold));

    }
}
