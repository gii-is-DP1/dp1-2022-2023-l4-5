package org.springframework.samples.nt4h.card.enemy;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.samples.nt4h.card.Card;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

// Comprobar si los getter y setters funcionan.
@Getter
@Setter
@Entity
@Table(name = "enemies")
public class Enemy extends Card {
    @NotNull
    @Range(min = 2, max = 10)
    private Integer health;

    @Range(min = 0, max = 4)
    private Integer hiddenGlory;

    @Range(min = 0, max = 4)
    private Integer notHiddenGlory;

    @Range(min = 0, max = 4)
    private Integer hiddenGold;

    @Range(min = 0, max = 4)
    private Integer notHiddenGold;

    @NotNull
    private Boolean hasCure;

    @NotNull
    private Boolean lessDamageWizard;

    @NotNull
    private Boolean isNightLord;

    public Integer getGlory() {
        return hiddenGlory + notHiddenGlory;
    }

    public Integer getGold() {
        return hiddenGold + notHiddenGold;
    }
}
