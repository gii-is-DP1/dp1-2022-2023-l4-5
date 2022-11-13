package org.springframework.samples.petclinic.game;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.card.enemy.EnemyInGame;
import org.springframework.samples.petclinic.effect.Phase;
import org.springframework.samples.petclinic.model.NamedEntity;
import org.springframework.samples.petclinic.player.Player;
import org.springframework.samples.petclinic.turn.Turn;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends NamedEntity {

    //DESCOMENAR CUANDO FUFE EL JSP COMPLETO

   // @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate startDate;

   // @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate finishDate;

    //@NotNull
    @Range(min = 1, max = 4)
    private Integer maxPlayers;



    @NotNull
    @Enumerated(EnumType.STRING)
    private Mode mode;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Phase phase;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Accessibility accessibility;

    //@NotNull
    private boolean hasStages;


    @OneToMany
    private List<Turn> turn;

    @OneToMany
    @Size(max = 3)
    private List<EnemyInGame> orcs;

    //@NotNull
    @OneToMany
    private List<EnemyInGame> passiveOrcs;

    @OneToMany(mappedBy = "game")
    private List<Player> players;

    public void addPlayer(Player player){
        if(this.players== null){
            List<Player> ls = new ArrayList<Player>();
            ls.add(player);
            this.setPlayers(ls);
        }else{this.players.add(player);}
    }

}
