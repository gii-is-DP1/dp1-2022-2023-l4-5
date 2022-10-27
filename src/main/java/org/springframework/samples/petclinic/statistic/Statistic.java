package org.springframework.samples.petclinic.statistic;

import org.springframework.samples.petclinic.model.BaseEntity;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;

public class Statistic extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Historic> historics;
}
