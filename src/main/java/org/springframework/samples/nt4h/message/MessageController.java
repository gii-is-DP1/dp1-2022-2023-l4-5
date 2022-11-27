package org.springframework.samples.nt4h.message;

import com.github.cliftonlabs.json_simple.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.nt4h.user.User;
import org.springframework.samples.nt4h.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

@Controller
@RequestMapping("/messages")
public class MessageController {

    // Servicios.
    private final MessageService messageService;
    private final UserService userService;

    // Constantes.
    private final String VIEW_MESSAGE_LIST = "messages/messagesList";
    private final String PAGE_MESSAGE_WITH = "redirect:/messages/{username}";

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    // Obtener el chat
    @GetMapping("/{username}")
    public String initCreationForm(@PathVariable String username, ModelMap model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails ud = null;
        if (principal instanceof UserDetails) {
            ud = ((UserDetails) principal);
        }
        model.addAttribute("receiver", username);
        model.addAttribute("chat", new Message());
        model.addAttribute("messages", messageService.getMessageBySenderWithReceiver(Objects.requireNonNull(ud).getUsername(), username));
        return VIEW_MESSAGE_LIST;
    }

    // Enviar un mensaje.
    @PostMapping("/{username}")
    public String sendMessage(@Valid Message message, @PathVariable String username, BindingResult result) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails ud = null;
        if (principal instanceof UserDetails) {
            ud = ((UserDetails) principal);
        }
        User receiver = userService.getUserByUsername(username);
        User sender = userService.getUserByUsername(Objects.requireNonNull(ud).getUsername());
        message.setReceiver(receiver);
        message.setSender(sender);
        message.setTime(LocalDateTime.now());
        messageService.saveMessage(message);
        return PAGE_MESSAGE_WITH;
    }

    // Actualizar el chat.
    @GetMapping("/update/{username}")
    public ResponseEntity<String> updateMessages(@PathVariable String username) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails ud = null;
        if (principal instanceof UserDetails) {
            ud = ((UserDetails) principal);
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("messages", messageService.getMessageBySenderWithReceiver(Objects.requireNonNull(ud).getUsername(), username).stream()
            .map(Message::toString).toArray());
        return new ResponseEntity<>(jsonObject.toJson(), HttpStatus.OK);
    }
}
