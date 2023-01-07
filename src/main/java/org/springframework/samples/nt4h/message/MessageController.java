package org.springframework.samples.nt4h.message;

import org.springframework.beans.factory.annotation.Autowired;
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

import javax.servlet.http.HttpSession;
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
        User loggedUser = userService.getLoggedUser();
        model.addAttribute("receiver", username);
        model.addAttribute("chat", new Message());
        model.addAttribute("messages", messageService.getMessageBySenderWithReceiver(loggedUser.getUsername(), username));
        return VIEW_MESSAGE_LIST;
    }

    // Enviar un mensaje.
    @PostMapping("/{username}")
    public String sendMessage(@Valid Message message, @PathVariable String username) {
        User loggedUser = userService.getLoggedUser();
        User receiver = userService.getUserByUsername(username);
        User sender = userService.getUserByUsername(loggedUser.getUsername());
        message.setReceiver(receiver);
        message.setSender(sender);
        message.setTime(LocalDateTime.now());
        messageService.saveMessage(message);
        return PAGE_MESSAGE_WITH;
    }

    @PostMapping("/game")
    public String sendMessage(Message message, HttpSession session) {
        System.out.println("Mensaje: " + message.getContent());
        User loggedUser = userService.getLoggedUser();
        message.setSender(loggedUser);
        message.setTime(LocalDateTime.now());
        message.setGame(loggedUser.getGame());
        messageService.saveMessage(message);
        return "redirect:" + session.getAttribute("url");
    }
}
