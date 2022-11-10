package org.springframework.samples.petclinic.user;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Tier;
import org.springframework.samples.petclinic.statistic.Statistic;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NotNull
    @Column(unique = true)
    @Size(min = 1, max = 20)
    private String username;

    @NotNull
    @Size(min = 1, max = 20)
    private String password;

    @NotNull
    @Pattern(regexp = "^([10])$") //TODO revisar por si falla(bastante probable no se que estoy haciendo)
    private String enable;

    @URL
    private String avatar;

    @Enumerated(EnumType.STRING)
    private Tier tier; // TODO: Poner un valor por defecto.

    // TODO: Decidir tamaño.
    @NotBlank
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private String authority;

    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<User> friends;

    @OneToOne(cascade = CascadeType.ALL)
    private Statistic statistic;
    }
