package org.springframework.samples.petclinic.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.achievement.Achievement;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.player.Tier;
import org.springframework.samples.petclinic.statistic.Statistic;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")

public class User extends NamedEntity {

    //Propiedades
    @Column(unique = true)
    private String username;

    @URL
    private String avatar;

    private Tier tier;

    @NotEmpty
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    //Relaciones
    @OneToMany(cascade = CascadeType.ALL)
    private Set<User> friends;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Achievement> achievements;

    @OneToOne(cascade = CascadeType.ALL)
    private Player players;

    @OneToOne(cascade = CascadeType.ALL)
    private Statistic statistics;

}
