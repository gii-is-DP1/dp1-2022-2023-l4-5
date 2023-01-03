package org.springframework.samples.nt4h.game;

import com.github.cliftonlabs.json_simple.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.nt4h.player.Player;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/games")
@AllArgsConstructor
public class GameResources {

    private final UserService userService;
    private final Integer duration = 20;

    @GetMapping
    public ResponseEntity<String> updateMessages() {
        JsonObject jsonObject = new JsonObject();
        LocalDateTime now = LocalDateTime.now();
        User loggedUser = userService.getLoggedUser();
        if (loggedUser != null && loggedUser.getGame() != null) {
            Game game = loggedUser.getGame();
            jsonObject.put("game", game);
            jsonObject.put("timer", duration - ChronoUnit.SECONDS.between(game.getStartDate(), now));
            jsonObject.put("loggedUser", loggedUser);
        }
        return ResponseEntity.ok(jsonObject.toJson());
    }

    @GetMapping("/ready")
    public ResponseEntity<String> areAllReady() {
        JsonObject jsonObject = new JsonObject();
        Game game = userService.getLoggedUser().getGame(); // NO estoy seguro de que funcione.
        if (game == null || game.getPlayers() == null)
            jsonObject.put("ready", false);
        else
            jsonObject.put("isReady", game.getPlayers().stream().allMatch(Player::getReady));
        return ResponseEntity.ok(jsonObject.toJson());
    }
}
