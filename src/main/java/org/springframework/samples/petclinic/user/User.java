package org.springframework.samples.petclinic.user;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Tier;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(unique = true)
    private String username;

    private String password;

    private String enable;

    @URL
    private String avatar;

    private Tier tier;

    @NotBlank
    private String description;

    @NotBlank
    private String authority;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<User> friends;
}
