package org.springframework.samples.nt4h.turn;

import com.github.cliftonlabs.json_simple.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/turns")
@AllArgsConstructor
public class TurnResource {

    private UserService userService;

    @GetMapping
    public ResponseEntity<String> getTurns() {
        JsonObject json = new JsonObject();
        User loggedUser = userService.getLoggedUser();
        if (loggedUser != null && loggedUser.getGame() != null && userService.getLoggedUser().getGame().getCurrentTurn() != null) {
            json.put("turn", userService.getLoggedUser().getGame().getCurrentTurn());
            json.put("playerInTurn", userService.getLoggedUser().getGame().getCurrentPlayer());
            json.put("loggedPlayer", userService.getLoggedUser().getPlayer());
        }
        return ResponseEntity.ok(json.toJson());
    }
}
