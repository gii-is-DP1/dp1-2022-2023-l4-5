package org.springframework.samples.petclinic.player;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.petclinic.card.hero.Heroe;
import org.springframework.samples.petclinic.model.NamedEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name= "Player")
public class Player extends NamedEntity {

    //Propiedades
    private Integer gold;
    private Integer glory;
    private Boolean evasion;
    private Integer numOrcsKilled;
    private Integer numWarLordKilled;

    //Getters
    public Integer getGold() { return gold; }
    public Integer getGlory() { return glory; }
    public Boolean getEvasion() { return evasion; }
    public Integer getNumOrcsKilled() { return numOrcsKilled; }
    public Integer getNumWarLordKilled() { return numWarLordKilled; }

    //Setters
    public void setGold(Integer gold) { this.gold = gold; }
    public void setGlory(Integer glory) { this.glory = glory; }
    public void setEvasion(Boolean evasion) { this.evasion = evasion; }
    public void setNumOrcsKilled(Integer numOrcsKilled) { this.numOrcsKilled = numOrcsKilled; }
    public void setNumWarLordKilled(Integer numWarLordKilled) {this.numWarLordKilled = numWarLordKilled; }

    //Relaciones
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Heroe> heroe;
}
