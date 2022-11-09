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

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotEmpty // TODO: revisar si es necesario.
    @Size(min = 1, max = 20)
    private String password;


    private String enable; // TODO: Revisar si es necesario.

    @URL
    private String avatar;

    // @Enumerated(EnumType.STRING)
    private Tier tier; // TODO: Poner un valor por defecto.

    // TODO: Decidir tamaño.
    @NotBlank
    private String description;

    // TODO: Decidir tamaño.
    //@NotBlank
    private String authority; // TODO: Dar un valor por defecto.

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthDate; // TODO: Revisar si es obligatorio.

    @OneToMany(cascade = CascadeType.ALL)
    private Set<User> friends;
    }
