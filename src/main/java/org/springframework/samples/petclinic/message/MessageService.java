package org.springframework.samples.petclinic.message;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Message save(Message message) {
        /*
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = null;
        if (principal instanceof User)
            user = (User) principal;
        message.setSender(user);
         */
        message.setTime(LocalDateTime.now());
        return messageRepository.save(message);

    }

    public Message findById(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    public List<Message> getBySenderWithReceiver(String usernameSender, String usernameReceiver) {
        return messageRepository.getBySenderWithReceiver(usernameSender, usernameReceiver);
    }
}
