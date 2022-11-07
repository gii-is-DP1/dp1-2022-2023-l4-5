package org.springframework.samples.petclinic.user;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Tier;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NotNull
    @Column(unique = true)
    private String username; // decidir un tamaño.


    private String password; // Decidir un tamaño.

    private String enable;

    @URL
    private String avatar;

    @Enumerated(EnumType.STRING)
    private Tier tier;

    @NotBlank
    private String description;

    @NotBlank
    private String authority;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<User> friends;
    }
