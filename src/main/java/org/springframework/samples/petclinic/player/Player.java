package org.springframework.nt4h.player;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Player extends NamedEntity {

    private Integer gold;
    private Integer glory;
    private Boolean evasion;
    private Integer numOrcsKilled;
    private Integer numWarLordKilled;

}
