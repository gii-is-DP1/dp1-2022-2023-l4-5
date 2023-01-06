package org.springframework.samples.nt4h.player;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.nt4h.action.Phase;
import org.springframework.samples.nt4h.card.ability.Ability;
import org.springframework.samples.nt4h.card.ability.AbilityInGame;
import org.springframework.samples.nt4h.card.ability.Deck;
import org.springframework.samples.nt4h.card.hero.HeroInGame;
import org.springframework.samples.nt4h.card.hero.Role;
import org.springframework.samples.nt4h.game.Game;
import org.springframework.samples.nt4h.game.exceptions.FullGameException;
import org.springframework.samples.nt4h.model.NamedEntity;
import org.springframework.samples.nt4h.player.exceptions.RoleAlreadyChosenException;
import org.springframework.samples.nt4h.statistic.Statistic;
import org.springframework.samples.nt4h.turn.Turn;
import org.springframework.samples.nt4h.user.User;

import javax.persistence.*;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "players")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Player extends NamedEntity implements Jsonable {
    private Boolean hasEvasion;
    @OneToOne(cascade = CascadeType.ALL)
    private Statistic statistic;
    private Integer sequence;
    private Phase nextPhase;
    private Boolean ready;
    private Boolean host;
    private Integer wounds;
    private Integer damageProtect;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private List<HeroInGame> heroes;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Turn> turns;

    @ManyToOne(cascade = CascadeType.ALL)
    private Game game;
    @OneToOne(cascade = CascadeType.ALL)
    private Deck deck;


    public static Player createPlayer(User user, Game game, Boolean host) throws FullGameException {
        Player player = Player.builder()
                .birthDate(user.getBirthDate())
                .host(host)
                .game(game)
                .build();
        player.defaultPlayer();
        player.setName(user.getUsername());
        user.setPlayer(player);
        user.setGame(game);
        player.setGame(game);
        game.addPlayer(player);
        return player;
    }

    public void defaultPlayer() {
        sequence = -1;
        nextPhase = Phase.START;
        ready = false;
        statistic = Statistic.createStatistic();
        wounds = 0;
        damageProtect = 0;
        hasEvasion = true;
        turns = Lists.newArrayList();
        deck = Deck.createEmptyDeck();
        heroes = Lists.newArrayList();
    }


    public Turn getTurn(Phase phase) {
        return this.turns.stream()
            .filter(turn -> turn.getPhase().equals(phase))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No turn found for phase " + phase));
    }

    public void addHero(HeroInGame hero) throws RoleAlreadyChosenException {
        if (heroes == null) heroes = Lists.newArrayList(hero);
        else if (hasRoleAlreadyBeenChosen(hero.getHero().getRole()))
            throw new RoleAlreadyChosenException();
        else heroes.add(hero);
    }

    public boolean hasRoleAlreadyBeenChosen(Role role) {
        return heroes.stream().anyMatch(hero -> hero.getHero().getRole().equals(role));
    }

    public void onDeleteSetNull() {
        heroes.forEach(HeroInGame::onDeleteSetNull);
        game.setPlayers(game.getPlayers().stream().filter(player -> !player.equals(this)).collect(Collectors.toList()));
        deck.onDeleteSetNull();
        game = null;
    }

    public int getHealth() {
        HeroInGame hero = this.getHeroes().get(0);
    	return hero.getHero().getHealth() - wounds;
    }

    @Override
    public String toJson() {
        JsonObject json = new JsonObject();
        json.put("id", this.getId());
        json.put("heroesInGame", heroes);
        json.put("host", host);
        json.put("name", this.getName());
        json.put("ready", ready);
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
