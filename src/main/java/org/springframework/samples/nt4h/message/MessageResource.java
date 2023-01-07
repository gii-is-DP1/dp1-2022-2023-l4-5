package org.springframework.samples.nt4h.message;

import com.github.cliftonlabs.json_simple.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
@RequestMapping("/api/messages")
public class MessageResource {
    private final UserService userService;
    private final MessageService messageService;

    @GetMapping("/{username}")
    public ResponseEntity<String> updateMessages(@PathVariable String username) {
        User loggedUser = userService.getLoggedUser();
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("messages", messageService.getMessageBySenderWithReceiver(loggedUser.getUsername(), username));
        return ResponseEntity.ok(jsonObject.toJson());
    }

    @GetMapping("/game")
    public ResponseEntity<String> gameMessages() {
        JsonObject jsonObject = new JsonObject();
        User loggedUser = userService.getLoggedUser();
        jsonObject.put("messages", messageService.getGameMessages(loggedUser.getGame()));
        return ResponseEntity.ok(jsonObject.toJson());
    }
}
