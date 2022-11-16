package org.springframework.samples.nt4h.game;

import lombok.Getter;
import lombok.Setter;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.model.NamedEntity;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.stage.Stage;
import org.springframework.samples.nt4h.turn.Turn;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends NamedEntity {

    //DESCOMENAR CUANDO FUFE EL JSP COMPLETO

    // @NotNull
    // @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime startDate;

    // @NotNull
    // @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime finishDate;

    //@NotNull
    // @Range(min = 1, max = 4)
    private Integer maxPlayers;



    @NotNull
    @Enumerated(EnumType.STRING)
    private Mode mode;

    // @NotNull
    @Enumerated(EnumType.STRING)
    private Phase phase;

    // @NotNull
    private String password;

    // @NotNull
    @Enumerated(EnumType.STRING)
    private Accessibility accessibility;

    //@NotNull
    private boolean hasStages;


    @OneToMany(cascade = CascadeType.ALL)
    private List<Turn> turn;

    @OneToMany(cascade = CascadeType.ALL)
    @Size(max = 3)
    private List<EnemyInGame> orcs;

    //@NotNull
    @OneToMany(cascade = CascadeType.ALL)
    private List<EnemyInGame> passiveOrcs;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Player> players;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Stage> stage;

    public void addPlayer(Player player){
        if(this.players== null){
            List<Player> ls = new ArrayList<>();
            ls.add(player);
            this.setPlayers(ls);
        }else{this.players.add(player);}
    }

}
