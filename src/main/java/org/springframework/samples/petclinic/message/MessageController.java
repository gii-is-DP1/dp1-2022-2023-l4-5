package org.springframework.samples.petclinic.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping("/message/{username}")
    public String initCreationForm(@PathVariable String username, ModelMap model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails ud = null;
        if (principal instanceof UserDetails)
            ud = ((UserDetails) principal);
        model.addAttribute("chat", new Message());
        model.addAttribute("messages", messageService.getBySenderWithReceiver(ud.getUsername(), username));
        return "messages/message";
    }

    @PostMapping("/message/{username}")
    public String sendMessage(@Valid Message message, @PathVariable String username, BindingResult result) {
        System.out.println("MessageController.sendMessage");
        if (result.hasErrors())
            System.out.println("Error");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails ud = null;
        if (principal instanceof UserDetails)
            ud = ((UserDetails) principal);
        User receiver = userService.getByUsername(username);
        User sender = userService.getByUsername(ud.getUsername());
        message.setReceiver(receiver);
        message.setSender(sender);
        messageService.save(message);
        return "redirect:/message/{username}";
    }

    /*
    @GetMapping("/message")
    public ResponseEntity<String> showMessages() {
        System.out.println("MessageController.showMessages");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;
        if (principal instanceof User) {
            user = (User) principal;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("messages", messageService.getBySenderOrReceiver(user.getId()));
        return new ResponseEntity<>(jsonObject.toJson(), HttpStatus.OK);
    }
     */
}
