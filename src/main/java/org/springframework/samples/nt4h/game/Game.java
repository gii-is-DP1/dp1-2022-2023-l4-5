package org.springframework.samples.nt4h.game;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.samples.nt4h.card.enemy.EnemyInGame;
import org.springframework.samples.nt4h.card.hero.Hero;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.game.exceptions.HeroAlreadyChosenException;
import org.springframework.samples.nt4h.model.NamedEntity;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "games")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Game extends NamedEntity implements Jsonable {

    private LocalDateTime startDate;

    private LocalDateTime finishDate;

    @NotNull
    private Integer maxPlayers;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Mode mode;

    public int isUniClass() {
        return mode == Mode.UNI_CLASS ? 1 : 2;
    }

    private String password;

    @Enumerated(EnumType.STRING)
    private Accessibility accessibility;

    private boolean hasStages;

    // Relaciones
    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> alivePlayersInTurnOrder;

    @OneToOne(cascade = CascadeType.ALL)
    private Turn currentTurn;

    @OneToMany(cascade = CascadeType.ALL)
    private List<EnemyInGame> actualOrcs;

    @OneToMany
    private List<EnemyInGame> allOrcsInGame;

    @OneToMany(cascade = CascadeType.ALL)
    private List<EnemyInGame> passiveOrcs;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Player> players;

    @OneToOne(cascade = CascadeType.ALL)
    private Player currentPlayer;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> spectators;

    @OneToOne(cascade = CascadeType.ALL)
    private Statistic statistic;

    public void addPlayer(Player player) throws FullGameException {
        if ((this.players.size()+1) > this.maxPlayers)
            throw new FullGameException();
        this.players.add(player);
    }

    public void addPlayerWithNewHero(Player player, HeroInGame hero) throws HeroAlreadyChosenException, RoleAlreadyChosenException {
        if (isHeroAlreadyChosen(hero.getHero()))
            throw new HeroAlreadyChosenException();
        player.addHero(hero);
    }

    public boolean isHeroAlreadyChosen(Hero hero) {
        return this.players != null && this.players.stream()
            .flatMap(player -> player.getHeroes().stream())
            .anyMatch(h -> h.getHero() == hero);
    }

    public static Game createGame(String name, Mode mode, int maxPlayers, String password) {
        Game game = Game.builder()
            .accessibility(password.isEmpty() ? Accessibility.PUBLIC : Accessibility.PRIVATE)
            .mode(mode)
            .maxPlayers(maxPlayers)
            .password(password)
            .players(Lists.newArrayList())
            .build();
        game.defaultGame();
        game.setName(name);
        return game;
    }

    public void defaultGame() {
        alivePlayersInTurnOrder = Lists.newArrayList();
        actualOrcs = Lists.newArrayList();
        allOrcsInGame = Lists.newArrayList();
        passiveOrcs = Lists.newArrayList();
        players = Lists.newArrayList();
        startDate = LocalDateTime.now();
        spectators = Lists.newArrayList();
    }

    public void onDeleteSetNull() {
        players.forEach(Player::onDeleteSetNull);
        allOrcsInGame.forEach(EnemyInGame::onDeleteSetNull);
    }

    @Override
    public String toJson() {
        JsonObject json = new JsonObject();
        json.put("players", this.players);
        json.put("maxPlayers", this.maxPlayers);
        return json.toJson();
    }

    @Override
    public void toJson(Writer writer) {
        try {
            writer.write(toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
